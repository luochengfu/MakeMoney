package com.tudouni.makemoney.network.rx;

import com.tudouni.makemoney.model.MallCommonModel;
import com.tudouni.makemoney.network.exception.NetWorkExceptionConvert;
import com.tudouni.makemoney.utils.TDLog;
import com.tudouni.makemoney.utils.ToastUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Jaron.Wu
 * 2018/4/25
 */

public abstract class BaseMallObserver<T> implements Observer<MallCommonModel<T>> {
    private final int SUCCESS_CODE = 0; //成功

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(MallCommonModel<T> value) {
        if (value.isSuccess()) {
            OnSuccess(value.getList());
        } else {
            OnFail(value.getErrorCode(), value.getErrorHint());
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

    public abstract void OnSuccess(List<T> t);

}

