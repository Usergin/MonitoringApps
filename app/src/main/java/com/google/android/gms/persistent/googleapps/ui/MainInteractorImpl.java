package com.google.android.gms.persistent.googleapps.ui;

import com.google.android.gms.persistent.googleapps.repositories.NetworkRepo;

import javax.inject.Inject;

/**
 * Created by OldMan on 08.04.2017.
 */

public class MainInteractorImpl implements MainInteractor{

    private final NetworkRepo repo;
    @Inject
    public MainInteractorImpl(NetworkRepo repo) {
        this.repo = repo;
    }

    @Override
    public void createAboutDev(int id, OnAboutDeviceListener listener) {

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
    public void onSetIdAccount(int id, OnAboutDeviceListener listener) {

    }

    @Override
    public void onSetInitialInfo() {

    }
}
