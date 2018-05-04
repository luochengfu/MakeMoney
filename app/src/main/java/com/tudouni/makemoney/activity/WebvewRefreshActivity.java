package com.tudouni.makemoney.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.tudouni.makemoney.BuildConfig;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.interfaces.IActionListener;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.utils.Constants;
import com.tudouni.makemoney.utils.ForwardUtils;
import com.tudouni.makemoney.utils.H5WebViewClient;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.utils.WVJBWebViewClient;
import com.tudouni.makemoney.utils.base.AppUtils;
import com.tudouni.makemoney.view.MineRefreshHeader;
import com.tudouni.makemoney.view.MyTitleBar;
import com.tudouni.makemoney.view.PullToRefreshBridgeWebView;
import com.tudouni.makemoney.view.WebviewHeadLoadingView;

import org.simple.eventbus.EventBus;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Administrator on 2018/5/3 0003.
 */

public class WebvewRefreshActivity extends BaseActivity
        implements EasyPermissions.PermissionCallbacks, PullToRefreshBase.OnRefreshListener2
{
    public static final int REQUEST_CODE_PERMISSION_CALL = 3;
    private String url;
    private WebView webview;
    private WVJBWebViewClient webViewClient;
    private PullToRefreshBridgeWebView pullToRefreshBridgeWebView;
    public MyTitleBar title_bar;
    private ProgressBar progress;
    private WebviewHeadLoadingView headLoadingView;
    private String mDefaultTitle;
    private boolean needResum;
    /* 需要重新刷新的页面*/
    private final String[] needResumUrl = new String[]{"lotteryDraw.html", Constants.MY_INVITE};

    private Handler payHandler = new Handler() {

        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 111: {
                    WebvewRefreshActivity.this.dissLoad();
                    if (msg.obj != null) {
                        Toast.makeText(WebvewRefreshActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case 222: {
                    WebvewRefreshActivity.this.dissLoad();
                    if (msg.obj != null) {
                        Toast.makeText(WebvewRefreshActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    }
                    finish();
                    break;
                }
            }
        }
    };

    private void initTitleBar() {
        if (title_bar == null) return;
        if (url.startsWith(Constants.h5_myinvite)) {
            title_bar.setRightIcon(R.mipmap.mall_search_icon);
            title_bar.setRightIconStatus(View.VISIBLE);
            title_bar.setOnRightClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ForwardUtils.target(WebvewRefreshActivity.this, Constants.h5_bindsearch);
                }
            });
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("url");

        setContentView(R.layout.webview_refresh_layout);
        initView();
        initDatas();
        initTitleBar();
    }

    private void initView() {
        pullToRefreshBridgeWebView = (PullToRefreshBridgeWebView) findViewById(R.id.pullToRefreshBridgeWebView);
        headLoadingView = new WebviewHeadLoadingView(this);
        pullToRefreshBridgeWebView.setHeaderLayout(headLoadingView);
        title_bar = (MyTitleBar) findViewById(R.id.title_bar);
        pullToRefreshBridgeWebView.setPullToRefreshOverScrollEnabled(false);
        webview = pullToRefreshBridgeWebView.getRefreshableView();
        progress = (ProgressBar) findViewById(R.id.progress);

        pullToRefreshBridgeWebView.setOnRefreshListener(this);
    }

    private void initDatas() {
        initWebView();
    }

    private void initWebView() {
        WebChromeClient webChromeClient = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (TextUtils.isEmpty(mDefaultTitle)) {
                    title_bar.setMiddleText(title);
                }
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
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
        webViewClient = new H5WebViewClient(this, payHandler, (BridgeWebView) webview);
        webViewClient.enableLogging();
        webview.setWebViewClient(webViewClient);
        ((H5WebViewClient)webViewClient).setListener(new IActionListener() {
            @Override
            public void action() {
                headLoadingView.getLoadImageView().setVisibility(View.GONE);
                pullToRefreshBridgeWebView.onRefreshComplete();
            }
        });

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

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                webview.loadUrl(url);
            }
        }, 1000);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (0 != (Intent.FLAG_ACTIVITY_SINGLE_TOP & intent.getFlags())) {
            url = intent.getStringExtra("url");
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
        String newCookie = cookieManager.getCookie(url);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(getApplicationContext());
            cookieSyncManager.sync();
        }
        return !TextUtils.isEmpty(newCookie);
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
}
