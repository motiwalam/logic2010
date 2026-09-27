#!/usr/bin/env python3
"""Convert Logic 2010's original (scrambled) data files to the readable formats.

    tools/convert-data.py LEGACY_DIR OUT_DIR

LEGACY_DIR is an installation's Contents/Resources directory (spirit.txt, ghost.txt,
syntax1/ghoul.txt, ...). OUT_DIR receives the readable data tree used by the program on
the main branch (version.conf, links.conf, options.rec, messages/*.rec,
syntax1/derivation-problems.rec, ...). PDFs are copied unchanged. The launcher files
(maps*.txt), the user's work/ directory and the macOS bits are skipped.

The legacy formats are described in docs/DATA-FORMATS-LEGACY.md, the readable ones in
data/README.md on the main branch. The field names here must match
DataFiles.SCHEMAS in the program (tools/check-data-conversion.sh verifies a conversion
against the program's own reader).
"""
import json
import os
import re
import shutil
import sys

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from unscramble import unscramble  # noqa: E402

# legacy name -> readable name (relative to the same directory)
FILE_ALIASES = [
    ("spirit.txt", "version.conf"),
    ("ghost.txt", "links.conf"),
    ("wraith.txt", "options.rec"),
    ("imp.txt", "derivation-tips.rec"),
    ("spectre.txt", "messages/general.rec"),
    ("zombie.txt", "messages/derivation.rec"),
    ("tomb.txt", "messages/invalidity.rec"),
    ("shade.txt", "messages/parsing.rec"),
    ("demon.txt", "messages/symbolization.rec"),
    ("crypt.txt", "messages/truth-tables.rec"),
    ("troll.txt", "messages/recognition.rec"),
    ("ghoul.txt", "derivation-problems.rec"),
    ("werewolf.txt", "invalidity-problems.rec"),
    ("vampire.txt", "parsing-problems.rec"),
    ("devil.txt", "symbolization-problems.rec"),
    ("mummy.txt", "symbolization-answers.rec"),
    ("warlock.txt", "truth-table-problems.rec"),
    ("goblin.txt", "recognition-problems.rec"),
    ("banshee.txt", "rules.list"),
    ("fiend.txt", "theorems.list"),
]
ALIAS = dict(FILE_ALIASES)

# internal link key -> readable link name (links.conf)
LINK_ALIASES = {
    "derwork.txt": "derivation-problems", "invwork.txt": "invalidity-problems",
    "parwork.txt": "parsing-problems", "symwork.txt": "symbolization-problems",
    "truwork.txt": "truth-table-problems", "recwork.txt": "recognition-problems",
    "symAnswers": "symbolization-answers", "derMessages": "derivation-messages",
    "invMessages": "invalidity-messages", "parMessages": "parsing-messages",
    "symMessages": "symbolization-messages", "truMessages": "truth-table-messages",
    "recMessages": "recognition-messages", "tips": "derivation-tips",
}

SCHEMA_OF = {
    "wraith.txt": "options", "imp.txt": "tips",
    "spectre.txt": "messages", "zombie.txt": "messages", "tomb.txt": "messages",
    "shade.txt": "messages", "demon.txt": "messages", "crypt.txt": "messages",
    "troll.txt": "messages",
    "ghoul.txt": "derivation-problems", "werewolf.txt": "invalidity-problems",
    "vampire.txt": "parsing-problems", "devil.txt": "symbolization-problems",
    "mummy.txt": "symbolization-answers", "warlock.txt": "truth-table-problems",
    "goblin.txt": "recognition-problems",
}

