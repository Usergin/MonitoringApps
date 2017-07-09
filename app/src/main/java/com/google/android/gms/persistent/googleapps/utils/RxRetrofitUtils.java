package com.google.android.gms.persistent.googleapps.utils;

import com.google.android.gms.persistent.googleapps.repositories.network.models.response.RetrofitError;

import java.io.IOException;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by OldMan on 15.04.2017.
 */

public class RxRetrofitUtils {
    public static <T> Single<T> wrapRetrofitCall(final Call<T> call) {
        return Single.create(subscriber -> {
            final Response<T> execute;
            try {
                execute = call.execute();
           } catch (IOException e) {
                subscriber.onError(e);
                return;
            }
            if (execute.isSuccessful()) {
                subscriber.onSuccess(execute.body());
            }
            else {
                subscriber.onError(new RetrofitError(execute.errorBody()));
            }
        });
   }
}
