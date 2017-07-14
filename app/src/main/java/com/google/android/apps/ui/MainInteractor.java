package com.google.android.apps.ui;

import com.google.android.apps.repositories.network.models.response.InitialResponse;

import io.reactivex.Single;

/**
 * Created by OldMan on 08.04.2017.
 */

public interface MainInteractor {
    void createAboutDev(int id, OnAboutDeviceListener listener);

    Single<String> getIpAddress();

    Single<String> setIpAddress(String ip);

    Single<String> getPort();

    Single<String> setPort(String port);

    void setFirstPref(OnAboutDeviceListener listener);

    void onFindIdOnStorage(OnAboutDeviceListener listener);

    void onSetHideIcon(boolean isHide);

    Single<InitialResponse> onRegisterDevice();

    Single<Integer> onSetDeviceInfo();

    interface OnAboutDeviceListener {
        void onSetBaseInfoFinished(boolean result);
        void onVisited(boolean result);
        void onSetIdSuccess();
        void onSetFirstSettings(boolean result);
        void onResultIdOnStorage(int account);
        void onInitialInfoSuccess();
    }
}
