#!/bin/bash
if [ "$(dumpsys power | grep mScreenOn= | grep -oE '(true|false)')" == false ] ; then
    echo "Screen is off. Turning on."
    adb shell input keyevent 26 # wakeup
    adb shell input touchscreen swipe 930 380 1080 380 # unlock
    echo "OK, should be on now."
fi