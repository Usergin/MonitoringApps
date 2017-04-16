package com.google.android.gms.persistent.googleapps.ui;

/**
 * Created by OldMan on 08.04.2017.
 */

public interface MainView {
    void showProgress();

    void hideProgress();

    void showSettingsDialog(String ip, String port);

    void setVisibleSignInButton(boolean isVisible);

    void showSnackBar(String message, String action);

    void hideButton();

    void showButton();

    void killActivity();


}
