package com.tudouni.makemoney.utils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.tudouni.makemoney.activity.AccountSecurityActivity;
import com.tudouni.makemoney.activity.BindingInvitationActivity;
import com.tudouni.makemoney.activity.FaceToFaceActivity;
import com.tudouni.makemoney.activity.InvitationDouFenActivity;
import com.tudouni.makemoney.activity.LoginActivity;
import com.tudouni.makemoney.activity.SettingActivity;
import com.tudouni.makemoney.myApplication.MyApplication;

/**
 * 跳转中心
 * Created by ZhangPeng on 2018/4/20.
 */

public class ForwardUtils {
    public static void target(Activity oriActivity, String url) {
        try {
            if (url.startsWith(Constants.BINDING_FACE_TO_FACE)) {//面对面绑定
                Intent intent = new Intent(oriActivity, FaceToFaceActivity.class);
                toIntent(oriActivity, url, intent);
            } else if (MyApplication.appConfig.isShareInvistor(url)) {//绑定界面
                Intent intent = new Intent(oriActivity, BindingInvitationActivity.class);
                toIntent(oriActivity, url, intent);
            } else if (url.startsWith(Constants.INVISIT_POSTER)) {//分享海报界面
                String code = null;
                try {
                    code = ((url.substring(url.indexOf("?") + 1, url.indexOf("&"))).split("="))[1];
                } catch (Exception e) {
                    TuDouLogUtils.e("ForwardUtils", "解析地址获取Code（邀请码）报错：" + e.getMessage());
                }
//                Intent intent = new Intent(oriActivity, MyInvitationActivity.class);
                Intent intent = new Intent(oriActivity, InvitationDouFenActivity.class);
                intent.putExtra("code", code);
                toIntent(oriActivity, url, intent);
            } else if (url.startsWith(Constants.SETTING)) {
                Intent intent = new Intent(oriActivity, SettingActivity.class);
                toIntent(oriActivity, url, intent);
            } else if (url.startsWith(Constants.ACCOUNT_SECURITY)) {
                Intent intent = new Intent(oriActivity, AccountSecurityActivity.class);
                toIntent(oriActivity, url, intent);
            } else if (url.startsWith(Constants.LOGIN)) {
                Intent intent = new Intent(oriActivity, LoginActivity.class);
                toIntent(oriActivity, url, intent);
                oriActivity.finish();
            }

        } catch (Exception e) {
            TuDouLogUtils.e("ForwardUtils", e.getMessage());
        }
    }

    /**
     * 跳转到对应的activity
     *
     * @param oriActivity
     * @param url
     * @param intent
     */
    private static void toIntent(Activity oriActivity, String url, Intent intent) {
        setIntentInfo(intent, url);
        oriActivity.startActivity(intent);
    }

    private static void setIntentInfo(Intent intent, String url) {
        try {
            int index = url.indexOf("?");
            if (index > -1) {
                String paramStr = url.substring(index + 1, url.length());
                String everyParams[] = paramStr.split("&");
                for (String e : everyParams) {
                    String[] a = e.split("=");
                    TuDouLogUtils.i("pppARGS", a[0].trim() + ":" + a[1].trim());
                    if (a.length == 2) {
                        intent.putExtra(a[0].trim(), a[1].trim());
                    } else {
                        intent.putExtra(a[0].trim(), "");
                    }
                }
            }
        } catch (Exception e) {
            TuDouLogUtils.e("ForwardUtils", e.getMessage());
        }
    }

    private static CharSequence getLastName(String s) {
        if (!TextUtils.isEmpty(s)) {
            String[] strings = s.split("/");
            return strings[strings.length - 1];
        }
        return "";
    }

}
