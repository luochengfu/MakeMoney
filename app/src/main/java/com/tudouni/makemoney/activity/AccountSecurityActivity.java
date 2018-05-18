package com.tudouni.makemoney.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.model.AliAuth;
import com.tudouni.makemoney.model.BindInfo;
import com.tudouni.makemoney.model.PayBindingInfo;
import com.tudouni.makemoney.model.User;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.AuthResult;
import com.tudouni.makemoney.utils.InjectView;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.view.CenterLoadingView;
import com.tudouni.makemoney.view.HintDialog;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;


import java.util.HashMap;
import java.util.Map;

public class AccountSecurityActivity extends BaseActivity implements View.OnClickListener
{
    private static final String TAG = "AccountSecurityActivity";
    @InjectView(id = R.id.llWechat)
    private LinearLayout llWechat;
    @InjectView(id = R.id.llQq)
    private LinearLayout llQq;
    @InjectView(id = R.id.llPhone)
    private LinearLayout llPhone;
    @InjectView(id = R.id.llAlipay)
    private LinearLayout llAlipay;
    @InjectView(id = R.id.tvWechat)
    private TextView tvWechat;
    @InjectView(id = R.id.tvQq)
    private TextView tvQq;
    @InjectView(id = R.id.tvPhone)
    private TextView tvPhone;
    @InjectView(id = R.id.tvAlipay)
    private TextView tvAlipay;
    @InjectView(id = R.id.ll_set)
    private LinearLayout llSet;
    @InjectView(id = R.id.security_hint)
    private TextView securityTV;
    private CenterLoadingView loadingDialog = null;
    private ImageView jiantou, jiantou2, jiantou3;
    private UMShareAPI mShareAPI;

