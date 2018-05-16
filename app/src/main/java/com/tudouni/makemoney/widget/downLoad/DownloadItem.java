package com.tudouni.makemoney.widget.downLoad;

import android.util.Log;

import com.tudouni.makemoney.utils.base.FileUtils;

public class DownloadItem {
    private String key;// 文件 命名方式：'video/jj/20170628/ac7c32a132b947b3bbdf5306d085e076_width_height.mp4'
    private String url;// 文件本地路径---
    private String localPath;// 文件本地路径
    private String fileName;//文件名字
    private int finished;//下载以已完成进度
    private int fileSize;// 文件大小，单位MB
    private int fileState = -1;// 状态，0：进行中；1：成功；2：失败
    private boolean isStop = false;//是否暂停下载
    private double uploadProgress;// 上传进度

    public DownloadItem(String url) {
        this.url = url;
        this.key = url;
        this.fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
        this.localPath = FileUtils.getDownloadTemporaryPath(fileName);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
        //修改进度百分比
        try {
            uploadProgress = finished / (double) fileSize;
        } catch (Exception e) {
            Log.e("DownloadItem", "修改进度（uploadProgress）报错：" + e.getMessage());
        }
    }


    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }


    public double getUploadProgress() {
        return uploadProgress;
    }

    public void setUploadProgress(double uploadProgress) {
        this.uploadProgress = uploadProgress;
    }

    public int getFileState() {
        return fileState;
    }

    public void setFileState(int fileState) {
        this.fileState = fileState;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DownloadItem other = (DownloadItem) obj;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        return true;
    }

}
