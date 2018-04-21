//package com.tudouni.makemoney.network.security;
//
//import android.content.Context;
//import android.text.TextUtils;
//import android.util.Log;
//import android.com.tudouni.makemoney.widget.Toast;
//
//import com.alibaba.wireless.security.jaq.JAQException;
//import com.alibaba.wireless.security.jaq.avmp.IJAQAVMPSignComponent;
//import com.alibaba.wireless.security.open.SecurityGuardManager;
//import com.alibaba.wireless.security.open.avmp.IAVMPGenericComponent;
//import com.doubozhibo.tudouni.App;
//
//import java.io.UnsupportedEncodingException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.HashMap;
//
///**
// * 移动安全
// * Created by ZhangPeng on 2018/1/8.
// */
//
//public class MobileSecurity {
//    private IAVMPGenericComponent.IAVMPGenericInstance instance = null;
//    private static IJAQAVMPSignComponent jaqVMPComp = null;
//    private static int VMP_SIGN_WITH_EXTERNAL_WUA = 3;
//    private static String TAG = "MobileSecurity";
//
//    /**
//     * 保证线程同步，调用AVMP的接口全局请只初始化一次即可，避免反复初始化造成不必要的开销。
//     *
//     * @return
//     */
//    public static synchronized boolean initAVMP() {
//        boolean result = false;
//        try {
//            if (jaqVMPComp != null) {
//                Log.d(TAG, "AVMP instance has been initialized");
//                return true;
//            }
//            jaqVMPComp = SecurityGuardManager.getInstance(App.getContext()).getInterface(IJAQAVMPSignComponent.class);
//            result = jaqVMPComp.initialize();
//        } catch (JAQException e) {
//            Log.d(TAG, "init failed with errorCode " + e.getErrorCode());
//        } catch (Exception e) {
//            Log.d(TAG, "unkown exception has occured");
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    /**
//     * 去阿里获取签名的数据
//     *
//     * @param input
//     * @return
//     */
//    private static synchronized String avmpSign(String input) {
//        try {
//            if (initAVMP() == false) {
//                return null;
//            }
//            byte[] result = jaqVMPComp.avmpSign(VMP_SIGN_WITH_EXTERNAL_WUA, input.getBytes("UTF-8"));
//            String signResult = new String(result, "UTF-8");
////            toastView("sign result =" + signResult);
//            return signResult;
//        } catch (JAQException e) {
//            Log.d(TAG, "avmp sign failed with errorCode=" + e.getErrorCode());
//        } catch (UnsupportedEncodingException e) {
//            Log.e(TAG, "UnsupportedEncodingException exception error !!!");
//        }
//        return null;
//    }
//
//    private void toastView(String content) {
//        Context context = App.getContext();
//        CharSequence text = content;
//        int duration = Toast.LENGTH_SHORT;
//        Toast toast = Toast.makeText(context, text, duration);
//        toast.show();
//        Log.d(TAG, content);
//    }
//
//    /**
//     * 测试签名数据
//     *
//     * @param content 要签名的数据
//     */
//    private void testAvmpSign(String content) {
//        String input = "user=lingxuan&psw=123456789&imei=9876543210";  //TODO: 待签名的关键数据
//        String sign = avmpSign(input);
//        if (sign != null) {
//            Log.d(TAG, "avmp sign succeed");
//            // send2server(input, sign);  将原数数据和签名数据发送到服务器端，用于后续的验签和风险识别
//        } else {
//            Log.d(TAG, "avmp sign failed");
//        }
//    }
//
//    public static String md5(String string) {
//        if (TextUtils.isEmpty(string)) {
//            return "";
//        }
//        MessageDigest md5 = null;
//        try {
//            md5 = MessageDigest.getInstance("MD5");
//            byte[] bytes = md5.digest(string.getBytes());
//            String result = "";
//            for (byte b : bytes) {
//                String temp = Integer.toHexString(b & 0xff);
//                if (temp.length() == 1) {
//                    temp = "0" + temp;
//                }
//                result += temp;
//            }
//            return result;
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    /**
//     * 将元数据通过处理后反馈给登录接口
//     * @param context
//     * @param rawData 原始数据
//     * @return
//     */
//    public static HashMap<String, String> toEncryptedData(Context context, String rawData) {
//        HashMap<String, String> dataMap = new HashMap<>();
//        try {
//            //加密原有的签名数据
//            String md5RawData = MobileSecurity.md5(rawData);
//            String sign = MobileSecurity.avmpSign(md5RawData);
//            String douboFingerPrint = md5(DeviceInfor.getDeviceId(context));
//            dataMap.put("rawData", md5RawData);
//            dataMap.put("sign", sign);
//            dataMap.put("douboFingerPrint", douboFingerPrint);
//        } catch (Exception e) {
//            Log.e(TAG, "toEncryptedData error" + e.getMessage());
//            dataMap = null;
//        }
//        return dataMap;
//    }
//}
