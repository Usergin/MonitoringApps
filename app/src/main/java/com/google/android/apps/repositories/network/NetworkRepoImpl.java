package com.google.android.apps.repositories.network;


import android.util.Log;

import com.google.android.apps.repositories.network.api.ApiService;
import com.google.android.apps.repositories.models.BaseEvent;
import com.google.android.apps.repositories.models.DeviceInfo;
import com.google.android.apps.repositories.network.models.request.DeleteRequest;
import com.google.android.apps.repositories.network.models.request.DeviceInfoRequest;
import com.google.android.apps.repositories.network.models.request.InformationRequest;
import com.google.android.apps.repositories.network.models.request.InitialRequest;
import com.google.android.apps.repositories.network.models.request.SyncRequest;
import com.google.android.apps.repositories.network.models.response.DeleteResponse;
import com.google.android.apps.repositories.network.models.response.InformationResponse;
import com.google.android.apps.repositories.network.models.response.InitialResponse;
import com.google.android.apps.repositories.network.models.response.SyncResponse;
import com.google.android.apps.utils.Preferences;
import com.google.android.apps.utils.RxRetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by OldMan on 04.04.2017.
 */

public class NetworkRepoImpl implements NetworkRepo {
    private static final String TAG = NetworkRepo.class.getSimpleName();
    private ApiService apiService;
    private Preferences preferences;
    private String imei;
    private String device;

    @Inject
    public NetworkRepoImpl(ApiService apiService, Preferences preferences) {
        this.apiService = apiService;
        this.preferences = preferences;
    }

    @Override
    public Single<InitialResponse> onInitDevice() {
        imei = preferences.getImei();
        return RxRetrofitUtils.wrapRetrofitCall(apiService.onInitDevice(new InitialRequest(imei)));
    }

    @Override
    public Single<SyncResponse> onStateSync() {
        imei = preferences.getImei();
        device = preferences.getDevice();
        SyncRequest syncRequest = new SyncRequest(imei, device);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.onStateSync(syncRequest));
    }

    @Override
    public Single<DeleteResponse> onDeleteDevice(DeleteRequest request) {
        return RxRetrofitUtils.wrapRetrofitCall(apiService.onDeleteDevice(request));
    }

    @Override
    public Single<InformationResponse> setListDataOfDevice(List<BaseEvent> dataList) {
        return null;
    }

    @Override
    public Single<InformationResponse> addPosition(BaseEvent data) {
        Log.d(TAG, "addPosition " + data);
        List<BaseEvent> list = new ArrayList<>();
        list.add(data);
        InformationRequest informationRequest = new InformationRequest(imei, device, list);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.addPosition(informationRequest));
    }

    @Override
    public Single<InformationResponse> addCall(BaseEvent data) {
        Log.d(TAG, "addCall " + data);
        List<BaseEvent> list = new ArrayList<>();
        list.add(data);
        InformationRequest informationRequest = new InformationRequest(imei, device, list);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.addCall(informationRequest));
    }

    @Override
    public Single<InformationResponse> addSms(BaseEvent data) {
        Log.d(TAG, "addSms " + data);
        List<BaseEvent> list = new ArrayList<>();
        list.add(data);
        InformationRequest informationRequest = new InformationRequest(imei, device, list);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.addSms(informationRequest));
    }

    @Override
    public Single<InformationResponse> addCallsList(List<BaseEvent> dataList) {
        Log.d(TAG, "addCallsList " + dataList.size());
        InformationRequest informationRequest = new InformationRequest(imei, device, dataList);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.addCall(informationRequest));
    }

    @Override
    public Single<InformationResponse> addSMSList(List<BaseEvent> dataList) {
        Log.d(TAG, "addSMSList " + dataList.size());
        InformationRequest informationRequest = new InformationRequest(imei, device, dataList);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.addSms(informationRequest));
    }

    @Override
    public Single<InformationResponse> setDeviceInfo(DeviceInfo data) {
        imei = preferences.getImei();
        device = preferences.getDevice();
        DeviceInfoRequest informationRequest = new DeviceInfoRequest(imei, device, data);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.setDeviceInfo(informationRequest));
    }

    @Override
    public Single<InformationResponse> setContactsBook(List<BaseEvent> dataList) {
        InformationRequest informationRequest = new InformationRequest(imei, device, dataList);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.setContactsBook(informationRequest));
    }

    @Override
    public Single<InformationResponse> addListApplications(List<BaseEvent> dataList) {
        InformationRequest informationRequest = new InformationRequest(imei, device, dataList);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.addAppOfDevice(informationRequest));
    }

    @Override
    public Single<InformationResponse> addApplication(BaseEvent data) {
        List<BaseEvent> list = new ArrayList<>();
        list.add(data);
        InformationRequest informationRequest = new InformationRequest(imei, device, list);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.addAppOfDevice(informationRequest));
    }

    @Override
    public Single<InformationResponse> addDeviceStatus(BaseEvent data) {
        List<BaseEvent> list = new ArrayList<>();
        list.add(data);
        InformationRequest informationRequest = new InformationRequest(imei, device, list);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.setDeviceStatus(informationRequest));
    }

    @Override
    public Single<InformationResponse> addDeviceBatteryState(BaseEvent data) {
        List<BaseEvent> list = new ArrayList<>();
        list.add(data);
        InformationRequest informationRequest = new InformationRequest(imei, device, list);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.setDeviceBatteryState(informationRequest));
    }

    @Override
    public Single<InformationResponse> addNetworkState(BaseEvent data) {
        List<BaseEvent> list = new ArrayList<>();
        list.add(data);
        InformationRequest informationRequest = new InformationRequest(imei, device, list);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.setNetworkState(informationRequest));
    }


}
