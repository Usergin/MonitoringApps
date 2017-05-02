package com.google.android.gms.persistent.googleapps.network.models.data.event;

import com.google.android.gms.persistent.googleapps.network.models.data.BaseInfo;

/**
 * Created by OldMan on 05.04.2017.
 */

public class Position extends BaseInfo {
    private double longitude, latitude;
    private float accuracy;
    private String provider;
    private long date;

    public Position(double longitude, double latitude, float accuracy, long date, String provider) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.accuracy = accuracy;
        this.date = date;
    }

    private Position(Builder builder) {
        setLongitude(builder.longitude);
        setLatitude(builder.latitude);
        setAccuracy(builder.accuracy);
        setDate(builder.date);
    }

    public static Position.Builder newBuilder() {
        return new Position.Builder();
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public static final class Builder {
        private double longitude;
        private double latitude;
        private float accuracy;
        private long date;
        private String provider;

        public Builder() {
        }

        public Builder longitude(double val) {
            longitude = val;
            return this;
        }

        public Builder latitude(double val) {
            latitude = val;
            return this;
        }

        public Builder accuracy(float val) {
            accuracy = val;
            return this;
        }

        public Builder date(long val) {
            date = val;
            return this;
        }

        public Builder provider(String val) {
            provider = val;
            return this;
        }

        public Position build() {
            return new Position(this);
        }

    }
}
