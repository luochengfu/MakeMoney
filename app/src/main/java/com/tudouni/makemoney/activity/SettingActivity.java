package com.tudouni.makemoney.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tudouni.makemoney.BuildConfig;
import com.tudouni.makemoney.model.LogOut;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.utils.CleanMessageUtil;
import com.tudouni.makemoney.utils.Constants;
import com.tudouni.makemoney.utils.FileCacheUtils;
import com.tudouni.makemoney.utils.ForwardUtils;
import com.tudouni.makemoney.utils.InjectView;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.view.CenterLoadingView;

import org.simple.eventbus.EventBus;


public class SettingActivity extends BaseActivity implements View.OnClickListener
{
    private static final String TAG = "SettingActivity";
    // 数组长度代表点击次数
    long[] mHits = new long[5];
    @InjectView(id=R.id.llAccount)
    private LinearLayout llAccount;
    @InjectView(id=R.id.llRealname)
    private LinearLayout llRealname;
    @InjectView(id=R.id.tvRealnameStatus)
    private TextView tvRealnameStatus;
    @InjectView(id = R.id.llClear)
    private LinearLayout llClear;//清理缓存
    @InjectView(id = R.id.llAboutUs)
    private LinearLayout llAboutUs;
    @InjectView(id = R.id.llContactUs)
    private LinearLayout llContactUs;
    @InjectView(id = R.id.tvCache)
    private TextView tvCache;
    @InjectView(id = R.id.tvLogout)
    private TextView tvLogout;
    @InjectView(id = R.id.ll_app_version)
    private LinearLayout llAppVersion;
    private TextView tvversion;
    private LinearLayout ll_onlineService;//在线客服

    private CenterLoadingView loadingDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initCache();//初始化内存
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initCache() {
        String cache = null;
        try {
            cache = FileCacheUtils.getCacheSize(this.getCacheDir());
        } catch (Exception e) {
            e.printStackTrace();
            cache = "0KB";
        }
        tvCache.setText(cache);
    }


    private void clearAppCache() {
        //清理app应用内存
        if (null == loadingDialog) {
            loadingDialog = new CenterLoadingView(SettingActivity.this);
            loadingDialog.setTitle("清理中");
        }
        loadingDialog.show();
        new Handler().postDelayed(new Runnable() {
            public void run() {

                new WebView(SettingActivity.this).clearCache(true);     //清除webview缓存

                //清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 cleanCustomCache
                FileCacheUtils.cleanCustomCache(SettingActivity.this.getCacheDir().getPath());
                initCache();

                CleanMessageUtil.clearAllCache(getApplicationContext());
                tvCache.setText("0KB");
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                }
                ToastUtil.show("清理成功");
            }
        }, 1000);

    }

    private void initView() {
        llAccount.setOnClickListener(this);
        llRealname.setOnClickListener(this);
        llClear.setOnClickListener(this);//清理缓存
        llAboutUs.setOnClickListener(this);
        llContactUs.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        tvversion = (TextView) findViewById(R.id.tvversion);
        tvversion.setText(getVersion());
        ll_onlineService = (LinearLayout) findViewById(R.id.ll_onlineService);
        ll_onlineService.setOnClickListener(this);
        llAppVersion.setOnClickListener(this);
        if (!MyApplication.getLoginUser().getRole().equals("0")) {
            tvRealnameStatus.setText("已认证");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llAccount: //账号与安全
                ForwardUtils.target(SettingActivity.this, Constants.ACCOUNT_SECURITY);
                break;
            case R.id.llRealname: //实名认证
                if (MyApplication.getLoginUser().getRole().equals("0")) {
                    ForwardUtils.target(SettingActivity.this, Constants.REALNAME2);
                } else {
                    if (BuildConfig.DEBUG) {
                        ForwardUtils.target(SettingActivity.this, Constants.REALNAME2);
                    } else {
                        ForwardUtils.target(SettingActivity.this, Constants.REALNAME_FINAL);
                    }
                }
                break;
            case R.id.llClear://清理缓存
                clearAppCache();
                break;
            case R.id.llAboutUs: //关于我们
                ForwardUtils.target(SettingActivity.this, Constants.ABOUTUS);
                break;
            case R.id.llContactUs:
                ForwardUtils.target(SettingActivity.this, Constants.CONTACTUS);
                break;
            case R.id.tvLogout: //退出登录
                EventBus.getDefault().post(new LogOut("", false), "clear");
                finish();
                break;
            case R.id.ll_onlineService://在线客服
//                ForwardUtils.target(SettingActivity.this, Constants.ONLINE_SERVICE);
                break;
        }
    }


    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return this.getString(R.string.can_not_find_version_name);
        }
    }
}
