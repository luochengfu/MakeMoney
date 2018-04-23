package com.tudouni.makemoney.viewModel;

import com.tudouni.makemoney.BuildConfig;
import com.tudouni.makemoney.fragment.category.GoodCategoryFragment;
import com.tudouni.makemoney.model.Category;
import com.tudouni.makemoney.model.CategoryNameItem;
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
                getCategoryNames(callback);
            }

            @Override
            public void OnFail(int code, String err) {
                TDLog.e(code,err);
                if (BuildConfig.DEBUG) {//Debug 服务器无数据时mock数据
                    List<CategoryNameItem> data = new ArrayList<>();
                    data.add(new CategoryNameItem("热门推荐"));
                    data.add(new CategoryNameItem("美妆个护"));
                    data.add(new CategoryNameItem("家居百货"));
                    data.add(new CategoryNameItem("母婴用品"));
                    data.add(new CategoryNameItem("手机数码"));
                    data.add(new CategoryNameItem("箱包鞋靴"));
                    data.add(new CategoryNameItem("文体车品"));
                    data.add(new CategoryNameItem("潮流男装"));
                    data.add(new CategoryNameItem("潮流女装"));
                    data.add(new CategoryNameItem("美食零食"));
                    data.add(new CategoryNameItem("潮流饰品"));
                    data.add(new CategoryNameItem("办公用品"));
                    data.add(new CategoryNameItem("图书音像"));
                    data.add(new CategoryNameItem("保健用品"));
                    if (callback != null) {
                        callback.onSuccess(data);
                    }
                }
            }
        });

    }


    private List<CategoryNameItem> getCategoryNames(GoodCategoryFragment.ResultCallback callback){
        List<CategoryNameItem> categories = new ArrayList<>();
        for (Category category: data) {
            CategoryNameItem item = new CategoryNameItem();
            item.setName(category.getName());
            categories.add(item);
        }
        TDLog.e(categories);
        if (callback != null) {
            callback.onSuccess(categories);
        }
        return categories;
    }
}
