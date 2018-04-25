package com.tudouni.makemoney.widget.versionUpdate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.SharedPreferencesUtils;
import com.tudouni.makemoney.utils.base.AppUtils;
import com.tudouni.makemoney.widget.dialog.UpDialog;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.io.File;

import org.xutils.x;

/**
 * Created by zp on 2018/4/25
 */

public class UpdateAPKUtil {


    public static void uptateAPK(Context context) {
        loadUpInfo(context);
    }

    private static void loadUpInfo(final Context context) {
        CommonScene.getVersionUpdataInfo(new BaseObserver<Upinfo>() {
            @Override
            public void OnSuccess(Upinfo data) {
                if (data == null) {
                    return;
                }
                //不跟新
                if (data.getAlert().equals("0")) {
                    return;
                    //强跟新
                } else if (data.getAlert().equals("1")) {
                    showUpWindow(data, context);
                    //选跟新
                } else if (data.getAlert().equals("2")) {
                    //
                    long l = SharedPreferencesUtils.getLong(context, "time");
                    if (l == 0 || System.currentTimeMillis() - SharedPreferencesUtils.getLong(context, "time") >= 1000) {
                        showUpWindow(data, context);
                        SharedPreferencesUtils.putLong(context, "time", System.currentTimeMillis());
                    }
                } else {
                    return;
                }
            }

            @Override
            public void OnFail(int code, String err) {
                super.OnFail(code, err);
            }
        });
    }

    private static void showUpWindow(final Upinfo data, final Context context) {
//        if (data.getAlert().equals("1")) {
//            //禁用返回键
//            backFlag = 99;
//        }
        //强制更新
        final UpDialog updialog = new UpDialog((Activity) context, R.style.loading_dialog, data);
        if ("2".equals(data.getAlert())) {
            updialog.setCancelable(true);
            updialog.setCanceledOnTouchOutside(true);
        }
        updialog.show();
        updialog.setLinstener(new UpDialog.BtnClickLinstener() {
            @Override
            public void clickOk() {
                //升级开始下载
                setDownLoad(context, data.getAppUrl(), updialog);
            }

            @Override
            public void clickCancel() {
                //   if (data.getAlert().equals("-1")) {
                updialog.dismiss();
//                } else {
//
//                }
            }

            @Override
            public void clickinstall() {
                install(context, updialog);
            }
        });

    }

    /**
     * 下载包
     *
     * @param downloadurl 下载的url
     */
    @SuppressLint("SdCardPath")
    private static void setDownLoad(final Context context, String downloadurl, final UpDialog updialog) {
        // TODO Auto-generated method stub
        RequestParams params = new RequestParams(downloadurl);
        params.setAutoResume(true);//断点下载
        params.setSaveFilePath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhuanzhuanshop.apk");
        x.http().get(params, new Callback.ProgressCallback<File>() {

            @Override
            public void onCancelled(CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                updialog.setErrorProgress();
                System.out.println("提示更新失败" + arg0.toString());
            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(File arg0) {
                updialog.setclickable(true, "立即安装");
                install(context, updialog);
            }

            @Override
            public void onLoading(long arg0, long arg1, boolean arg2) {
                updialog.setMax((int) arg0);
                updialog.setProgress((int) arg1);
            }

            @Override
            public void onStarted() {
//                backFlag = 99;
                updialog.setBtnDisable();
            }

            @Override
            public void onWaiting() {


            }
        });
    }

    private static void install(Context context, UpDialog updialog) {
        if (!checkAppPackageLegitimate(context, new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "zhuanzhuanshop.apk"))) {
            updialog.installError("安装程序异常，请到各大应用市场搜索土豆泥直播或关注官方微信公众号“豆播网”进行下载安装!");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.tudouni.makemoney.FileProvider", new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "tudouni.apk"));
            intent.setDataAndType(contentUri, "application/vnd.android" +
                    ".package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "zhuanzhuanshop.apk")), "application/vnd.android" +
                    ".package-archive");
        }
        context.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 获取包名是否合法（是否是土豆泥的APP）
     *
     * @param file
     * @return
     */
    public static boolean checkAppPackageLegitimate(Context context, File file) {
        try {
            if (file == null || !file.exists()) return false;
            String archiveFilePath = file.getAbsolutePath();//安装包路径
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(archiveFilePath, PackageManager.GET_ACTIVITIES);
            if (info == null) return false;
            ApplicationInfo appInfo = info.applicationInfo;
            String packageName = appInfo.packageName;  //得到安装包名称
            return (AppUtils.getAppPackageName()).equals(packageName);
        } catch (Exception e) {
            Log.e("AppUtils", "获取apk包信息报错：" + e.getMessage());
        }
        return false;
    }
}
