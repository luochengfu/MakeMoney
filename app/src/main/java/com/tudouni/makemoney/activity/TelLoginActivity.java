package com.tudouni.makemoney.activity;


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
import com.tudouni.makemoney.interfaces.NoDoubleClickListener;
import com.tudouni.makemoney.model.User;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.NetConfig;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.InjectView;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.utils.ValidateUtil;
import com.tudouni.makemoney.view.CenterLoadingView;
import com.tudouni.makemoney.view.MyTitleBar;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

/**
 * 手机号登录
 */
public class TelLoginActivity extends BaseActivity implements View.OnClickListener {


    private static final String TAG = "TelLoginActivity";
    @InjectView(id = R.id.title_bar)
    private MyTitleBar title_bar;

    @InjectView(id = R.id.etTelNumber)
    EditText etTelNumber;

    @InjectView(id = R.id.etCode)
    EditText etCode;

    @InjectView(id = R.id.etInvitCode)
    EditText etInvitCode;

    @InjectView(id = R.id.tvCode)
    TextView tvCode;


    @InjectView(id = R.id.tvLogin)
    TextView tvLogin;

    //手机登录(1)、绑定手机号码(2)、找回密码(3)、解除原手机号(4)、绑定新手机号(5)、新用户注册绑定手机号(6)、登录时第三方老用户没有绑定手机号码(7)
    private  CenterLoadingView loadingDialog = null;
    private String pageType;
    private InputMethodManager inputMethodManager;
    private Context mContext;
    private TextWatcher mTelNumberWatcher;
    private TextWatcher mCodeWatcher;
    private String verifyToken;
    private String handleToken;
    private boolean mCodeBtnStatus = true;
    private boolean mActivityStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_tel_login);
        initView();
        initData();
    }


    private void initView() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mActivityStatus = false;
    }

    /**
     * 获取数据
     */
    private void initData() {
        mActivityStatus = true;
        Intent intent = getIntent();
        pageType = intent.getStringExtra("type");
        if(pageType.equals("6")) {
            handleToken = intent.getStringExtra("handleToken");
        }

        if (pageType.equals("1")) {
            title_bar.setMiddleText(getResources().getString(R.string.telLogin));
        } else if (pageType.equals("2")) {
            title_bar.setMiddleText(getResources().getString(R.string.telbandin));
            tvLogin.setText(getResources().getString(R.string.verification));
        } else if (pageType.equals("3")) {
            title_bar.setMiddleText(getResources().getString(R.string.find_passsword_hint));
            tvLogin.setText(getResources().getString(R.string.next_step));
        } else if (pageType.equals("4")) {//解除原手机号
            title_bar.setMiddleText(getResources().getString(R.string.removetelbandin));
            tvLogin.setText(getResources().getString(R.string.verification));
        } else if (pageType.equals("5")) {//绑定新手机号
            verifyToken = getIntent().getStringExtra("verifyToken");
            title_bar.setMiddleText(getResources().getString(R.string.telbandinnew));
            tvLogin.setText(getResources().getString(R.string.verification));
        } else if(pageType.equals("6")) {
            title_bar.setMiddleText(getResources().getString(R.string.telbandin));
            tvLogin.setText(getResources().getString(R.string.sure));
        } else if(pageType.equals("7")) {
            MyApplication.saveLoginUser((User) getIntent().getSerializableExtra("loginUser"));
            title_bar.setMiddleText(getResources().getString(R.string.telbandin));
            tvLogin.setText(getResources().getString(R.string.sure));
            /*title_bar.setRightText("跳过");
            title_bar.setOnRightClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //保存数据到本地
                    Intent intent = new Intent(TelLoginActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    startActivity(SplashActivity.createIntent(mContext));
                    finish();
                }
            });*/

            title_bar.setOnLeftClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyApplication.logout();
                    finish();
                }
            });
        }

        focusAndUnFocus();

        tvCode.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                String input = etTelNumber.getText().toString();
                if(null == input || "".equals(input) ||  !ValidateUtil.isMobileNO(input)|| !mCodeBtnStatus)
                    return;
                generateCode();
            }
        });

        title_bar.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityStatus = false;
                finish();
            }
        });
        tvLogin.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 0x200 && resultCode == 0x200) || (requestCode == 0x201 && resultCode == 0x201)) {
            finish();
        }
    }

    /**
     * 判断是否可以获取验证码和是否可以登录
     */
    private void focusAndUnFocus() {
        mTelNumberWatcher = new TextWatcher() {
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
                } else {
                    disenableTvCode();
                }
                String code = etCode.getText().toString();
                if(bool && code.length() > 3) {
                    enableTvLogin();
                } else {
                    disenableTvLogin();
                }
            }
        };
        etTelNumber.addTextChangedListener(mTelNumberWatcher);

        mCodeWatcher = new TextWatcher() {
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
        };

        etCode.addTextChangedListener(mCodeWatcher);
    }

    @Override
    protected void onDestroy() {
        etTelNumber.removeTextChangedListener(mTelNumberWatcher);
        etCode.removeTextChangedListener(mCodeWatcher);
        mTelNumberWatcher = null;
        mCodeWatcher = null;
        super.onDestroy();
    }

    private void disenableTvLogin() {
        tvLogin.setClickable(false);
        tvLogin.setTextColor(getResources().getColor(R.color.color_999999));
        tvLogin.setSelected(false);
    }

    private void enableTvLogin() {
        tvLogin.setClickable(true);
        tvLogin.setTextColor(getResources().getColor(R.color.white));
        tvLogin.setSelected(true);
    }

    private void disenableTvCode() {
        tvCode.setClickable(false);
        tvCode.setTextColor(getResources().getColor(R.color.color_999999));
        tvCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.get_vcode_style_01));
    }


    private void enableTvCode() {
        String input = etTelNumber.getText().toString();
        if(input == null || "".equals(input) ||  !ValidateUtil.isMobileNO(input)){
            disenableTvCode();
        } else {
            tvCode.setClickable(true);
            tvCode.setTextColor(getResources().getColor(R.color.white));
            tvCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.get_vcode_style_02));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCode:
                generateCode();
                break;
            case R.id.tvLogin:
                String phone = etTelNumber.getText().toString();
                boolean bool = ValidateUtil.isMobileNO(phone);
                String code = etCode.getText().toString();
                if (!bool && TextUtils.isEmpty(code) && code.length() < 3) {
                    return;
                }
                if (pageType.equals("1")) {
                    login("登录中");
                } else if (pageType.equals("2") || pageType.equals("3") || pageType.equals("4") || pageType.equals("5") || pageType.equals("6") || pageType.equals("7")) {
                    login("请稍候");
                }
                break;

        }
    }

    private void login(String hint) {
        if (null == loadingDialog) {
            loadingDialog = new CenterLoadingView(TelLoginActivity.this);
        }
        loadingDialog.setTitle(hint);
        loadingDialog.show();
        final Map<String, String> params = new HashMap<String, String>();

        if (pageType.equals("2") || pageType.equals("7")) {
            bindPhone();
        } else if (pageType.equals("4")) {
            verifcodeChange();
        } else if (pageType.equals("5")) {
           bindPhone2();
        } else if (pageType.equals("3")) {//找回密码
            findPassword();
        } else if(pageType.equals("6")) {
           bindNewPhone();
        }
    }

    /**
     * 新用户注册绑定手机号
     */
    private void bindNewPhone() {
        CommonScene.bindNewPhone(etTelNumber.getText().toString(), etCode.getText().toString(), android.os.Build.MODEL, android.os.Build.BRAND, handleToken, new BaseObserver<User>() {
            @Override
            public void OnSuccess(User user) {
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }

                saveLoginInfo(user);
                startActivityForResult(SplashActivity.createIntent(mContext),0x200);
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
     * 找回密码
     */
    private void findPassword() {
        CommonScene.findPassword(etTelNumber.getText().toString(), etCode.getText().toString(), android.os.Build.MODEL, android.os.Build.BRAND, new BaseObserver<User>() {
            @Override
            public void OnSuccess(User user) {
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }

                Intent intent = new Intent(TelLoginActivity.this, PwdActivity.class);
                intent.putExtra("type", "3");
                intent.putExtra("userId", user.getUid());
                intent.putExtra("token", user.getToken());
                startActivityForResult(intent, 0x200);
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
     * 绑定新手机号
     */
    private void bindPhone2() {
        CommonScene.bindPhone2(etTelNumber.getText().toString(), etCode.getText().toString(), android.os.Build.MODEL, android.os.Build.BRAND, verifyToken, new BaseObserver<User>() {
            @Override
            public void OnSuccess(User user) {
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }

                ToastUtil.show("成功换绑");
                setResult(0x201);
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
     * 解除原手机号
     */
    private void verifcodeChange() {
        CommonScene.verifcodeChange(etTelNumber.getText().toString(), etCode.getText().toString(), android.os.Build.MODEL, android.os.Build.BRAND, new BaseObserver<User>() {
            @Override
            public void OnSuccess(User user) {
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }

                Intent intent = new Intent(TelLoginActivity.this, TelLoginActivity.class);
                intent.putExtra("verifyToken",user.getVerifyToken());
                intent.putExtra("type", "5");
                startActivityForResult(intent, 0x201);
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
    private void bindPhone() {
        CommonScene.bindPhone(etTelNumber.getText().toString(), etCode.getText().toString(), android.os.Build.MODEL, android.os.Build.BRAND, new BaseObserver<User>() {
            @Override
            public void OnSuccess(User user) {
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }

                if (pageType.equals("2")) {//绑定手机号
                    finish();
                } else if(pageType.equals("7")) {
                    Intent intent = new Intent(TelLoginActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    saveLoginInfo(user);
                    startActivityForResult(SplashActivity.createIntent(mContext),0x200);
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

    private void saveLoginInfo(User user) {
        MyApplication.saveLoginUser(user);
    }

    private void generateCode() {
        if (null == loadingDialog) {
            loadingDialog = new CenterLoadingView(TelLoginActivity.this);
        }
        loadingDialog.setTitle("获取中");
        loadingDialog.show();
        if (pageType.equals("1") || pageType.equals("6") || pageType.equals("5")) {
            getMsgCode();
        } else if (pageType.equals("2") || pageType.equals("4") || pageType.equals("3") || pageType.equals("7")) {
            String url = "";

            Map<String, String> params = new HashMap<String, String>();
            if (pageType.equals("2") || pageType.equals("4") || pageType.equals("7")) {
                getVerifCode();
            } else if (pageType.equals("3")) {
                getPasswordCode();
            }
        }

    }

    /**
     * 重置密码获取验证码
     */
    private void getPasswordCode() {
        CommonScene.getPasswordCode(etTelNumber.getText().toString(),  new BaseObserver<String>() {
            @Override
            public void OnSuccess(String s) {
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }
                mCodeBtnStatus = false;
                myThread = new MyThread();
                myThread.run();
            }

            @Override
            public void OnFail(int code, String err) {
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }
                mCodeBtnStatus = true;
                ToastUtil.show(err);
            }
        });
    }

    private void getVerifCode() {
        CommonScene.getVerifCode(etTelNumber.getText().toString(),  new BaseObserver<String>() {
            @Override
            public void OnSuccess(String s) {
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }
                mCodeBtnStatus = false;
                myThread = new MyThread();
                myThread.run();
            }

            @Override
            public void OnFail(int code, String err) {
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }
                mCodeBtnStatus = true;
                ToastUtil.show(err);
            }
        });
    }

    /**
     * 获取验证码
     */
    private void getMsgCode() {
        CommonScene.getMsgCode(etTelNumber.getText().toString(),  new BaseObserver<String>() {
            @Override
            public void OnSuccess(String s) {
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }
                mCodeBtnStatus = false;
                myThread = new MyThread();
                myThread.run();
            }

            @Override
            public void OnFail(int code, String err) {
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }
                mCodeBtnStatus = true;
                ToastUtil.show(err);
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
                        if (state == 0 || !mActivityStatus) { // 停止线程
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
                            mCodeBtnStatus = true;
                            enableTvCode();
                        }
                        tvCode.setText(values[0].toString());
                    }
                }
            }.execute();
        }
    }
}
