package com.tudouni.makemoney.myApplication;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.tudouni.makemoney.model.AppConfig;
import com.tudouni.makemoney.model.User;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.UserInfoHelper;
import com.tudouni.makemoney.utils.base.BaseFrameworkInit;
import com.tudouni.makemoney.utils.base.IBaseRequirement;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ZhangPeng on 2018/4/19.
 */

public class MyApplication extends BaseApplication {
    private static MyApplication sContext;
    private static User mLoginUser;
    public static String mNewUserClipPhone = "";
    public static AppConfig appConfig;


    @Override
    public IBaseRequirement getBaseRequirement() {
        return new BaseFrameworkInit();
    }

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initConfig();
        initJPush();
        initUmeng();
    }

    private void initConfig() {
        CommonScene.getConfig(new BaseObserver<AppConfig>() {
            @Override
            public void OnSuccess(AppConfig appConfig) {
                if (appConfig != null)
                    MyApplication.appConfig = appConfig;
            }

            @Override
            public void OnFail(int code, String err) {
                super.OnFail(code, err);
            }
        });
    }

    private void initJPush() {
        sContext = this;
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
    }

    private void initUmeng() {
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);

        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true);
        /**
         * 设置日志加密
         * 参数：boolean 默认为false（不加密）
         */
        UMConfigure.setEncryptEnabled(true);
        //场景类型设置接口
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        PlatformConfig.setWeixin("wxb7563676e3d2b035", "b3babc1e374bf1f7fbb540d2497f64ed");//需要替换第二个apps
        PlatformConfig.setQQZone("1106781729", "KsDhxiNWe91diHM0");
    }

    public static void saveLoginUser(User user) {
        mLoginUser = user;
        UserInfoHelper.saveUserDatas(sContext, user);
    }

    public static User getLoginUser() {
        if (mLoginUser == null) {
            User mUser = UserInfoHelper.getUserDatas(sContext);
            if (null != mUser.getToken() && !"".equals(mUser.getToken())) {
                mLoginUser = mUser;
            }
        }

        return mLoginUser;
    }
}
