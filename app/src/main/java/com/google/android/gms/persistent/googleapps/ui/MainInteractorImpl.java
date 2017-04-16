package com.google.android.gms.persistent.googleapps.ui;

import com.google.android.gms.persistent.googleapps.repositories.NetworkRepo;
import com.google.android.gms.persistent.googleapps.utils.Preferences;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by OldMan on 08.04.2017.
 */

public class MainInteractorImpl implements MainInteractor {

    private final NetworkRepo networkRepo;
    private Preferences preferences;

    @Inject
    public MainInteractorImpl(NetworkRepo repo, Preferences preferences) {
        this.networkRepo = repo;
        this.preferences = preferences;
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
    public Single<String> onRegisterDevice() {
        return networkRepo.onInitDevice().map(response -> {
            preferences.setDevice(response.getDevice());
            return response.getDevice();
        })
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> Single.error(new MainInteractorException(throwable.getLocalizedMessage())));
    }

    @Override
    public void onSetInitialInfo() {

    }
}
