package com.tudouni.makemoney.utils;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/22 0022.
 */

public class ClipboardUtil
{
    /**
     * 由于系统剪贴板在某些情况下会多次调用，但调用间隔基本不会超过5ms
     * 考虑到用户操作，将阈值设为100ms，过滤掉前几次无效回调
     */
    private static final int THRESHOLD = 100;

    private Context mContext;
    private static ClipboardUtil mInstance;
    private ClipboardManager mClipboardManager;

    private Handler mHandler = new Handler();
    private ClipboardChangedRunnable mRunnable = new ClipboardChangedRunnable();

    private List<OnPrimaryClipChangedListener> mOnPrimaryClipChangedListeners = new ArrayList<>();

    private ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() {
        @Override
        public void onPrimaryClipChanged() {
            mHandler.removeCallbacks(mRunnable);
            mHandler.postDelayed(mRunnable, THRESHOLD);
        }
    };

    /**
     * 获取ClipboardUtil实例，记得初始化
     *
     * @return
     */
    public static ClipboardUtil getInstance() {
        return mInstance;
    }

    /**
     * 单例。暂时不清楚此处传 Activity 的 Context 是否会造成内存泄漏
     * 建议在 Application 的 onCreate 方法中实现
     *
     * @param context
     * @return
     */
    public static ClipboardUtil init(Context context) {
        if (mInstance == null) {
            mInstance = new ClipboardUtil(context);
        }
        return mInstance;
    }

    public void addOnPrimaryClipChangedListener(OnPrimaryClipChangedListener listener) {
        if (!mOnPrimaryClipChangedListeners.contains(listener)) {
            mOnPrimaryClipChangedListeners.add(listener);
        }
    }

    public void removeOnPrimaryClipChangedListener(OnPrimaryClipChangedListener listener) {
        mOnPrimaryClipChangedListeners.remove(listener);
    }

    /**
     * 判断剪贴板内是否有数据
     *
     * @return
     */
    public boolean hasPrimaryClip() {
        return mClipboardManager.hasPrimaryClip();
    }

    /**
     * 获取剪贴板中第一条String
     *
     * @return
     */
    public String getClipText() {
        if (!hasPrimaryClip()) {
            return null;
        }
        ClipData data = mClipboardManager.getPrimaryClip();
        if (data != null
                && mClipboardManager.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
            return data.getItemAt(0).getText().toString();
        }
        return null;
    }

    /**
     * 获取剪贴板中第一条String
     *
     * @param context
     * @return
     */
    public String getClipText(Context context) {
        return getClipText(context, 0);
    }

    /**
     * 获取剪贴板中指定位置item的string
     *
     * @param context
     * @param index
     * @return
     */
    public String getClipText(Context context, int index) {
        if (!hasPrimaryClip()) {
            return null;
        }
        ClipData data = mClipboardManager.getPrimaryClip();
        if (data == null) {
            return null;
        }
        if (data.getItemCount() > index) {
            return data.getItemAt(index).coerceToText(context).toString();
        }
        return null;
    }

    /**
     * 清空剪贴板
     */
    public void clearClip() {
        mClipboardManager.setPrimaryClip(null);
    }


    private ClipboardUtil(Context context) {
        mContext = context;
        mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        mClipboardManager.addPrimaryClipChangedListener(onPrimaryClipChangedListener);
    }

    private class ClipboardChangedRunnable implements Runnable {

        @Override
        public void run() {
            for (OnPrimaryClipChangedListener listener : mOnPrimaryClipChangedListeners) {
                listener.onPrimaryClipChanged(mClipboardManager);
            }
        }
    }

    /**
     * 自定义 OnPrimaryClipChangedListener
     * 用于处理某些情况下系统多次调用 onPrimaryClipChanged()
     */
    public interface OnPrimaryClipChangedListener {
        void onPrimaryClipChanged(ClipboardManager clipboardManager);
    }

    public CharSequence coercePrimaryClipToText() {
        if (!hasPrimaryClip()) {
            return null;
        }
        return mClipboardManager.getPrimaryClip().getItemAt(0).coerceToText(mContext);
    }

    public CharSequence coercePrimaryClipToStyledText() {
        if (!hasPrimaryClip()) {
            return null;
        }
        return mClipboardManager.getPrimaryClip().getItemAt(0).coerceToStyledText(mContext);
    }

    public CharSequence coercePrimaryClipToHtmlText() {
        if (!hasPrimaryClip()) {
            return null;
        }
        return mClipboardManager.getPrimaryClip().getItemAt(0).coerceToHtmlText(mContext);
    }

    /**
     * 获取当前剪贴板内容的MimeType
     *
     * @return 当前剪贴板内容的MimeType
     */
    public String getPrimaryClipMimeType() {
        if (!hasPrimaryClip()) {
            return null;
        }
        return mClipboardManager.getPrimaryClipDescription().getMimeType(0);
    }

    /**
     * 获取剪贴板内容的MimeType
     *
     * @param clip 剪贴板内容
     * @return 剪贴板内容的MimeType
     */
    public String getClipMimeType(ClipData clip) {
        return clip.getDescription().getMimeType(0);
    }

    /**
     * 获取剪贴板内容的MimeType
     *
     * @param clipDescription 剪贴板内容描述
     * @return 剪贴板内容的MimeType
     */
    public String getClipMimeType(ClipDescription clipDescription) {
        return clipDescription.getMimeType(0);
    }

    /**
     * 将文本拷贝至剪贴板
     *
     * @param text
     */
    public void copyText(String label, String text) {
        ClipData clip = ClipData.newPlainText(label, text);
        mClipboardManager.setPrimaryClip(clip);
    }

}
