package com.google.android.gms.persistent.googleapps.repositories;

import com.google.android.gms.persistent.googleapps.network.models.request.DeleteRequest;
import com.google.android.gms.persistent.googleapps.network.models.request.InformationRequest;
import com.google.android.gms.persistent.googleapps.network.models.request.InitialRequest;
import com.google.android.gms.persistent.googleapps.network.models.request.SyncRequest;
import com.google.android.gms.persistent.googleapps.network.models.response.DeleteResponse;
import com.google.android.gms.persistent.googleapps.network.models.response.InformationResponse;
import com.google.android.gms.persistent.googleapps.network.models.response.InitialResponse;
import com.google.android.gms.persistent.googleapps.network.models.response.SyncResponse;

import io.reactivex.Observable;

/**
 * Created by OldMan on 04.04.2017.
 */

public interface NetworkRepo {

    Observable<InitialResponse> onInitDevice(InitialRequest request);

    Observable<SyncResponse> onStateSync(SyncRequest request);

    Observable<DeleteResponse> onDeleteDevice(DeleteRequest request);

    Observable<InformationResponse> setDataOfDevice(InformationRequest request);


}