# tag -> (field name, description). Must match DataFiles.SCHEMAS.
COMMON = {
    "$": ("problem", "problem name, as shown in the problem list"),
    "%": ("options", "per-problem options, e.g. eg (a worked example)"),
    "!": ("note", "note shown under the problem title"),
    "C": ("common-name", "common name of the problem (course database)"),
    "o": ("original-name", "name the problem was copied from"),
    "e": ("errors", "error count (saved work)"),
    "t": ("seconds", "time worked, in seconds (saved work)"),
}
SCHEMAS = {
    "derivation-problems": dict(COMMON, **{
        "-": ("show", "a Show line; the first one (written as 'statement') is the argument to derive"),
        "+": ("collapsed-show", "a Show line whose box is collapsed"),
        "<": ("line", "formula of an ordinary line"),
        ">": ("reason", "justification (annotation) of the preceding line"),
        "#": ("cancel", "cancels the current Show line with this justification and closes its box"),
        "=": ("end-box", "closes a box that was not canceled"),
        ":": ("cached-justification", "the program's cached analysis of the preceding line's justification"),
        "s": ("command", "command that created the Show line"),
        "m": ("message", "saved message, 'line:text'"),
        "?": ("flag", "flag (not used by the program)"),
        "p": ("proves", "rule or theorem this problem proves"),
    }),
    "invalidity-problems": dict(COMMON, **{
        "?": ("argument", "the argument to show invalid"),
        "#": ("universe-size", "size of the universe"),
        "=": ("interpretation", "interpretations, separated by '.'"),
        "&": ("workspace", "free-form workspace text (encoded)"),
    }),
    "parsing-problems": dict(COMMON, **{
        "=": ("formula", "the formula to parse"),
        "[": ("notation", "chosen notation: O official, I informal, N not well formed"),
        "]": ("expansion", "which parse-tree nodes are expanded"),
        "*": ("main-connective-answer", "answer in main-connective-only mode"),
    }),
    "symbolization-problems": dict(COMMON, **{
        "-": ("english", "the English sentence"),
        "+": ("node", "a node of the symbolization tree, 'code:English', in pre-order"),
        "=": ("scheme", "scheme of abbreviation, 'symbol:English' items separated by '.'"),
        "@": ("answer-keys", "answer-key names (see symbolization-answers.rec), separated by '.'"),
        "g": ("answer-group", "problems in the same group share their answers"),
        "h": ("hints", "hint count (saved work)"),
    }),
    "symbolization-answers": {
        "$": ("answer-key", "answer key, referred to from symbolization-problems.rec"),
        "-": ("english", "the English sentence"),
        "+": ("node", "a node of the answer's symbolization tree, 'code:English', in pre-order"),
        "e": ("errors", "error count"),
        "t": ("seconds", "time worked, in seconds"),
    },
    "truth-table-problems": dict(COMMON, **{
        "=": ("statement", "the argument or formula"),
        "@": ("row", "work on one table row"),
        "*": ("answer", "answer to the question: 0 yes, 1 no"),
        "#": ("counterexample-row", "index of the row marked as counterexample"),
        "&": ("setup", "setup-stage work"),
    }),
    "recognition-problems": dict(COMMON, **{
        "=": ("argument", "the argument"),
        "*": ("answer", "the student's answer (saved work)"),
        "@": ("correct-rules", "rules that are accepted as correct answers, separated by '.'"),
        "~": ("near-miss-rules", "rules that get a 'nearly right' response"),
        "&": ("comment", "comment shown after a correct answer"),
    }),
    "messages": {
        "s": ("sort", "sort key (editorial, not used by the program)"),
        "n": ("id", "message id, used by the program"),
        "e": ("error", "title of an error message (shown in red)"),
        "E": ("error-revised", "revised error title (not used by the program)"),
        "i": ("info", "title of an information message (shown in gold)"),
        "I": ("info-revised", "revised info title (not used by the program)"),
        "x": ("text", "message text; \\n is a line break, \\l toggles symbol translation, <param> is replaced"),
        "d": ("description", "when the message appears (for the programmer)"),
        "p": ("programmer-note", "comment to the programmer"),
        "b": ("buttons", "buttons: 'Label:action.Label:action;default-index'"),
    },
    "tips": {
        "$": ("text", "text of the next entry (several text fields are concatenated)"),
        "+": ("entry", "creates an entry (collapsed) with the preceding text; the value is its title"),
        "-": ("expanded-entry", "like entry, but initially expanded"),
        "=": ("end", "ends the current level of the outline"),
    },
    "options": {
        "$": ("section", "which part of the program the record configures: logic, derivation, parsing, ..."),
        "+": ("set", "turns an on/off option on"),
        "-": ("unset", "an on/off option that is left off"),
        "?": ("option", "'name:problem-selector', e.g. noErrMess:{\"1.7\",\"1.72\"}"),
    },
}
OPTION_SECTIONS = {
    "logic": {
        "u": ("login", "program login for the course server, 'user:password'"),
        "f": ("font-size", "font size"),
        "c": ("backup-count", "number of server backups to keep"),
        "b": ("backup-name", "name of the server backup set"),
        "r": ("restore-name", "name of the backup set to restore from"),
        "p": ("instance-port", "port used to allow only one running copy"),
        "o": ("option-o", "(not used by the program)"),
    },
    "derivation": {
        "d": ("disable", "rules disabled for the selected problems, 'RULES:selector'"),
        "D": ("disable-all-forms", "like disable, applied to every form of compound rules"),
        "m": ("manual", "rules that must be applied without the program's help"),
        "M": ("manual-all-forms", "like manual, applied to every form of compound rules"),
        "a": ("assume-weakly", "rules/theorems that may be used without proof (weakly assumed)"),
        "A": ("assume", "rules/theorems that may be used without proof"),
    },
    "recognition": {
        "a": ("activate-rules", "rules offered for the selected problems"),
    },
}

