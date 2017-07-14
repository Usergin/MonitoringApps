package com.google.android.apps.ui;

/**
 * Created by OldMan on 08.04.2017.
 */

public interface MainView {
    void showProgress();

    void hideProgress();

    void showSettingsDialog(String ip, String port);

    void showSnackBar(String message, String action);

    void startService();

    void hideButton();

    void showButton();

    void killActivity();


}
