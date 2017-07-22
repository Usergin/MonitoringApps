package com.google.android.apps.repositories.models.events;

import com.google.android.apps.repositories.models.BaseEvent;
import java.util.Date;

/**
 * Created by OldMan on 05.04.2017.
 */

public class Location extends BaseEvent {
    private double longitude, latitude;
    private float accuracy;
    private Date date;
    private String method;

    public Location(double longitude, double latitude, float accuracy, Date date, String method) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.accuracy = accuracy;
        this.method = method;
        this.date = date;
    }

    private Location(Builder builder) {
        setLongitude(builder.longitude);
        setLatitude(builder.latitude);
        setAccuracy(builder.accuracy);
        setDate(builder.date);
        setMethod(builder.method);
    }

    public static Location.Builder newBuilder() {
        return new Location.Builder();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static final class Builder {
        private double longitude;
        private double latitude;
        private float accuracy;
        private Date date;
        private String method;

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

        public Builder date(Date val) {
            date = val;
            return this;
        }

        public Builder method(String val) {
            method = val;
            return this;
        }

        public Location build() {
            return new Location(this);
        }

    }
}
