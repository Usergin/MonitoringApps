package com.google.android.apps.repositories.network.models.request;

import com.google.android.apps.repositories.network.models.data.DeviceInfo;

/**
 * Created by OldMan on 08.07.2017.
 */

public class DeviceInfoRequest extends BaseRequest {
    private DeviceInfo data;

    public DeviceInfoRequest(String imei, String device,DeviceInfo deviceInfo ) {
        super(imei, device);
        this.data = deviceInfo;
    }

    public DeviceInfo getData() {
        return data;
    }

    public void setData(DeviceInfo data) {
        this.data = data;
    }
}
