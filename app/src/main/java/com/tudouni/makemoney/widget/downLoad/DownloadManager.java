package com.tudouni.makemoney.widget.downLoad;


import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.tudouni.makemoney.interfaces.DownFileCallBack;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.utils.Constants;
import com.tudouni.makemoney.utils.TuDouLogUtils;
import com.tudouni.makemoney.utils.base.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;


/**
 * 下载管理 仅仅只要一个可以下载的
 */
public class DownloadManager implements OnProgressListener {
    //    private DownloadThread mThread;
    private String TAG = "DownloadManager";
    private Map<String, DownloadItem> map = new HashMap<>();//保存正在下载的任务信息
    private static DownloadManager instance;
    private int mOpenCounter;
    private static OnProgressListener listener;
    private DownLoadTask downLoadTask;
    private LinkedBlockingDeque<DownloadItem> mQueue = null;// 等待队列
    private DownFileCallBack downFileCallBack;

    private DownloadManager() {
        if (mQueue == null)
            mQueue = new LinkedBlockingDeque<DownloadItem>();
    }

    public static synchronized OnProgressListener getListener() {
        return listener;
    }

    public static void setListener(OnProgressListener listener) {
        DownloadManager.listener = listener;
    }

    public synchronized static DownloadManager getInstance() {
        if (instance == null) {
            synchronized (DownloadManager.class) {
                if (instance == null) {
                    instance = new DownloadManager();
                }
            }
        }
        FileUtils.deleteFilesInDir(FileUtils.getDownloadTemporaryPath(null));
        return instance;
    }


    /**
     * 添加下载任务
     *
     * @param info 下载文件信息
     */
    public void addTask(DownloadItem info) {
        checkMap();
        //判断数据库是否已经存在这条下载信息
        if (!map.containsKey(info.getKey()))
            map.put(info.getKey(), info);
        start(info.getKey());
    }

    /**
     * 一次添加多个任务
     * 添加下载任务
     */
    public void addTask(List<String> items) {
        checkMap();
        if (items == null || items.size() == 0) return;
        for (String item : items) {
            DownloadItem downloadItem = new DownloadItem(item);
            if (!map.containsKey(downloadItem.getKey()))
                map.put(downloadItem.getKey(), downloadItem);
            addToQueue(downloadItem);
        }
        autoDownNext();
    }

    /**
     * 开始下载任务
     */
    private void start(String key) {
        //开始任务下载
        if (downLoadTask != null && downLoadTask.isRuning()) {
            addToQueue(map.get(key));
        } else {
            map.get(key).setStop(false);
            downLoadTask = new DownLoadTask(map.get(key), this);
            downLoadTask.start();
        }
    }


    /**
     * 此方法会判断正在下载的和等待下载的不重复添加，下载完成的需要调用此方法之前自行判断
     *
     * @param item
     */
    public void addToQueue(DownloadItem item) {
        if (mQueue == null)
            mQueue = new LinkedBlockingDeque<DownloadItem>();
        if (item == null || mQueue.contains(item))
            return;
        mQueue.add(item);
        item.setFileState(Constants.STATE_WAIT);
        if (map.get(item.getKey()) != null)
            map.get(item.getKey()).setFileState(Constants.STATE_WAIT);
//        DownloadDbTool.insertOrUpdate(item);
        if (listener != null)
            listener.updateStop(item);
    }


    /**
     * 停止下载任务
     */
    public void stop(String key) {
        map.get(key).setStop(true);
    }


    /**
     * 获取当前任务状态
     */
    public int getCurrentState(String url) {
        return map.get(url).getFileState();
    }

    /**
     * 集合中是否已经存在
     *
     * @param fileInfo
     * @return
     */
    private boolean isExitItem(DownloadItem fileInfo) {
        boolean isExit = false;
        if (map == null)
            map = new HashMap<>();
        if (fileInfo != null && !TextUtils.isEmpty(fileInfo.getKey()))
            isExit = map.get(fileInfo.getKey()) != null;
        return isExit;
    }

    private void checkMap() {
        if (map == null)
            map = new HashMap<>();
    }

    /**
     * 不再使用时调用，此方法会停止线程，释放引用，下次再getInstance()会重新创建实例
     */
    public void stop() {
        instance = null;
    }

    /**
     * 释放下载队列列表
     */
    public void releaseDownLoadManager() {
        if (mQueue != null)
            mQueue.clear();
        if (map != null)
            map.clear();
        //停止线程
        if (downLoadTask != null && downLoadTask.isRuning()) {
            downLoadTask.stopDown();
            //            downLoadTask.interrupt();
        }
        downLoadTask = null;
        mQueue = null;
        map = null;
    }

    //===============下载回调======================
    @Override
    public void updateProgress(DownloadItem fileInfo, int max, int progress) {
        if (listener != null)
            listener.updateProgress(fileInfo, max, progress);
    }

    @Override
    public void updateFiled(DownloadItem fileInfo) {
        if (listener != null)
            listener.updateFiled(fileInfo);
        autoDownNext();
    }


    @Override
    public void updateStop(DownloadItem fileInfo) {
        if (listener != null)
            listener.updateStop(fileInfo);
        autoDownNext();
    }

    @Override
    public void updateSucess(DownloadItem fileInfo) {
        if (listener != null)
            listener.updateSucess(fileInfo);
//        upLoadDownloadComplete(fileInfo.getVmetaId());
        MyApplication.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(fileInfo.getLocalPath()))));
        autoDownNext();
    }

    /**
     * 自动去下载下一个
     */
    private void autoDownNext() {
        try {
            if (mQueue == null || mQueue.size() == 0) {
                if (downFileCallBack != null)
                    downFileCallBack.onFinish();
                return;
            }
            DownloadItem downloadingItem = mQueue.take();
            TuDouLogUtils.e(TAG, "autoDownNext()获取第二个下载的");
            start(downloadingItem.getKey());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void setDownFileCallBack(DownFileCallBack downFileCallBack) {
        this.downFileCallBack = downFileCallBack;
    }
}
