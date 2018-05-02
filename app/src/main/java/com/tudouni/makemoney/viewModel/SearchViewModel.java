package com.tudouni.makemoney.viewModel;

import com.tudouni.makemoney.model.SearchHistory;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.DeviceInfor;
import com.tudouni.makemoney.utils.TDLog;

import java.util.List;

/**
 * Jaron.Wu
 * 2018/5/2
 */
public class SearchViewModel extends LoadingViewModel {
    public void loadSearchHistory(String unionId, VMResultCallback<List<SearchHistory>> callback){
        CommonScene.loadSearchHistory(unionId, new BaseObserver<List<SearchHistory>>() {
            @Override
            public void OnSuccess(List<SearchHistory> historyList) {
                TDLog.e(historyList);
                if (callback != null) {
                    callback.onSuccess(historyList);
                }
            }

            @Override
            public void OnFail(int code, String err) {
                TDLog.e(code,err);
                if (callback != null) {
                    callback.onFailure();
                }
            }
        });
    }


    public void saveSearchHistoryToService(String source,String searchKey){
        CommonScene.saveSearchHistoryToService(MyApplication.getLoginUser().getUnionid(),source, DeviceInfor.getDeviceModel(), searchKey, new BaseObserver<Object>() {
            @Override
            public void OnSuccess(Object o) {
                TDLog.e(o);
            }

            @Override
            public void OnFail(int code, String err) {
                TDLog.e(code,err);
            }
        });
    }
}
