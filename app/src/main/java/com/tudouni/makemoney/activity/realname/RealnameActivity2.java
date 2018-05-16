package com.tudouni.makemoney.activity.realname;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.activity.BaseActivity;
import com.tudouni.makemoney.model.User;
import com.tudouni.makemoney.model.Zma;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.ColorUtil;
import com.tudouni.makemoney.utils.CommonUtil;
import com.tudouni.makemoney.utils.Constants;
import com.tudouni.makemoney.utils.ForwardUtils;
import com.tudouni.makemoney.utils.InjectView;
import com.tudouni.makemoney.utils.base.ACache;
import com.tudouni.makemoney.view.CenterLoadingView;
import com.tudouni.makemoney.view.HintDialog;
import com.tudouni.makemoney.view.MyTitleBar;
import com.zmxy.ZMCertification;
import com.zmxy.ZMCertificationListener;

public class RealnameActivity2 extends BaseActivity implements ZMCertificationListener {
    @InjectView(id = R.id.title_bar)
    private MyTitleBar title_bar;
    @InjectView(id = R.id.etTelNumber)
    private EditText etTelNumber;
    @InjectView(id = R.id.etCode)
    private EditText etCode;
    @InjectView(id = R.id.tvLogin)
    private TextView tvLogin;
    @InjectView(id = R.id.tv_kefu)
    private TextView tv_kefu;
    private ZMCertification zmCertification;
    private static final String MERCHANTID = "268821000000400246461";  //商户号
    private CenterLoadingView loading;
    private static Zma mZma;
    private String idNumber;
    private String realname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname2);
        initview();
        zmCertification = ZMCertification.getInstance();
        zmCertification.setZMCertificationListener(RealnameActivity2.this);
    }

    private void initview() {
        title_bar.setOnLeftClickListener(view -> {
            HintDialog hintDialog = new HintDialog(RealnameActivity2.this);
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

        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 18) {
                    tvLogin.setBackgroundResource(R.drawable.login_btn_back_03);
                    tvLogin.setTextColor(Color.WHITE);
                } else if (s.length() < 18) {
                    tvLogin.setBackgroundResource(R.drawable.login_btn_back_01);
                    tvLogin.setTextColor(ColorUtil.black());
                }
            }
        });

        tvLogin.setOnClickListener(view -> {
            idNumber = etCode.getText().toString().trim();
            realname = etTelNumber.getText().toString().trim();
            if (TextUtils.isEmpty(realname)) {
                Toast.makeText(RealnameActivity2.this, "请输入姓名", Toast.LENGTH_LONG).show();
                return;
            }
            if (!CommonUtil.isIDCard(idNumber)) {
                Toast.makeText(RealnameActivity2.this, "请检查身份证号", Toast.LENGTH_LONG).show();
                return;
            }
            geturl();
        });

        tv_kefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForwardUtils.target(RealnameActivity2.this, Constants.UPLOAD_IDCARD);
                finish();
            }
        });
    }

    private void geturl() {
        loading("");

        CommonScene.genCertUrl(idNumber, realname, "FACE_SDK", new BaseObserver<Zma>() {
            @Override
            public void OnSuccess(Zma data) {
                mZma = data;
                //为了避免第三方调回来的时候  mZma 可能有误，所以存一份在本地，有效时常是60
                ACache.getInstance().put("ZIMAINFO", data, 60);
                dissLoad();
                //data.getBizNO,业务串号
                //MERCHANTID 商户号
                zmCertification.startCertification(RealnameActivity2.this, data.getBizNo(), MERCHANTID, null);
            }

            @Override
            public void OnFail(int code, String err) {
                dissLoad();
                User user = MyApplication.getLoginUser();
                user.setZma(true);
                MyApplication.saveLoginUser(user);
                Toast.makeText(RealnameActivity2.this, err, Toast.LENGTH_SHORT).show();
                super.OnFail(code, err);
            }
        });
    }

    @Override
    public void loading(String title) {
        if (null == loading) {
            loading = new CenterLoadingView(RealnameActivity2.this);
        }
        loading.setTitle(title);
        loading.show();
    }

    @Override
    public void dissLoad() {
        if (loading != null) {
            loading.dismiss();
        }
    }

    @Override
    public void onFinish(boolean isCanceled, boolean isPassed, int errorCode) {
        zmCertification.setZMCertificationListener(null);
        if (isCanceled) {

        }
        else {
            if (isPassed) {
                loading("");

                mZma = (Zma) ACache.getInstance().getAsObject("ZIMAINFO");
                if (null == mZma) {
                    mZma = (Zma) ACache.getInstance().getAsObject("ZIMAINFO");
                    if (null == mZma) {
                        return;
                    }
                }

                CommonScene.genCertUrl(idNumber, realname, mZma.getBizNo(), new BaseObserver<Zma>() {
                    @Override
                    public void OnSuccess(Zma data) {
                        User user = MyApplication.getLoginUser();
                        user.setRole("1");
                        user.setIdNumber(idNumber);
                        user.setRealname(realname);
                        MyApplication.saveLoginUser(user);
                        dissLoad();
                        finish();
                    }

                    @Override
                    public void OnFail(int code, String err) {
                        dissLoad();
                        User user = MyApplication.getLoginUser();
                        user.setZma(true);
                        MyApplication.saveLoginUser(user);
                        super.OnFail(code, err);
                    }
                });
            } else {
                User user = MyApplication.getLoginUser();
                user.setZma(true);
                MyApplication.saveLoginUser(user);
            }
        }
        Log.w("ceshi", "onFinish+++ isPassed = " + isPassed + ", error = " + errorCode);
    }
}
