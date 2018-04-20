package com.tudouni.makemoney.utils;

import android.widget.TextView;

/**
 * TextView 设置文案
 * Created by ZhangPeng on 2018/4/2.
 */

public class TuDouTextUtil {
    /**
     * 一万元为单位
     *
     * @param textView
     * @param number
     */
    public static void setTextToTextView(TextView textView, long number) {
        if (textView == null) {
            TuDouLogUtils.e("TuDouTextUtil", "控件空指针异常！");
            return;
        }
        String showStr = "¥";
        if (number > 10000) {
            String s = number + "";
            showStr += s.substring(0, s.length() - 4) + "万+";
        } else {
            showStr += number;
        }
        setTextToTextView(textView, showStr);
    }

    /**
     * 格式化为一万元给单位一万元为单位
     *
     * @param textView
     */
    public static void setTextToTextViewFormatWan(TextView textView, String str) {
        setTextToTextViewFormatWan(textView, str, false);
    }

    /**
     * 格式化为一万元给单位一万元为单位
     *
     * @param textView
     */
    public static void setTextToTextViewFormatWan(TextView textView, String str, boolean withDollar) {
        try {
            long number = Long.parseLong(str);
            String showStr = withDollar ? "¥" : "";
            if (number > 10000) {
                String s = number + "";
                showStr += s.substring(0, s.length() - 4) + "万+";
            } else {
                showStr += number;
            }
            setTextToTextView(textView, showStr);
        } catch (Exception e) {
            TuDouLogUtils.e("TuDouTextUtil", "数据转换报错str=" + str + ";转为Int");
        }
    }


    public static void setTextToTextView(TextView textView, String str) {
        if (str == null || textView == null) {
            TuDouLogUtils.e("TuDouTextUtil", (str == null) ? "设置的文字不合法！" : "控件空指针异常！");
            return;
        }
        textView.setText(str);
    }
}
