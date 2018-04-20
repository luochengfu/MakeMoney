package com.tudouni.makemoney.network;


import android.text.TextUtils;


import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.security.DouBoInterfaceLevel;
import com.tudouni.makemoney.network.security.SecurityComponents;
import com.tudouni.makemoney.utils.Constants;
import com.tudouni.makemoney.utils.TuDouLogUtils;
import com.tudouni.makemoney.utils.base.AppUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.TreeMap;

import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 网络请求拦截器
 * Created by Yang on 2017/3/27.
 */
public class NetInterceptor implements Interceptor {

    private static final String TAG = "NetIntercepter";
    private int maxAge = 60 * 60; // 有网络时 设置缓存超时时间1个小时
    private int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
//    HashMap<String, String> mDefaultRequestBodyParamesMap;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        Request newRequest = null;
        String urlStr = oldRequest.url().toString();
        if (urlStr.contains("/auth/games/h5/newAccessToken") || urlStr.contains("/auth/games/h5/server/gamesStatus")) {   //TODO:重构
            urlStr = urlStr.replace(NetConfig.getBaseUrl(), NetConfig.getBaseGameUrl());
        }
        oldRequest = oldRequest.newBuilder().url(urlStr).build();
        boolean isClass3 = DouBoInterfaceLevel.newInstance().isClass3(urlStr);//判断接口等级
        Request.Builder requestBuilder;
        if (!NetworkStateDetection.isNetworkAvailable(MyApplication.getContext().getApplicationContext()))
            requestBuilder = oldRequest.newBuilder().method(oldRequest.method(), oldRequest.body()).cacheControl(CacheControl.FORCE_CACHE); //无网络时只从缓存中读取
        else
            requestBuilder = oldRequest.newBuilder().method(oldRequest.method(), oldRequest.body());

        if ("POST".equals(oldRequest.method())) {
            TreeMap<String, String> mRequestBodyMap = new TreeMap<>();
            initDefaultRequestParamesMap(mRequestBodyMap);//初始化默认的请求体参数信息
            TuDouLogUtils.d(TAG, "显示mDefaultRequestBodyParamesMap=" + mRequestBodyMap.toString());
            StringBuilder sb = new StringBuilder();
            FormBody.Builder newFormBody = new FormBody.Builder();
            if (oldRequest.body() instanceof FormBody) { //添加原来的Body参数
                FormBody body = (FormBody) oldRequest.body();
                for (int i = 0; i < body.size(); i++)
                    mRequestBodyMap.put(body.name(i), body.value(i));
            }
            if (isClass3)
                newFormBody.add("cdata", SecurityComponents.cipherEncryptData(mRequestBodyMap));
            else
                concatParams(sb, newFormBody, mRequestBodyMap);
            String sbString = sb.toString();
            String sign = SecurityComponents.signatureData(sbString);//获取签名
            TuDouLogUtils.d(TAG, "post --------->   sign=" + sign + ";;params=" + sbString + ";;url=" + urlStr);
            initDefaultRequestHeaderInfo(requestBuilder, sign);//添加默认的头部信息
            requestBuilder.method(oldRequest.method(), newFormBody.build());
            newRequest = requestBuilder.build();
        } else if ("GET".equals(oldRequest.method())) {
            HttpUrl.Builder httpBuilder = oldRequest.url().newBuilder();
            //拼接参数
            StringBuilder sb = new StringBuilder();
            String url = oldRequest.url().toString();
            if (url.contains("newAccessToken")) {
                requestBuilder.addHeader("gh5AppId", "GH50001").url(urlStr).build();
            }
//            if (url.contains(NetConfig.getBaseBuyerUrl())) {  //商城买家订单添加 header
//                requestBuilder.addHeader("Doubo-Token", MyApplication.getLoginUser().getToken());
//                requestBuilder.addHeader("Doubo-UID", MyApplication.getLoginUser().getUid());
//            }
            //获取参数列表
            String[] parts = url.split("\\?");
            //TreeMap里面的数据会按照key值自动升序排列
            TreeMap<String, String> param_map = new TreeMap<>();
            TreeMap<String, String> mDefaultRequestBodyParamesMap = new TreeMap<>();
            initDefaultRequestParamesMap(mDefaultRequestBodyParamesMap);
            //获取参数对
            if (parts.length > 1) {
                String[] param_pairs = parts[1].split("&");
                for (String pair : param_pairs) {
                    String[] param = pair.split("=");
                    if (param.length != 2) {
                        //没有value的参数
                        param_map.put(param[0], "");
                        continue;
                    }
                    param_map.put(param[0], param[1]);
                }
                //添加公共参数
                for (String key : mDefaultRequestBodyParamesMap.keySet()) {
                    if (param_map.containsKey(key)) continue;
                    param_map.put(key, mDefaultRequestBodyParamesMap.get(key));
                    httpBuilder.addQueryParameter(key, param_map.get(key));
                }
                //加sign
                Iterator it = param_map.keySet().iterator();
                while (it.hasNext()) {
                    String key = it.next().toString();
                    String value = param_map.get(key).toString();
                    sb.append(key + value);
                }
            }
            HttpUrl httpUrl = httpBuilder.build();
//            String sign = isDevUrl ? SecurityComponents.signatureData(sb.toString()) : SHA1Utils.shaEncrypt(sb.toString());//获取签名
            String sign = SecurityComponents.signatureData(sb.toString());//获取签名

            initDefaultRequestHeaderInfo(requestBuilder, sign);
            newRequest = requestBuilder
                    .method(oldRequest.method(), oldRequest.body())
                    .url(httpUrl)
                    .build();
        }

