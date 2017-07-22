package com.google.android.apps.repositories.network;


import com.google.android.apps.repositories.models.BaseEvent;
import com.google.android.apps.repositories.models.DeviceInfo;
import com.google.android.apps.repositories.network.models.request.DeleteRequest;
import com.google.android.apps.repositories.network.models.response.DeleteResponse;
import com.google.android.apps.repositories.network.models.response.InformationResponse;
import com.google.android.apps.repositories.network.models.response.InitialResponse;
import com.google.android.apps.repositories.network.models.response.SyncResponse;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by OldMan on 04.04.2017.
 */

public interface NetworkRepo {

    Single<InitialResponse> onInitDevice();

    Single<SyncResponse> onStateSync();

//    Single<InformationResponse> setDeviceInfo(BaseEvent data);

    Single<DeleteResponse> onDeleteDevice(DeleteRequest request);

    Single<InformationResponse> setListDataOfDevice(List<BaseEvent> dataList);

    Single<InformationResponse> setDeviceInfo(DeviceInfo data);

    Single<InformationResponse> addPosition(BaseEvent data);

    Single<InformationResponse> addCall(BaseEvent data);

    Single<InformationResponse> addSms(BaseEvent data);

    Single<InformationResponse> addCallsList(List<BaseEvent> dataList);

    Single<InformationResponse> addSMSList(List<BaseEvent> dataList);

    Single<InformationResponse> setContactsBook(List<BaseEvent> dataList);

    Single<InformationResponse> addListApplications(List<BaseEvent> dataList);

    Single<InformationResponse> addApplication(BaseEvent data);

    Single<InformationResponse> addDeviceStatus(BaseEvent data);

    Single<InformationResponse> addDeviceBatteryState(BaseEvent data);

    Single<InformationResponse> addNetworkState(BaseEvent data);
}
