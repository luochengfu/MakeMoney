package com.tudouni.makemoney.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.view.CenterLoadingView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class ModifyPasswordActivity extends BaseActivity implements View.OnClickListener {
    private EditText passwordET;
    private TextView submitBtn;
    private CenterLoadingView loadingDialog;
    private ImageView passwordLookIcon;
    private boolean mLookStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_passwrod_layout);

        initView();
        initData();
    }

    private void initView() {
        passwordET = (EditText) findViewById(R.id.comfirm_pasword_et);
        submitBtn = (TextView) findViewById(R.id.tv_commit);
        passwordLookIcon = (ImageView) findViewById(R.id.password_look_iv);
    }

    private void initData() {
        submitBtn.setOnClickListener(this);
        passwordLookIcon.setOnClickListener(this);
        passwordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = s.toString();
                if ((password.length() >= 6) && password.length() <= 16) {
                    submitBtn.setTextColor(getResources().getColor(R.color.color_333333));
                    submitBtn.setSelected(true);
                } else {
                    submitBtn.setTextColor(getResources().getColor(R.color.color_999999));
                    submitBtn.setSelected(false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
                userLogin();
                break;
            case R.id.password_look_iv:
                if (mLookStatus) {
                    passwordLookIcon.setImageDrawable(getResources().getDrawable(R.mipmap.password_look));
                    passwordET.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mLookStatus = false;
                } else {
                    passwordLookIcon.setImageDrawable(getResources().getDrawable(R.mipmap.password_unlook));
                    passwordET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mLookStatus = true;
                }
                passwordET.setSelection(passwordET.getText().toString().length());
                break;
        }
    }

    /**
     * 密码登录
     */
    private void userLogin() {
        String password = passwordET.getText().toString();

        if (password == null || password.trim().equals("")
                || password.length() < 6 || password.length() > 16) {
            return;
        }
        if (null == loadingDialog) {
            loadingDialog = new CenterLoadingView(ModifyPasswordActivity.this);
        }
        loadingDialog.setTitle("请稍候...");
        loadingDialog.show();

        CommonScene.verifyPassword(password, new BaseObserver<String>() {
            @Override
            public void OnSuccess(String s) {
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }

                Intent intent = new Intent(ModifyPasswordActivity.this, PwdActivity.class);
                intent.putExtra("type", "4");
                startActivity(intent);

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
}
