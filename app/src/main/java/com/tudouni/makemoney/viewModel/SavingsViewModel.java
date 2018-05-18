package com.tudouni.makemoney.viewModel;

import android.app.Activity;
import android.databinding.ObservableField;

import com.tudouni.makemoney.model.SavingsProfile;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.TDLog;

public class SavingsViewModel extends LoadingViewModel {

    public ObservableField<SavingsProfile> savings = new ObservableField<>();

    public void loadSavings(){
        CommonScene.loadSavingsProfile(MyApplication.getLoginUser().getUid(), new BaseObserver<SavingsProfile>() {
            @Override
            public void OnSuccess(SavingsProfile savingsProfile) {
                TDLog.e(savingsProfile);
                savings.set(savingsProfile);
            }
        });
    }

    public void finish(){
        Activity currActivity = MyApplication.sCurrActivity;
        if (currActivity != null) {
            currActivity.finish();
        }
    }
}
