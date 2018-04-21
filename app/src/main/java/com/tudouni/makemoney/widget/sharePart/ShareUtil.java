package com.tudouni.makemoney.widget.sharePart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.activity.BaseActivity;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.utils.TuDouLogUtils;
import com.tudouni.makemoney.widget.callBack.ApiCallback;
import com.tudouni.makemoney.widget.sharePart.model.Share;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by ZhangPeng on 2018/4/21.
 */

public class ShareUtil {

    /**
     * @param activity
     * @param isSharePotatoes 是否是分享海报
     * @param platform
     * @param share
     * @param apiCallback
     */
    public static void shareURL2(final Activity activity, boolean isSharePotatoes, final SHARE_MEDIA platform, final Share share,
                                 final ApiCallback<Share> apiCallback) {

        ShareAction shareAction = new ShareAction(activity);
        shareAction.setPlatform(platform)
                .withText(share.getContent())
//                .withTitle(share.getTitle())
//                .withTargetUrl(share.getTargetUrl())
//                .withMedia(image)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA platform) {
                        TuDouLogUtils.d("plat", "platform" + platform);

                        apiCallback.onSuccess(share);
                    }

                    @Override
                    public void onError(SHARE_MEDIA platform, Throwable t) {

                        if (t != null) {
                            TuDouLogUtils.d("throw", "throw:" + t.getMessage());
                        }
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        Toast.makeText(activity, " 分享取消了", Toast.LENGTH_SHORT).show();
                    }
                });

        if (!isSharePotatoes) {
            UMImage image = null;
            if (!TextUtils.isEmpty(share.getCover())) {
                image = new UMImage(activity, share.getCover());//网络图片
                image.compressStyle = UMImage.CompressStyle.SCALE;
            } else {
                image = new UMImage(activity, R.mipmap.ic_launcher);//网络图片
            }

            UMWeb umWeb = new UMWeb(share.getTargetUrl());
            umWeb.setTitle(share.getTitle());
            umWeb.setThumb(image);
            umWeb.setDescription(share.getContent());
            shareAction.withMedia(umWeb);
        } else {
            UMImage umImage = new UMImage(activity, share.getMinePotatoes());
            umImage.setTitle(share.getTitle());
            umImage.setThumb(new UMImage(activity, R.mipmap.ic_launcher));
            umImage.setDescription(share.getContent());
            shareAction.withMedia(umImage);
        }
        shareAction.share();

    }

    public static void shareURL2(Activity activity, SHARE_MEDIA platform, Share share,
                                 ApiCallback<Share> apiCallback) {
        shareURL2(activity, false, platform, share, apiCallback);
    }


    public static void showTip(Context activity, String msg) {
        //暂时不实用，因为这个是Dialog会导致Activity走onPause生命周期
//        try {
//            ColaProgressTip cpTip = ColaProgressTip.show(activity, msg, false, true, null, null);
//            ColaProgressTip.showTip(900l, cpTip);
//        } catch (Exception e) {
//        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
//        }
        ToastUtil.show(msg);

    }

}
