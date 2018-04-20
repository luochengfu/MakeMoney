package com.tudouni.makemoney.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.tudouni.makemoney.R;


public class SplashActivity extends Activity {

    private Context mContext;
    private Handler mHandler = new Handler();
    private int WAIT_TIME = 2; //单位秒

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();

            }
        }, WAIT_TIME * 1000);
    }


}