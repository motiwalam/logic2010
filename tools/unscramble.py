#!/usr/bin/env python3
"""Decode (or encode) Logic 2010's scrambled data files.

Port of Scrambler.unscramble / Scrambler.scramble (see docs/ARCHITECTURE.md §3).
Each line is enciphered independently with a running-key cipher over a 96-char
alphabet; characters outside the alphabet pass through unchanged.

    tools/unscramble.py FILE...            # print decoded text
    tools/unscramble.py --scramble FILE... # inverse
"""
import sys

ALPHABET = "\t " + "".join(chr(c) for c in range(0x21, 0x7F))
N = len(ALPHABET)
INDEX = {c: i for i, c in enumerate(ALPHABET)}
DEFAULT_KEY = "the Logic Program is protected by international copyright law"


def jmod(a, b):
    """Java's % (sign follows the dividend)."""
    r = abs(a) % abs(b)
    return -r if a < 0 else r


def unscramble(line, key=DEFAULT_KEY):
    out, k = [], 0
    for pos, c in enumerate(line):
        kc = key[pos % len(key)] if key else None
        if kc in INDEX:
            k += INDEX[kc]
        if c in INDEX:
            out.append(ALPHABET[N - 1 - jmod(k - INDEX[c] + N - 1, N)])
            k = INDEX[c]
        else:
            out.append(c)
    return "".join(out)


def scramble(line, key=DEFAULT_KEY):
    out, k = [], 0
    for pos, p in enumerate(line):
        kc = key[pos % len(key)] if key else None
        if kc in INDEX:
            k += INDEX[kc]
        if p in INDEX:
            # find the cipher char that unscrambles to p given the running k
            c = next(ch for ch in ALPHABET if ALPHABET[N - 1 - jmod(k - INDEX[ch] + N - 1, N)] == p)
            out.append(c)
            k = INDEX[c]
        else:
            out.append(p)
    return "".join(out)


def main(argv):
    fn = unscramble
    if argv and argv[0] == "--scramble":
        fn, argv = scramble, argv[1:]
    if not argv:
        print(__doc__, file=sys.stderr)
        return 2
    for path in argv:
        with open(path, encoding="latin-1", newline="") as f:
            for line in f.read().splitlines():
                sys.stdout.write(fn(line) + "\n")
    return 0


if __name__ == "__main__":
    sys.exit(main(sys.argv[1:]))
