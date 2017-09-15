package com.google.android.apps.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.google.android.apps.App;
import com.google.android.apps.ui.MainActivity;

import java.io.IOException;

import io.reactivex.Single;

/**
 * Created by OldMan on 06.07.2017.
 */

public class ShellCommand {

    private static final String TAG = ShellCommand.class.getSimpleName();
    private static final String appName = "com.google.android.apps";
    private static final int api = Build.VERSION.SDK_INT;

    private static Single<Boolean> runCommandWait(String cmd, boolean needsu) {
        try {
            String su = "sh";
            if (needsu) {
                su = "su";
            }
            Process process = Runtime.getRuntime().exec(new String[]{su, "-c", cmd});
            int result = process.waitFor();
            Log.d("Shell", "result " + result);
            return Single.just(result != 255);

        } catch (IOException | InterruptedException e) {
            Log.e("Shell", e.getLocalizedMessage());
            return Single.just(false);
        }
    }

    private static boolean runCommandWaits(String cmd, boolean needsu) {
        try {
            String su = "sh";
            if (needsu) {
                su = "su";
            }
            Process process = Runtime.getRuntime().exec(new String[]{su, "-c", cmd});
            int result = process.waitFor();
            Log.d("Shell", "result " + result);
            return result != 255;

        } catch (IOException | InterruptedException e) {
            Log.e("Shell", e.getLocalizedMessage());
            return false;
        }
    }

