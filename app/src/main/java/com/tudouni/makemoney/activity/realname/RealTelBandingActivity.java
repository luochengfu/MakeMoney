package com.tudouni.makemoney.activity.realname;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.activity.BaseActivity;
import com.tudouni.makemoney.activity.SplashActivity;
import com.tudouni.makemoney.activity.TelLoginActivity;
import com.tudouni.makemoney.model.User;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.Constants;
import com.tudouni.makemoney.utils.ForwardUtils;
import com.tudouni.makemoney.utils.InjectView;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.utils.ValidateUtil;
import com.tudouni.makemoney.view.CenterLoadingView;
import com.tudouni.makemoney.view.HintDialog;
import com.tudouni.makemoney.view.MyTitleBar;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

public class RealTelBandingActivity extends BaseActivity {
    private static final String TAG = "TelLoginActivity";

    @InjectView(id=R.id.title_bar)
    private MyTitleBar title_bar;

    @ViewInject(R.id.etTelNumber)
    EditText etTelNumber;

    @ViewInject(R.id.etCode)
    EditText etCode;

    @ViewInject(R.id.etInvitCode)
    EditText etInvitCode;

    @ViewInject(R.id.tvCode)
    TextView tvCode;

    @ViewInject(R.id.tvLogin)
    TextView tvLogin;

    @ViewInject(R.id.llXieyi)
    LinearLayout llXieyi;

    @ViewInject(R.id.tv_xieyi)
    TextView tv_xieyi;


    private InputMethodManager inputMethodManager;

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tel_banding);
        x.view().inject(this);
        initView();
    }

    private void initView() {
        user = MyApplication.getLoginUser();

        title_bar.setOnLeftClickListener(view -> {
            HintDialog hintDialog = new HintDialog(RealTelBandingActivity.this);
            hintDialog.setMessage("确定退出实名认证吗？");
            hintDialog.setLinstener(new HintDialog.OnDialogClickLinstener() {
                @Override
                public void onPositiveClick() {
                    finish();
                }

                @Override
                public void onNegativeClick() {

                }
            });
            hintDialog.show();
        });
        tv_xieyi.setOnClickListener(view -> {
            ForwardUtils.target(this, Constants.h5_loginXy);
        });
        focusAndUnFocus();
    }

    /**
     * 判断是否可以获取验证码和是否可以登录
     */
    private void focusAndUnFocus() {
        etTelNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String phone = etTelNumber.getText().toString();
                boolean bool = ValidateUtil.isMobileNO(phone);
                if (bool) {
                    enableTvCode();
                    enableTvLogin();
                } else {
                    disenableTvCode();
                    disenableTvLogin();
                }
            }
        });

        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String phone = etTelNumber.getText().toString();
                boolean bool = ValidateUtil.isMobileNO(phone);
                String code = etCode.getText().toString();
                if (bool && !TextUtils.isEmpty(code) && code.length() > 3) {
                    enableTvLogin();
                } else {
                    disenableTvLogin();
                }
            }
        });
    }

    private void disenableTvLogin() {
        tvLogin.setClickable(false);
        tvLogin.setBackground(getResources().getDrawable(R.drawable.login_btn_back_01));
    }

    private void enableTvLogin() {
        tvLogin.setClickable(true);
        tvLogin.setOnClickListener(view -> {
            login();
        });
        tvLogin.setBackground(getResources().getDrawable(R.drawable.login_btn_back));
    }

    private void disenableTvCode() {
        tvCode.setClickable(false);
        tvCode.setBackground(getResources().getDrawable(R.drawable.get_vcode_style_01));
    }


    private void enableTvCode() {
        tvCode.setClickable(true);
        tvCode.setOnClickListener(view -> {
            generateCode();
        });
        tvCode.setBackground(getResources().getDrawable(R.drawable.login_btn_back));
    }

    CenterLoadingView loadingDialog = null;

    private void login() {
        if (null == loadingDialog) {
            loadingDialog = new CenterLoadingView(RealTelBandingActivity.this);
        }
        loadingDialog.setTitle("绑定中");
        loadingDialog.show();
        String uid = "";
        String token = "";
        if (user != null) {
            uid = user.getUid();
            token = user.getToken();
        }

//        CommonScene.authBindPhone(etTelNumber.getText().toString(), etCode.getText().toString(), uid, token, new BaseObserver<String>() {
//            @Override
//            public void OnSuccess(String user) {
//                if (null != loadingDialog) {
//                    loadingDialog.dismiss();
//                }
//                User _user = MyApplication.getLoginUser();
//                _user.setBindPhoneStatus("1");
//                MyApplication.saveLoginUser(_user);
//                if (RealTelBandingActivity.this.user != null) {
//                    saveLoginInfo(RealTelBandingActivity.this.user);
//                }
//
//                ForwardUtils.target(RealTelBandingActivity.this, Constants.REALNAME2);
//                finish();
//            }
//
//            @Override
//            public void OnFail(int code, String err) {
//                super.OnFail(code, err);
//                if (null != loadingDialog) {
//                    loadingDialog.dismiss();
//                }
//            }
//        });
    }

    private void saveLoginInfo(User user) {
        MyApplication.saveLoginUser(user);

    }

    private void generateCode() {
        if (null == loadingDialog) {
            loadingDialog = new CenterLoadingView(RealTelBandingActivity.this);
        }
        loadingDialog.setTitle("获取中");
        loadingDialog.show();

        CommonScene.getMsgCode(etTelNumber.getText().toString(),  new BaseObserver<String>() {
            @Override
            public void OnSuccess(String s) {
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }
                myThread = new MyThread();
                myThread.run();
            }

            @Override
            public void OnFail(int code, String err) {
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }
                Toast.makeText(RealTelBandingActivity.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public int state = 1; //状态 1 表示未启动线程或正在运行线程。0 停止线程
    private MyThread myThread;

    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            new AsyncTask<Object, Object, Object>() {
                @Override
                protected Object doInBackground(Object... arg0) {
                    for (int i = 60; i >= 0; i--) {
                        if (state == 0) { // 停止线程
                            return null;
                        }
                        if (i == 0) {
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
                        if ("剩余59秒".equals(values[0])) {
                            disenableTvCode();
                        } else if ("获取验证码".equals(values[0])) {
                            enableTvCode();
                        }
                        tvCode.setText(values[0].toString());
                    }
                }
            }.execute();
        }
    }
}
