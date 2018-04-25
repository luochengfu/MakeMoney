package com.tudouni.makemoney.utils.upload;

/**
 * Created by hjw on 17/3/22.
 */


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.alibaba.sdk.android.oss.model.ResumableUploadRequest;
import com.alibaba.sdk.android.oss.model.ResumableUploadResult;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.Constants;
import com.tudouni.makemoney.widget.callBack.ApiCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 阿里云上传
 */
public class UploadUtils {


    /**
     * 文件上传阿里云不带进度回调
     *
     * @param data
     * @param filePath
     * @return
     */
    public static String uploadFileToAliYun(UploadInfo data, String filePath) {
        try {
            String endpoint = data.getEndPoint();
            OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(data
                    .getAccessid(), data.getSignature(), data.getPolicy());
            OSS oss = new OSSClient(MyApplication.getContext(), endpoint, credentialProvider);
            PutObjectRequest put = new PutObjectRequest(data.getBucket() /*+ "-trans"*/, data.getName
                    (), filePath);
            PutObjectResult putResult = oss.putObject(put);
            return data.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 文件上传阿里云带进度回调
     *
     * @param data
     * @param filePath
     * @param progressCallback
     * @return
     */
    public static String uploadFileToAliYun(UploadInfo data, String filePath, OSSProgressCallback<PutObjectRequest> progressCallback) {
        try {
            String endpoint = data.getEndPoint();
            OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(data
                    .getAccessid(), data.getSignature(), data.getPolicy());
            OSS oss = new OSSClient(MyApplication.getContext(), endpoint, credentialProvider);
            PutObjectRequest put = new PutObjectRequest(data.getBucket() /*+ "-trans"*/, data.getName
                    (), filePath);
            put.setProgressCallback(progressCallback);
            PutObjectResult putResult = oss.putObject(put);
            return data.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 不带进度的文件上传
     *
     * @param filepath
     * @param apiCallback
     */
    public static void uploadFileForType(String uptype, final String filepath, final BaseObserver<String> apiCallback) {

        CommonScene.uploadInfo(uptype, new BaseObserver<UploadInfo>() {
            @Override
            public void OnSuccess(UploadInfo data) {
                (new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        return UploadUtils.uploadFileToAliYun(data, filepath);
                    }

                    @Override
                    protected void onPostExecute(String path) {
                        super.onPostExecute(path);
                        if (apiCallback != null) {
                            if (TextUtils.isEmpty(path)) {
                                apiCallback.OnFail(-1, "upload aliyun failure network error");
                            } else {
                                apiCallback.OnSuccess(path);
                            }

                        }
                    }
                }).execute();
            }

            @Override
            public void OnFail(int code, String err) {
                apiCallback.OnFail(code, err);
            }
        });

    }

    /**
     * 上传图片 用户相关
     *
     * @param filepath
     * @param apiCallback
     */
    public static void uploadImageForUser(final String filepath, final BaseObserver<String> apiCallback) {
        uploadFileForType(Constants.UPTYPE_IMGHEADER, filepath, apiCallback);

    }

}
