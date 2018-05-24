package com.tudouni.makemoney.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.content.PermissionChecker;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;


import java.util.UUID;

import static com.tudouni.makemoney.utils.StringUtil.isEmpty;


/**
 * Created by ZhangPeng on 2018/1/8.
 */

public class DeviceInfor {

    public static String getDeviceId(Context context) {
        StringBuilder deviceId = new StringBuilder();
        try {
            //wifi mac地址
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            String wifiMac = info.getMacAddress();
            if (!isEmpty(wifiMac))
                deviceId.append(wifiMac);
            if (PermissionChecker.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                //IMEI（imei）
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String imei = tm.getDeviceId();
                if (!isEmpty(imei))
                    deviceId.append(imei);
                //序列号（sn）
                String sn = tm.getSimSerialNumber();
                if (!isEmpty(sn))
                    deviceId.append(sn);
            } else {//没有权限的状态
                //如果上面都没有， 则生成一个id：随机码
                String uuid = getUUID(context);
                if (!isEmpty(uuid)) {
                    deviceId.append("id");
                    deviceId.append(uuid);
                    Log.e("gDeviceInfor ", deviceId.toString());
                    return deviceId.toString();
                }
            }
            return deviceId.toString();
        } catch (Exception e) {
            Log.e("DeviceInfor", "Get DeviceId Error " + e.getMessage());
            deviceId.append("id").append(getUUID(context));
        } finally {
            return deviceId.toString();
        }
    }

    /**
     * 得到全局唯一UUID
     */
    public static String getUUID(Context context) {
        String uuid = SPUtil.getString(context, "uuid");

        if (isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            SPUtil.putString(context, "uuid", uuid);
        }
        return uuid;
    }

    public static String getDeviceModel() {
        return Build.BRAND + " " + Build.MODEL;
    }
}
