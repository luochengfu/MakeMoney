package com.tudouni.makemoney.utils.glideUtil;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by ZhangPeng on 2018/4/20.
 */

public class GlideUtil {
    public static GlideUtil instance = new GlideUtil();

    public GlideUtil() {
    }

    public static GlideUtil getInstance() {
        return instance;
    }

    /**
     * 加载圆型网络图片
     *
     * @param context
     * @param url        图片地址
     * @param imageView
     * @param defaultImg 默认图片
     */
    public void loadCircle(Context context, String url, ImageView imageView, int defaultImg) {
        Glide.with(context)
                .load(url)
                .error(defaultImg)
                .fallback(defaultImg)
                .crossFade()
                .bitmapTransform(new GlideCircleTransform(context))
                .into(imageView);
    }

    public void loadImage(Context context, String url, ImageView imageView, int defaultImg) {
        Glide.with(context)
                .load(url)
                .error(defaultImg)
                .fallback(defaultImg)
                .crossFade()
                .centerCrop()
                .into(imageView);
    }


    @BindingAdapter("loadImage")
    public static void bindImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).crossFade().centerCrop().into(imageView);
    }

    @BindingAdapter({"roundImageUrl", "roundImageCornerRadius"})
    public static void roundImage(ImageView imageView, String url, String dp) {
        int dpInt = Integer.valueOf(dp);
        Glide.with(imageView.getContext()).load(url).crossFade().centerCrop().bitmapTransform(new GlideRoundTransform(imageView.getContext(), dpInt)).into(imageView);
    }


}
