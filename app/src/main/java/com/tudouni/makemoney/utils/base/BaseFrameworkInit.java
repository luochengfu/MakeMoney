package com.tudouni.makemoney.utils.base;


import com.tudouni.makemoney.network.NetConfig;
import com.tudouni.makemoney.network.NetInterceptor;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;


public class BaseFrameworkInit implements IBaseRequirement {

    @Override
    public String getUrl() {
        return NetConfig.getBaseUrl();
    }

    @Override
    public List<Interceptor> getInterceptors() {
        List<Interceptor> list = new ArrayList<>();
        list.add(new NetInterceptor());
        return list;
    }

    @Override
    public IActivityHelper getActivityHelper() {
        return new ActivityHelper();
    }

    @Override
    public IFragmentHelper getFragmentHelper() {
        return null;
    }

    @Override
    public String getImageCacheDir() {
        return null;
    }
}
