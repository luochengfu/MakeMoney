package com.tudouni.makemoney.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.utils.CommonUtil;
import com.umeng.analytics.MobclickAgent;


public class SplashActivity extends Activity {

    private Context mContext;
    private Handler mHandler = new Handler();
    private int WAIT_TIME = 2; //单位秒

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
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
        if (null != MyApplication.getLoginUser() && CommonUtil.isNetworkAvailable(mContext)) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        } else {  //跳转到登录
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
            return;
        }
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