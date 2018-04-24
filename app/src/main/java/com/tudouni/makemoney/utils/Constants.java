package com.tudouni.makemoney.utils;

import com.tudouni.makemoney.network.NetConfig;
import com.tudouni.makemoney.utils.base.AppUtils;

/**
 * 该框架定义的常量
 */

public class Constants {

    /**
     * 图片缓存目录
     */
    public static String IMAGE_CACHE_DIR = "image_cache";

    /**
     * Retrofit.Builder().baseUrl()
     */
    public static String HTTP_URL = "";

    public static final String DEFALT_HEAD = "";

    /**
     * 默认分页大小
     */
    public final static int DEFAULT_PAGE_SIZE = 20;
    public static final String LOG_TITLT = "DouBo_";
    public final static String APP_VERSION = AppUtils.getAppVersionName();
    public final static String DEVICE_MODEL = android.os.Build.MODEL;
    public final static String OS_TYPE = android.os.Build.MANUFACTURER;
    public final static String OS_VERSION = android.os.Build.VERSION.RELEASE;
    public static final String BASE_PRE = "tudouni://tudouni/";
    public static final String SETTING = BASE_PRE + "setting";
    public static final String LOGIN = BASE_PRE + "login";


    public static final String BASE_H5_URL = NetConfig.getBaseTuDouNiH5Url();
    public static final String INVISIT_POSTER = BASE_PRE + "invisitPoster";
    public static final String SHARE_INVISTOR = BASE_H5_URL + "/tudouni/html/invistor.html";
    public static final String MYINVITATION = BASE_PRE + "invisit";
    public static final String ACCOUNT_SECURITY = BASE_PRE + "sefaty";
    public static final String ABOUTUS = BASE_H5_URL + "/tudouni/html/contact.html";
    public static final String CONTACTUS = BASE_H5_URL + "/tudouni/html/contact.html";
    public static final String BINDING_FACE_TO_FACE = BASE_PRE + "binding_face_to_face";

}
