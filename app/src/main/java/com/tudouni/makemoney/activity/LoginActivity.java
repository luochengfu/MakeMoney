package com.tudouni.makemoney.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tudouni.makemoney.BuildConfig;
import com.tudouni.makemoney.interfaces.NoDoubleClickListener;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.model.FinishLoginActivity;
import com.tudouni.makemoney.model.Invite;
import com.tudouni.makemoney.model.LogOut;
import com.tudouni.makemoney.model.LoginBean;
import com.tudouni.makemoney.model.User;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.myApplication.jPush.TagAliasOperatorHelper;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.NetConfig;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.CommonUtil;
import com.tudouni.makemoney.utils.Constants;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.utils.ValidateUtil;
import com.tudouni.makemoney.utils.glideUtil.GlideUtil;
import com.tudouni.makemoney.view.CenterLoadingView;
import com.tudouni.makemoney.view.Tip_dialog;
import com.tudouni.makemoney.widget.dialog.MenuDialog;
import com.tudouni.makemoney.widget.versionUpdate.UpdateAPKUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;
import org.xutils.common.util.LogUtil;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = "LoginActivity";
    private final int PERMISSION_REQUEST_CODE = 125;
    private Context mContext = this;
    private LinearLayout iv_WechatLogin, iv_QqLogin, tv_phone_login;

    private TextView mTaghint;
    private LinearLayout buttomTV;
    private UMShareAPI mShareAPI = null;
    private CenterLoadingView loadingDialog = null;
    private final String[] permissionManifest = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    public int state = 1; //状态 1 表示未启动线程或正在运行线程。0 停止线程
    private ExecutorService mExecutouService;
    private String mPhoneNum;
    private boolean mActivityStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EventBus.getDefault().register(this);
        initPermissions();
        mShareAPI = UMShareAPI.get(this);
        initView();
        initDatas();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        App.setDeviceId();
    }

    /**
     * 防止多次点击
     */
    private void noDoubleClick() {
        iv_WechatLogin.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                MobclickAgent.onEvent(LoginActivity.this, "lg_wx");
                wechatLogin();
            }
        });

        iv_QqLogin.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                MobclickAgent.onEvent(LoginActivity.this, "lg_qq");
                qqLogin();
            }
        });


        buttomTV.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                MobclickAgent.onEvent(LoginActivity.this, "lg_deal");
                Intent intent = new Intent(LoginActivity.this, H5Activity.class);
                intent.putExtra("url", NetConfig.getBaseTuDouNiH5Url() + "mycenter/protocol.html");
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO 有bug，没有释放引用，Activity依然驻留内存
        mShareAPI.release();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        iv_WechatLogin = (LinearLayout) findViewById(R.id.iv_WechatLogin);
        iv_QqLogin = (LinearLayout) findViewById(R.id.iv_QqLogin);
        tv_phone_login = (LinearLayout) findViewById(R.id.tv_phone_login);
        tv_phone_login.setOnClickListener(this);
        buttomTV = (LinearLayout) findViewById(R.id.xy);
        mTaghint = (TextView) findViewById(R.id.activity_login_tv);
    }

    private void initDatas() {
        mActivityStatus = true;
        mExecutouService = Executors.newSingleThreadExecutor();
        mTaghint.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        //是否有更新
        UpdateAPKUtil.uptateAPK(this);
        //如果维护中
        /*if (App.appConfig.isMaintain()) {
            new MaintinDialog(this, App.appConfig.getShutdownMaintainMsg()).show();
        }*/

        initEnvironmentButton();
        noDoubleClick();
    }


    @Override
    public void onClick(View v) {
        String statisticsType = null;
        switch (v.getId()) {
            case R.id.tv_phone_login:
                MyApplication.logout();
                skipTelephoneLogin(null, "1");
                break;
        }

        if (!TextUtils.isEmpty(statisticsType))
            MobclickAgent.onEvent(this, statisticsType);
    }

    /**
     * 微信登录
     */
    private void wechatLogin() {
        if (!CommonUtil.isWXInstall(this)) {
            ToastUtil.show("请安装微信客户端");
            return;
        }
        login(SHARE_MEDIA.WEIXIN);
    }


    /**
     * QQ登录
     */
    private void qqLogin() {
        if (!CommonUtil.isQQInstall(this)) {
            ToastUtil.show("请安装QQ客户端");
            return;
        }
        login(SHARE_MEDIA.QQ);
    }


    private void login(final SHARE_MEDIA platform) {
        MyApplication.logout();
        if (null == loadingDialog) {
            loadingDialog = new CenterLoadingView(LoginActivity.this);
        }

        mShareAPI.getPlatformInfo(LoginActivity.this, platform, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                if (!platform.equals(SHARE_MEDIA.SINA)) {
                    loadingDialog.setTitle("授权中");
                    loadingDialog.show();
                }
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                if (null != loadingDialog) {
                    loadingDialog.setTitle("登录中");
                }
                String flag = "";
                String accessToken = map.get("access_token");
                String openid = "";
                String rawDataKey = "";
                if (platform == SHARE_MEDIA.QQ) {
                    flag = "1";
                    rawDataKey = "openid";
                    openid = map.get("openid");
                } else if (platform == SHARE_MEDIA.WEIXIN) {
                    flag = "2";
                    rawDataKey = "unionid";
                    openid = map.get("openid");
                }
                String temp = "";
                for (String key : map.keySet()) {
                    temp = temp + key + " : " + map.get(key) + "\n";
                }
                String sex = "0";
                if ("男".equals(map.get("gender"))) {
                    sex = "1";
                }
                if (((platform != SHARE_MEDIA.WEIXIN) && TextUtils.isEmpty(openid)) || ((platform == SHARE_MEDIA.WEIXIN) && TextUtils.isEmpty(map
                        .get("unionid")))) {
                    loadingDialog.dismiss();
                    ToastUtil.show("获取数据失败，请重试！");
                    return;
                }
                thirdLogin(accessToken, openid, flag,
                        "", map.get("city"), map.get("name"), map.get("iconurl"), sex, "", map
                                .get("unionid"), platform, rawDataKey + ":" + map.get(rawDataKey));

            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }
                throwable.printStackTrace();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }
            }
        });

    }

    /**
     * 第三方登录
     */
    private void thirdLogin(String accessToken, String openid, final String platform,
                            String birthday,
                            String city,
                            String nickname,
                            String photo,
                            String sex,
                            String signature,
                            final String unionid,
                            final SHARE_MEDIA share_media,
                            String rawData) {

        CommonScene.threeLoginProcess(accessToken, openid, platform, birthday, city, nickname, photo,
                sex, signature, unionid, Build.MODEL, Build.BRAND, new BaseObserver<LoginBean>() {
                    @Override
                    public void OnSuccess(LoginBean user) {
                        if (null != loadingDialog) {
                            loadingDialog.dismiss();
                        }

                        mShareAPI.deleteOauth(LoginActivity.this, share_media, null);
                        if (user.getUser() == null || user.getNewer()) {
                            skipTelephoneLogin(user, "6");
                        } else if (!TextUtils.isEmpty(user.getUser().getToken()) && TextUtils.isEmpty(user.getUser().getPhone())) {//老用户没有手机号码
                            saveLoginInfo(user.getUser());
                            skipTelephoneLogin(user, "7");
                        } else {
                            saveLoginInfo(user.getUser());
                            //判断有没有绑定上级
                            if (!user.getUser().isSkipping()) {//用户信息保存放在下级
                                skipTelephoneLogin(user, "8");
                            } else {
                                startActivity(SplashActivity.createIntent(mContext));
                                finish();
                            }
                        }
                    }

                    @Override
                    public void OnFail(int code, String err) {
                        super.OnFail(code, err);
                        if (null != loadingDialog) {
                            loadingDialog.dismiss();
                        }
                    }
                });
    }

    private void skipTelephoneLogin(LoginBean user, String type) {
        Intent intent = new Intent(LoginActivity.this, TelLoginActivity.class);
        intent.putExtra("type", type);
        if (user != null)
            intent.putExtra("handleToken", user.getHandleToken());
        if (type.equals("7") || type.equals("8"))
            intent.putExtra("loginUser", user.getUser());
        startActivity(intent);
    }


    private void saveLoginInfo(User user) {
        TagAliasOperatorHelper.getInstance().handleAction((++TagAliasOperatorHelper.sequence), new TagAliasOperatorHelper.TagAliasBean(user.getUnionid()));
        MyApplication.saveLoginUser(user);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mActivityStatus = false;
    }


    /**
     * 初始化权限
     */
    private void initPermissions() {
        if (!permissionCheck()) {
            if (Build.VERSION.SDK_INT >= 23) {
                ActivityCompat.requestPermissions(this, permissionManifest,
                        PERMISSION_REQUEST_CODE);
            }
        }
    }

    /**
     * 权限检查（适配6.0以上手机）
     */
    private boolean permissionCheck() {
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        String permission;
        for (int i = 0; i < permissionManifest.length; i++) {
            permission = permissionManifest[i];
            if (PermissionChecker.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionCheck = PackageManager.PERMISSION_DENIED;
            }
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }


    private void initEnvironmentButton() {
        final Button button = (Button) findViewById(R.id.btn_net_env);
        final Runnable setBtnText = new Runnable() {
            @Override
            public void run() {
                button.setText("网络环境：" + NetConfig.getEnvironmentName() + "\n(点击以修改。测试用，正式发布会删除)");
            }
        };
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MenuDialog(v.getContext()).setMenuText(NetConfig.ENVIRONMENT_NAMES)
                        .setOnItemClickListener(new MenuDialog.OnItemClickListener() {
                            @Override
                            public void onItemClick(MenuDialog dialog, int index) {
                                NetConfig.setEnvironment(index);
                                setBtnText.run();
                                Tip_dialog tip_dialog = new Tip_dialog(dialog.getContext(), "网络环境已设置为：[" + NetConfig.getEnvironmentName() +
                                        "]");
                                tip_dialog.setCancelable(false);
                                tip_dialog.setCanceledOnTouchOutside(false);
                                tip_dialog.setLinstener(new Tip_dialog.BtnClickLinstener() {
                                    @Override
                                    public void clickOk() {
                                        if (MyApplication.sCurrActivity != null) {
                                            MyApplication.sCurrActivity.finish();
                                        }
                                        System.exit(0);
                                    }

                                    @Override
                                    public void clickCancel() {

                                    }
                                });
                                tip_dialog.show();
                            }
                        }).show();
            }
        });
        setBtnText.run();
        if (!"release".equals(BuildConfig.BUILD_TYPE)) {
            button.setVisibility(View.VISIBLE);
        }

        // 秘籍：背景区域点击“上上下下左右左右BABA”显示网络环境按钮及开启Log。触碰点靠近外框为“上下左右”，触碰点x方向不那么靠边时按左右为“AB”
