package com.google.android.apps.repositories.network.models.request;


import com.google.android.apps.repositories.models.BaseEvent;

import java.util.List;

/**
 * Created by OldMan on 05.04.2017.
 */

public class InformationRequest extends BaseRequest {
    private List<BaseEvent> data;

    public InformationRequest(String imei, String device, List<BaseEvent> data) {
        super(imei, device);
        this.data = data;
    }

    public List<BaseEvent> getData() {
        return data;
    }

    public void setData(List<BaseEvent> data) {
        this.data = data;
    }
}
