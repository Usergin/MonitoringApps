package com.google.android.gms.persistent.googleapps.network.api;

import javax.inject.Inject;

/**
 * Created by OldMan on 03.04.2017.
 */

public class ServerService {
    @Inject
    private ServerApi driverApi;

    public ServerService(ServerApi driverApi) {
        this.driverApi = driverApi;
    }
//    public Call<StatusOrderResponseModel> setStatusDriverOrder(OrderModelRequest orderModelRequest) {
//        String order = new Gson().toJson(orderModelRequest.getMessage());
//        Timber.d("setStatusDriverOrder " + order + orderModelRequest.getDeviceUuid() + " "
//                + orderModelRequest.getLogin() + " "
//                + orderModelRequest.getDevicePlatform() + " ");
//        return driverApi.getStatusDriverOrder(orderModelRequest.getDeviceUuid(),
//                orderModelRequest.getLogin(), orderModelRequest.getDevicePlatform(),
//                order);
//    }

}
