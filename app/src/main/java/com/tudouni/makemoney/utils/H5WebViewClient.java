package com.tudouni.makemoney.utils;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Keep;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tudouni.makemoney.activity.H5Activity;
import com.tudouni.makemoney.activity.MainActivity;
import com.tudouni.makemoney.interfaces.IActionListener;
import com.tudouni.makemoney.model.LogOut;
import com.tudouni.makemoney.model.User;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.NetConfig;
import com.tudouni.makemoney.utils.base.AppUtils;
import com.tudouni.makemoney.view.CenterLoadingView;
import com.tudouni.makemoney.widget.sharePart.ShareWindow_v3;
import com.tudouni.makemoney.widget.sharePart.model.Share;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2018/4/9 0009.
 */

public class H5WebViewClient extends WVJBWebViewClient
{
    private Activity mActivity;
    private Handler payHandler;
    private CenterLoadingView loading;
    private WebView webview;
    private IActionListener mListner;

    private boolean isGameHallPage;

    public void setGameHallPage(boolean gameHallPage) {
        isGameHallPage = gameHallPage;
    }


    /**
     * 土豆泥协议
     */
    private static final String SCHEME_TUDOUNI = "tudounihybird";

    interface Command {
        /**
         * 土豆泥协议命令字
         */
        String COMMAND_FORWARD = "forward";
        /**
         * 返回/关闭页面
         */
        String COMMAND_FINISH = "finishPage";
        /**
         * 隐藏Title
         */
        String HIDE_TITLE = "hideTitle";
        /**
         * 设置Title
         */
        String SET_TITLE = "setTitle";
    }

    interface ParamKey {
        /**
         * page参数键
         */
        String KEY_PAGE = "page";
        /**
         * title参数键
         */
        String KEY_TITLE = "title";
    }

    interface ParamValue {
        /**
         * 开启直播页面
         */
        String PAGE_VALUE_START_LIVE = "startLivePage";
    }

    public H5WebViewClient(Activity activity, Handler handler, WebView webView) {
        super(webView, new WVJBHandler() {
            @Override
            public void request(Object data, WVJBResponseCallback callback) {
                callback.callback("Response for message from ObjC!");
            }
        });

        this.mActivity = activity;
        this.payHandler = handler;
        this.webview = webView;

        initDatas();
    }

    private void initDatas() {
        support();
        getUserInfo();
        getUserAgent();
        getMetaInfo();
        login();
        logout();
        closeWebView();
        getDeviceInfo();
        copy();
        share();
        jump();
        startTaobao();
        setBGColor();
        aliauth();
        setTabIndex();

        enableLogging();
    }

