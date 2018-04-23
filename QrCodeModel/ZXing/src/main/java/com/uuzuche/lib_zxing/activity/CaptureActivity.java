package com.uuzuche.lib_zxing.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.FunctionOptions;
import com.luck.picture.lib.model.PictureConfig;
import com.uuzuche.lib_zxing.R;
import com.uuzuche.lib_zxing.utils.BarTextColorUtils;
import com.uuzuche.lib_zxing.utils.LogUtil;
import com.yalantis.ucrop.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Initial the camera
 * <p>
 * 默认的二维码扫描Activity
 */
public class CaptureActivity extends AppCompatActivity {

    private int selectMode = FunctionConfig.MODE_SINGLE;
    private int maxSelectNum = 1;// 图片最大可选数量
    private ImageButton minus, plus;
    private EditText select_num;
    private EditText et_w, et_h, et_compress_width, et_compress_height;
    private LinearLayout ll_luban_wh;
    private boolean isShow = true;
    private int selectType = FunctionConfig.TYPE_IMAGE;
    private int copyMode = FunctionConfig.CROP_MODEL_1_1;
    private boolean enablePreview = true;
    private boolean isPreviewVideo = true;
    private boolean enableCrop = false;
    private boolean theme = false;
    private boolean selectImageType = false;
    private int cropW = 640;
    private int cropH = 640;
    private int compressW = 0;
    private int compressH = 0;
    private boolean isCompress = false;
    private boolean isCheckNumMode = false;
    private int compressFlag = 1;// 1 系统自带压缩 2 luban压缩
    private List<LocalMedia> selectMedia = new ArrayList<>();
    private Context mContext;
    private LinearLayout loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        initWindow();
        super.onCreate(savedInstanceState);
        BarTextColorUtils.statusBarLightMode(this);

        setContentView(R.layout.camera);
        CaptureFragment captureFragment = new CaptureFragment();
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_zxing_container, captureFragment).commit();
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaptureActivity.this.finish();
            }
        });
        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialogOpenAvatar();
            }
        });
        loading = (LinearLayout) findViewById(R.id.loading);
    }

    private void initDialogOpenAvatar() {
        checkPermission();
        FunctionOptions options = new FunctionOptions.Builder()
                .setType(selectType)
                .setCropMode(copyMode)
                .setCompress(true)
                .setEnablePixelCompress(true)
                .setEnableQualityCompress(true)
                .setMaxSelectNum(1)
                .setSelectMode(selectMode)
                .setShowCamera(isShow)
                .setEnablePreview(enablePreview)
                .setEnableCrop(enableCrop)
                .setCropW(cropW)
                .setCropH(cropH)
                .setCheckNumMode(isCheckNumMode)
                .setCompressQuality(100)
                .setImageSpanCount(3)
                .setSelectMedia(selectMedia)
                .setCompressFlag(1)
                .setCompressW(compressW)
                .setCompressH(compressH)
                .create();


        // 先初始化参数配置，在启动相册
        PictureConfig.getInstance().init(options);
        PictureConfig.getInstance().openPhoto(this, resultCallback2);
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            onsuccess(result, CodeUtils.RESULT_SUCCESS);
        }

        @Override
        public void onAnalyzeFailed() {
            onfailed(CodeUtils.RESULT_FAILED, "");
        }
    };

    private void onfailed(int resultFailed, String value) {
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(CodeUtils.RESULT_TYPE, resultFailed);
        bundle.putString(CodeUtils.RESULT_STRING, value);
        resultIntent.putExtras(bundle);
        CaptureActivity.this.setResult(RESULT_OK, resultIntent);
        CaptureActivity.this.finish();
    }

    private void onsuccess(String result, int resultSuccess) {
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(CodeUtils.RESULT_TYPE, resultSuccess);
        bundle.putString(CodeUtils.RESULT_STRING, result);
        resultIntent.putExtras(bundle);
        CaptureActivity.this.setResult(RESULT_OK, resultIntent);
        CaptureActivity.this.finish();
    }


    @TargetApi(19)
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int write = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int read = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (write != PackageManager.PERMISSION_GRANTED || read != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 300);
            } else {
                String name = "CrashDirectory";
                File file1 = new File(Environment.getExternalStorageDirectory(), name);
                if (file1.mkdirs()) {
                    LogUtil.i("wytings", "permission -------------> " + file1.getAbsolutePath());
                } else {
                    LogUtil.i("wytings", "permission -------------fail to make file ");
                }
            }
        } else {
            LogUtil.i("wytings", "------------- Build.VERSION.SDK_INT < 23 ------------");
        }
    }


    private PictureConfig.OnSelectResultCallback resultCallback2 = new PictureConfig.OnSelectResultCallback() {


        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            selectMedia = resultList;
            if (selectMedia != null) {
                loading.setVisibility(View.VISIBLE);
                LogUtil.i("callBack_result", selectMedia.size() + "");
                String photoPath = selectMedia.get(0).getCompressPath();
                LogUtil.i("malegebi", photoPath);
                CodeUtils.analyzeBitmap(photoPath, new CodeUtils.AnalyzeCallback() {
                    @Override
                    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                        loading.setVisibility(View.GONE);
                        LogUtil.e(result, result);
                        onsuccess(result, CodeUtils.RESULT_SUCCESS);
                    }

                    @Override
                    public void onAnalyzeFailed() {
                        Toast.makeText(CaptureActivity.this, "未发现土豆泥二维码", Toast.LENGTH_LONG).show();
                        onfailed(CodeUtils.RESULT_FAILED, "");
                    }
                });
            }
        }

        @Override
        public void onSelectSuccess(LocalMedia media) {
            if (media != null) {
                CaptureActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loading.setVisibility(View.VISIBLE);
                    }
                });

                String photoPath = media.getCompressPath();
                CodeUtils.analyzeBitmap(photoPath, new CodeUtils.AnalyzeCallback() {
                    @Override
                    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                        CaptureActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loading.setVisibility(View.GONE);
                            }
                        });
                        if (result.startsWith("tudouni://tudouni/qrgroup?gid=") || result.startsWith("tudouni://tudouni/UserInfoInChat?uid=")) {

                            onsuccess(result, CodeUtils.RESULT_SUCCESS);
                        } else {

                            Toast.makeText(CaptureActivity.this, "未发现土豆泥二维码", Toast.LENGTH_LONG).show();
                        }
                        LogUtil.e("@@@@", result);
                    }

                    @Override
                    public void onAnalyzeFailed() {
                        Toast.makeText(CaptureActivity.this, "未发现土豆泥二维码", Toast.LENGTH_LONG).show();
                        onfailed(CodeUtils.RESULT_FAILED, "");
                    }
                });
            }
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    };
}