HEADERS = {
    "derivation-problems": "Derivation problems",
    "invalidity-problems": "Invalidity problems",
    "parsing-problems": "Parsing problems",
    "symbolization-problems": "Symbolization problems",
    "symbolization-answers": "Symbolization answer keys",
    "truth-table-problems": "Truth-table problems",
    "recognition-problems": "Rule-recognition problems",
    "messages": "Messages",
    "tips": "Derivation tips (an outline)",
    "options": "Options",
}


def parse_tagged(line):
    """TaggedRecord.parse: list of (tag, value)."""
    fields = []
    if not line or line[0] == "#":
        return fields
    s = line[1:] if line[0] == "`" else line
    acc = ""
    while True:
        i = s.find("`")
        if i == -1 or i == len(s) - 1:
            return fields
        c = s[i + 1]
        v = acc + s[:i]
        s = s[i + 2:]
        if c == "`":
            acc = v + c
        else:
            fields.append((c, v))
            acc = ""


def fmt_value(v):
    if v == "":
        return ""
    if v != v.strip() or v.startswith('"'):
        return " " + json.dumps(v, ensure_ascii=False)
    return " " + v


def comment(line):
    if line.startswith("#-"):
        return "## " + line[2:]
    return "#" + line[1:] if line[1:2] == " " else "# " + line[1:]


def header_lines(schema, title):
    out = ["# " + title + ".", "# Format: see data/README.md. Fields:"]
    tables = [SCHEMAS[schema]] + ([OPTION_SECTIONS[s] for s in OPTION_SECTIONS] if schema == "options" else [])
    seen = set()
    for t in tables:
        for tag, (name, desc) in t.items():
            if name in seen:
                continue
            seen.add(name)
            out.append("#   %-24s %s" % (name + ":", desc))
            if schema == "derivation-problems" and tag == "-":
                out.append("#   %-24s %s" % ("statement:", "the first Show line: the argument to derive"))
    return out + [""]


def field_name(schema, tag, section, first_show):
    if schema == "options" and section in OPTION_SECTIONS and tag in OPTION_SECTIONS[section]:
        return OPTION_SECTIONS[section][tag][0]
    if tag in SCHEMAS[schema]:
        name = SCHEMAS[schema][tag][0]
        if schema == "derivation-problems" and tag == "-" and first_show:
            return "statement"
        return name
    return "tag-space" if tag == " " else "tag-" + tag


def convert_records(lines, schema, title):
    out = header_lines(schema, title)
    trim = schema == "messages"   # the program trims every message field it uses
    if schema == "tips":
        return out + convert_tips(lines)
    prev_section = None
    for line in lines:
        if line.strip() == "":
            continue
        if line.startswith("#"):
            if not line.startswith("#-"):
                out.append(comment(line))   # comments may sit inside a record block
                continue
            if out and out[-1] != "" and not out[-1].startswith("##"):
                out.append("")
            out.append(comment(line))
            prev_section = None
            continue
        fields = parse_tagged(line)
        if not fields:
            continue
        section = fields[0][1].strip().lower() if schema == "options" and fields[0][0] == "$" else None
        if schema == "options" and section is not None and section == prev_section:
            fields = fields[1:]             # same section as the previous record: continue its block
            while out and out[-1] == "":
                out.pop()
        else:
            # start a new block: a blank line before it, and before any comments leading into it
            k = len(out)
            while k > 0 and out[k - 1].startswith("#") and not out[k - 1].startswith("##"):
                k -= 1
            if k > 0 and out[k - 1] != "":
                out.insert(k, "")
        prev_section = section
        first_show = True
        for tag, value in fields:
            name = field_name(schema, tag, section, first_show)
            if tag == "-":
                first_show = False
            out.append(name + ":" + fmt_value(value.strip() if trim else value))
        if schema != "options":
            out.append("")
    while out and out[-1] == "":
        out.pop()
    return out


