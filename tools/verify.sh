#!/usr/bin/env bash
# Builds from source and checks the result against the original jars:
#   build/logic.jar  vs build/logic-remapped.jar (original/logic.jar with mappings applied)
#   build/loader.jar vs original/loader.jar
# See tools/compare/Compare.java for what "equivalent" means.
set -euo pipefail
cd "$(dirname "$0")/.."
tools/fetch-tools.sh
CP=$(ls tools/asm-*.jar | tr '\n' :)
./build.sh
java -cp "$CP" tools/remap/Remap.java original/logic.jar build/logic-remapped.jar build/ids-check.txt mappings/*.mapping
KNOWN=tools/compare/known-differences.txt
status=0
echo "== logic.jar";  java -cp "$CP" tools/compare/Compare.java build/logic-remapped.jar build/logic.jar --known "$KNOWN" || status=1
echo "== loader.jar"; java -cp "$CP" tools/compare/Compare.java original/loader.jar build/loader.jar --known "$KNOWN" || status=1
exit $status
