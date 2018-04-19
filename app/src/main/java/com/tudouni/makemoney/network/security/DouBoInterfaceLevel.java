package com.tudouni.makemoney.network.security;

import android.text.TextUtils;


import com.tudouni.makemoney.R;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.utils.TuDouLogUtils;

import java.util.ArrayList;

/**
 * Created by ZhangPeng on 2018/2/6.
 */

public class DouBoInterfaceLevel {

    private ArrayList<String> class3;

    private static class SingletonHolder {
        public static DouBoInterfaceLevel instance = new DouBoInterfaceLevel();
    }

    private DouBoInterfaceLevel() {
        initData();
    }

    private void initData() {
        if (class3 == null || class3.isEmpty()) {
            String[] items = MyApplication.getContext().getResources().getStringArray(R.array.API_CLASS_3);
            if (class3 == null)
                class3 = new ArrayList<>();
            class3.clear();
            for (String item : items)
                class3.add(item);
        }
    }

    public static DouBoInterfaceLevel newInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 判断接口级别
     *
     * @param url
     * @return
     */
    public synchronized boolean isClass3(String url) {
        if (TextUtils.isEmpty(url)) return false;
        try {
            if (class3 == null || class3.isEmpty())
                initData();
            for (String uri : class3) {
                if (url.contains(uri))
                    return true;
            }
        } catch (Exception e) {
            TuDouLogUtils.e("DouBoInterfaceLevel", "登录判断有误：" + e.getMessage());
        }
        return false;
    }


}
