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
    private final int PERMISSION_REQUEST_CODE = 124;
    private Context mContext;
    private Handler mHandler = new Handler();
    private int WAIT_TIME = 2; //单位秒
    private final String[] permissionManifest = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private final int[] noPermissionTip = {
            R.string.no_camera_permission,
            R.string.no_record_audio_permission,
            R.string.no_read_phone_state_permission,
            R.string.no_write_external_storage_permission,
            R.string.no_read_external_storage_permission
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_splash);
        initPermissions();
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
        if (null != MyApplication.getLoginUser() && CommonUtil.isNetworkAvailable(mContext) && !TextUtils.isEmpty(MyApplication.getLoginUser().getParent())) {
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

    /**
     * 初始化权限
     */
    private void initPermissions() {
        if (!permissionCheck()) {
            if (Build.VERSION.SDK_INT >= 23) {
                ActivityCompat.requestPermissions(this, permissionManifest,
                        PERMISSION_REQUEST_CODE);
            }
        }
    }

    /**
     * 权限检查（适配6.0以上手机）
     */
    private boolean permissionCheck() {
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        String permission;
        for (int i = 0; i < permissionManifest.length; i++) {
            permission = permissionManifest[i];
            if (PermissionChecker.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionCheck = PackageManager.PERMISSION_DENIED;
            }
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}