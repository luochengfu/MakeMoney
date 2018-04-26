package com.tudouni.makemoney.activity;

import android.content.Context;
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
import com.tudouni.makemoney.model.User;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.view.MyTitleBar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 设置登录密码
 * Created by Administrator on 2017/7/24 0024.
 */

public class PwdActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext = this;
    private EditText etPwd, et_confirm_pwd;
    private ImageView passwordLookIcon, passwordComfirmLookIcon;
    private TextView tv_commit;
    private String mPageType = "1";//手机登录没有设置密码设置登录密码(1)、首页找回密码(3)、个人中心设置密码(4)
    private String mUserId;
    private String mToken;
    private User mUser;//设置密码保存用户信息
    private boolean mLookStatus = false, mComfirmLookStatus = false;
    private MyTitleBar title_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setpwd_layout);
        Intent intent = getIntent();
        mPageType = intent.getStringExtra("type");
        if (mPageType.equals("1")) {
            mUser = (User) intent.getSerializableExtra("user");
        } else if ("3".equals(mPageType)) {
            mUserId = intent.getStringExtra("userId");
            mToken = intent.getStringExtra("token");
        }
        initView();
        initData();
    }


    private void initView() {
        etPwd = (EditText) findViewById(R.id.et_psw);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        et_confirm_pwd = (EditText) findViewById(R.id.et_confirm_pwd);
        passwordLookIcon = (ImageView) findViewById(R.id.password_look_iv);
        passwordComfirmLookIcon = (ImageView) findViewById(R.id.password_comfirm_look_iv);
        title_bar = (MyTitleBar) findViewById(R.id.title_bar);
    }

    private void initData() {
        tv_commit.setSelected(false);
        passwordLookIcon.setOnClickListener(this);
        passwordComfirmLookIcon.setOnClickListener(this);
        title_bar.setMiddleText("设置登录密码");
        if ("1".equals(mPageType)) {
            title_bar.setRightText("跳过");
            title_bar.setOnRightClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //保存数据到本地
                    MyApplication.saveLoginUser(mUser);
                    Intent intent = new Intent(PwdActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    startActivity(SplashActivity.createIntent(mContext));
                    finish();
                }
            });

            title_bar.setOnLeftClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyApplication.saveLoginUser(mUser);
                    Intent intent = new Intent(PwdActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    startActivity(SplashActivity.createIntent(mContext));
                    finish();
                }
            });
        } else if ("3".equals(mPageType) || "4".equals(mPageType)) {
            tv_commit.setText(getResources().getString(R.string.sure));
        }

        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPwd.getText().toString();
                String comfirmPassword = et_confirm_pwd.getText().toString();
                if (null == password || "".equals(password)
                        || null == comfirmPassword || "".equals(comfirmPassword)
                        || password.length() < 6 || comfirmPassword.length() < 6) {
                    return;
                }

                if (!etPwd.getText().toString().trim().equals(et_confirm_pwd.getText().toString())) {
                    ToastUtil.show(getResources().getString(R.string.password_not_same_error_hint));
                    return;
                }

                if (!checkPasswrod(password)) {
                    ToastUtil.show(getResources().getString(R.string.password_format_error_hint));
                    return;
                }
                loading("请稍候...");
                startRequset();
            }
        });

        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString();
                if (input.contains(" ")) {
                    String str[] = input.split(" ");
                    String tempInput = "";
                    for (int i = 0; i < str.length; i++) {
                        tempInput += str[i];
                    }
                    etPwd.setText(tempInput);
                    etPwd.setSelection(start);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = s.toString();
                String confrimPassword = et_confirm_pwd.getText().toString();
                if ((null != password && !password.equals(""))
                        && (null != confrimPassword && !confrimPassword.equals(""))
                        && (password.length() <= 16) && (password.length() >= 6)
                        && (confrimPassword.length() <= 16) && (confrimPassword.length() >= 6)) {
                    tv_commit.setSelected(true);
                    tv_commit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    tv_commit.setTextColor(getResources().getColor(R.color.color_999999));
                    tv_commit.setSelected(false);
                }
            }
        });

        et_confirm_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString();
                if (input.contains(" ")) {
                    String str[] = input.split(" ");
                    String tempInput = "";
                    for (int i = 0; i < str.length; i++) {
                        tempInput += str[i];
                    }
                    et_confirm_pwd.setText(tempInput);
                    et_confirm_pwd.setSelection(start);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = etPwd.getText().toString();
                String confrimPassword = s.toString();
                if ((null != password && !password.equals(""))
                        && (null != confrimPassword && !confrimPassword.equals(""))
                        && (password.length() <= 16) && (password.length() >= 6)
                        && (confrimPassword.length() <= 16) && (confrimPassword.length() >= 6)) {
                    tv_commit.setSelected(true);
                    tv_commit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    tv_commit.setTextColor(getResources().getColor(R.color.color_999999));
                    tv_commit.setSelected(false);
                }
            }
        });
    }

    private boolean checkPasswrod(String input) {
        String regEx = "^[^\\u4e00-\\u9fa5]{6,16}$";

        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    private void startRequset() {
        HashMap<String, String> map = new HashMap<>();
        map.put("password", etPwd.getText().toString());



        if (mPageType.equals("1")) {
            map.put("uid", mUser.getUid());
            map.put("token", mUser.getToken());
        } else if (mPageType.equals("3")) {
            map.put("uid", mUserId);
            map.put("token", mToken);
        }

        CommonScene.setPWD(etPwd.getText().toString(), new BaseObserver<String>() {
            @Override
            public void OnSuccess(String s) {
                ToastUtil.show("设置密码成功");
                dissLoad();

                if (mPageType.equals("1")) {
                    Intent intent = new Intent(PwdActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    mUser.setPwd("1");
                    MyApplication.saveLoginUser(mUser);
                    startActivity(SplashActivity.createIntent(mContext));
                } else if (mPageType.equals("4")) {
                    MyApplication.getLoginUser().setPwd("1");
                } else if (mPageType.equals("3")) {
                    setResult(0x200);
                }

                finish();
            }

            @Override
            public void OnFail(int code, String err) {
                dissLoad();
                ToastUtil.show("设置密码失败");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.password_look_iv:
                if (mLookStatus) {
                    passwordLookIcon.setImageDrawable(getResources().getDrawable(R.mipmap.password_look));
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mLookStatus = false;
                } else {
                    passwordLookIcon.setImageDrawable(getResources().getDrawable(R.mipmap.password_unlook));
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mLookStatus = true;
                }
                etPwd.setSelection(etPwd.getText().toString().length());
                break;
            case R.id.password_comfirm_look_iv:
                if (mComfirmLookStatus) {
                    passwordComfirmLookIcon.setImageDrawable(getResources().getDrawable(R.mipmap.password_look));
                    et_confirm_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mComfirmLookStatus = false;
                } else {
                    passwordComfirmLookIcon.setImageDrawable(getResources().getDrawable(R.mipmap.password_unlook));
                    et_confirm_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mComfirmLookStatus = true;
                }
                et_confirm_pwd.setSelection(et_confirm_pwd.getText().toString().length());
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(mPageType.equals("1")) {
            MyApplication.saveLoginUser(mUser);
            startActivity(SplashActivity.createIntent(mContext));
            finish();
        } else {
            finish();
        }
    }
}
