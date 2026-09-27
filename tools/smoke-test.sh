#!/usr/bin/env bash
# Starts a logic.jar on a virtual display, presses Return on the startup dialog, and
# saves screenshots plus the list of open windows:  tools/smoke-test.sh JAR NAME
# Needs Xvfb + xdotool + ImageMagick `import` (see README "Testing").
set -uo pipefail
cd "$(dirname "$0")/.."
X=${XVFB_PREFIX:-$HOME/.local/opt/xvfb/usr}
export DISPLAY_TEST=${DISPLAY_TEST:-:77}
if ! pgrep -x Xvfb -a | grep -q " $DISPLAY_TEST"; then
    "$X/bin/Xvfb" "$DISPLAY_TEST" -screen 0 1280x900x24 >/dev/null 2>&1 &
    sleep 2
fi
xdo() { DISPLAY=$DISPLAY_TEST LD_LIBRARY_PATH=$X/lib "$X/bin/xdotool" "$@"; }
shot() { DISPLAY=$DISPLAY_TEST import -window root "$1"; }

timeout 60 tools/run-test.sh "$1" "$2" > "scratch/$2.log" 2>&1 &
PID=$!
sleep 12
shot "scratch/$2-1.png"
xdo key Return; sleep 6            # default button of the startup dialog
shot "scratch/$2-2.png"
xdo search --name '' getwindowname %@ 2>/dev/null | sort -u > "scratch/$2-windows.txt"
kill $PID 2>/dev/null; pkill -P $PID 2>/dev/null
pkill -f "Droot.dir=$(pwd)/scratch/run-$2 " 2>/dev/null
wait 2>/dev/null
echo "screenshots: scratch/$2-1.png scratch/$2-2.png"
