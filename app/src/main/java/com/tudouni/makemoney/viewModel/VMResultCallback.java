package com.tudouni.makemoney.viewModel;


/**
 * Jaron.Wu
 *      2018/04/23
 * 用于页面与ViewModel之间回传数据
 * @param <T> 回传的数据类型
 */
public interface VMResultCallback<T> {

    void onSuccess(T data);

    void onFailure();
}
