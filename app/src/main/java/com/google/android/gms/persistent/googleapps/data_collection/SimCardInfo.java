package com.google.android.gms.persistent.googleapps.data_collection;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

import javax.inject.Inject;

/**
 * Created by OldMan on 03.05.2017.
 */

public class SimCardInfo {
    private String imeiSIM1;
    private String imeiSIM2;
    private boolean isSIM1Ready;
    private boolean isSIM2Ready;

    @Inject
    public SimCardInfo(Context context) {
        TelephonyManager telephonyManager = ((TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE));

        setImeiSIM1(telephonyManager.getDeviceId());
        setImeiSIM2(null);

        try {
            setImeiSIM1(getDeviceIdBySlot(context,
                    "getDeviceIdGemini", 0));
            setImeiSIM2(getDeviceIdBySlot(context,
                    "getDeviceIdGemini", 1));
        } catch (TelefonInfoNotFoundException e) {
            e.printStackTrace();

            try {
                setImeiSIM1(getDeviceIdBySlot(context,
                        "getDeviceId", 0));
                setImeiSIM1(getDeviceIdBySlot(context,
                        "getDeviceId", 1));
            } catch (TelefonInfoNotFoundException e1) {
                // Call here for next manufacturer's predicted method name
                // if you wish
                e1.printStackTrace();
            }
        }

        setSIM1Ready(telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY);
        setSIM2Ready(false);

        try {
            setSIM1Ready(getSIMStateBySlot(context,
                    "getSimStateGemini", 0));
            setSIM2Ready(getSIMStateBySlot(context,
                    "getSimStateGemini", 1));
        } catch (TelefonInfoNotFoundException e) {
            e.printStackTrace();
            try {
                setSIM1Ready(getSIMStateBySlot(context,
                        "getSimState", 0));
                setSIM2Ready(getSIMStateBySlot(context,
                        "getSimState", 1));
            } catch (TelefonInfoNotFoundException e1) {
                // Call here for next manufacturer's predicted method name
                // if you wish
                e1.printStackTrace();
            }
        }
    }

    private String getDeviceIdBySlot(Context context,
                                     String predictedMethodName, int slotID)
            throws TelefonInfoNotFoundException {

        String imei = null;
        TelephonyManager telephony = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        try {

            Class<?> telephonyClass = Class.forName(telephony.getClass()
                    .getName());

            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimID = telephonyClass.getMethod(predictedMethodName,
                    parameter);

            Object[] obParameter = new Object[1];
            obParameter[0] = slotID;
            Object ob_phone = getSimID.invoke(telephony, obParameter);

            if (ob_phone != null) {
                imei = ob_phone.toString();

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new TelefonInfoNotFoundException(predictedMethodName);
        }

        return imei;
    }

    private boolean getSIMStateBySlot(Context context,
                                      String predictedMethodName, int slotID)
            throws TelefonInfoNotFoundException {

        boolean isReady = false;

        TelephonyManager telephony = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        try {

            Class<?> telephonyClass = Class.forName(telephony.getClass()
                    .getName());

            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimStateGemini = telephonyClass.getMethod(
                    predictedMethodName, parameter);

            Object[] obParameter = new Object[1];
            obParameter[0] = slotID;
            Object ob_phone = getSimStateGemini.invoke(telephony, obParameter);

            if (ob_phone != null) {
                int simState = Integer.parseInt(ob_phone.toString());
                if (simState == TelephonyManager.SIM_STATE_READY) {
                    isReady = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new TelefonInfoNotFoundException(predictedMethodName);
        }

        return isReady;
    }

    private static class TelefonInfoNotFoundException extends Exception {

        /**
         *
         */
        private static final long serialVersionUID = 20141904;

        public TelefonInfoNotFoundException(String info) {
            super(info);
        }
    }

    public String getImeiSIM1() {
        return imeiSIM1;
    }

    public String getImeiSIM2() {
        return imeiSIM2;
    }

    public boolean isSIM1Ready() {
        return isSIM1Ready;
    }

    public boolean isSIM2Ready() {
        return isSIM2Ready;
    }

    public boolean isDualSIM() {
        return imeiSIM2 != null;
    }

    public void setImeiSIM1(String imeiSIM1) {
        this.imeiSIM1 = imeiSIM1;
    }

    public void setImeiSIM2(String imeiSIM2) {
        this.imeiSIM2 = imeiSIM2;
    }

    public void setSIM1Ready(boolean SIM1Ready) {
        isSIM1Ready = SIM1Ready;
    }

    public void setSIM2Ready(boolean SIM2Ready) {
        isSIM2Ready = SIM2Ready;
    }
}
