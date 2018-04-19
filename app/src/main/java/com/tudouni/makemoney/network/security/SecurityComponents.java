package com.tudouni.makemoney.network.security;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tudouni.makemoney.myApplication.MyApplication;


import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * 安全组件
 * Created by ZhangPeng on 2018/1/26.
 */

public class SecurityComponents {
    private static final String TAG = "SecurityComponents";
    private static boolean mIsInited = false;
    private static String APP_KEY = "2689f06a-32e8-4279-9d0b-2441492e9cf8";
//    private String planText = "user=lingxuan&psw=123456789&imei=9876543210";
//    String sign;
//    String ciphertext;
//
//    StringBuffer stringBuffer = new StringBuffer();


    /**
     * 初始化组件
     */
    public static void initSecurityInit() {
//        if (!mIsInited) {
//            //初始化
//            try {
//                //返回0，表明初始化成功
//                if (SecurityInit.Initialize(MyApplication.getContext()) == 0) {
//                    mIsInited = true;
//                }
//            } catch (JAQException e) {
//                Log.e(TAG, "clickOnInitButton: errorCode =" + e.getErrorCode());
//                showToast(("初始化失败！错误码") + e.getErrorCode());
//            }
//        }
    }


    /**
     * 对接口数据进行签名
     *
     * @param planText 待签名数据
     * @return
     */
    public static String signatureData(String planText) {
        String sign = "";
//        Log.e(TAG, "===================签名开始======================");
//        if (planText == null || planText.length() == 0) {
//            Log.e(TAG, "无效输入参数！");
//            showToast("无效输入参数！");
//            return "";
//        }
//        try {
//            //text			:待签名字串
//            //APP_KEY		:签名用的密匙key，预埋在jpg文件中
//            SecuritySignature securitySignature = new SecuritySignature(App.getContext());
//            sign = securitySignature.sign(planText, APP_KEY);
//            Log.e(TAG, "签名结果——原数据：" + planText + ";签名后的数据：" + sign);
//        } catch (JAQException e) {
//            Log.e(TAG, "clickOnSignatureButton: errorCode =" + e.getErrorCode());
//        }
//        Log.e(TAG, "===================签名结束======================");
        return sign;
    }

    /**
     * 对数据进行加密
     */
//    public static String cipherEncryptData(List<KeyValue> param_map) {
//        StringBuilder sb = new StringBuilder();
//        String result = new Gson().toJson(param_map);
//        return cipherEncryptData(result);
//    }

    /**
     * 对数据进行加密
     */
    public static String cipherEncryptData(Map<String, String> param_map) {
        StringBuilder sb = new StringBuilder();
        String result = new Gson().toJson(param_map);
        return cipherEncryptData(result);
    }

    /**
     * 对数据进行加密
     */
    public static String cipherEncryptData(TreeMap<String, String> param_map) {
        StringBuilder sb = new StringBuilder();
        String result = new Gson().toJson(param_map);
        return cipherEncryptData(result);
    }

    /**
     * 对数据进行加密
     *
     * @param text
     */
    public static String cipherEncryptData(String text) {
        String ciphertext = "";
//        Log.e(TAG, "===================加密开始======================");
//        if (text == null || text.length() == 0) {
//            Log.e(TAG, "无效输入参数！");
//            return ciphertext;
//        }
//        try {
//            ciphertext = new SecurityCipher(App.getContext()).encryptString(text, APP_KEY);
//            Log.e(TAG, "加密结果——原数据：" + text + ";加密后的数据：" + ciphertext);
//        } catch (JAQException e) {
//            Log.e(TAG, "clickOnSignatureButton: errorCode =" + e.getErrorCode());
//        }
//        Log.e(TAG, "===================加密结束======================");
        return ciphertext;
    }

    /**
     * 对数据解密
     *
     * @param text
     */
    private String cipherDecryptData(String text) {
        String decryptString = "";
//        Log.e(TAG, "===================解密开始======================");
//        if (text == null || text.length() == 0) {
//            Log.e(TAG, "CipherDecrypt:无效输入参数 ");
//            return decryptString;
//        }
//        try {
//            decryptString = new SecurityCipher(App.getContext()).decryptString(text, APP_KEY);
//            Log.e(TAG, "解密结果——原数据：" + text + ";解密后的数据：" + decryptString);
//        } catch (JAQException e) {
//            Log.e(TAG, "clickOnCipherDecryptButton: errorCode =" + e.getErrorCode());
//        }
//        Log.e(TAG, "===================解密结束======================");
        return decryptString;
    }

    private static void showToast(String toast) {
        Toast.makeText(MyApplication.getContext(), toast, Toast.LENGTH_SHORT).show();
    }

}
