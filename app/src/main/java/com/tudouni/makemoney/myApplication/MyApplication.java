package com.tudouni.makemoney.myApplication;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.tudouni.makemoney.activity.BindInvitationCodeActivity;
import com.tudouni.makemoney.activity.BindingInvitationActivity;
import com.tudouni.makemoney.activity.FaceToFaceActivity;
import com.tudouni.makemoney.activity.LoginActivity;
import com.tudouni.makemoney.activity.PwdActivity;
import com.tudouni.makemoney.activity.SearchGoodActivity;
import com.tudouni.makemoney.activity.SplashActivity;
import com.tudouni.makemoney.activity.TelLoginActivity;
import com.tudouni.makemoney.model.AppConfig;
import com.tudouni.makemoney.model.LogOut;
import com.tudouni.makemoney.model.User;
import com.tudouni.makemoney.myApplication.jPush.TagAliasOperatorHelper;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.BindInvitationUtil;
import com.tudouni.makemoney.utils.ClipboardUtil;
import com.tudouni.makemoney.utils.DBLifecycleHandler;
import com.tudouni.makemoney.utils.PatternUtil;
import com.tudouni.makemoney.utils.UserInfoHelper;
import com.tudouni.makemoney.utils.ValidateUtil;
import com.tudouni.makemoney.utils.base.BaseFrameworkInit;
import com.tudouni.makemoney.utils.base.IBaseRequirement;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.uuzuche.lib_zxing.PicassoImageLoader;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ZhangPeng on 2018/4/19.
 */

public class MyApplication extends BaseApplication implements ClipboardUtil.OnPrimaryClipChangedListener{
    private static MyApplication sContext;
    private static User mLoginUser;
    public static String mNewUserClipPhone = "";
    public static AppConfig appConfig;
    public static Activity sCurrActivity = null;
    private ClipboardUtil mClipboard;
    public static String mClipStr = "";


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
//        initUmeng();
        ZXingLibrary.initDisplayOpinion(this);
        registerActivityLifecycleCallbacks();

        initImagePicker();

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
        PlatformConfig.setWeixin("wx9dc84ba75bee1a73", "ea0ad0c6438f1dd58f8909dc0e531276");//需要替换第二个apps
        PlatformConfig.setQQZone("1106781729", "KsDhxiNWe91diHM0");

        //注册监听器
        ClipboardUtil.init(this);
        mClipboard = ClipboardUtil.getInstance();
        mClipboard.addOnPrimaryClipChangedListener(this);
        String mimeType = mClipboard.getPrimaryClipMimeType();
        if (ClipDescription.MIMETYPE_TEXT_PLAIN.equals(mimeType)) {
            mClipStr = mClipboard.coercePrimaryClipToText().toString();
        }
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
//                super.OnFail(code, err);
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
        PlatformConfig.setWeixin("wx9dc84ba75bee1a73", "ea0ad0c6438f1dd58f8909dc0e531276");//需要替换第二个apps
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

                popGoodSerachWindow(activity);
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

    @Override
    public void onTerminate() {
        super.onTerminate();
        mClipboard.removeOnPrimaryClipChangedListener(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    private void popGoodSerachWindow(Activity activity) {
        if (activity.getClass().getName().startsWith("com.tudouni") && !(sCurrActivity instanceof SearchGoodActivity) && !(activity instanceof LoginActivity) &&
                !(activity instanceof TelLoginActivity) && !(activity instanceof PwdActivity) &&
                !(activity instanceof SplashActivity) && !(sCurrActivity instanceof BindInvitationCodeActivity)) {
            String mimeType = mClipboard.getPrimaryClipMimeType();
            //一般来说，收到系统 onPrimaryClipChanged() 回调时，剪贴板一定不为空
            //但为了保险起见，在这边还是做了空指针判断

            if (ClipDescription.MIMETYPE_TEXT_PLAIN.equals(mimeType) || ClipDescription.MIMETYPE_TEXT_HTML.equals(mimeType)) {
                try {
                    if (mClipStr != null && !mClipStr.equals("") && mClipStr.trim().length() > 6 && !ValidateUtil.isMobileNO(mClipStr) && mClipStr.startsWith("#tdn")) {
                        String userCode = mClipStr.substring(4, mClipStr.lastIndexOf("#tdn"));
                        ClipboardUtil.getInstance().copyText("", "");
                        mClipStr = "";
                        BindInvitationUtil.getBindInvitationUseInfo(userCode);
                    /*Intent intent = new Intent(getContext(), BindInvitationCodeActivity.class);
                    intent.putExtra("userCode", userCode);
                    activity.startActivity(intent);*/

                    } else if (mClipStr != null && !mClipStr.equals("") && mClipStr.trim().length() > 15 && !PatternUtil.matchClipStr(mClipStr.trim()) &&
                            !PatternUtil.matchURL(mClipStr.trim()) && !ValidateUtil.isMobileNO(mClipStr) && !mClipStr.startsWith("#bind") && !mClipStr.contains("邀请您加入赚赚")) {
                        EventBus.getDefault().post(mClipStr, "search_good_action");
                       /* Intent intent = new Intent(getContext(), SearchGoodActivity.class);
                        intent.putExtra("url", mClipStr);
                        activity.startActivity(intent);*/
                    }
                } catch (Exception e) {}
            }
        }

        //如果是新注册的账户如果粘贴板有"#tdn绑定码#tdn手机号"格式就赋值到登录界面的账号登录中的手机控件
        if (mClipStr != null && !mClipStr.equals("") && mClipStr.trim().length() > 6 && !ValidateUtil.isMobileNO(mClipStr) && mClipStr.startsWith("#tdn")) {
            String phone = mClipStr.substring(mClipStr.lastIndexOf("#tdn") + 4, mClipStr.length());
            if (ValidateUtil.isMobileNO(mClipStr.substring(mClipStr.lastIndexOf("#tdn") + 4, mClipStr.length()))) {
                mNewUserClipPhone = phone;
            }
        }

        //如果只有手机号码的情况
        if (mClipStr != null && !mClipStr.equals("") && mClipStr.trim().length() > 5 && !ValidateUtil.isMobileNO(mClipStr) && mClipStr.startsWith("#bind")) {
            String phone = mClipStr.substring(mClipStr.lastIndexOf("#bind") + 5, mClipStr.length());
            if (ValidateUtil.isMobileNO(phone)) {
                ClipboardUtil.getInstance().copyText("", "");
                mNewUserClipPhone = phone;
            }
        }
    }


    @Override
    public void onPrimaryClipChanged(ClipboardManager clipboardManager) {
        ClipData data = clipboardManager.getPrimaryClip();
        String mimeType = mClipboard.getPrimaryClipMimeType();
        //一般来说，收到系统 onPrimaryClipChanged() 回调时，剪贴板一定不为空
        //但为了保险起见，在这边还是做了空指针判断
        if (data == null) {
            return;
        }

        if (ClipDescription.MIMETYPE_TEXT_PLAIN.equals(mimeType) || ClipDescription.MIMETYPE_TEXT_HTML.equals(mimeType)) {
            if (!DBLifecycleHandler.isApplicationInForeground()) {
                mClipStr = mClipboard.coercePrimaryClipToText().toString();
                return;
            }
            popGoodSerachWindow(sCurrActivity);
        }
    }

}