def convert_tips(lines):
    """The tips file is one long record forming an outline; write one block per entry, indented by depth."""
    fields = []
    for line in lines:
        if line.startswith("#"):
            continue
        fields.extend(parse_tagged(line))
    out, depth, block = [], 0, []

    def flush():
        if block:
            out.extend(block)
            out.append("")
            block.clear()

    for tag, value in fields:
        name = SCHEMAS["tips"][tag][0] if tag in SCHEMAS["tips"] else "tag-" + tag
        if tag == "=":
            depth = max(0, depth - 1)
        block.append("  " * depth + name + ":" + fmt_value(value))
        if tag in "+-":
            depth += 1
        elif tag == "=":
            flush()
    flush()
    return out


def convert_list(lines, what):
    out = ["# " + what + ". One per line: NAME, blanks, then the definition.",
           "# '## text' lines are headings shown in the program's lists.", ""]
    names = [l.split(None, 1)[0] for l in lines if l.strip() and not l.startswith("#")]
    width = max(len(n) for n in names) + 2 if names else 8
    for line in lines:
        if line.startswith("#"):
            out.append(comment(line))
        elif line.strip():
            s = line.strip()
            name = re.split(r"\s", s, maxsplit=1)[0]
            body = s[len(name):].strip()
            out.append(name.ljust(width) + body)
    return out


def convert_conf(lines, links):
    out = []
    for line in lines:
        if line.startswith("#") or ":" not in line:
            out.append(line)
            continue
        key, value = line.split(":", 1)
        key, value = key.strip(), value.strip()
        if links:
            key = LINK_ALIASES.get(key, key)
            # point file links at the readable files
            d, _, b = value.rpartition("/")
            if b in ALIAS:
                value = (d + "/" if d else "") + ALIAS[b]
        out.append(key + ": " + value)
    return out


def read_legacy(path):
    with open(path, encoding="latin-1", newline="") as f:
        return [unscramble(l) for l in f.read().splitlines()]


def write(path, lines):
    os.makedirs(os.path.dirname(path) or ".", exist_ok=True)
    with open(path, "w", encoding="utf-8", newline="\n") as f:
        f.write("\n".join(lines) + "\n")


def convert_file(src, dst_dir, name, notation):
    lines = read_legacy(src)
    new = os.path.join(dst_dir, ALIAS[name])
    if name == "spirit.txt":
        write(new, ["# Version of the program's core files."] + convert_conf(lines, False))
    elif name == "ghost.txt":
        write(new, ["# Links: course identity, notation, where the data files are, help documents and server URLs.",
                    "# 'key: value'; ruleDir = syntax1/ or syntax2/ (by notation), linkDir = this directory."]
              + convert_conf(lines, True))
    elif name in ("banshee.txt", "fiend.txt"):
        write(new, convert_list(lines, ("Rules" if name == "banshee.txt" else "Theorems") + notation))
    else:
        schema = SCHEMA_OF[name]
        write(new, convert_records(lines, schema, HEADERS[schema] + notation))
    return new


def main(argv):
    if len(argv) != 2:
        print(__doc__, file=sys.stderr)
        return 2
    src_root, dst_root = argv
    count = 0
    for dirpath, dirnames, filenames in os.walk(src_root):
        rel = os.path.relpath(dirpath, src_root)
        top = rel.split(os.sep)[0]
        if top in ("work", "en.lproj") or top.endswith(".lproj"):
            continue
        notation = " (notation %s)" % top[-1] if top in ("syntax1", "syntax2") else (" (local additions)" if top == "local" else "")
        dst_dir = os.path.normpath(os.path.join(dst_root, rel))
        for name in sorted(filenames):
            src = os.path.join(dirpath, name)
            if name in ALIAS:
                convert_file(src, dst_dir, name, notation)
                count += 1
            elif name.lower().endswith(".pdf"):
                os.makedirs(dst_dir, exist_ok=True)
                shutil.copy2(src, os.path.join(dst_dir, name))
            elif name.startswith("maps") or name.endswith(".icns") or name.startswith("."):
                continue
            else:
                print("skipping unknown file", src, file=sys.stderr)
    print("converted %d files into %s" % (count, dst_root))
    return 0


if __name__ == "__main__":
    sys.exit(main(sys.argv[1:]))
