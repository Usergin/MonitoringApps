package com.google.android.gms.persistent.googleapps.ui;

import com.google.android.gms.persistent.googleapps.data_collection.AboutDevice;
import com.google.android.gms.persistent.googleapps.repositories.network.models.response.BaseResponse;
import com.google.android.gms.persistent.googleapps.repositories.network.models.response.InitialResponse;
import com.google.android.gms.persistent.googleapps.repositories.network.NetworkRepo;
import com.google.android.gms.persistent.googleapps.utils.Preferences;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by OldMan on 08.04.2017.
 */

public class MainInteractorImpl implements MainInteractor {

    private final NetworkRepo networkRepo;
    private Preferences preferences;
    private  AboutDevice aboutDevice;

    @Inject
    public MainInteractorImpl(NetworkRepo repo, Preferences preferences, AboutDevice aboutDevice) {
        this.networkRepo = repo;
        this.preferences = preferences;
        this.aboutDevice = aboutDevice;
    }

    @Override
    public void createAboutDev(int id, OnAboutDeviceListener listener) {

    }

    @Override
    public Single<String> getIpAddress() {
        return preferences.getIpAddress();
    }

    @Override
    public Single<String> setIpAddress(String ip) {
        return preferences.setIpAddress(ip);
    }

    @Override
    public Single<String> getPort() {
        return preferences.getPort();
    }

    @Override
    public Single<String> setPort(String port) {
        return preferences.setPort(port);
    }

    @Override
    public void setFirstPref(OnAboutDeviceListener listener) {

    }

    @Override
    public void onFindIdOnStorage(OnAboutDeviceListener listener) {

    }

    @Override
    public void onSetHideIcon(boolean isHide) {

    }

    @Override
    public Single<InitialResponse> onRegisterDevice() {
        return networkRepo.onInitDevice()
                .doOnSuccess(initialResponse -> preferences.setDevice(initialResponse.getDevice()))
                .onErrorResumeNext(throwable -> Single.error(new MainInteractorException(throwable.getLocalizedMessage())));
    }

    @Override
    public Single<Integer> onSetDeviceInfo() {
        return aboutDevice.onDeviceInfo()
                .flatMap(networkRepo::setDeviceInfo)
                .map(BaseResponse::getCode)
                        .onErrorResumeNext(throwable -> Single.error(new MainInteractorException(throwable.getLocalizedMessage()))
              );
    }
}
