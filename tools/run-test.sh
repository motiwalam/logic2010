#!/usr/bin/env bash
# Runs a logic.jar against a throwaway copy of the installed data files, on a
# virtual X display, so experiments never touch the real installation.
#   tools/run-test.sh JAR [NAME]    (DISPLAY defaults to :77, see README)
set -euo pipefail
JAR=$(realpath "$1"); NAME=${2:-test}
INSTALL=${LOGIC_INSTALL:-/data/logic2010}
ROOT=$(cd "$(dirname "$0")/.." && pwd)/scratch/run-$NAME
rm -rf "$ROOT"; mkdir -p "$ROOT/Contents/Java"
cp -r "$INSTALL/Contents/Resources" "$ROOT/Contents/"
# keep work/ (prefs.txt lives there) so the program starts past the first-run dialog
cp "$JAR" "$ROOT/Contents/Java/logic.jar"
cd "$ROOT"
export DISPLAY=${DISPLAY_TEST:-:77}
export JAVA_TOOL_OPTIONS="-Djavax.net.ssl.trustStore=$INSTALL/certs/truststore.jks -Djavax.net.ssl.trustStorePassword=changeit"
# Offline by default (the copied prefs identify the real user; don't let experiments
# talk to the course server). LOGIC_ONLINE=1 to allow network access.
if [ -z "${LOGIC_ONLINE:-}" ]; then
    JAVA_TOOL_OPTIONS+=" -Dhttp.proxyHost=127.0.0.1 -Dhttp.proxyPort=9 -Dhttps.proxyHost=127.0.0.1 -Dhttps.proxyPort=9 -DsocksProxyHost=127.0.0.1 -DsocksProxyPort=9"
fi
exec java -Droot.dir="$ROOT" -Dprog.dir="$ROOT/Contents/Java" \
     -Dconfig.dir="$ROOT/Contents/Resources" -Dlink.dir="$ROOT/Contents/Resources" \
     -jar "$ROOT/Contents/Java/logic.jar"
