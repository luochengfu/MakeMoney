package com.tudouni.makemoney.viewModel;

import com.tudouni.makemoney.fragment.category.GoodCategoryFragment;
import com.tudouni.makemoney.model.Category;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.TDLog;

import java.util.ArrayList;
import java.util.List;

public class GoodCategoryViewModel extends LoadingViewModel {

    private List<Category> data = new ArrayList<>();

    public void getGoodList(final GoodCategoryFragment.ResultCallback callback){
        CommonScene.getGoodList(new BaseObserver<List<Category>>() {
            @Override
            public void OnSuccess(List<Category> category) {
                if (category != null) {
                    data.clear();
                    data.addAll(category);
                }
                if (callback != null) {
                    callback.onSuccess(category);
                }
            }

            @Override
            public void OnFail(int code, String err) {
                TDLog.e(code,err);
            }
        });

    }


}
