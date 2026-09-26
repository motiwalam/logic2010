#!/usr/bin/env bash
# Builds build/logic.jar and build/loader.jar from source. Needs only a JDK (8+).
set -euo pipefail
cd "$(dirname "$0")"
JAVAC_FLAGS=(-encoding UTF-8 -nowarn -Xlint:none)
# Target Java 8 bytecode (the original was built for Java 6, which modern javac can't emit).
if javac --help 2>&1 | grep -q -- '--release'; then JAVAC_FLAGS+=(--release 8); fi

build_jar() {  # name main-class
    local name=$1 main=$2 classes=build/$1-classes
    rm -rf "$classes"; mkdir -p "$classes"
    find "$name/src/main/java" -name '*.java' > "build/$name-sources.txt"
    javac "${JAVAC_FLAGS[@]}" -d "$classes" @"build/$name-sources.txt"
    [ -d "$name/src/main/resources" ] && cp -r "$name/src/main/resources/." "$classes/"
    jar --create --file "build/$name.jar" --main-class "$main" -C "$classes" .
    echo "built build/$name.jar"
}
mkdir -p build
build_jar logic edu.ucla.phil.logic.LogicProgram
build_jar loader edu.ucla.phil.logic.LPUpdateLoader
