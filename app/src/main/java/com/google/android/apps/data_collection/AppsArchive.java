package com.google.android.apps.data_collection;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.google.android.apps.App;
import com.google.android.apps.repositories.models.BaseEvent;
import com.google.android.apps.repositories.models.events.InstallApp;
import com.google.android.apps.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by OldMan on 19.07.2017.
 */

public class AppsArchive {
    private String TAG = AppsArchive.class.getSimpleName();
    private Context context;

    public AppsArchive() {
        this.context = App.getAppComponent().getContext();
    }

    public Single<List<BaseEvent>> getInstallApps() {
        // -------initial json line----------------------
        List<BaseEvent> appsList = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();
        final List<ApplicationInfo> installedApps = packageManager
                .getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo applicationInfo : installedApps) {
            InstallApp app = new InstallApp();
            try {
                StringBuilder info = new StringBuilder();
                PackageInfo packageInfo = packageManager.getPackageInfo(
                        applicationInfo.packageName, PackageManager.GET_PERMISSIONS);

                info.append("Дата установки:")
                        .append(" ")
                        .append(DateUtils.convertMilliSecondsToFormattedDate(packageInfo.firstInstallTime))
                        .append('\n');
                info.append("Дирректория:")
                        .append(" ")
                        .append(applicationInfo.sourceDir)
                        .append('\n');
                info.append("Версия:")
                        .append(" ")
                        .append(packageInfo.versionCode)
                        .append('\n');
                info.append("Дата установки:")
                        .append(" ")
                        .append(packageInfo.describeContents())
                        .append('\n');
                app.setDate(DateUtils.convertMilliSecondsToFormattedDate(packageInfo.lastUpdateTime));
                app.setName(packageInfo.versionName);
                app.setInfo(info.toString());
                appsList.add(app);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "appsList : " + appsList.size());

        return Single.just(appsList);
    }
}
