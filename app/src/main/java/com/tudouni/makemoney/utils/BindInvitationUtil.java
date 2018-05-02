package com.tudouni.makemoney.utils;

import android.os.Handler;

import com.tudouni.makemoney.activity.BindInvitationCodeActivity;
import com.tudouni.makemoney.activity.LoginActivity;
import com.tudouni.makemoney.activity.PwdActivity;
import com.tudouni.makemoney.activity.SearchGoodActivity;
import com.tudouni.makemoney.activity.SplashActivity;
import com.tudouni.makemoney.activity.TelLoginActivity;
import com.tudouni.makemoney.model.BindUserBean;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.view.BindInvitationCodeDialog;

import org.simple.eventbus.EventBus;

/**
 * Created by Administrator on 2018/5/2 0002.
 */

public class BindInvitationUtil {
    public static void getBindInvitationUseInfo(String mUserCode) {
        CommonScene.getBindUserInfo(mUserCode, new BaseObserver<BindUserBean>() {
            @Override
            public void OnSuccess(BindUserBean bindUserBean) {
                EventBus.getDefault().post(bindUserBean, "bind_code_action");
            }

            @Override
            public void OnFail(int code, String err) {}
        });
    }
}
