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

public class NetworkRepoImpl implements NetworkRepo {
    @Override
    public Observable<InitialResponse> onInitDevice(InitialRequest request) {
        return null;
    }

    @Override
    public Observable<SyncResponse> onStateSync(SyncRequest request) {
        return null;
    }

    @Override
    public Observable<DeleteResponse> onDeleteDevice(DeleteRequest request) {
        return null;
    }

    @Override
    public Observable<InformationResponse> setDataOfDevice(InformationRequest request) {
        return null;
    }
}
