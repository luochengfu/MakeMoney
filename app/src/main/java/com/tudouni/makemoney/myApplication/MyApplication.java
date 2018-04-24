package com.tudouni.makemoney.myApplication;

import android.app.Activity;
import android.content.Context;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.tudouni.makemoney.model.AppConfig;
import com.tudouni.makemoney.model.User;
import com.tudouni.makemoney.myApplication.jPush.TagAliasOperatorHelper;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.DBLifecycleHandler;
import com.tudouni.makemoney.utils.UserInfoHelper;
import com.tudouni.makemoney.utils.base.BaseFrameworkInit;
import com.tudouni.makemoney.utils.base.IBaseRequirement;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.uuzuche.lib_zxing.PicassoImageLoader;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

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
    public static Activity sCurrActivity = null;


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
        this.sContext = this;
        initConfig();
        initJPush();
        initUmeng();
        ZXingLibrary.initDisplayOpinion(this);
        registerActivityLifecycleCallbacks();

        initImagePicker();
    }

    /**
     * 初始化图片选择器
     */
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    /**
     * 初始启动页配置接口
     */
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
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
    }

    /**
     * 初始化友盟
     */
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
        if (null == user)
            return;
        mLoginUser = user;
        UserInfoHelper.saveUserDatas(sContext, user);
        TagAliasOperatorHelper.getInstance().handleAction((++TagAliasOperatorHelper.sequence), new TagAliasOperatorHelper.TagAliasBean(mLoginUser.getUnionid()));
    }

    /**
     * activity 生命周期监听
     */
    private void registerActivityLifecycleCallbacks() {
        registerActivityLifecycleCallbacks(new DBLifecycleHandler() {
            @Override
            public void onActivityResumed(Activity activity) {
                super.onActivityResumed(activity);

//                popGoodSerachWindow(activity);
                sCurrActivity = activity;

            }

            @Override
            public void onActivityPaused(Activity activity) {
                super.onActivityPaused(activity);

            }
        });
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

    public static void logout() {
        mLoginUser = null;
        UserInfoHelper.clearLoginUser(sContext);
    }
}
