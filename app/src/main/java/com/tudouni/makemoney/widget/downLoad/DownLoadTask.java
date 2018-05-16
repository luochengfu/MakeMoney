package com.tudouni.makemoney.widget.downLoad;

import android.os.Handler;
import android.os.Message;

import com.tudouni.makemoney.utils.Constants;
import com.tudouni.makemoney.utils.TuDouLogUtils;
import com.tudouni.makemoney.utils.base.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 下载文件线程
 * 从服务器获取需要下载的文件大小
 */
public class DownLoadTask extends Thread {
    private DownloadItem info;
    private int finished = 0;//当前已下载完成的进度
    private boolean isRuning = false;
    private OnProgressListener onProgressListener;

    public DownLoadTask(DownloadItem info, OnProgressListener onProgressListener) {
        this.info = info;
        this.onProgressListener = onProgressListener;
        info.setFileState(Constants.STATE_ING);
    }

    @Override
    public void run() {
        //提示已经开始下载了
        Message msg1 = new Message();
        msg1.what = 0x123;
        msg1.obj = info;
        msg1.arg1 = info.getFileSize();
        msg1.arg2 = info.getFinished();
        handler.sendMessage(msg1);

        getLength();
        HttpURLConnection connection = null;
        RandomAccessFile rwd = null;
        try {
            URL url = new URL(info.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            //从上次下载完成的地方下载
            int start = info.getFinished();
            //设置下载位置(从服务器上取要下载文件的某一段)
            connection.setRequestProperty("Range", "bytes=" + start + "-" + info.getFileSize());//设置下载范围
            //设置文件写入位置
            File file = new File(FileUtils.getDownloadTemporaryPath(null), info.getFileName());
            rwd = new RandomAccessFile(file, "rwd");
            //从文件的某一位置开始写入
            rwd.seek(start);
            finished += info.getFinished();
            TuDouLogUtils.i("===========>", "0——数据来了start=" + start + ";总长度=" + info.getFileSize());
            if (connection.getResponseCode() == 206 || connection.getResponseCode() == 200) {//文件部分下载，返回码为206
                isRuning = true;
                TuDouLogUtils.i("===========>", "1——可以存储");
                InputStream is = connection.getInputStream();
                byte[] buffer = new byte[1024 * 4];
                int len;
                info.setFileState(Constants.STATE_ING);
                //保存此次下载的进度
//                DownloadDbTool.insertOrUpdate(info);
                while ((len = is.read(buffer)) != -1) {
                    TuDouLogUtils.i("===========>", "2——进行存储");
                    //写入文件
                    rwd.write(buffer, 0, len);
                    finished += len;
                    info.setFinished(finished);
                    //更新界面显示
                    Message msg = new Message();
                    msg.what = 0x123;
                    msg.obj = info;
                    msg.arg1 = info.getFileSize();
                    msg.arg2 = info.getFinished();
                    handler.sendMessage(msg);
                    //停止下载
                    if (info.isStop()) {
                        TuDouLogUtils.i("===========>", "3——主动停止了下载");
                        info.setFileState(Constants.STATE_STOP);
                        //保存此次下载的进度
//                        DownloadDbTool.insertOrUpdate(info);
                        isRuning = false;
                        handler.sendEmptyMessage(0x124);
                        return;
                    }
                }
                //下载完成
                info.setFileState(Constants.STATE_SUCESS);
//                DownloadDbTool.insertOrUpdate(info);
                isRuning = false;
                handler.sendEmptyMessage(0x126);
            } else {
                TuDouLogUtils.i("===========>", "connection.getResponseCode()=" + connection.getResponseCode());
                info.setFileState(Constants.STATE_REPORT_FILED);
                //保存此次下载的进度
//                DownloadDbTool.insertOrUpdate(info);
                isRuning = false;
                handler.sendEmptyMessage(0x125);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //停止下载
            TuDouLogUtils.i("reeejj_DownLoadTask", "下载异常");
            info.setFileState(Constants.STATE_STOP);
            handler.sendEmptyMessage(0x124);
//            //保存此次下载的进度
//            DownloadDbTool.insertOrUpdate(info);
        } finally {
            isRuning = false;
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (rwd != null) {
                    rwd.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 首先开启一个线程去获取要下载文件的大小（长度）
     */
    private void getLength() {
        HttpURLConnection connection = null;
        try {
            //连接网络
            URL url = new URL(info.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            int length = -1;
            if (connection.getResponseCode() == 200) {//网络连接成功
                //获得文件长度
                length = connection.getContentLength();
                TuDouLogUtils.i("===========>", "获取长度--length=" + length);
            } else {
                TuDouLogUtils.i("===========>", "获取长度--链接失败！code=" + connection.getResponseCode());
            }
            if (length <= 0) {
                TuDouLogUtils.i("===========>", "获取长度--连接失败");
                //连接失败
                return;
            }
            //创建文件保存路径
            File dir = new File(FileUtils.getDownloadTemporaryPath(null));
            if (!dir.exists()) {
                dir.mkdirs();
            }
            info.setFileSize(length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放资源
            try {
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新进度
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x123:
                    if (onProgressListener != null) {
                        onProgressListener.updateProgress((DownloadItem) msg.obj, msg.arg1, msg.arg2);
                    }
                    break;
                case 0x124:
                    if (onProgressListener != null) {
                        onProgressListener.updateStop(info);
                    }
                    break;
                case 0x125:
                    if (onProgressListener != null) {
                        onProgressListener.updateFiled(info);
                    }
                    break;
                case 0x126:
                    if (onProgressListener != null) {
                        onProgressListener.updateSucess(info);
                    }
                    break;
            }
        }
    };

    public boolean isRuning() {
        return isRuning;
    }

    /**
     * 停止下载任务
     */
    public void stopDown() {
        if (info != null)
            info.setStop(true);
    }
}
