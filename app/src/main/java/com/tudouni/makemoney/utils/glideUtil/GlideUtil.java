package com.tudouni.makemoney.utils.glideUtil;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.tudouni.makemoney.interfaces.DownFileCallBack;
import com.tudouni.makemoney.utils.CornersTransform;
import com.tudouni.makemoney.utils.TuDouLogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

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


    public void loadImage(Context context, String url, ImageView imageView, float redis, int defaultImg) {
        if (imageView == null) return;
        Glide.with(context)
                .load(url)
                .transform(new CornersTransform(context, redis))
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
    public static void circleImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .crossFade()
                .bitmapTransform(new GlideCircleTransform(imageView.getContext()))
                .into(imageView);
    }

    /**
     * Glide保存图片
     *
     * @param context
     * @param url
     */
    public static void savePicture(Context context, String url,
                                   final DownFileCallBack downFileCallBack) {
        String fileName = url.substring(url.lastIndexOf("/", url.length()));
        Glide.with(context).load(url).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
            @Override
            public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                try {
                    savaFileToSD(context, fileName, bytes, downFileCallBack);
                } catch (Exception e) {
                    TuDouLogUtils.e("", "保存图片出错：" + e.getMessage());
                }
            }
        });
    }

    /**
     * 往SD卡写入文件的方法
     *
     * @param context
     * @param filename
     * @param bytes
     * @throws Exception
     */
    public static void savaFileToSD(Context context, String filename,
                                    byte[] bytes, DownFileCallBack downFileCallBack) throws Exception {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()
                + File.separator + "豆播" + File.separator + "豆播相册";
        File dir1 = new File(filePath);
        if (!dir1.exists()) {
            dir1.mkdirs();
        }
        filename = filePath + "/" + filename;
        //这里就不要用openFileOutput了,那个是往手机内存中写数据的
        FileOutputStream output = new FileOutputStream(filename);
        output.write(bytes);
        //将bytes写入到输出流中
        output.close();
        //关闭输出流
        if (downFileCallBack != null)
            downFileCallBack.onFinish();
        Toast.makeText(context, "图片已成功保存到" + filePath, Toast.LENGTH_SHORT).show();
    }


}
