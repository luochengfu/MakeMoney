package com.tudouni.makemoney.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.view.CenterLoadingView;
import com.tudouni.makemoney.view.FragmentLoadingView;

/**
 * 等待提示框
 * Created by huang on 2017/5/23.
 */

public class LoadingUtils {

    private FragmentLoadingView load;
    private Context mContext;

    public LoadingUtils(Context context){
        this.mContext = context;
    }

    /**
     * 统一耗时操作Dialog
     */
    public void showLoading(String txt) {
        if (load == null) {
            load = new FragmentLoadingView(mContext, R.style.dialog);
        }
        if (load.isShowing()) {
            load.dismiss();
        }
        if (!TextUtils.isEmpty(txt))
            load.setTitle(txt);
        if (mContext instanceof Activity && ((Activity)mContext).isFinishing()) {
            return;
        }
        load.show();
    }

    /**
     * 关闭Dialog
     */
    public void dismissLoading() {
        if (mContext instanceof Activity && ((Activity)mContext).isFinishing()) {
            return;
        }
        if (load != null && load.isShowing()) {
            load.dismiss();
        }
    }


}
