package com.google.android.gms.persistent.googleapps.network.models.response;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by OldMan on 15.04.2017.
 */

public class RetrofitError extends Throwable {
    public RetrofitError(ResponseBody responseBody) {
        super(getMessage(responseBody));
    }
    /*
    todo set ErrorMessage
     */
    private static String getMessage(ResponseBody responseBody) {
        Log.d(RetrofitError.class.getSimpleName(), responseBody.toString());
        try {
            ErrorResponse errorResponse = new Gson()
                    .fromJson(responseBody.string(), ErrorResponse.class);
            return errorResponse.getError();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBody.toString();
    }
}