package com.tudouni.makemoney.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.widget.Toast;


import com.tudouni.makemoney.utils.base.BaseViewHelper;

import java.lang.ref.WeakReference;


public class ToastUtil {

    private static Toast toast = null;
    private static WeakReference<Toast> mShowingToastRef;

    private ToastUtil() {
    }

    public static void showError(String text, int errrorCode) {
        show(text + "（" + errrorCode + ")");
    }

    public static void show(CharSequence text) {
        if (TextUtils.isEmpty(text))
            return;
        if (mShowingToastRef != null) {
            toast = mShowingToastRef.get();
        }
        if (toast == null) {  //保证不重复弹出
            if (text.length() < 10) {
                toast = Toast.makeText(BaseViewHelper.getInstance().getApplicationContext(), text, Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(BaseViewHelper.getInstance().getApplicationContext(), text, Toast.LENGTH_LONG);
            }
            mShowingToastRef = new WeakReference<>(toast);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    public static void show(@StringRes int resId) {
        show(BaseViewHelper.getInstance().getApplicationContext().getString(resId));
    }

    public static void show(Context context, String message) {
        if (context == null) {
            return;
        }
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

}