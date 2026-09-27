#!/usr/bin/env bash
# Writes the difference between the raw decompiler output (build/pre-patch, produced by
# tools/deobfuscate.sh) and logic/src/main/java as a patch:  tools/make-patch.sh OUT.patch
set -euo pipefail
ROOT=$(cd "$(dirname "$0")/.." && pwd)
OUT=$(realpath -m "$1")
T=$(mktemp -d); trap 'rm -rf "$T"' EXIT
ln -s "$ROOT/build/pre-patch" "$T/a"; ln -s "$ROOT/logic/src/main/java" "$T/b"
(cd "$T" && diff -ruN a/ b/ | sed -E 's/^(---|\+\+\+) ([^\t]+)\t.*/\1 \2/' > "$OUT") || true
echo "wrote $OUT"
