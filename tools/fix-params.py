#!/usr/bin/env python3
"""Works around a Vineflower variable-renaming bug.

When a parameter's generated name equals a field name, Vineflower renames the
*uses* inside the body to `<name>x` but leaves the declaration as `<name>`:

    FormulaParseNode(Expression expression) { this.expression = expressionx; }

This script compiles the tree, finds "cannot find symbol: variable Nx" (and
"package Nx does not exist", the same thing before a `.`), and renames the
parameter declaration `N` -> `Nx` in the enclosing signature. Repeats until
javac reports no more such errors.

Usage: tools/fix-params.py SRC_DIR
"""
import os
import re
import subprocess
import sys
import tempfile

ERR = re.compile(r"^(.*\.java):(\d+): error: (cannot find symbol|package (\w+) does not exist)")
SYM = re.compile(r"symbol:\s+variable (\w+)")


def compile_errors(src):
    files = [os.path.join(d, f) for d, _, fs in os.walk(src) for f in fs if f.endswith(".java")]
    with tempfile.TemporaryDirectory() as out, tempfile.NamedTemporaryFile("w", suffix=".txt", delete=False) as lst:
        lst.write("\n".join(files))
        lst.flush()
        p = subprocess.run(["javac", "-Xmaxerrs", "100000", "-nowarn", "-encoding", "UTF-8", "-d", out, "@" + lst.name],
                           capture_output=True, text=True)
    os.unlink(lst.name)
    lines = p.stderr.splitlines()
    found = []
    for i, l in enumerate(lines):
        m = ERR.match(l)
        if not m:
            continue
        name = m.group(4)
        if name is None:
            for l2 in lines[i + 1:i + 6]:
                s = SYM.search(l2)
                if s:
                    name = s.group(1)
                    break
        if name and name.endswith("x") and len(name) > 1:
            found.append((m.group(1), int(m.group(2)), name))
    return found


def find_signature(src, line, name):
    """Index of the nearest signature line at/above `line` declaring parameter name[:-1]."""
    decl = declaration(name)
    for i in range(line - 1, -1, -1):
        stripped = src[i].strip()
        if re.match(r"(if|else|for|while|switch|catch|synchronized|try|do|return|\}|new\b)", stripped) or " new " in stripped:
            continue
        if re.search(r"\)\s*(throws [\w., ]+)?\s*\{\s*$", src[i]):
            # first signature above the error: it must be the enclosing one
            return i if decl.search(src[i]) else None
    return None


def declaration(name):
    return re.compile(r"(\(|,\s*)((?:final\s+)?[\w.$<>\[\], ?]+?\s+)" + re.escape(name[:-1]) + r"(?=\s*[,)])")


def main(src):
    for _ in range(10):
        targets = {}
        for path, line, name in compile_errors(src):
            with open(path, encoding="utf-8") as f:
                lines = f.read().split("\n")
            i = find_signature(lines, line, name)
            if i is not None:
                targets[(path, i)] = name
        if not targets:
            break
        for (path, i), name in sorted(targets.items()):
            with open(path, encoding="utf-8") as f:
                lines = f.read().split("\n")
            lines[i] = declaration(name).sub(lambda m: m.group(1) + m.group(2) + name, lines[i], count=1)
            with open(path, "w", encoding="utf-8") as f:
                f.write("\n".join(lines))
        print(f"fix-params: renamed {len(targets)} parameter declaration(s)")


if __name__ == "__main__":
    main(sys.argv[1])
