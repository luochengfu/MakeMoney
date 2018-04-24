package com.tudouni.makemoney.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.tudouni.makemoney.Listener.NoDoubleClickListener;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.model.LoginBean;
import com.tudouni.makemoney.model.User;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.myApplication.jPush.TagAliasOperatorHelper;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.CommonUtil;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.utils.ValidateUtil;
import com.tudouni.makemoney.view.CenterLoadingView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends BaseActivity implements View.OnClickListener
{
    private final String TAG = "LoginActivity";

    private Context mContext = this;
    private ImageView iv_WechatLogin, iv_QqLogin;
    private TextView loginModeChangeView, mLossPasswordView;
    private TextView buttomTV, submitBtn, tvCode, mLossPasswordBtn;
    private EditText mPhoneNumberInput, mPasswrodInput, mPhoneNumberInput2, mPhoneCodeInput;
    private View mLine1, mLine2;
    private LinearLayout mTelLogin1, mTelLogin2;
    private UMShareAPI mShareAPI = null;
    private CenterLoadingView loadingDialog = null;
    private final String[] permissionManifest = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };
    private int mLoginModeStatus = 2;//登录方式切换状态
    public int state = 1; //状态 1 表示未启动线程或正在运行线程。0 停止线程
    private ExecutorService mExecutouService;
    private String mPhoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        tvCode.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                MobclickAgent.onEvent(LoginActivity.this, "lg_sms");
                generateCode();
            }
        });

        buttomTV.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                MobclickAgent.onEvent(LoginActivity.this, "lg_deal");
