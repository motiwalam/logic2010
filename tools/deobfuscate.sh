#!/usr/bin/env bash
# Regenerates logic/src/main/java from original/logic.jar:
#   1. tools/remap renames obfuscated identifiers using mappings/*.mapping
#      (and writes mappings/ids.txt, the id -> original-name listing)
#   2. Vineflower decompiles the remapped jar
#   3. patches/logic/*.patch fix the few places where decompiler output doesn't compile
# WARNING: overwrites logic/src/main/java. Only meaningful while the source is still
# generated; see README.
set -euo pipefail
ROOT=$(cd "$(dirname "$0")/.." && pwd)
cd "$ROOT"
tools/fetch-tools.sh
CP=$(ls tools/asm-*.jar | tr '\n' :)
mkdir -p build
java -cp "$CP" tools/remap/Remap.java original/logic.jar build/logic-remapped.jar mappings/ids.txt mappings/*.mapping

OUT=build/decompiled
rm -rf "$OUT"
java -jar tools/vineflower-1.11.1.jar -dgs=1 -rsy=1 -rbr=1 -lit=1 \
    --variable-renaming=jad --rename-parameters=1 -log=WARN \
    build/logic-remapped.jar "$OUT/"

rm -rf logic/src/main/java logic/src/main/resources
mkdir -p logic/src/main/java logic/src/main/resources
cp -r "$OUT/edu" logic/src/main/java/
cp -r "$OUT/images" "$OUT/fonts" logic/src/main/resources/
for p in patches/logic/*.patch; do
    [ -e "$p" ] || continue
    patch -s -p4 -d logic/src/main/java < "$p" || { echo "patch failed: $p"; exit 1; }
done
echo "done: logic/src/main/java regenerated"