    /**
     * 跳转到主页相应 Tab
     */
    private void setTabIndex() {
        registerHandler("setTabIndex", new WVJBHandler() {
            @Override
            public void request(Object data, WVJBResponseCallback callback) {
                try {
                    int index = 2;
                    if (data != null) {
                        JSONObject jsonObject = new JSONObject(data.toString());
                        index = jsonObject.getInt("index");
                    }
                    Intent intent = new Intent();
                    intent.setClass(mActivity, MainActivity.class);
                    intent.putExtra("index", index);
                    mActivity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    /**
     * 阿里授权
     */
    private void aliauth() {
        registerHandler("aliauth", new WVJBHandler() {

            @Override
            public void request(Object data, WVJBResponseCallback callback) {
//                startAuth();
            }
        });
    }

    /**
     * 设置背景色
     */
    private void setBGColor() {
        registerHandler("setBgColor", new WVJBHandler() {

            @Override
            public void request(Object data, WVJBResponseCallback callback) {
                try {
                    JSONObject ja = new JSONObject(data.toString());
                    int r = ja.getInt("r");
                    int g = ja.getInt("g");
                    int b = ja.getInt("b");
                    webview.setBackgroundColor(Color.argb(1, r, g, b));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * 启动淘宝
     */
    private void startTaobao() {
        registerHandler("taobaoBridge", new WVJBHandler() {

            @Override
            public void request(Object data, WVJBResponseCallback callback) {
                try {
                    JSONObject ja = new JSONObject(data.toString());
                    String url = ja.getString("url");
                    openTaobaoShopping(url);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    /**
     * 跳转
     */
    private void jump() {

        registerHandler("jumpPage", new WVJBWebViewClient.WVJBHandler() {

            @Override
            public void request(Object data, WVJBResponseCallback callback) {
               ToastUtil.show("tttttttttttttttttt");
            }
        });

//        registerHandler("jumpPage", new WVJBHandler() {
//
//            @Override
//            public void request(Object data, WVJBResponseCallback callback) {
//                if (data != null) {
//                    try {
//                        JSONObject ja = new JSONObject(data.toString());
//                        String url = ja.getString("url");
////                        if("tudouni://tudouni/home".equals(url) || "tudouni://tudouni/back".equals(url)) {
////                            mActivity.finish();
////                        } else if(url.startsWith(NetConfig.getShopMainUrl())){
////                            Intent intent = new Intent(mActivity, FreeShopActivity.class);
////                            intent.putExtra("tagUrl", url);
////                            if(url.contains("/shopHome/sousuo.html")) {
////                               intent.putExtra("titleStatus",0);
////                            } else {
////                                intent.putExtra("titleStatus",1);
////                            }
////                            mActivity.startActivity(intent);
////                        } else {
////                            ForwardUtils.target(mActivity, url);
////                            callback.callback("{\"status\":\"yes\"}");
////                        }
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                }
//
//            }
//        });
    }
    /**
     * 分享
     */
    private void share() {
        registerHandler("share", new WVJBHandler() {

            @Override
            public void request(Object data, WVJBResponseCallback callback) {
                JSONObject ja = null;
                try {
                    ja = new JSONObject(data.toString());
//                        {"url":"","title","","content":"","img":""}
                    String url = ja.getString("url");
                    String title = ja.getString("title");
                    String img = ja.getString("img");
                    String content = "";
                    try {
                        content = ja.getString("content");
                    }catch (Exception e){}

                    boolean sharekg = false;
                    try {
                        sharekg = ja.getBoolean("sharekg");
                    } catch (Exception e){}

                    if(sharekg) {
//                            Bitmap bitmap = getBitmapFromByte(decode(img));
                        try {
                            Glide.with(mActivity).load(img).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
                                @Override
                                public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                                    try {
                                        Bitmap bitmap = getBitmapFromByte(bytes);
                                        new ShareWindow_v3(mActivity, Share.Type.IMAGE_POTATOES, Share.obtain(Share.Type.IMAGE_POTATOES, bitmap), null, null).show(mActivity);
                                    } catch (Exception e) {
                                        ToastUtil.show("获取图片失败，请重试");
                                        return;
                                    }
                                }
                            });

                        } catch (Exception e) {
                            ToastUtil.show("获取图片失败，请重试");
                            return;
                        }
                    } else {
                        Share share = new Share(title, content, img, url);
                        ShareWindow_v3 p = new ShareWindow_v3((Activity) mActivity, null, share, null, null);
                        p.show((Activity) mActivity);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }
    /**
     * 拷贝
     */
    private void copy() {
        registerHandler("copy", new WVJBHandler() {
            @Override
            public void request(Object data, WVJBResponseCallback callback) {
                if (data != null) {
                    try {
                        JSONObject ja = new JSONObject(data.toString());
                        ClipboardManager clip = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                        clip.setText(ja.getString("content"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                if (callback != null) {
                    callback.callback("{\"status\":\"yes\"}");
                }
            }
        });
    }
    /**
     * 获取设备信息
     */
    private void getDeviceInfo() {
        registerHandler("getDeviceInfo", new WVJBHandler() {
            @Override
            public void request(Object data, WVJBResponseCallback callback) {

            }
        });

    }
    /**
     * 关闭webview
     */
    private void closeWebView() {
        registerHandler("closeWebView", new WVJBHandler() {
            @Override
            public void request(Object data, WVJBResponseCallback callback) {
                try {
                    mActivity.onBackPressed();
                    mActivity.finish();
                    if (callback != null) {
                        callback.callback("{\"status\":\"yes\"}");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * 退出
     */
    private void logout() {
        registerHandler("logout", new WVJBHandler() {
            @Override
            public void request(Object data, WVJBResponseCallback callback) {
                try {
                    EventBus.getDefault().post(new LogOut("登录失效，请重新登录", true), "clear");
                    if (callback != null) {
                        callback.callback("{\"status\":\"yes\"}");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 获取用户设备信息
     */
    private void getMetaInfo() {
        registerHandler("getMetaInfo", new WVJBHandler() {
            @Override
            public void request(Object data, WVJBResponseCallback callback) {
                JsonObject o = new JsonObject();
//                o.addProperty("appVersion", CommonHelper.getAppVersionName(App.getContext()));
//                o.addProperty("deviceId", App.deviceId);
//                o.addProperty("deviceModel", App.deviceModel);
//                o.addProperty("osType", "Android");
//                o.addProperty("osVersion", App.osVersion);
//                if (App.sLocal != null) {
//                    o.addProperty("province", App.sLocal.getProvince());
//                    o.addProperty("city", App.sLocal.getCity());
//                }
                callback.callback(new Gson().toJson(o));
            }
        });
    }

    /**
     * 登录
     */
    private void login() {
        registerHandler("login", new WVJBHandler() {
            @Override
            public void request(Object data, WVJBResponseCallback callback) {

            }
        });
    }

    /**
     * 是否支持该方法
     */
    private void support() {
        registerHandler("support", new WVJBHandler() {
            @Override
            public void request(Object data, WVJBResponseCallback callback) {

                if (data != null && H5Activity.support.indexOf(data.toString()) != -1) {
                    callback.callback("{\"status\":\"yes\"}");
                }
            }
        });
    }

    /**
     * 获取用户数据
     */
    private void getUserInfo() {
        registerHandler("getUserInfo", new WVJBHandler() {
            @Override
            public void request(Object data, WVJBResponseCallback callback) {
                User user = MyApplication.getLoginUser();
                if (user != null) {
                    String js = new Gson().toJson(user);
                    callback.callback(js);
                } else {
                    callback.callback("");
                }
            }
        });
    }

    /**
     * 获取用户 agent
     */
    private void getUserAgent() {
        registerHandler("getUserAgent", new WVJBHandler() {

            @Override
            public void request(Object data, WVJBResponseCallback callback) {
                callback.callback("tuoduni-android-" + AppUtils.getAppVersionName());
            }
        });
    }

    @Keep       //混淆保持
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();      //忽略ssl错误
    }

    public void loading(String title) {
        if (null == loading) {
            loading = new CenterLoadingView(mActivity);
        }
        loading.setTitle(title);
        loading.show();
    }

    public void dissLoad() {
        if (loading != null) {
            loading.dismiss();
        }
    }

    private void openTaobaoShopping(String url) {
        if (AppUtils.checkPackage(mActivity, "com.taobao.taobao"))
        {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(url);
            intent.setData(uri);
            mActivity.startActivity(intent);
        } else {
            Intent intent = new Intent();
            intent.setClass(mActivity,H5Activity.class);
            intent.putExtra("url",url);
            mActivity.startActivity(intent);
        }
    }

    public Bitmap getBitmapFromByte(byte[] temp){   //将二进制转化为bitmap
        if(temp != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            return bitmap;
        }else{
            return null;
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if(mListner != null) {
            mListner.action();
        }
    }

    public void setListener(IActionListener listener) {
        mListner = listener;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //处理代码

        if (url.startsWith("tudouni://tudouni/home")) {
            ForwardUtils.target(mActivity, Constants.MAIN);
            return true;
        }

        if (url.startsWith("doubozhibo://")) {
            ForwardUtils.target(mActivity, url);
            return true;
        }
        if (url.startsWith("tudouni://tudouni/")) {
            ForwardUtils.target(mActivity, url);
            return true;
        }

        if(url.startsWith(NetConfig.getShopMainUrl())) {
//            Intent intent = new Intent(mActivity, FreeShopActivity.class);
//            intent.putExtra("tagUrl", url);
//            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            mActivity.startActivity(intent);
        }

        return super.shouldOverrideUrlLoading(view, url);
    }
}
