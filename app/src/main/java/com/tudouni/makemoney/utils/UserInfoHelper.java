package com.tudouni.makemoney.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.tudouni.makemoney.model.User;
import com.tudouni.makemoney.myApplication.MyApplication;


/**
 * Created by Administrator on 2018/2/27 0027.
 */

public class UserInfoHelper {
    public static void saveUserDatas(Context context, User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_info_sp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("birthday", user.getBirthday());
        editor.putString("city", user.getCity());
        editor.putString("follows", user.getFollows());
        editor.putString("grade", user.getGrade());
        editor.putString("nickName", user.getNickName());
        editor.putString("photo", user.getPhoto());
        editor.putString("sex", user.getSex());
        editor.putString("signature", user.getSignature());
        editor.putString("token", user.getToken());
        editor.putString("uid", user.getUid());
        editor.putString("phone", user.getPhone());
        editor.putString("invistCode", user.getInvistCode());
        editor.putString("unumber", user.getUnumber());
        editor.putString("gradeName", user.getGradeName());
        editor.putString("expense", user.getExpense());
        editor.putString("stamp", user.getStamp());
        editor.putString("experience", user.getExperience());
        editor.putString("isFollowFan", user.getIsFollowFan());
        editor.putString("background", user.getBackground());
        editor.putString("realname", user.getRealname());
        editor.putString("idNumber", user.getIdNumber());
        editor.putString("frontPhoto", user.getFrontPhoto());
        editor.putString("backPhoto", user.getBackPhoto());
        editor.putString("profit", user.getProfit());
        editor.putBoolean("Zma", user.isZma());
        editor.putInt("seq", user.getSeq());
        editor.putBoolean("closeCommon", user.getCloseCommon());
        editor.putString("bindPhoneStatus", user.getBindPhoneStatus());
        editor.putString("unionid", user.getUnionid());
        editor.putString("pwd", user.getPwd());

        editor.commit();
    }

    public static User getUserDatas(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_info_sp", Context.MODE_PRIVATE);
        User mUser = new User();
        mUser.setBirthday(sharedPreferences.getString("birthday", ""));
        mUser.setCity(sharedPreferences.getString("city", ""));
        mUser.setFollows(sharedPreferences.getString("follows", ""));
        mUser.setGrade(sharedPreferences.getString("grade", ""));
        mUser.setNickName(sharedPreferences.getString("nickName", ""));
        mUser.setPhoto(sharedPreferences.getString("photo", ""));
        mUser.setRole(sharedPreferences.getString("role",""));
        mUser.setSex(sharedPreferences.getString("sex", ""));
        mUser.setSignature(sharedPreferences.getString("signature", ""));
        mUser.setToken(sharedPreferences.getString("token", ""));
        mUser.setUid(sharedPreferences.getString("uid", ""));
        mUser.setPhone(sharedPreferences.getString("phone", ""));
        mUser.setInvistCode(sharedPreferences.getString("invistCode", ""));
        mUser.setUnumber(sharedPreferences.getString("unumber", ""));
        mUser.setGradeName(sharedPreferences.getString("gradeName", ""));
        mUser.setExpense(sharedPreferences.getString("expense", ""));
        mUser.setStamp(sharedPreferences.getString("stamp", ""));
        mUser.setExperience(sharedPreferences.getString("experience", ""));
        mUser.setIsFollowFan(sharedPreferences.getString("isFollowFan", ""));
        mUser.setBackground(sharedPreferences.getString("background", ""));
        mUser.setRealname(sharedPreferences.getString("realname", ""));
        mUser.setIdNumber(sharedPreferences.getString("idNumber", ""));
        mUser.setFrontPhoto(sharedPreferences.getString("frontPhoto", ""));
        mUser.setBackPhoto(sharedPreferences.getString("backPhoto", ""));
        mUser.setProfit(sharedPreferences.getString("profit", ""));
        mUser.setZma(sharedPreferences.getBoolean("Zma", false));
        mUser.setSeq(sharedPreferences.getInt("seq", 0));
        boolean closeCommon = sharedPreferences.getBoolean("closeCommon", false);
        if (closeCommon) {
            mUser.setCloseCommon("1");
        } else {
            mUser.setCloseCommon("0");
        }
        mUser.setBindPhoneStatus(sharedPreferences.getString("bindPhoneStatus", ""));
        mUser.setUnionid(sharedPreferences.getString("unionid", ""));
        mUser.setBindPhoneStatus(sharedPreferences.getString("pwd", ""));
        mUser.setSetAlias(sharedPreferences.getBoolean("setAlias", false));
        return mUser;
    }

    public static void clearLoginUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_info_sp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("birthday", "");
        editor.putString("city", "");
        editor.putString("coins", "");
        editor.putString("fans", "");
        editor.putString("follows", "");
        editor.putString("grade", "");
        editor.putString("hots", "");
        editor.putString("nickName", "");
        editor.putString("photo", "");
        editor.putString("role", "");
        editor.putString("sex", "");
        editor.putString("signature", "");
        editor.putString("token", "");
        editor.putString("uid", "");
        editor.putString("phone", "");
        editor.putString("invistCode", "");
        editor.putString("unumber", "");
        editor.putString("rongtoken", "");
        editor.putString("gradeName", "");
        editor.putString("expense", "");
        editor.putString("stamp", "");
        editor.putString("experience", "");
        editor.putString("isFollowFan", "");
        editor.putString("background", "");
        editor.putString("realname", "");
        editor.putString("idNumber", "");
        editor.putString("frontPhoto", "");
        editor.putString("backPhoto", "");
        editor.putString("profit", "");
        editor.putString("strangerreminder", "");
        editor.putBoolean("appreminder", false);
        editor.putString("groupAgree", "");
        editor.putBoolean("Zma", false);
        editor.putInt("seq", 0);
        editor.putString("roomID", "");
        editor.putBoolean("closeCommon", false);
        editor.putString("bindPhoneStatus", "");
        editor.putString("cover", "");
        editor.putString("officialStatus", "");
        editor.putString("dynamicCount", "");
        editor.putString("unionid", "");
        editor.putString("pwd", "");
        editor.putBoolean("setAlias", false);
        editor.commit();

    }

    /**
     * 是否设置过别名
     *
     * @param context
     * @return
     */
    public static boolean isSetAlias(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("user_info_sp", Context.MODE_PRIVATE);
            return sharedPreferences.getBoolean("setAlias", false);
        } catch (Exception e) {
            TuDouLogUtils.e("UserInfoHelper", e.getMessage());
        }
        return false;
    }

    /**
     * 设置别名
     *
     * @param context
     */
    public static void setAlias(Context context) {
        SharedPreferences.Editor editor = null;
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("user_info_sp", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putBoolean("setAlias", true);
            MyApplication.getLoginUser().setSetAlias(true);
        } catch (Exception e) {
            TuDouLogUtils.e("UserInfoHelper", e.getMessage());
        } finally {
            if (editor != null)
                editor.commit();
        }
    }


}