    private TextView tv_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_security);
        initView();
        mShareAPI = UMShareAPI.get(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initBindInfo();
        if ("1".equals(MyApplication.getLoginUser().getPwd())) {
            tv_set.setText("已设置");
            llSet.setVisibility(View.VISIBLE);
            securityTV.setVisibility(View.VISIBLE);
        } else {
            tv_set.setText("未设置");
            llSet.setVisibility(View.GONE);
            securityTV.setVisibility(View.GONE);
        }
    }

    /**
     * 获取绑定信息
     */
    private BindInfo info;

    private void initBindInfo() {
        if (null == loadingDialog) {
            loadingDialog = new CenterLoadingView(AccountSecurityActivity.this);
            loadingDialog.setTitle("正在加载");
        }
        loadingDialog.show();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                CommonScene.accountBind(new BaseObserver<BindInfo>() {
                    @Override
                    public void OnSuccess(BindInfo bindInfo) {
                        AccountSecurityActivity.this.info = bindInfo;
                        if (null != loadingDialog) {
                            loadingDialog.dismiss();
                        }
                        upStatus(info);
                    }

                    @Override
                    public void OnFail(int code, String err) {
                        super.OnFail(code, err);
                        if (null != loadingDialog) {
                            loadingDialog.dismiss();
                        }
                    }
                });

                loadPayInfo();
            }
        }, 10);
    }

    private void loadPayInfo() {
        CommonScene.payStatus(new BaseObserver<PayBindingInfo>() {
            @Override
            public void OnSuccess(PayBindingInfo payBindingInfo) {
                initPayInfo(payBindingInfo);
            }
        });
    }

    private void upStatus(BindInfo info) {
        if (null != loadingDialog) {
            loadingDialog.dismiss();
        }
        if (info.getPhone().equals("0")) {
            tvPhone.setText("未绑定");
        } else {
            tvPhone.setText(info.getPhoneHid());
            jiantou.setVisibility(View.INVISIBLE);
        }

        if (info.getPhone().equals("1")) {
            llSet.setVisibility(View.VISIBLE);
            securityTV.setVisibility(View.VISIBLE);
        } else {
            llSet.setVisibility(View.GONE);
            securityTV.setVisibility(View.GONE);
        }

        if (info.getWeixin().equals("0")) {
            tvWechat.setText("未绑定");
        } else {
            tvWechat.setText("已绑定");
            jiantou3.setVisibility(View.INVISIBLE);
        }
        if (info.getQq().equals("0")) {
            tvQq.setText("未绑定");
        } else {
            tvQq.setText("已绑定");
            jiantou2.setVisibility(View.INVISIBLE);
        }
    }

    private void initPayInfo(PayBindingInfo info) {
        View arrow = ((ViewGroup) tvAlipay.getParent()).getChildAt(3);
        if ("1".equals(info.getAlipay())) {
            tvAlipay.setText("已绑定");
            arrow.setVisibility(View.INVISIBLE);
            llAlipay.setClickable(false);
        } else {
            tvAlipay.setText("未绑定");
            arrow.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        jiantou = (ImageView) findViewById(R.id.jiantou);
        jiantou2 = (ImageView) findViewById(R.id.jiantou2);
        jiantou3 = (ImageView) findViewById(R.id.jiantou3);
        tv_set = (TextView) findViewById(R.id.tv_set);
        llWechat.setOnClickListener(this);
        llQq.setOnClickListener(this);
        llPhone.setOnClickListener(this);
        llAlipay.setOnClickListener(this);
        llSet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llWechat:
                if (null == info)
                    return;
                if (info.getWeixin().equals("0")) {
                    banding(SHARE_MEDIA.WEIXIN);
                } else {
                    setTip(v);
                }
                break;
            case R.id.llQq:
                if (null == info)
                    return;
                if (info.getQq().equals("0")) {
                    banding(SHARE_MEDIA.QQ);
                } else {
                    setTip(v);
                }
                break;
            case R.id.llPhone:
                ToastUtil.show("暂不支持更换绑定手机");
                /*if (null == info)
                    return;
                if (info != null && "0".equals(info.getPhone())) {
                    Intent intent = new Intent(this, TelLoginActivity.class);
                    intent.putExtra("type", "2");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, TelLoginActivity.class);
                    intent.putExtra("type", "4");
                    startActivity(intent);
                }*/
                break;
            case R.id.llAlipay:
                startAuthAlipay();
                break;
            case R.id.ll_set:
                if ("1".equals(MyApplication.getLoginUser().getPwd())) {//已设置过登录密码
                    Intent intent = new Intent(this, ModifyPasswordActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, PwdActivity.class);
                    intent.putExtra("type", "4");
                    startActivityForResult(intent, 0x200);
                }
                break;
        }
    }

    private void setTip(final View view) {
        final HintDialog hintDialog = new HintDialog(this);
        hintDialog.setPositiveText("确定");
        hintDialog.setNegativeText("取消");
        hintDialog.setMessage("是否解绑");
        hintDialog.setLinstener(new HintDialog.OnDialogClickLinstener() {
            @Override
            public void onPositiveClick() {
                switch (view.getId()) {
                    case R.id.llWechat:
                        unBand(SHARE_MEDIA.WEIXIN);
                        break;
                    case R.id.llQq:
                        unBand(SHARE_MEDIA.QQ);
                        break;
                }
            }

            @Override
            public void onNegativeClick() {
                hintDialog.dismiss();
            }
        });
        hintDialog.show();
    }

    private void banding(final SHARE_MEDIA platform) {
        loading("授权中");
        mShareAPI.getPlatformInfo(AccountSecurityActivity.this, platform, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                loading("绑定中");
                String flag = "";
                if (null == map) {
                    ToastUtil.show("授权失败,请重试");
                    loadingDialog.dismiss();
                    return;
                }
                String accessToken = map.get("access_token");
                String openid = "";
                if (platform == SHARE_MEDIA.QQ) {
                    flag = "1";
                } else if (platform == SHARE_MEDIA.WEIXIN) {
                    flag = "2";
                    openid = map.get("openid");
                } else if (platform == SHARE_MEDIA.SINA) {
                    flag = "3";
                }

                tobanding(accessToken, openid, flag);
                mShareAPI.deleteOauth(AccountSecurityActivity.this, platform, null);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                dissLoad();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

                dissLoad();
            }
        });
    }

    private void unBand(final SHARE_MEDIA platform) {
        loading("授权中");
        mShareAPI.getPlatformInfo(AccountSecurityActivity.this, platform, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                loading("解绑中");
                String flag = "";
                if (null == map) {
                    ToastUtil.show("授权失败,请重试");
                    loadingDialog.dismiss();
                    return;
                }
                String accessToken = map.get("access_token");
                String openid = "";
                if (platform == SHARE_MEDIA.QQ) {
                    flag = "1";
                } else if (platform == SHARE_MEDIA.WEIXIN) {
                    flag = "2";
                    openid = map.get("openid");
                }
                unBindRequest(accessToken, openid, flag);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                dissLoad();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

                dissLoad();
            }
        });
    }

    private void unBindRequest(String acessToken, String openid, final String platform) {
        CommonScene.unbindThree(acessToken, openid, platform, new BaseObserver<String>() {
            @Override
            public void OnSuccess(String s) {
                dissLoad();
                if ("1".equals(platform)) {
                    tvQq.setText("未绑定");
                    info.setQq("0");
                    jiantou2.setVisibility(View.VISIBLE);
                } else if ("2".equals(platform)) {
                    tvWechat.setText("未绑定");
                    info.setWeixin("0");
                    jiantou3.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void OnFail(int code, String err) {
                super.OnFail(code, err);
                dissLoad();
            }
        });
    }

    private void tobanding(String accessToken, String openid, final String flag)
    {
        CommonScene.bindAccount(accessToken, openid, flag, new BaseObserver<String>() {
            @Override
            public void OnSuccess(String s) {
                dissLoad();
                if ("1".equals(flag)) {
                    tvQq.setText("已绑定");
                    info.setQq("1");
                    jiantou2.setVisibility(View.INVISIBLE);
                } else if ("2".equals(flag)) {
                    tvWechat.setText("已绑定");
                    info.setWeixin("1");
                    jiantou3.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void OnFail(int code, String err) {
                super.OnFail(code, err);
                dissLoad();
            }
        });
    }

    private void startAuthAlipay()
    {
        loading("认证中...");
        CommonScene.aliAuth(new BaseObserver<AliAuth>() {
            @Override
            public void OnSuccess(AliAuth aliAuth) {
                Runnable authRunnable = new Runnable() {

                    @Override
                    public void run() {
                        AuthTask authTask = new AuthTask(AccountSecurityActivity.this);
                        Map<String, String> result = authTask.authV2(aliAuth.getPayInfo(), true);
                        AuthResult authResult = new AuthResult(result, true);
                        String resultStatus = authResult.getResultStatus();
                        if ("9000".equals(resultStatus) && "200".equals(authResult.getResultCode())) {
                            CommonScene.bindAlipay(authResult.getAuthCode(), new BaseObserver<String>() {
                                @Override
                                public void OnSuccess(String s) {
                                    Message msg = new Message();
                                    msg.what = 222;
                                    msg.obj = "认证成功";
                                    payHandler.sendMessage(msg);
                                }

                                @Override
                                public void OnFail(int code, String err) {
                                    Message msg = new Message();
                                    msg.what = 111;
                                    msg.obj = err;
                                    payHandler.sendMessage(msg);
                                }
                            });
                        } else {
                            Message msg = new Message();
                            msg.what = 111;
                            msg.obj = "操作失败";
                            payHandler.sendMessage(msg);
                        }
                    }
                };
                // 必须异步调用
                Thread authThread = new Thread(authRunnable);
                authThread.start();
            }

            @Override
            public void OnFail(int code, String err) {
                super.OnFail(code, err);
                AccountSecurityActivity.this.dissLoad();
            }
        });
    }

    private Handler payHandler = new Handler() {

        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 111: {
                    AccountSecurityActivity.this.dissLoad();
                    Toast.makeText(AccountSecurityActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                }
                case 222: {
                    AccountSecurityActivity.this.dissLoad();
                    Toast.makeText(AccountSecurityActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    loadPayInfo();
                    break;
                }
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        dissLoad();
    }
}
