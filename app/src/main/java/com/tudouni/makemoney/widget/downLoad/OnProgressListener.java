package com.tudouni.makemoney.widget.downLoad;

/*
 * 描述:     TODO 进度更新
 */
public interface OnProgressListener {

    void updateProgress(DownloadItem fileInfo, int max, int progress);

    void updateFiled(DownloadItem fileInfo);

    void updateStop(DownloadItem fileInfo);

    void updateSucess(DownloadItem fileInfo);
}
