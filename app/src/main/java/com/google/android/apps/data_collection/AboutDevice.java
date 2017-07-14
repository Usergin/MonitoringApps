package com.google.android.apps.data_collection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.google.android.apps.repositories.network.models.data.DeviceInfo;

import java.io.File;
import java.lang.reflect.Method;

import javax.inject.Inject;

import io.reactivex.Single;
import timber.log.Timber;

/**
 * Created by OldMan on 03.05.2017.
 */

public class AboutDevice {
    private Context context;
    private TelephonyManager telephonyManager;


    @Inject
    public AboutDevice(Context context) {
        this.context = context;
        telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        Timber.tag(AboutDevice.class.getSimpleName());
    }

    public Single<DeviceInfo> onDeviceInfo() {
        return Single.just(DeviceInfo.newBuilder()
                .brand(getBrand())
                .imei(getIMEI())
                .imsi(getIMSI())
                .manufactured(getManufactured())
                .model(getModel())
                .product(getProduct())
                .screenSize(getScreenSize())
                .serialNum(getSerialNum())
                .versionOS(getVersionOS())
                .sdk(String.valueOf(getSDK()))
                .imeiSim1(getIMEISim1())
                .imeiSim2(getIMEISim2())
                .isDualSim(getIsDualSIM())
                .mcc(getMCC())
                .mnc(getMNC())
                .phoneType(getPhoneType())
                .networkType(getNetworkType())
                .network(getConnectType())
                .operatorName(getOperatorName())
                .isRoot(isRooted())
                .build());

//        App.getAppComponent().getNetworkRepo()
//                .setDeviceInfo(deviceInfo);
    }

    /**
     * get getBrand
     *
     * @return
     */
    private String getBrand() {
        try {
            return android.os.Build.BRAND;
        } catch (Exception e) {
            Timber.e(e);
            return null;
        }
    }

    /**
     * get getSDK
     *
     * @return
     */
    public int getSDK() {
        try {
            return android.os.Build.VERSION.SDK_INT;
        } catch (Exception e) {
            Timber.e(e);
            return 0;
        }
    }

    /**
     * get getOperatorName
     *
     * @return
     */
    private String getOperatorName() {
        try {
            return telephonyManager.getNetworkOperatorName();
        } catch (Exception e) {
            Timber.e(e);
            return null;
        }
    }

    /**
     * getIMEI
     *
     * @return
     */

    private String getIMEI() {
        try {
            return telephonyManager.getDeviceId();
        } catch (Exception e) {
            Timber.e(e);
            return null;
        }
    }

    /**
     * getIMSI
     *
     * @return
     */

    private String getIMSI() {
        try {
            return telephonyManager.getSubscriberId();
        } catch (Exception e) {
            Timber.e(e);
            return null;
        }
    }

    /**
     * getVersion Android
     *
     * @return
     */
    private String getVersionOS() {
        try {
            return android.os.Build.VERSION.RELEASE;
        } catch (Exception e) {
            Timber.e(e);
            return null;
        }
    }

    /**
     * getManufactured
     *
     * @return
     */

    private String getManufactured() {
        try {
            return android.os.Build.MANUFACTURER;
        } catch (Exception e) {
            Timber.e(e);
            return null;
        }
    }

    /**
     * getProduct
     *
     * @return
     */
    private String getProduct() {
        try {
            return android.os.Build.PRODUCT;
        } catch (Exception e) {
            Timber.e(e);
            return null;
        }
    }

    /**
     * getModel
     *
     * @return
     */
    private String getModel() {
        try {
            return android.os.Build.MODEL;
        } catch (Exception e) {
            Timber.e(e);
            return null;
        }
    }

