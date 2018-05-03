package com.tudouni.makemoney.widget.sharePart;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.utils.base.AppUtils;
import com.tudouni.makemoney.widget.callBack.ApiCallback;
import com.tudouni.makemoney.widget.callBack.ServiceException;
import com.tudouni.makemoney.widget.pop.BottomPushPopupWindow;
import com.tudouni.makemoney.widget.sharePart.model.Share;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhangPeng on 2018/4/21.
 */
public class ShareWindow_v3 extends BottomPushPopupWindow<Void> implements OnClickListener {

    private Activity mActivity;
    private Share mShare;
    private Share.Type mType;
    private Object mData;
    private Object mExtra;
    private ApiCallback<Share> mCallback;
    private ArrayList<Map<String, Object>> dataList;
    private SimpleAdapter adapter;
    private GridView gridView;

    private boolean canClick = true;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            canClick = true;
        }
    };


    /**
     * 分享窗口
     *
     * @param activity 略
     * @param type     分享类型，影响数据封装以及分享行为
     * @param data     分享原始数据对象，依据type接受不同数据，自动组装分享内容，见 {@link Share#obtain(Share.Type, Object)}<br>
     *                 也可以直接传一个Share对象
     * @param extra    额外参数，可为空
     * @param callback 第三方分享的回调，如为空则toast提示
     */
    public ShareWindow_v3(Activity activity, Share.Type type, Object data, @Nullable Object extra, @Nullable ApiCallback<Share> callback) {
        super(activity, activity, null);
        mActivity = activity;
        mType = type;
        mData = data;
        mExtra = extra;
        mCallback = callback;
        mShare = data instanceof Share ? (Share) data : Share.obtain(type, data);
        initData();
        initViews();
    }

    private void initData() {
        int icno[] = {R.mipmap.share_wx, R.mipmap.share_circle,
                R.mipmap.share_qq, R.mipmap.share_copy,
                R.mipmap.icon_shre_keep};
        String name[] = {"微信", "朋友圈", "QQ", "复制", "保存"};
        ArrayList<Integer> invalidPosition = new ArrayList<>();
        if (mType != null && mType == Share.Type.LIVE) {
            invalidPosition.add(1);
            invalidPosition.add(7);
        }
        if (mType != null && mType == Share.Type.IMAGE_POTATOES) {
            invalidPosition.add(3);
            invalidPosition.add(4);
        }
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < icno.length; i++) {
            if (invalidPosition.contains(i)) continue;
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", icno[i]);
            map.put("text", name[i]);
            dataList.add(map);
        }
    }

    @Override
    protected View generateCustomView(Void aVoid) {     //此方法在父类的构造方法中调用，此时各fields尚未赋值
        return View.inflate(context, R.layout.pop_share_zp, null);
    }

    private void initViews() {
        View view = getContentView();
        gridView = (GridView) view.findViewById(R.id.gridview);

        String[] from = {"img", "text"};
        int[] to = {R.id.img, R.id.text};
        adapter = new SimpleAdapter(mActivity, dataList, R.layout.iteam_share_layout, from, to);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!canClick) return;
                canClick = false;
                handler.postDelayed(null, 500);
                iteamOnclick((Integer) dataList.get(position).get("img"));
            }
        });
        view.findViewById(R.id.pop_layout).setOnClickListener(this);
        view.findViewById(R.id.tvCancel).setOnClickListener(this);
    }

    /**
     * iteam点击响应
     *
     * @param imageId
     */
    private void iteamOnclick(int imageId) {
        String type = null;
        switch (imageId) {
            case R.mipmap.share_wx://分享到微信
                type = "me_wxshare";
                if (!AppUtils.isWXInstall(mActivity)) {
                    ToastUtil.show(mActivity, "请安装微信客户端");
                    dismiss();
                    return;
                }
                doShare(SHARE_MEDIA.WEIXIN);
                break;
            case R.mipmap.share_circle://分享到微信朋友圈
                type = "me_fqshare";
                if (!AppUtils.isWXInstall(mActivity)) {
                    ToastUtil.show(mActivity, "请安装微信客户端");
                    dismiss();
                    return;
                }
                doShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case R.mipmap.share_qq://分享到qq
                type = "me_qqshare";
                if (!AppUtils.isQQInstall(mActivity)) {
                    ToastUtil.show(mActivity, "请安装QQ客户端");
                    dismiss();
                    return;
                }
                doShare(SHARE_MEDIA.QQ);
                break;
            case R.mipmap.share_copy://复制
                type = "me_copyshare";
                doCopy();
                dismiss();
                break;
            case R.mipmap.icon_shre_keep://保存
//                doSave();
                dismiss();
                break;
        }
//        if (!TextUtils.isEmpty(type) && mActivity instanceof InvitationDouFenActivity)
//            MobclickAgent.onEvent(mActivity, type);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pop_layout:
            case R.id.tvCancel:
                dismiss();
                break;
        }
    }


    private void doShare(SHARE_MEDIA platform) {
        ShareUtil.shareURL2(activity, mType == Share.Type.IMAGE_POTATOES, platform, mShare, new ApiCallback<Share>() {
            @Override
            public void onSuccess(Share data) {
                if (mCallback != null) {
                    mCallback.onSuccess(data);
                } else {
                    ToastUtil.show(mActivity, "分享成功");
                }
                dismiss();
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                if (mCallback != null)
                    mCallback.onFailure(e);
                else
                    ToastUtil.show(mActivity, "分享失败");
            }
        });
    }

    private void doCopy() {
        ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(mShare.getTargetUrl());
        ToastUtil.show(activity, "链接复制成功");
        if (mCallback != null) {
            mCallback.onSuccess(mShare);
        }
    }

}