//                ForwardUtils.target(LoginActivity.this, Constant.h5_loginXy);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO 有bug，没有释放引用，Activity依然驻留内存
        mShareAPI.release();
    }

    private void initView() {
        iv_WechatLogin = (ImageView) findViewById(R.id.iv_WechatLogin);
        iv_QqLogin = (ImageView) findViewById(R.id.iv_QqLogin);
        buttomTV = (TextView) findViewById(R.id.xy);
        loginModeChangeView = (TextView) findViewById(R.id.login_mode_change_view);
        mPhoneNumberInput = (EditText) findViewById(R.id.phone_number_et);
        mPasswrodInput = (EditText) findViewById(R.id.comfirm_pasword_et);
        mLine1 = findViewById(R.id.password_login_child_layout_line);
        mLine2 = findViewById(R.id.tel_login_line);
        mPhoneNumberInput2 = (EditText) findViewById(R.id.etTelNumber);
        mPhoneCodeInput = (EditText) findViewById(R.id.etCode);
        mTelLogin1 = (LinearLayout) findViewById(R.id.tel_phone_ll_layout_1);
        mTelLogin2 = (LinearLayout) findViewById(R.id.tel_phone_ll_layout_2);
        mLossPasswordView = (TextView) findViewById(R.id.loss_password_tv);
        submitBtn = (TextView) findViewById(R.id.tv_commit);
        tvCode = (TextView) findViewById(R.id.tvCode);
        mLossPasswordBtn = (TextView) findViewById(R.id.loss_password_tv);
    }

    private void initDatas() {
        mExecutouService = Executors.newSingleThreadExecutor();
        loginModeChangeView.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        mLossPasswordBtn.setOnClickListener(this);
        //获取设备ID
        /*if (!permissionCheck()) {
            if (Build.VERSION.SDK_INT >= 23) {
                ActivityCompat.requestPermissions(this, permissionManifest, 0);
            }
        } else {
//            App.setDeviceId();
        }*/
        //是否有更新
//        UpdateAPKUtil.uptateAPK(this);
        //如果维护中
        /*if (App.appConfig.isMaintain()) {
            new MaintinDialog(this, App.appConfig.getShutdownMaintainMsg()).show();
        }*/

        //密码登录监听
        passwordLoginStatus();
        //验证码登录
        telLoginStatus();
//        initEnvironmentButton();
        noDoubleClick();
    }

    @Override
    public void onClick(View v) {
        String statisticsType = null;
        switch (v.getId()) {
            case R.id.login_mode_change_view:
                if(mLoginModeStatus == 1) {
                    mLoginModeStatus = 2;
                } else if(mLoginModeStatus == 2) {
                    mLoginModeStatus = 1;
                }
                loginModeChange();
                break;
            case R.id.tv_commit:
                if(mLoginModeStatus == 1) {
                    passwordSubmit();
                    statisticsType = "lg_shaclick";
                } else if(mLoginModeStatus == 2) {
                    codeSubmit();
                    statisticsType = "lg_lgclick";
                }
                break;
            case R.id.loss_password_tv:
                Intent intent = new Intent(LoginActivity.this, TelLoginActivity.class);
                intent.putExtra("type", "3");
                startActivity(intent);
                break;
        }

        if (!TextUtils.isEmpty(statisticsType))
            MobclickAgent.onEvent(this, statisticsType);
    }

    /**
     * 验证码登录
     */
    private void codeSubmit() {
        String phone = mPhoneNumberInput2.getText().toString();
        String code = mPhoneCodeInput.getText().toString();

        boolean bool = ValidateUtil.isMobileNO(phone);
        if(!bool) {
            return;
        }

        if(phone == null || code == null || phone.equals("") || code.equals(""))
            return;
        if (null == loadingDialog) {
            loadingDialog = new CenterLoadingView(LoginActivity.this);
        }
        loadingDialog.setTitle("登录中...");
        loadingDialog.show();

        CommonScene.telCodeLogin(phone, code, Build.MODEL, Build.BRAND, new BaseObserver<User>() {
            @Override
            public void OnSuccess(User user) {
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }
                if ("0".equals(user.getPwd())) {//没有设置登录密码
                    Intent intent1 = new Intent(LoginActivity.this, PwdActivity.class);
                    intent1.putExtra("type", "1");
                    intent1.putExtra("user", user);
                    startActivity(intent1);
                }else {//设置了登录密码
                    saveLoginInfo(user);
                    startActivity(new Intent(LoginActivity.this,SplashActivity.class));
                    finish();
                }
            }

            @Override
            public void OnFail(int code, String err) {
                ToastUtil.show(err);
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }
            }
        });
    }


    /**
     * 密码登录
     */
    private void passwordSubmit() {
        String userName = mPhoneNumberInput.getText().toString();
        String password = mPasswrodInput.getText().toString();

        boolean bool = ValidateUtil.isMobileNO(userName);
        if(!bool) {
            return;
        }

        if (userName == null || userName.trim().equals("") ||
                password == null || password.trim().equals("")
                || password.length() < 6 || password.length() > 16 || userName.length() != 11) {
            return;
        }
        if (null == loadingDialog) {
            loadingDialog = new CenterLoadingView(LoginActivity.this);
        }
        loadingDialog.setTitle("登录中");
        loadingDialog.show();

        CommonScene.passwordLogin(userName, password, Build.MODEL, Build.BRAND, new BaseObserver<User>() {
            @Override
            public void OnSuccess(User user) {
                MyApplication.saveLoginUser(user);
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }
                saveLoginInfo(user);
                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                startActivityForResult(SplashActivity.createIntent(mContext),0x200);
                finish();
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

    /**
     * 点击切换
     */
    private void loginModeChange() {
        if(mLoginModeStatus == 1) {
            mPhoneNumberInput.setText(mPhoneNum);
            setLoginBtnStatus(mPhoneNumberInput,mPasswrodInput);
            loginModeChangeView.setText(getResources().getString(R.string.telLogin));
            mPhoneNumberInput.setVisibility(View.VISIBLE);
            mPasswrodInput.setVisibility(View.VISIBLE);
            mLine1.setVisibility(View.VISIBLE);
            mLossPasswordView.setVisibility(View.VISIBLE);

            mTelLogin1.setVisibility(View.GONE);
            mTelLogin2.setVisibility(View.GONE);
            mLine2.setVisibility(View.GONE);
        } else if(mLoginModeStatus == 2) {
            mPhoneNumberInput2.setText(mPhoneNum);
            loginModeChangeView.setText(getResources().getString(R.string.tudouni_password_login_change));
            mPhoneNumberInput.setVisibility(View.GONE);
            mPasswrodInput.setVisibility(View.GONE);
            mLine1.setVisibility(View.GONE);
            mLossPasswordView.setVisibility(View.GONE);

            mTelLogin1.setVisibility(View.VISIBLE);
            mTelLogin2.setVisibility(View.VISIBLE);
            mLine2.setVisibility(View.VISIBLE);
            setLoginBtnStatus(mPhoneNumberInput2,mPhoneCodeInput);
        }
    }

    /**
     * 设置登录按钮状态
     */
    private void setLoginBtnStatus(EditText editText1, EditText editText2) {
        String number = editText1.getText().toString();
        String password = editText2.getText().toString();
        if ((null != number && !number.equals(""))
                && (null != password && !password.equals(""))
                && (number.length() == 11) && (password.length() >= 6) && password.length() <= 16) {
            submitBtn.setSelected(true);
            submitBtn.setTextColor(getResources().getColor(R.color.color_333333));
        } else {
            submitBtn.setSelected(false);
            submitBtn.setTextColor(getResources().getColor(R.color.color_999999));
        }
    }

    /**
     * 密码登录模块监听
     */
    private void passwordLoginStatus() {
        mPhoneNumberInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String number = s.toString();
                mPhoneNum = number;
                String password = mPasswrodInput.getText().toString();
                if ((null != number && !number.equals(""))
                        && (null != password && !password.equals(""))
                        && (number.length() == 11) && (password.length() >= 6)
                        && password.length() <= 16 && ValidateUtil.isMobileNO(number)) {
                    submitBtn.setSelected(true);
                    submitBtn.setTextColor(getResources().getColor(R.color.color_333333));
                } else {
                    submitBtn.setSelected(false);
                    submitBtn.setTextColor(getResources().getColor(R.color.color_999999));
                }
            }
        });

        mPasswrodInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String number = mPhoneNumberInput.getText().toString();
                String password = s.toString();
                if ((null != number && !number.equals(""))
                        && (null != password && !password.equals(""))
                        && (number.length() == 11) && (password.length() >= 6)
                        && password.length() <= 16 && ValidateUtil.isMobileNO(number)) {
                    enableTvLogin();
                } else {
                    disenableTvLogin();
                }
            }
        });

    }

    private void telLoginStatus() {
        mPhoneNumberInput2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String phone = s.toString();
                mPhoneNum = phone;
                boolean bool = ValidateUtil.isMobileNO(phone);
                String code = mPhoneCodeInput.getText().toString();
                if (bool) {
                    enableTvCode();
                } else {
                    disenableTvCode();
                }

                if (bool && !TextUtils.isEmpty(code) && code.length() > 3) {
                    enableTvLogin();
                } else {
                    disenableTvLogin();
                }
            }
        });

        mPhoneCodeInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String phone = mPhoneNumberInput2.getText().toString();
                boolean bool = ValidateUtil.isMobileNO(phone);
                String code = s.toString();
                if (bool && !TextUtils.isEmpty(code) && code.length() > 3) {
                    enableTvLogin();
                } else {
                    disenableTvLogin();
                }
            }
        });

    }

    private void disenableTvLogin() {
        submitBtn.setTextColor(getResources().getColor(R.color.color_999999));
        submitBtn.setSelected(false);
    }

    private void enableTvLogin() {
        submitBtn.setTextColor(getResources().getColor(R.color.color_333333));
        submitBtn.setSelected(true);
    }

    private void disenableTvCode() {
        tvCode.setClickable(false);
        tvCode.setTextColor(getResources().getColor(R.color.color_999999));
        tvCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.get_vcode_style_01));
    }

    private void enableTvCode() {
        tvCode.setClickable(true);
        tvCode.setTextColor(getResources().getColor(R.color.color_333333));
        tvCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.get_vcode_style_02));
    }

    private void enableClickCode() {
        tvCode.setClickable(false);
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
        if (null == loadingDialog) {
            loadingDialog = new CenterLoadingView(LoginActivity.this);
        }

        mShareAPI.getPlatformInfo(LoginActivity.this, platform, new UMAuthListener()
        {
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
     *
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
                        if(user.getUser() == null || user.getNewer()) {
                            skipTelephoneLogin(user,"6");
                        } else if(null == user.getUser().getPhone() || "".equals(user.getUser().getPhone())){//老用户没有手机号码
                            skipTelephoneLogin(user,"6");
                        }else {
                            saveLoginInfo(user.getUser());
                            //防止点击第三方登录取消在点击密码登录的功能
                            startActivity(SplashActivity.createIntent(mContext));
                            finish();
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
        intent.putExtra("handleToken",user.getHandleToken());
        if(type.equals("7")) {
            intent.putExtra("loginUser",user.getUser());
        }
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
    }

    /**
     * 权限检查（适配6.0以上手机）
     */
   /* private boolean permissionCheck() {
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
                                        if(App.sCurrActivity != null){
                                            App.sCurrActivity.finish();
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
        final int[] asx = {3, 5, 6, 4}; // L,A,B,R
        final int[] asy = {1, 0, 0, 2}; // U,*,*,D
        View view = findViewById(R.id.activity_login);
        view.setTag(0L);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int[] loc = new int[2];
                    v.getLocationOnScreen(loc);
                    float px = (event.getRawX() - loc[0]) / v.getWidth();       // persent
                    float py = (event.getRawY() - loc[1]) / v.getHeight();
                    int qx = Math.min(Math.max((int) (px * 4), 0), asx.length); // quarter
                    int qy = Math.min(Math.max((int) (py * 4), 0), asy.length);
                    int action = asy[qy] > 0 ? asy[qy] : asx[qx];               // action this time
                    long actions = (Long) v.getTag();
                    actions = ((actions << 4) | action) & 0xffffffffffffL;      // actions queue
                    v.setTag(actions);
                    if (actions == 0x112234346565L) { // UUDDLRLRBABA
                        button.setVisibility(View.VISIBLE);
                        LogUtil.setLogLevel(LogUtil.VERBOSE);
                        ToastUtils.showToast(v.getContext(), "Log已开启");
                    }
                }
                return false;
            }
        });
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        if(!"".equals(MyApplication.mNewUserClipPhone))
        {
            mPhoneNumberInput2.setText(MyApplication.mNewUserClipPhone);
            MyApplication.mNewUserClipPhone = "";
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (0 != (Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags())) {
            finish();
        }
    }

    /**
     * 获取验证码
     */
    private void generateCode() {
        String phone = mPhoneNumberInput2.getText().toString();
        if(null == phone || "".equals(phone))
            return;

        if (null == loadingDialog) {
            loadingDialog = new CenterLoadingView(LoginActivity.this);
        }
        loadingDialog.setTitle("获取中");
        loadingDialog.show();
        CommonScene.getMsgCode(phone,  new BaseObserver<String>() {
            @Override
            public void OnSuccess(String s) {
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }
                mExecutouService.execute(new Runnable() {
                    @Override
                    public void run() {
                        new MyAsyncTask().execute();
                    }
                });
            }

            @Override
            public void OnFail(int code, String err) {
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }
                Toast.makeText(LoginActivity.this, err, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 定义任务类，用于实现倒计时功能
     */
    public class MyAsyncTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            for (int i = 60; i > 0; i--) {
                if (state == 0) { // 停止线程
                    return null;
                }
                if (i == 1) {
                    publishProgress("获取验证码");
                } else {
                    // 剩余多少秒
                    publishProgress("" + (i - 1) + "");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
            if (values.length > 0 && values[0] != null) {
                if ("获取验证码".equals(values[0])) {
                    enableTvCode();
                } else {
                    enableClickCode();
                }
                tvCode.setText(values[0].toString());
            }
        }
    }
}
