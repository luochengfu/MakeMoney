package com.tudouni.makemoney.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.widget.sharePart.model.Share;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

import com.uuzuche.lib_zxing.activity.CodeUtils;


/**
 * Created by ZhangPeng on 2018/4/8.
 */

public class BitMapUtils {
    private static String TAG = "BitMapUtils";

    /**
     * 把两个位图覆盖合成为一个位图，以底层位图的长宽为基准
     *
     * @param backBitmap  在底部的位图
     * @param frontBitmap 盖在上面的位图
     * @return
     */
    public static Bitmap mergeBitmap(Context context, Bitmap backBitmap, Bitmap frontBitmap) {

        if (backBitmap == null || backBitmap.isRecycled()
                || frontBitmap == null || frontBitmap.isRecycled()) {
            TuDouLogUtils.e(TAG, "backBitmap=" + backBitmap + ";frontBitmap=" + frontBitmap);
            return null;
        }
        Bitmap bitmap = backBitmap.copy(Bitmap.Config.RGB_565, true);
        int size = backBitmap.getWidth() / 3;
//        if (size > frontBitmap.getWidth())
//            size = frontBitmap.getWidth();
        Canvas canvas = new Canvas(bitmap);
        int top = backBitmap.getHeight() - ScreenUtils.dp2px(context, 180) - size - 2;
        int left = (backBitmap.getWidth() - size) / 2 - 2;
        Rect baseRect = new Rect(left, top, left + size, top + size);
        canvas.drawBitmap(frontBitmap, null, baseRect, null);
        return bitmap;
    }


    /**
     * 把两个位图覆盖合成为一个位图，以底层位图的长宽为基准
     *
     * @return
     */
    public static Bitmap createPotatoesBitMap(Context context) {
        Bitmap mPotatoesBitmap = null;
        Bitmap potatoesBitMapBg = null;//海报背景图片
        Bitmap qrCodeBitmap = null;//二维码图片
        String mesg = "";
        int mar = ScreenUtils.dp2px(context, 40);
        int w = ScreenUtils.getScreenWidth(context) - mar * 2;
        try {
            mesg = String.format(Share.getShareInviteUrl(), MyApplication.getLoginUser().getInvistCode(), URLEncoder.encode(MyApplication.getLoginUser().getNickName(), "utf-8"), MyApplication.getLoginUser().getUnionid());
            qrCodeBitmap = CodeUtils.createImage(mesg, w / 2, w / 2, null);
            potatoesBitMapBg = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_potatoes_bg);
            mPotatoesBitmap = BitMapUtils.mergeBitmap(context, potatoesBitMapBg, qrCodeBitmap);
        } catch (Exception e) {
            TuDouLogUtils.e(TAG, "生成海报出错 e:" + e.getMessage());
        } finally {
            try {
                if (qrCodeBitmap != null && !qrCodeBitmap.isRecycled())
                    qrCodeBitmap.recycle();
                if (potatoesBitMapBg != null && !potatoesBitMapBg.isRecycled())
                    potatoesBitMapBg.recycle();
            } catch (Exception e) {
                TuDouLogUtils.e(TAG, "海报分享图片报错");
            }
        }
        return mPotatoesBitmap;
    }

    /**
     * 保存图片到相册
     *
     * @param context
     * @param bmp
     * @param imageName
     */
    public static void saveIamge(Context context, Bitmap bmp, String imageName, SaveImageCallBack callBack) {
        if (bmp == null || TextUtils.isEmpty(imageName)) return;
        try {
            if (imageName.contains("?")) {
                imageName = imageName.substring(0, imageName.indexOf("?")) + ".jpg";
            } else {
                imageName = imageName + ".jpg";
            }
        } catch (Exception e) {
            Log.e("saveImageToGallery", "TuDou_saveImageToGallery error:" + e.getMessage());
            imageName = System.currentTimeMillis() + ".jpg";
        }
        //注意小米手机必须这样获得public绝对路径
        File dcimDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsoluteFile();
        String fileName = "豆播" + File.separator + "豆播相册";
        File appDir = new File(dcimDir, fileName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        File currentFile = new File(appDir, imageName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(currentFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (Exception e) {
            if (callBack != null)
                callBack.saveFail();
            TuDouLogUtils.e(TAG, e.getMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                TuDouLogUtils.e(TAG, e.getMessage());
            }
        }
        if (callBack != null)
            callBack.saveSucess();
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(currentFile.getPath()))));
    }

    /**
     * 保存图片回调
     */
    public interface SaveImageCallBack {
        /**
         * 保存成功
         */
        void saveSucess();

        /**
         * 保存失败
         */
        void saveFail();
    }

}
