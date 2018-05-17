package com.tudouni.makemoney.utils.glideUtil;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tudouni.makemoney.utils.CornersTransform;

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
        if (imageView == null) return;
        Glide.with(context)
                .load(url)
                .error(defaultImg)
                .fallback(defaultImg)
                .crossFade()
                .into(imageView);
    }



    public void loadImage(Context context, String url, ImageView imageView, float redis,int defaultImg) {
        if (imageView == null) return;
        Glide.with(context)
                .load(url)
                .transform(new CornersTransform(context,redis))
                .error(defaultImg)
                .fallback(defaultImg)
                .into(imageView);
    }


    /**
     * 加载图片【数据绑定方式，xml中引用】，也可以直接调用
     *
     * @param imageView 图片View
     * @param url       路径
     */
    @BindingAdapter("loadImage")
    public static void bindImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).crossFade().into(imageView);
    }

    /**
     * 加载圆角图片【数据绑定方式，xml中引用】，也可以直接调用
     *
     * @param imageView 图片View
     * @param url       图片路径
     * @param dp        圆角弧度
     */
    @BindingAdapter({"roundImageUrl", "roundImageCornerRadius"})
    public static void roundImage(ImageView imageView, String url, String dp) {
        int dpInt = Integer.valueOf(dp);
        Glide.with(imageView.getContext()).load(url).crossFade().bitmapTransform(new GlideRoundTransform(imageView.getContext(), dpInt)).into(imageView);
    }

    @BindingAdapter("loadCircleImage")
    public static void circleImage(ImageView imageView, String url){
        Glide.with(imageView.getContext())
                .load(url)
                .crossFade()
                .bitmapTransform(new GlideCircleTransform(imageView.getContext()))
                .into(imageView);
    }


}
