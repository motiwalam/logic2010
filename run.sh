#!/usr/bin/env bash
# Runs the locally built program (build/logic.jar) against an existing Logic 2010
# installation's data files:   ./run.sh [INSTALL_DIR]      (default: /data/logic2010)
#
# INSTALL_DIR is a macOS-bundle-style installation, i.e. it contains
# Contents/Resources (the course data files, and the student's work/ folder).
# The program reads and writes that folder exactly like the official build does.
# Note: if the program downloads a core update, it installs it into the installation
# (Contents/Java/logic.jar) and restarts from there, i.e. you're back on the official
# build until you run this script again.
set -euo pipefail
HERE=$(cd "$(dirname "$0")" && pwd)
INSTALL=$(realpath "${1:-/data/logic2010}")
JAR="$HERE/build/logic.jar"
[ -f "$JAR" ] || "$HERE/build.sh"
[ -d "$INSTALL/Contents/Resources" ] || { echo "no Contents/Resources in $INSTALL" >&2; exit 1; }

# logiclx.humnet.ucla.edu serves an incomplete certificate chain; if the installation
# has a fixed-up truststore (certs/truststore.jks), use it. JAVA_TOOL_OPTIONS so that
# processes the program spawns (the updater) inherit it too.
if [ -f "$INSTALL/certs/truststore.jks" ]; then
    export JAVA_TOOL_OPTIONS="${JAVA_TOOL_OPTIONS:-} -Djavax.net.ssl.trustStore=$INSTALL/certs/truststore.jks -Djavax.net.ssl.trustStorePassword=changeit"
fi

cd "$INSTALL"
exec java -Droot.dir="$INSTALL" \
     -Dprog.dir="$INSTALL/Contents/Java" \
     -Dconfig.dir="$INSTALL/Contents/Resources" \
     -Dlink.dir="$INSTALL/Contents/Resources" \
     -jar "$JAR"
