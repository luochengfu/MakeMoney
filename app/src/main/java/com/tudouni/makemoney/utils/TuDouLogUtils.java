package com.tudouni.makemoney.utils;

import android.util.Log;

import com.tudouni.makemoney.network.NetConfig;


/**
 * 日志帮助工具 测试环境才会打印Log
 * Created by ZhangPeng on 2018/1/31.
 */

public class TuDouLogUtils {
    enum TuDouLogLevel {
        ERROR,
        DEBUG,
        INFO
    }

    /**
     * 显示error级别Log
     *
     * @param tag
     * @param message
     */
    public static void e(String tag, String message) {
        showLog(tag, message, TuDouLogLevel.ERROR);
    }

    /**
     * 显示info级别Log
     *
     * @param tag
     * @param message
     */
    public static void i(String tag, String message) {
        showLog(tag, message, TuDouLogLevel.INFO);
    }

    /**
     * 显示info级别Log
     *
     * @param tag
     * @param message
     */
    public static void d(String tag, String message) {
        showLog(tag, message, TuDouLogLevel.DEBUG);
    }

    /**
     * 显示log信息
     *
     * @param tag
     * @param message  log信息
     * @param logLevel log级别
     */
    private static void showLog(String tag, String message, TuDouLogLevel logLevel) {
        try {
            if (logLevel == TuDouLogLevel.ERROR) {
                Log.e(tag, Constants.LOG_TITLT + message);
                return;
            }
            if (NetConfig.getEnvironment() == 1) {
                if (logLevel == TuDouLogLevel.INFO)
                    Log.i(tag, Constants.LOG_TITLT + message);
                if (logLevel == TuDouLogLevel.DEBUG)
                    Log.d(tag, Constants.LOG_TITLT + message);
                else
                    Log.v(tag, Constants.LOG_TITLT + message);
            }
        } catch (Exception e) {
            Log.e("TuDouLogUtils", "TuDouLogUtils_e():error" + e.getMessage());
        }
    }


}
