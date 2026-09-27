#!/usr/bin/env bash
# Verifies a conversion made by tools/convert-data.py, using the main branch's program
# to read the converted files (see docs/DATA-FORMATS-LEGACY.md §6).
#   tools/check-data-conversion.sh LEGACY_DIR CONVERTED_DIR MAIN_CHECKOUT
set -euo pipefail
[ $# -eq 3 ] || { sed -n '2,4p' "$0"; exit 2; }
HERE=$(cd "$(dirname "$0")" && pwd)
MAIN=$(cd "$3" && pwd)
[ -f "$MAIN/build/logic.jar" ] || "$MAIN/build.sh"
OUT=$(mktemp -d); trap 'rm -rf "$OUT"' EXIT
javac -nowarn -cp "$MAIN/build/logic.jar" -d "$OUT" "$HERE/dataformat/CheckDataConversion.java"
java -cp "$MAIN/build/logic.jar:$OUT" edu.ucla.phil.logic.CheckDataConversion "$1" "$2"
