package com.google.android.gms.persistent.googleapps.network.models.data;

/**
 * Created by OldMan on 05.04.2017.
 */

public class Data {
    private BaseInfo info;
    private int type;
    private long date;

    public Data(BaseInfo info, int type, long date) {
        this.info = info;
        this.type = type;
        this.date = date;
    }

    private Data(Builder builder) {
        setInfo(builder.info);
        setType(builder.type);
        setDate(builder.date);
    }

    public BaseInfo getInfo() {
        return info;
    }

    public void setInfo(BaseInfo info) {
        this.info = info;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public static Data.Builder newBuilder() {
        return new Data.Builder();
    }

    public static final class Builder {
        private BaseInfo info;
        private int type;
        private long date;

        public Builder() {
        }

        public Builder info(BaseInfo val) {
            info = val;
            return this;
        }

        public Builder type(int val) {
            type = val;
            return this;
        }

        public Builder date(long val) {
            date = val;
            return this;
        }

        public Data build() {
            return new Data(this);
        }
    }
}