    /**
     * getSerialNumber
     *
     * @return
     */
    private String getSerialNum() {
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            return (String) (get.invoke(c, "ro.getSerialNum", "unknown"));
        } catch (Exception e) {
            Timber.e(e);
            return null;
        }
    }

    /**
     * get PhoneType
     *
     * @return
     */
    private String getPhoneType() {
        int phoneType = telephonyManager.getPhoneType();
        String phoneTypeName;
        switch (phoneType) {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                phoneTypeName = "PHONE_TYPE_CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                phoneTypeName = "PHONE_TYPE_GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                phoneTypeName = "PHONE_TYPE_NONE";
                break;
            case (TelephonyManager.PHONE_TYPE_SIP):
                phoneTypeName = "PHONE_TYPE_SIP";
                break;
            default:
                phoneTypeName = "UNKNOWN";
                break;
        }
        return phoneTypeName;
    }

    /**
     * get NetworkType
     *
     * @return
     */
    private String getNetworkType() {
        int networkType = telephonyManager.getNetworkType();
        String type = null;
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                type = "1xRTT";
                break;
            case TelephonyManager.NETWORK_TYPE_CDMA:
                type = "CDMA";
                break;
            case TelephonyManager.NETWORK_TYPE_EDGE:
                type = "EDGE";
                break;
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                type = "eHRPD";
                break;
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                type = "EVDO rev. 0";
                break;
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                type = "EVDO rev. A";
                break;
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                type = "EVDO rev. B";
                break;
            case TelephonyManager.NETWORK_TYPE_GPRS:
                type = "GPRS";
                break;
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                type = "HSDPA";
                break;
            case TelephonyManager.NETWORK_TYPE_HSPA:
                type = "HSPA";
                break;
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                type = "HSPA+";
                break;
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                type = "HSUPA";
                break;
            case TelephonyManager.NETWORK_TYPE_IDEN:
                type = "iDen";
                break;
            case TelephonyManager.NETWORK_TYPE_LTE:
                type = "LTE";
                break;
            case TelephonyManager.NETWORK_TYPE_UMTS:
                type = "UMTS";
                break;
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                type = "UNKNOWN";
                break;
        }
        return type;
    }

    /**
     * getConnectType
     *
     * @return
     */
    private String getConnectType() {
        String network = null;
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            switch (cm.getActiveNetworkInfo().getType()) {
                case ConnectivityManager.TYPE_MOBILE:
                    network = "Cell Network/3G";
                    break;
                case ConnectivityManager.TYPE_ETHERNET:
                    network = "Ethernet";
                    break;
                case ConnectivityManager.TYPE_WIFI:
                    network = "WiFi";
                    break;
                case ConnectivityManager.TYPE_DUMMY:
                    network = "DUMMY";
                    break;
                case ConnectivityManager.TYPE_BLUETOOTH:
                    network = "BLUETOOTH";
                    break;
                case ConnectivityManager.TYPE_MOBILE_DUN:
                    network = "MOBILE_DUN";
                    break;
                case ConnectivityManager.TYPE_VPN:
                    network = "VPN";
                    break;
                case ConnectivityManager.TYPE_WIMAX:
                    network = "WIMAX";
                    break;
                default:
                    network = "N/A";
            }
        } catch (Exception e) {
            Timber.e(e);
        }
        return network;
    }

    /**
     * getIMEISim1
     *
     * @return
     */

    private String getIMEISim1() {
        try {
            return getDeviceIdBySlot(context,
                    "getDeviceIdGemini", 0);
        } catch (TelefonInfoNotFoundException e) {
            e.printStackTrace();
            try {
                return getDeviceIdBySlot(context,
                        "getDeviceId", 0);
            } catch (TelefonInfoNotFoundException e1) {
                // Call here for next manufacturer's predicted method name
                // if you wish
                e1.printStackTrace();
            }
        } catch (Exception e) {
            Timber.e(e);
            return null;
        }
        return null;
    }

    /**
     * getIMEISim2
     *
     * @return
     */

    private String getIMEISim2() {
        try {
            return getDeviceIdBySlot(context,
                    "getDeviceIdGemini", 1);
        } catch (TelefonInfoNotFoundException e) {
            e.printStackTrace();
            try {
                return getDeviceIdBySlot(context,
                        "getDeviceId", 1);
            } catch (TelefonInfoNotFoundException e1) {
                // Call here for next manufacturer's predicted method name
                // if you wish
                e1.printStackTrace();
            }
        } catch (Exception e) {
            Timber.e(e);
            return null;
        }
        return null;
    }

    /**
     * getIMEISim1Ready
     *
     * @return
     */
    private boolean getIsSIM1Ready() {
        try {
            return getSIMStateBySlot(context,
                    "getSimStateGemini", 0);
        } catch (TelefonInfoNotFoundException e) {
            e.printStackTrace();
            try {
                return getSIMStateBySlot(context,
                        "getSimState", 0);
            } catch (TelefonInfoNotFoundException e1) {
                // Call here for next manufacturer's predicted method name
                // if you wish
                e1.printStackTrace();
            }
        }
        return false;
    }

    /**
     * getIMEISim2Ready
     *
     * @return
     */
    private boolean getIsSIM2Ready() {
        try {
            return getSIMStateBySlot(context,
                    "getSimStateGemini", 1);
        } catch (TelefonInfoNotFoundException e) {
            e.printStackTrace();
            try {
                return getSIMStateBySlot(context,
                        "getSimState", 1);
            } catch (TelefonInfoNotFoundException e1) {
                // Call here for next manufacturer's predicted method name
                // if you wish
                e1.printStackTrace();
            }
        }
        return false;
    }

    /**
     * getIsDualSim
     *
     * @return
     */
    private boolean getIsDualSIM() {
        try {
            return getIMEISim2() != null;
        } catch (Exception e) {
            Timber.e(e);
            return false;
        }
    }

    /**
     * getMCC
     *
     * @return
     */
    private String getMCC() {
        String networkOperator = telephonyManager.getNetworkOperator();
        String mcc = null;
        try {
            if (networkOperator != null) {
                mcc = networkOperator.substring(0, 3);
            }
        } catch (Exception e) {
            Timber.e(e);
        }
        return mcc;

    }

    /**
     * getMCC
     *
     * @return
     */
    private String getMNC() {
        String networkOperator = telephonyManager.getNetworkOperator();
        String mnc = null;
        try {
            if (networkOperator != null) {
                mnc = networkOperator.substring(3);
            }
        } catch (Exception e) {
            Timber.e(e);
        }
        return mnc;

    }

    /**
     * getDisplayMetrics w*h
     *
     * @return
     */
    private String getScreenSize() {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        // Best way for new devices
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        String screenSize = displayMetrics.widthPixels
                + " x " + displayMetrics.heightPixels;
        return screenSize;
    }

    private static boolean isRooted() {
        boolean found = false;
        String[] places = {"/sbin/", "/system/bin/", "/system/xbin/", "/data/local/xbin/",
                "/data/local/bin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/"};
        for (String where : places) {
            if (new File(where + "su").exists()) {
                return true;
            }
        }
        return found;
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

}
