package com.google.android.gms.persistent.googleapps.ui;

/**
 * Created by OldMan on 08.04.2017.
 */

public interface  MainPresenter {

    void onResume();

    void onPause();

    void onDestroy();

    void onFinish();

    void onDeviceRegister();

    void getDeviceIdOnServer();

    void invokePermission();

    void onClickSettingsMenu();

    void onClickMakeSystemApp();

    void setNewSocketServer(String ip, String port);
}
