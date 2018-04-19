package com.tudouni.makemoney.utils.base;

import android.app.Activity;

import com.umeng.analytics.MobclickAgent;

/**
 * Activity拦截器
 */

class ActivityHelper implements IActivityHelper {

    @Override
    public void onCreate(Activity activity) {

    }

    @Override
    public void onStart(Activity activity) {

    }

    @Override
    public void onResume(Activity activity) {
        MobclickAgent.onResume(activity);
    }

    @Override
    public void onPause(Activity activity) {
        MobclickAgent.onPause(activity);
    }

    @Override
    public void onStop(Activity activity) {

    }

    @Override
    public void onDestroy(Activity activity) {

    }
}
