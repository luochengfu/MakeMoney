package com.tudouni.makemoney.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.utils.CommonUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;


public class SplashActivity extends Activity {
    private Context mContext;
    private Handler mHandler = new Handler();
    private int WAIT_TIME = 2; //单位秒

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_splash);
        setTitle("启动页");
        mContext = this;
        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        setPageShow();
    }

    /**
     * 初始化视图
     */
    private void initView() {
    }


    /**
     * 展示当前界面的内容
     */
    private void setPageShow() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextPage();
            }
        }, WAIT_TIME * 1000);
    }

    private void nextPage() {
        //没有上级跳转到登录界面
        startActivity(new Intent(SplashActivity.this, ((isToLoginPage()) ? LoginActivity.class : MainActivity.class)));
        finish();
    }

    /**
     * 用户参数是否正常，异常重新登录  zp
     *
     * @return
     */
    private boolean isToLoginPage() {
        return null == MyApplication.getLoginUser() || !CommonUtil.isNetworkAvailable(mContext) || !MyApplication.getLoginUser().isSkipping() || TextUtils.isEmpty(MyApplication.getLoginUser().getToken());
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}