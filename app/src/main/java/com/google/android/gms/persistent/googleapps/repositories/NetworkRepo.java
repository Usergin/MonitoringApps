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

    Observable<SyncResponse> onStateSync(SyncRequest request);

    Observable<DeleteResponse> onDeleteDevice(DeleteRequest request);

    Single<InformationResponse> setListDataOfDevice(List<Data> dataList);

    Single<InformationResponse> setDataOfDevice(Data dataList);
}
