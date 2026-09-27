#!/usr/bin/env bash
# Runs Logic 2010 from this repository.
#
#   ./run.sh --local [--syntax 1|2]   fully offline: no server, no registration; all
#                                     modules and problems available to a local user
#   ./run.sh                          normal mode: the program talks to the course
#                                     server (institution/course selection, registration,
#                                     updates, submissions, backups)
#   ./run.sh --install DIR            normal mode against an existing installation's
#                                     data and work (DIR contains Contents/Resources)
#
# Options:
#   --home DIR     runtime directory (default: runtime/local or runtime/server here)
#   --syntax 1|2   notation for --local mode (default: the one in data/ghost.txt, i.e. 1)
#
# The runtime directory is laid out like the macOS app bundle the program expects:
#   Contents/Resources  course data (copied from data/, newer files win) + work/
#   Contents/Java       the jars built by ./build.sh
# Student work lives in Contents/Resources/work and survives rebuilds.
set -euo pipefail
REPO=$(cd "$(dirname "$0")" && pwd)

LOCAL=0 SYNTAX= HOME_DIR= INSTALL=
while [ $# -gt 0 ]; do
    case "$1" in
        --local) LOCAL=1 ;;
        --syntax) SYNTAX=${2:?--syntax needs 1 or 2}; shift ;;
        --home) HOME_DIR=${2:?--home needs a directory}; shift ;;
        --install) INSTALL=${2:?--install needs a directory}; shift ;;
        -h|--help) sed -n '2,19p' "$0" | sed 's/^# \{0,1\}//'; exit 0 ;;
        *) echo "unknown option: $1 (see --help)" >&2; exit 2 ;;
    esac
    shift
done
case "$SYNTAX" in ''|1|2) ;; *) echo "--syntax must be 1 or 2" >&2; exit 2 ;; esac

command -v java >/dev/null || { echo "java not found; install a JDK (8 or newer)" >&2; exit 1; }
[ -f "$REPO/build/logic.jar" ] && [ -f "$REPO/build/loader.jar" ] || "$REPO/build.sh"

JAVA_OPTS=()
if [ -n "$INSTALL" ]; then
    ROOT=$(realpath "$INSTALL")
    [ -d "$ROOT/Contents/Resources" ] || { echo "no Contents/Resources in $ROOT" >&2; exit 1; }
    JAR="$REPO/build/logic.jar"
else
    if [ $LOCAL = 1 ]; then ROOT=${HOME_DIR:-$REPO/runtime/local}; else ROOT=${HOME_DIR:-$REPO/runtime/server}; fi
    mkdir -p "$ROOT/Contents/Resources" "$ROOT/Contents/Java"
    ROOT=$(realpath "$ROOT")
    # Course data: copy files from data/ that are missing or older in the runtime
    # directory. (In normal mode the server may install newer course files; keep those.)
    (cd "$REPO/data" && find . -type f) | while IFS= read -r f; do
        src="$REPO/data/$f" dst="$ROOT/Contents/Resources/$f"
        if [ ! -e "$dst" ] || [ "$src" -nt "$dst" ]; then
            mkdir -p "$(dirname "$dst")" && cp -p "$src" "$dst"
        fi
    done
    cp "$REPO/build/logic.jar" "$REPO/build/loader.jar" "$ROOT/Contents/Java/"
    JAR="$ROOT/Contents/Java/logic.jar"
fi

if [ $LOCAL = 1 ]; then
    JAVA_OPTS+=(-Dlogic.local=true)
    if [ -n "$SYNTAX" ]; then JAVA_OPTS+=(-Dlogic.syntax="$SYNTAX"); fi
else
    # The course server (logiclx.humnet.ucla.edu) sends an incomplete certificate chain.
    # Build a truststore = this JDK's CAs + the missing intermediate (certs/), and pass it
    # via JAVA_TOOL_OPTIONS so processes the program starts (the updater) inherit it.
    TS="$ROOT/truststore.jks"
    if [ ! -f "$TS" ] || [ "$REPO/certs/InCommonRSAServerCA2.pem" -nt "$TS" ]; then
        JH=$(java -XshowSettings:properties -version 2>&1 | sed -n 's/^ *java.home = //p')
        CACERTS="$JH/lib/security/cacerts"; [ -f "$CACERTS" ] || CACERTS="$JH/jre/lib/security/cacerts"
        rm -f "$TS"
        keytool -importkeystore -noprompt -srckeystore "$CACERTS" -srcstorepass changeit \
                -destkeystore "$TS" -deststoretype PKCS12 -deststorepass changeit >/dev/null 2>&1
        keytool -importcert -noprompt -alias incommon-rsa-server-ca2 -file "$REPO/certs/InCommonRSAServerCA2.pem" \
                -keystore "$TS" -storepass changeit >/dev/null
    fi
    export JAVA_TOOL_OPTIONS="${JAVA_TOOL_OPTIONS:-} -Djavax.net.ssl.trustStore=$TS -Djavax.net.ssl.trustStorePassword=changeit"
fi

cd "$ROOT"
exec java "${JAVA_OPTS[@]}" \
     -Droot.dir="$ROOT" \
     -Dprog.dir="$ROOT/Contents/Java" \
     -Dconfig.dir="$ROOT/Contents/Resources" \
     -Dlink.dir="$ROOT/Contents/Resources" \
     -jar "$JAR"
