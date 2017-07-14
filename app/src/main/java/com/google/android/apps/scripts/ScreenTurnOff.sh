#!/bin/bash
if [ "$(adb shell dumpsys power | grep mScreenOn= | grep -oE '(true|false)')" == true ] ; then
    echo "Screen is on."
    echo "Turning off."
    adb shell input keyevent 26 # sleep
fi