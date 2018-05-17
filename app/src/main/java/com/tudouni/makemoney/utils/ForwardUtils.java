package com.tudouni.makemoney.utils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.tudouni.makemoney.activity.AccountSecurityActivity;
import com.tudouni.makemoney.activity.BindingInvitationActivity;
import com.tudouni.makemoney.activity.FaceToFaceActivity;
import com.tudouni.makemoney.activity.H5Activity;
import com.tudouni.makemoney.activity.InvitationDouFenActivity;
import com.tudouni.makemoney.activity.realname.IdentificationActivity;
import com.tudouni.makemoney.activity.realname.RealTelBandingActivity;
import com.tudouni.makemoney.activity.realname.RealnameActivity;
import com.tudouni.makemoney.activity.realname.RealnameActivity2;
import com.tudouni.makemoney.activity.realname.RealnameFinalActivity;
import com.tudouni.makemoney.activity.LoginActivity;
import com.tudouni.makemoney.activity.MainActivity;
import com.tudouni.makemoney.activity.MessageActivity;
import com.tudouni.makemoney.activity.SettingActivity;
import com.tudouni.makemoney.activity.UserInfoActivity;
import com.tudouni.makemoney.activity.WebvewRefreshActivity;
import com.tudouni.makemoney.activity.withdrawmoney.IncomeActivity;
import com.tudouni.makemoney.activity.withdrawmoney.TelAuthenticationActivity;
import com.tudouni.makemoney.activity.withdrawmoney.WithdrawMoneyActivity;
import com.tudouni.makemoney.myApplication.MyApplication;

import java.net.URLDecoder;

/**
 * 跳转中心
 * Created by ZhangPeng on 2018/4/20.
 */

public class ForwardUtils {
    public static void target(Activity oriActivity, String url) {
        try {
            if (TextUtils.isEmpty(url)) return;
            if (url.startsWith(Constants.BINDING_FACE_TO_FACE)) {//面对面绑定
                Intent intent = new Intent(oriActivity, FaceToFaceActivity.class);
                toIntent(oriActivity, url, intent);
            } else if (MyApplication.appConfig != null && MyApplication.appConfig.isShareInvistor(url)) {//绑定界面
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
            } else if (url.equals(Constants.REALNAME)) {
                Intent intent = new Intent(oriActivity, RealnameActivity.class);
                toIntent(oriActivity, url, intent);
            } else if (url.equals(Constants.REALNAME_FINAL)) {
                Intent intent = new Intent(oriActivity, RealnameFinalActivity.class);
                toIntent(oriActivity, url, intent);
            } else if (url.equals(Constants.UPLOAD_IDCARD)) {
                Intent intent = new Intent(oriActivity, IdentificationActivity.class);
                toIntent(oriActivity, url, intent);
            } else if (url.equals(Constants.REALNAME_TEL_BANDING)) {
                Intent intent = new Intent(oriActivity, RealTelBandingActivity.class);
                toIntent(oriActivity, url, intent);
            } else if (url.equals(Constants.REALNAME2)) {
                Intent intent = new Intent(oriActivity, RealnameActivity2.class);
                toIntent(oriActivity, url, intent);
            } else if (url.equals(Constants.WITHDRAW_MONEY)) {
                Intent intent = new Intent(oriActivity, WithdrawMoneyActivity.class);
                toIntent(oriActivity, url, intent);
            } else if (url.equals(Constants.WITHDRAW_TEL)) {
                Intent intent = new Intent(oriActivity, TelAuthenticationActivity.class);
                toIntent(oriActivity, url, intent);
            } else if (url.equals(Constants.NATIVE_INCOME)) {
                Intent intent = new Intent(oriActivity, IncomeActivity.class);
                toIntent(oriActivity, url, intent);
            } else if (url.startsWith(Constants.LOGIN)) {
                Intent intent = new Intent(oriActivity, LoginActivity.class);
                toIntent(oriActivity, url, intent);
                oriActivity.finish();
            } else if (url.startsWith(Constants.USERINFO)) { //编辑用户资料
                Intent intent = new Intent(oriActivity, UserInfoActivity.class);
                toIntent(oriActivity, url, intent);
            } else if (url.startsWith(Constants.MAIN) || (url.startsWith(Constants.HOME))) {
                Intent intent = new Intent(oriActivity, MainActivity.class);
                toIntent(oriActivity, url, intent);
                oriActivity.finish();
            } else if (url.startsWith(Constants.MESSAGE)) {//系统消息
                Intent intent = new Intent(oriActivity, MessageActivity.class);
                toIntent(oriActivity, url, intent);
            } else if (url.startsWith("http") || url.startsWith(Constants.h5_myinvite) || url.startsWith("https") || url.startsWith(Constants.h5_bindsearch)) {//本次判断放在最后
                Intent intent = new Intent(oriActivity, H5Activity.class);
                toH5Intent(oriActivity, url, intent);
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

    private static void toH5Intent(Activity oriActivity, String url, Intent intent) throws Exception {
        intent.putExtra("url", URLDecoder.decode(url, "utf-8"));
        toIntent(oriActivity, url, intent);
    }

}