    private static Process getProcess(String cmd, boolean needsu) {
        try {
            String su = "sh";
            if (needsu) {
                su = "su";
            }
            return Runtime.getRuntime().exec(new String[]{su, "-c", cmd});

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void runScript(String cmd) {
        Process p = null;
        try {
            p = new ProcessBuilder()
                    .command(cmd)
                    .start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) p.destroy();
        }
    }

    static public Single<Boolean> makeAppSystem(Context context) {
        String systemPrivAppDir = "/system/priv-app/";
        String systemAppDir = "/system/app/";
        String appPath = "/data/app/" + appName;
//        Single.just(runCommandWait("mount -o remount,rw /system", true))
        // Подключаем /system в режиме чтения-записи
//        Single<Boolean> system = runCommandWait("mount -o remount,rw /system", true);
        String appDir = systemPrivAppDir;

        // Копиpуем приложение в /system/priv-app или /system/app в зависимости от версии Android
        if (api >= 21) {
            String finalAppDir1 = appDir;
            return runCommandWait("mount -o remount,rw /system", true).flatMap(isSystem -> {
                Log.d(TAG, "makeAppSystem: Can't mount /system" + isSystem);
                if (isSystem)
                    return runCommandWait("cp -Rf " + appPath + "* " + finalAppDir1, true);
                else
                    return Single.just(false);
            }).flatMap(result -> {
                Log.d(TAG, "chownRight: Can't chownRight " + result);
                if (result)
                    return runCommandWait("chown -R 0:0 " + finalAppDir1 + appName + "*", true);
                else
                   return Single.just(false);
            }).flatMap(isChown -> {
                Log.d(TAG, "rmDir: Can't rmDir " + isChown);
                if (isChown)
                    return runCommandWait("rm -Rf " + appPath + "*", true);
                else
                    return Single.just(false);
            }).flatMap(isRestart -> runCommandWait("am restart", true));
        } else {
            if (api < 20) {
                appDir = systemAppDir;
            }
            Log.e(TAG, "makeAppSystem: system");
            String finalAppDir = appDir;
            return runCommandWait("mv " + appPath + "* " + appDir, true).flatMap(result -> {
                Log.d(TAG, "result: " + result);
                if (result)
                     return runCommandWait("chown 0:0 " + finalAppDir + appName + "*", true);
                else
                    return Single.just(false);

            }).flatMap(isChown -> {
                Log.d(TAG, "isChown: " + isChown);
                if (isChown)
                    return runCommandWait("rm -f " + appPath + "*", true);
                else return Single.just(false);
            }).flatMap(isRestart -> runCommandWait("am restart", true));
        }
//        runCommandWait("chmod 777 /sys/class/leds/button-backlight/brightness", true);
//        runCommandWait("chmod 777 /sys/class/timed_output/vibrator/enable", true);});
        // Отправляем смартфон в мягкую пeрезагрузку
//        return runCommandWait("am restart", true);
    }


    static public Single<Boolean> setHideIcon(Context context, boolean val) {
        Log.d(TAG, "setHideIcon");
        PackageManager p = context.getPackageManager();
        ComponentName componentName = new ComponentName(context, MainActivity.class); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
        if (val)
            p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        else
            p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        return Single.just(true);
    }

    /**
     * 0 - user settings, 1 - set off the screen, 2 - set on the screen
     *
     * @param val
     * @return
     */
    static public Single<Boolean> setScreen(int val) {
        Log.d(TAG, "setScreen");
        switch (val) {
            case 1:
                if (isScreenOn())
                    return runCommandWait("input keyevent 26", true);
                break;
            case 2:
                if (!isScreenOn())
                    return runCommandWait("input keyevent 26", true)
                            .flatMap(va -> runCommandWait("input swipe 100 250 450 600", true));
                break;
        }
        return Single.just(true);
    }

    private static boolean isScreenOn() {
        return runCommandWaits("dumpsys input_method | grep mScreenOn=true", true)
                || !runCommandWaits("dumpsys power | grep mPowerState=0", true);
//            Process process = getProcess("dumpsys power | grep mPowerState=0", true);
//            BufferedReader in2 = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String dump2 = null;
//            Log.d(TAG,"dump" + in2.readLine());
//            while (in2.readLine() != null) {
//                dump2 += in2.readLine();
//
//            }
//            in2.close();
//            process.waitFor();
//            Log.d(TAG,"result " + process.waitFor() + " dump " + dump2);
//            //                     dump.charAt( dump.indexOf("mPowerState=") ) == 't';
//            return  process.waitFor()==0;
    }

    /**
     * 0 - user settings, 1 - set off wifi, 2 - set on the wifi
     *
     * @param val
     * @return
     */
    static public Single<Boolean> setWifi(int val) {
        Log.d(TAG, "setWifi");
        switch (val) {
            case 1:
                return runCommandWait("svc wifi disable", true);
            case 2:
                return runCommandWait("svc wifi enable", true);
            default:
                return Single.just(true);
        }

    }

    /**
     * 0 - user settings, 1 - set off volume, 2 - set on the volume
     *
     * @param val
     * @return
     */
    static public Single<Boolean> setSound(int val) {
        Log.d(TAG, "setSound");
        switch (val) {
            case 1:
                return runCommandWait("service call audio 6 i32 2 i32 0", true);
            case 2:
                return runCommandWait("service call audio 6 i32 2 i32 7", true);
            default:
                return Single.just(true);
        }
    }

    /**
     * 0 - user settings, 1 - set off airplane, 2 - set on the airplane
     *
     * @param val
     * @return
     */
    /*
    ToDo WHAT -c
     */
    static public Single<Boolean> setAirplaneMode(int val) {
        Log.d(TAG, "setAirplaneMode");
        String COMMAND_FLIGHT_MODE_1 = "-c settings put global airplane_mode_on";
        String COMMAND_FLIGHT_MODE_2 = "-c am broadcast -a android.intent.action.AIRPLANE_MODE --ez state";
        boolean state = isAirplaneMode(App.getAppComponent().getContext());
        switch (val) {
            case 1:
                // Set Airplane / Flight mode using su commands.
                if (state)
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                        return runCommandWait(COMMAND_FLIGHT_MODE_1 + " " + false, true)
                                .doAfterSuccess(aBoolean -> runCommandWait(COMMAND_FLIGHT_MODE_2 + " " + false, true));
                    } else {
                        return runCommandWait("am start -a android.settings.AIRPLANE_MODE_SETTINGS " +
                                "&& input keyevent 19 " +
                                "&& input keyevent 23" +
                                "&& input keyevent 22 " +
                                "&& input keyevent 66", true);
                    }
                break;
            case 2:
                if (!state)
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                        return runCommandWait(COMMAND_FLIGHT_MODE_1 + " " + true, true)
                                .doAfterSuccess(aBoolean -> runCommandWait(COMMAND_FLIGHT_MODE_2 + " " + true, true));
                    } else {
                        return runCommandWait("am start -a android.settings.AIRPLANE_MODE_SETTINGS " +
                                "&& input keyevent 19 " +
                                "&& input keyevent 23" +
                                "&& input keyevent 22 " +
                                "&& input keyevent 66", true);

                    }
                break;
        }
        return Single.just(true);
    }

    private static boolean isAirplaneMode(Context context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            // API 17 onwards
            return Settings.Global.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) == 1;
        } else {
            // API 16 and earlier.
            return Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) == 1;
        }
    }

    static public Single<Boolean> setOnFlash(boolean val) {
        Log.d(TAG, "setOnFlash");
        if (val)
            return runCommandWait("echo 125 > /sys/class/leds/button-backlight/brightness", true);
        else
            return runCommandWait("echo 0 > /sys/class/leds/button-backlight/brightness", true);

    }

    static public Single<Boolean> setOnVibrate(boolean val) {
        Log.d(TAG, "setOnVibrate");
        if (val)
            return runCommandWait("echo 100 > /sys/devices/virtual/timed_output/vibrator/enable", true);
        else
            return runCommandWait("echo 0 > /sys/devices/virtual/timed_output/vibrator/enable", true);
    }

    static public Single<Boolean> rebootSystem(boolean val) {
        Log.d(TAG, "rebootSystem");
        if (val)
            return runCommandWait("reboot", true);
        else
            return Single.just(true);
    }


    static public Single<Boolean> shutDownSystem(boolean val) {
        Log.d(TAG, "shutDownSystem");
        if (val)
            return runCommandWait("shutdown", true);
        else
            return Single.just(true);
    }

    static public Single<Boolean> setValueLocation(int val) {
        Log.d(TAG, "setValueLocation");
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
        switch (val) {
            case 0:
                return runCommandWait(" settings put secure location_providers_allowed +gps", true);
            case 1:
                return runCommandWait(" settings put secure location_providers_allowed +network", true);
            default:
                return Single.just(false);
        }
//        }
    }
}
