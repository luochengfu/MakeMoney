package com.tudouni.makemoney.viewModel;

import android.app.Activity;
import android.databinding.ObservableField;

import com.tudouni.makemoney.model.EarningsRank;
import com.tudouni.makemoney.model.SavingsProfile;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.TDLog;

import java.util.List;

public class SavingsViewModel extends LoadingViewModel {

    public ObservableField<SavingsProfile> savings = new ObservableField<>();
    public ObservableField<String> mineRankInfo = new ObservableField<>();

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

    public void loadSavingsRank(VMResultCallback<List<EarningsRank>> callback){
        CommonScene.loadSavingsRank(100, new BaseObserver<List<EarningsRank>>() {
            @Override
            public void OnSuccess(List<EarningsRank> earningsRanks) {
                if (callback != null) {
                    callback.onSuccess(earningsRanks);

                    if (earningsRanks != null) {
                        int mineRank = 0;
                        for (int i = 0; i < earningsRanks.size(); i++) {
                            String uid = earningsRanks.get(i).getUid();
                            if (uid != null && uid.equals(MyApplication.getLoginUser().getUid())) {
                                mineRank = i + 1;
                            }
                        }
                        if (mineRank <= 100 && mineRank > 0) {
                            mineRankInfo.set("我的排名：" + mineRank);
                        }else{
                            mineRankInfo.set("您的排名未上榜，仍需努力");
                        }
                    }
                }
            }
        });
    }

}
