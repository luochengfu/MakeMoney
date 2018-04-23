package com.tudouni.makemoney.viewModel;

public interface VMResultCallback<T> {
    void onSuccess(T data);

    void onFailure();
}
