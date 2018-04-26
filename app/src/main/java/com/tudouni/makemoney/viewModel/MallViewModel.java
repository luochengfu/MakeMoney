package com.tudouni.makemoney.viewModel;

import com.tudouni.makemoney.model.MallAlbumModel;
import com.tudouni.makemoney.model.MallGoodItem;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseMallObserver;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.TDLog;

import java.util.List;

/**
 * Jaron.Wu
 * 2018/4/25
 */
public class MallViewModel extends LoadingViewModel {


    /**
     * 加载商城Banner数据
     * */
    public void loadMallBannerData(VMResultCallback<List<MallAlbumModel>> callback){
        CommonScene.getMallBanner(new BaseObserver<List<MallAlbumModel>>() {
            @Override
            public void OnSuccess(List<MallAlbumModel> t) {
                TDLog.e(t);
                if (callback != null) {
                    callback.onSuccess(t);
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

    /**
     * 加载专栏数据
     */
    public void loadAlbumData(VMResultCallback<List<MallAlbumModel>> callback){
        CommonScene.getMallAlbumData(new BaseObserver<List<MallAlbumModel>>() {
            @Override
            public void OnSuccess(List<MallAlbumModel> models) {
                TDLog.e(models);
                if (callback != null) {
                    callback.onSuccess(models);
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

    public void loadRecommendGoodData(VMResultCallback<List<MallGoodItem>> callback){
        CommonScene.getRecommendGood(new BaseObserver<List<MallGoodItem>>() {
            @Override
            public void OnSuccess(List<MallGoodItem> items) {
                TDLog.e(items);
                if (callback != null) {
                    callback.onSuccess(items);
                }
            }

            @Override
            public void OnFail(int code, String err) {
                super.OnFail(code, err);
                TDLog.e(code,err);
                if (callback != null) {
                    callback.onFailure();
                }
            }
        });
    }
}
