package com.tudouni.makemoney.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.tudouni.makemoney.BuildConfig;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.utils.AndroidBug5497Workaround;
import com.tudouni.makemoney.utils.Constants;
import com.tudouni.makemoney.utils.H5WebViewClient;
import com.tudouni.makemoney.utils.InjectView;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.utils.WVJBWebViewClient;
import com.tudouni.makemoney.utils.base.AppUtils;
import com.tudouni.makemoney.view.MyTitleBar;
import org.simple.eventbus.EventBus;
import java.util.List;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * H5统一页面
 */
@SuppressLint("SetJavaScriptEnabled")
public class H5Activity extends BaseActivity implements
        EasyPermissions.PermissionCallbacks {
    public static final int REQUEST_CODE_PERMISSION_CALL = 3;
    private Context mContext = this;

    public static final String support = "support,getUserInfo,login,closeWebView,getDeviceInfo," +
            "copy,setShareInfo,jumpPage,getUserAgent,setBgColor,uploadCallback,loginSuccess,payCallback";

    private String url;

    /**
     * 本地是否正在直播
     */
    private boolean isLive = true;
    /**
     * OAuth2.0授权后的Token
     */
    private String accessToken = "";


    public WebView webview;
    @InjectView(id = R.id.progress)
    ProgressBar progress;

    private WVJBWebViewClient webViewClient;

    @InjectView(id = R.id.title_bar)
    public MyTitleBar title_bar;
    private String mDefaultTitle;
    private int titleStatus;//状态栏状态：0、隐藏；1、显示

    private Handler payHandler = new Handler() {

        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 111: {
                    H5Activity.this.dissLoad();
                    if (msg.obj != null) {
                        Toast.makeText(H5Activity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case 222: {
                    H5Activity.this.dissLoad();
                    if (msg.obj != null) {
                        Toast.makeText(H5Activity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    }
                    finish();
                    break;
                }
            }
        }
    };

    private ValueCallback<Uri[]> filePathCallback;
    /* 需要重新刷新的页面*/
    private final String[] needResumUrl = new String[]{"lotteryDraw.html", Constants.MY_INVITE};
    private boolean needResum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);

        EventBus.getDefault().register(this);

        AndroidBug5497Workaround.assistActivity(this);
        webview = (BridgeWebView) findViewById(R.id.webView);
        url = getIntent().getStringExtra("url");
//        url = "http://192.168.16.183:3001/html/webView.html";
        titleStatus = getIntent().getIntExtra("titleStatus", 0);
        mDefaultTitle = getIntent().getStringExtra("title");
        isLive = getIntent().getBooleanExtra("isLive", false);
        accessToken = getIntent().getStringExtra("accessToken");

        if (url.contains(Constants.MY_INVITE)) {
            title_bar.setStatusBackground(ContextCompat.getColor(this, R.color.color_FDCE00));
            title_bar.setHeadBackground(ContextCompat.getColor(this, R.color.color_FDCE00));
            title_bar.setMiddleTextColor(ContextCompat.getColor(this, R.color.white));
        }

        if (!TextUtils.isEmpty(mDefaultTitle)) {
            title_bar.setHeadVisibility(View.VISIBLE);
            title_bar.setMiddleText(mDefaultTitle);
        }
        if ("#".equals(mDefaultTitle)) {
            title_bar.setHeadVisibility(View.GONE);
        }
        WebChromeClient webChromeClient = new WebChromeClient()
        {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (TextUtils.isEmpty(mDefaultTitle)) {
                    title_bar.setMiddleText(title);
                }
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                H5Activity.this.filePathCallback = filePathCallback;
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progress.setVisibility(View.GONE);
                } else {
                    if (progress.getVisibility() == View.GONE)
                        progress.setVisibility(View.VISIBLE);
                    progress.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        };


        webview.setWebChromeClient(webChromeClient);

        WebSettings settings = webview.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDatabasePath(this.getApplicationContext().getCacheDir().getAbsolutePath());
        settings.setAllowFileAccess(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //添加useragent
        settings.setUserAgentString(settings.getUserAgentString() + " tuoduni-android-" + AppUtils.getAppVersionName());
        /** * Webview在安卓5.0之前默认允许其加载混合网络协议内容 * 在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容 */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webViewClient = new H5WebViewClient(this,payHandler,(BridgeWebView) webview);
        webViewClient.enableLogging();
        webview.setWebViewClient(webViewClient);


        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);       //允许调试
        }

        //把http换成https
        if (url.toLowerCase().startsWith("http://h5.tudouni.doubozhibo.com")) {
            url = "https" + url.substring(4);
        }

        if (url.toLowerCase().startsWith("taobao")) {
            url = "https" + url.substring(6);
        }

        //如果是登录状态加上token 和 uid
        if (MyApplication.getLoginUser() != null) {
            if (url.contains("?")) {
                url = url + "&token=" + MyApplication.getLoginUser().getToken() + "&uid=" + MyApplication.getLoginUser().getUid() + "&tdid=" + MyApplication.getLoginUser().getUnumber();
            } else {
                url = url + "?token=" + MyApplication.getLoginUser().getToken() + "&uid=" + MyApplication.getLoginUser().getUid() + "&tdid=" + MyApplication.getLoginUser().getUnumber();
            }
        }
        syncCookie();
        webview.loadUrl(url);
    }

    String phoneUrl;

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_CODE_PERMISSION_CALL && perms.size() == 1) {
            // 调用扫描的二维码
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneUrl));
            startActivity(intent);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_CODE_PERMISSION_CALL) {
            ToastUtil.show(this, "您拒绝了「拨打电话」所需要的相关权限!");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onBackPressed() {
        webViewClient.callHandler("clearListData");
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //页面激活
        webViewClient.callHandler("reActive");

        if (needResum) {
            webview.reload();
        } else {
            for (String item : needResumUrl) {
                if (url.contains(item)) {
                    needResum = true;
                }
            }
        }
    }

    public static Intent newIntent(Context context, String url, String title) {
        Intent intent = new Intent(context, H5Activity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        return intent;
    }

    public static Intent newIntent(Context context, String url, String title, int titleStatus) {
        Intent intent = new Intent(context, H5Activity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("titleStatus", titleStatus);
        return intent;
    }

    public static Intent newIntent(Context context, String url, String title, boolean isLive, String accessToken) {
        Intent intent = new Intent(context, H5Activity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("isLive", isLive);
        intent.putExtra("accessToken", accessToken);
        return intent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private boolean syncCookie() {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(url, "IsLive=" + (isLive ? 1 : 0));
        cookieManager.setCookie(url, "AccessToken=" + accessToken);
        String newCookie = cookieManager.getCookie(url);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(getApplicationContext());
            cookieSyncManager.sync();
        }
        return !TextUtils.isEmpty(newCookie);
    }
}
