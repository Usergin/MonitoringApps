package com.google.android.gms.persistent.googleapps.ui;

import com.google.android.gms.persistent.googleapps.network.models.response.BaseResponse;
import com.google.android.gms.persistent.googleapps.network.models.response.InitialResponse;

import io.reactivex.Flowable;
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

    Single<String> onRegisterDevice();

    void onSetInitialInfo();

    interface OnAboutDeviceListener {
        void onSetBaseInfoFinished(boolean result);
        void onVisited(boolean result);
        void onSetIdSuccess();
        void onSetFirstSettings(boolean result);
        void onResultIdOnStorage(int account);
        void onInitialInfoSuccess();
    }
}