//        final int[] asx = {3, 5, 6, 4}; // L,A,B,R
//        final int[] asy = {1, 0, 0, 2}; // U,*,*,D
//        View view = findViewById(R.id.activity_login);
//        view.setTag(0L);
//        view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    int[] loc = new int[2];
//                    v.getLocationOnScreen(loc);
//                    float px = (event.getRawX() - loc[0]) / v.getWidth();       // persent
//                    float py = (event.getRawY() - loc[1]) / v.getHeight();
//                    int qx = Math.min(Math.max((int) (px * 4), 0), asx.length); // quarter
//                    int qy = Math.min(Math.max((int) (py * 4), 0), asy.length);
//                    int action = asy[qy] > 0 ? asy[qy] : asx[qx];               // action this time
//                    long actions = (Long) v.getTag();
//                    actions = ((actions << 4) | action) & 0xffffffffffffL;      // actions queue
//                    v.setTag(actions);
//                    if (actions == 0x112234346565L) { // UUDDLRLRBABA
//                        button.setVisibility(View.VISIBLE);
//                    }
//                }
//                return false;
//            }
//        });
    }


    @Subscriber(tag = Constants.EVENT_TAG_FINSISH_LOGIN_ACTIVITY, mode = ThreadMode.MAIN)
    private void finishActivity(FinishLoginActivity o) {
        finish();
    }
}
