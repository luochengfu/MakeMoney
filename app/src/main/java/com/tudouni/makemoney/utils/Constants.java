package com.tudouni.makemoney.utils;

import com.tudouni.makemoney.network.NetConfig;
import com.tudouni.makemoney.utils.base.AppUtils;

import java.io.File;

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
    public static final String SHARE_INVISTOR = BASE_H5_URL + "/shoplite/html/invistor.html";
    public static final String MYINVITATION = BASE_PRE + "invisit";
    public static final String ACCOUNT_SECURITY = BASE_PRE + "sefaty";
    public static final String REALNAME_FINAL = BASE_PRE + "realnameFinal";
    public static final String UPLOAD_IDCARD = BASE_PRE + "idcard";
    public static final String REALNAME2 = BASE_PRE + "realname2";
    public static final String ABOUTUS = BASE_H5_URL + "mycenter/contact.html";
    public static final String h5_loginXy = BASE_H5_URL + "/tudouni/html/puser.html";
    public static final String CONTACTUS = BASE_H5_URL + "/tudouni/html/contact.html";
    public static final String BINDING_FACE_TO_FACE = BASE_PRE + "binding_face_to_face";
    public static final String USERINFO = BASE_PRE + "setinfo";
    public static final String WITHDRAW_MONEY = BASE_PRE + "withdraw_money";
    public static final String WITHDRAW_TEL = BASE_PRE + "withdraw_tel";
    public static final String NATIVE_INCOME = BASE_PRE + "native_income";

    public static final String UPTYPE_IMGHEADER = "img-header";
    public static final String UPTYPE_IMGCOMMON = "img-common";
    public static final String MY_INVITE = BASE_H5_URL + "/m/subject/invite.income.html";
    public static final String MAIN = BASE_PRE + "root";
    public static final String HOME = BASE_PRE + "home";
    public static final String h5_myinvite = BASE_H5_URL + "mycenter/myinvite.html";//我的豆粉详情和我的邀请详情
    public static final String h5_bindsearch = BASE_H5_URL + "mycenter/bindsearch.html";//我的邀请顶部搜索
    public static final String H5_MALL_DINGDAN = BASE_H5_URL + "mycenter/order.html";//商城订单
    public static final String h5_mall_grade = BASE_H5_URL + "mycenter/mallgrade.html";//商城等级
    public static final String h5_collect = BASE_H5_URL + "mycenter/collect.html";//我的收藏
    public static final String h5_makemoney = BASE_H5_URL + "mycenter/makemoney.html";//我赚的钱
    public static final String h5_savemoney = BASE_H5_URL + "mycenter/savemoney.html";//我省的钱
    public static final String h5_novice = BASE_H5_URL + "mycenter/novice.html";//新手攻略
    public static final String h5_faq = BASE_H5_URL + "mycenter/faq.html";//常见问题
    public static final String MESSAGE = BASE_PRE + "message";//消息列表
    public static final String h5_inviterule = BASE_H5_URL + "mycenter/inviterule.html";//邀请规则
    public static final String IMAGE_DOWN_DIR = "豆播" + File.separator + "豆播相册";//图片下载地址
    public static final String IMAGE_DOWN_DIR_PRE = IMAGE_DOWN_DIR + File.separator + "temporary";//图片下载地址

    public static final int STATE_ING = 0;
    public static final int STATE_SUCESS = 1;
    public static final int STATE_FILED = 2;
    public static final int STATE_WAIT = 3;
    public static final int STATE_STOP = 4;
    public static final int STATE_REPORT_FILED = 5;
    public static final int INVALID_STATE = -1;// 无效的状态

    public static final int GET_SHARE_IMAGE_START = 111;//获取图片分享开始
    public static final int GET_SHARE_IMAGE_END = 112;// 获取图片分享结束


}
