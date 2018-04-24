package com.tudouni.makemoney.viewModel;

import com.tudouni.makemoney.model.Category;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.TDLog;

import java.util.List;

/**
 * 品类ViewModel
 * Jaron.Wu
 *      2018/04/23
 */
public class GoodCategoryViewModel extends LoadingViewModel {


    public void getGoodList(final VMResultCallback<List<Category>> callback){
        CommonScene.getGoodList(new BaseObserver<List<Category>>() {
            @Override
            public void OnSuccess(List<Category> category) {
                if (category != null) {
                    if (category.size() > 0) {
                        //默认选中第一个品类
                        category.get(0).setSelected(true);
                    }
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
