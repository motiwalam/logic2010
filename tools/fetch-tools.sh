#!/usr/bin/env bash
# Downloads the deobfuscation tools (only needed to regenerate src/ from original/,
# not to build the program).
set -euo pipefail
cd "$(dirname "$0")"
MAVEN=https://repo1.maven.org/maven2
for a in org/vineflower/vineflower/1.11.1/vineflower-1.11.1.jar \
         org/ow2/asm/asm/9.8/asm-9.8.jar \
         org/ow2/asm/asm-commons/9.8/asm-commons-9.8.jar \
         org/ow2/asm/asm-tree/9.8/asm-tree-9.8.jar \
         org/ow2/asm/asm-util/9.8/asm-util-9.8.jar \
         org/ow2/asm/asm-analysis/9.8/asm-analysis-9.8.jar; do
    f=$(basename "$a")
    [ -f "$f" ] || curl -sSfLO "$MAVEN/$a"
done
