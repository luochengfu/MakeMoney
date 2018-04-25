package com.tudouni.makemoney.myApplication;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.tudouni.makemoney.network.OkHttp3Utils;
import com.tudouni.makemoney.utils.Constants;
import com.tudouni.makemoney.utils.base.BaseViewHelper;
import com.tudouni.makemoney.utils.base.IBaseRequirement;

import org.xutils.x;

/**
 *
 */
public abstract class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        BaseViewHelper.getInstance().setApplicationContext(getApplicationContext());
        // 初始化框架
        IBaseRequirement requirement = getBaseRequirement();
        if (requirement != null) {
            BaseViewHelper.getInstance().setBaseRequirement(requirement);
            OkHttp3Utils.setInterceptors(requirement.getInterceptors());
            if (!TextUtils.isEmpty(requirement.getUrl()))
                Constants.HTTP_URL = requirement.getUrl();
            if (!TextUtils.isEmpty(requirement.getImageCacheDir()))
                Constants.IMAGE_CACHE_DIR = requirement.getImageCacheDir();
        }
        x.Ext.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 获取实现 IBaseRequirement 接口的对象，来初始化框架
     **/
    public abstract IBaseRequirement getBaseRequirement();

}
