package com.tudouni.makemoney.utils.base;

import android.content.Context;

/**
 * 给这个框架提供应用环境,
 */

public class BaseViewHelper {

    private static BaseViewHelper mInstance;

    private IBaseRequirement mBaseRequirement;

    private Context mContext;

    private BaseViewHelper() {
    }

    public static BaseViewHelper getInstance() {
        if (mInstance == null) {
            mInstance = new BaseViewHelper();
        }
        return mInstance;
    }

    public void setBaseRequirement(IBaseRequirement requirement) {
        mBaseRequirement = requirement;
    }

    public Context getApplicationContext() {
        return mContext;
    }

    public void setApplicationContext(Context context) {
        mContext = context;
    }

    public IActivityHelper getActivityHelper() {
        if (mBaseRequirement != null) {
            return mBaseRequirement.getActivityHelper();
        }

        return null;
    }

    public IFragmentHelper getFragmentHelper() {

        if (mBaseRequirement != null) {
            return mBaseRequirement.getFragmentHelper();
        }

        return null;
    }

}