        //重新构建Response
        Response response = chain.proceed(newRequest);
        if (NetworkStateDetection.isNetworkAvailable(MyApplication.getContext().getApplicationContext())) {
//          Logger.d("ysl","有网络 设置缓存超时时间1个小时");
            response.newBuilder()
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
//          Logger.d("ysl","没有网络 设置超时时间为4周");
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        return response;
    }

    /**
     * 初始化请求的请求头部信息
     * <p>
     * Created by ZhangPeng on 2018/3/1.
     */
    private void initDefaultRequestHeaderInfo(Request.Builder requestBuilder, String sign) {
        requestBuilder.addHeader("sign", sign);
        requestBuilder.addHeader("osType", "android");
        requestBuilder.addHeader("appVersion", Constants.OS_VERSION);
        requestBuilder.addHeader("package", AppUtils.getAppPackageName());
    }

    /**
     * 初始化请求的Body中默认的参数
     * <p>
     * Created by ZhangPeng on 2018/3/1.
     */
    private void initDefaultRequestParamesMap(TreeMap<String, String> mRequestBodyMap) {
        if (mRequestBodyMap == null)
            mRequestBodyMap = new TreeMap<>();
        if (!mRequestBodyMap.containsKey("appVersion") || TextUtils.isEmpty(mRequestBodyMap.get("appVersion")))
            mRequestBodyMap.put("appVersion", Constants.APP_VERSION);
        if (!mRequestBodyMap.containsKey("deviceModel") || TextUtils.isEmpty(mRequestBodyMap.get("deviceModel")))
            mRequestBodyMap.put("deviceModel", Constants.DEVICE_MODEL);
        if (!mRequestBodyMap.containsKey("osType") || TextUtils.isEmpty(mRequestBodyMap.get("osType")))
            mRequestBodyMap.put("osType", "android");
        if (!mRequestBodyMap.containsKey("osVersion") || TextUtils.isEmpty(mRequestBodyMap.get("osVersion")))
            mRequestBodyMap.put("osVersion", Constants.OS_VERSION);
        if (!mRequestBodyMap.containsKey("seqId") || TextUtils.isEmpty(mRequestBodyMap.get("seqId")))
            mRequestBodyMap.put("seqId", String.valueOf(System.currentTimeMillis()));
//        if ((!mRequestBodyMap.containsKey("douboFingerPrint")) || TextUtils.isEmpty(mRequestBodyMap.get("douboFingerPrint")))
//            mRequestBodyMap.put("douboFingerPrint", MobileSecurity.md5(DeviceInfor.getDeviceId(App.getContext())));
//        if (App.sLocal != null) {
//            mRequestBodyMap.put("city", App.sLocal.getCity());
//            mRequestBodyMap.put("province", App.sLocal.getProvince());
//        }
//        if (null != App.getLoginUser()) {
//            mRequestBodyMap.put("uid", App.getLoginUser().getUid());
//            mRequestBodyMap.put("token", App.getLoginUser().getToken());
//        }
    }

    private void concatParams(StringBuilder sb, FormBody.Builder newFormBody, TreeMap<String, String> param_map) {
        Iterator it = param_map.keySet().iterator();
        //拼接参数
        while (it.hasNext()) {
            String key = it.next().toString();
            String value = (TextUtils.isEmpty(param_map.get(key))) ? "" : ((param_map.get(key)).toString());
            sb.append(key + value);
            //添加公共参数后完整的body
            newFormBody.add(key, value);
        }
    }

    private boolean canInjectIntoBody(Request request) {
        if (request == null) {
            return false;
        }
        if (!TextUtils.equals(request.method(), "POST")) {
            return false;
        }
        RequestBody body = request.body();
        if (body == null) {
            return false;
        }
        MediaType mediaType = body.contentType();
        if (mediaType == null) {
            return false;
        }
        if (!TextUtils.equals(mediaType.subtype(), "x-www-form-urlencoded")) {
            return false;
        }
        return true;
    }

    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

}
