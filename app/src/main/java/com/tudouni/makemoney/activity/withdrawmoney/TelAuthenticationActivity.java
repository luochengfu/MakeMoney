package com.tudouni.makemoney.activity.withdrawmoney;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tudouni.makemoney.BuildConfig;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.activity.BaseActivity;
import com.tudouni.makemoney.activity.realname.RealTelBandingActivity;
import com.tudouni.makemoney.model.User;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.ColorUtil;
import com.tudouni.makemoney.utils.InjectView;
import com.tudouni.makemoney.utils.ValidateUtil;
import com.tudouni.makemoney.view.CenterLoadingView;
import com.tudouni.makemoney.view.ConfirmDialog;

public class TelAuthenticationActivity extends BaseActivity {
    @InjectView(id = R.id.etTelNumber)
    private TextView etTelNumber;
    @InjectView(id = R.id.tvCode)
    private TextView tvCode;
    @InjectView(id = R.id.etCode)
    private EditText etCode;
    @InjectView(id = R.id.tvSubmit)
    private TextView tvSubmit;
    private CenterLoadingView loadingDialog = null;
    private String moneyNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tel_authentication);
        Bundle extras = null;
        try {
            extras = getIntent().getExtras();
        } catch (Exception e) {
            finish();
            return;
        }
        if (extras == null) {
            finish();
            return;
        }
        moneyNumber = extras.getString("moneyNumber");
        if (null == moneyNumber) {
            finish();
            return;
        }
        initview();
    }

    private void initview() {
        User user = MyApplication.getLoginUser();
        String phoneNumber = user.getPhone();
        if (BuildConfig.DEBUG) {
            phoneNumber = "18826292660";
        }
        if (phoneNumber != null && phoneNumber.length() == 11) {
            phoneNumber = phoneNumber.substring(0, 3)
                    + "****" + phoneNumber.substring(7);
            etTelNumber.setText(phoneNumber);
            enableTvCode();
        }

        tvCode.setOnClickListener(view -> {
            generateCode();
            disableTvCode();
        });

        tvSubmit.setOnClickListener((View view) -> {
            String phoneCode = etCode.getText().toString().trim();
            if (TextUtils.isEmpty(phoneCode)) {
                Toast.makeText(TelAuthenticationActivity.this,
                        "请输入短信验证码",
                        Toast.LENGTH_LONG);
                return;
            }

            if (null == loadingDialog) {
                loadingDialog = new CenterLoadingView(TelAuthenticationActivity.this);
            }
            loadingDialog.setTitle("提交申请中");
            loadingDialog.show();

            CommonScene.payCash(moneyNumber, new BaseObserver<String>() {
                @Override
                public void OnSuccess(String s) {
                    if (null != loadingDialog) {
                        loadingDialog.dismiss();
                    }
                    ConfirmDialog confirmDialog = new ConfirmDialog(TelAuthenticationActivity.this,
                            "提交成功",
                            "您的提现申请已提交，预计1-3个工作日到账，敬请留意");
                    confirmDialog.setLinstener(new ConfirmDialog.BtnClickLinstener(){
                        @Override
                        public void clickOk(){
                            finish();
                        }
                    });
                    confirmDialog.show();
                }

                @Override
                public void OnFail(int code, String err) {
                    if (null != loadingDialog) {
                        loadingDialog.dismiss();
                    }
                    Toast.makeText(TelAuthenticationActivity.this, err, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void generateCode() {
        if (null == loadingDialog) {
            loadingDialog = new CenterLoadingView(TelAuthenticationActivity.this);
        }
        loadingDialog.setTitle("获取中");
        loadingDialog.show();

        User user = MyApplication.getLoginUser();
        String phoneNumber = user.getPhone();
        if (BuildConfig.DEBUG) {
            phoneNumber = "18826292660";
        }
        CommonScene.getMsgCode(phoneNumber,  new BaseObserver<String>() {
            @Override
            public void OnSuccess(String s) {
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }
                myThread = new TelAuthenticationActivity.MyThread();
                myThread.run();
            }

            @Override
            public void OnFail(int code, String err) {
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }
                Toast.makeText(TelAuthenticationActivity.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
                enableTvCode();
            }
        });
    }

    private void enableTvCode() {
        tvCode.setClickable(true);
        tvCode.setBackground(getResources().getDrawable(R.drawable.login_btn_back_03));
        tvCode.setTextColor(Color.WHITE);
    }

    private void disableTvCode() {
        tvCode.setClickable(false);
        tvCode.setBackground(getResources().getDrawable(R.drawable.login_btn_back_01));
        tvCode.setTextColor(ColorUtil.black());
    }

    public int state = 1; //状态 1 表示未启动线程或正在运行线程。0 停止线程
    private TelAuthenticationActivity.MyThread myThread;

    class MyThread extends Thread {
        @SuppressLint("StaticFieldLeak")
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
