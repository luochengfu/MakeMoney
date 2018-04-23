package com.tudouni.makemoney.network.rx;


import android.util.Log;

import com.tudouni.makemoney.network.Result;
import com.tudouni.makemoney.network.exception.NetWorkExceptionConvert;
import com.tudouni.makemoney.utils.TDLog;
import com.tudouni.makemoney.utils.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * 基本观察者
 * Created by YSL on 2017/4/17.
 */

public abstract class BaseObserver<T> implements Observer<Result<T>> {
    private final int SUCCESS_CODE = 0; //成功

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Result<T> value) {
        if (value.getStatus() == SUCCESS_CODE) {
            OnSuccess(value.getData());
        } else {
            OnFail(value.getStatus(), value.getMessage());
        }

    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable e) {
        /*if (BuildConfig.DEBUG) {
            e.printStackTrace();
        }*/
        TDLog.e("TuDouNi_" + e.getMessage());
        OnFail(5001, NetWorkExceptionConvert.exceptionConvert(e).getMsg());
    }


    public void OnFail(int code, String err) {
        boolean canToast = true;
        if (code == 10) {
            canToast = false;
        } else if (code == 1048) {//绑定手机号
            canToast = false;
        } else if (code == 5400) {//直播已结束
            canToast = false;
        } else if (code == 1050) {//用户未绑定
            canToast = false;
        }
        if (!canToast) return;
        ToastUtil.show(err);
    }

    public abstract void OnSuccess(T t);

}
