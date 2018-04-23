package com.tudouni.makemoney.viewModel;

import com.tudouni.makemoney.model.Category;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.TDLog;

import java.util.List;

public class GoodCategoryViewModel extends LoadingViewModel {

    public void getGoodList(){
        CommonScene.getGoodList(new BaseObserver<List<Category>>() {
            @Override
            public void OnSuccess(List<Category> category) {
                TDLog.e(category);
            }

            @Override
            public void OnFail(int code, String err) {
                TDLog.e(code,err);
            }
        });

    }
}
