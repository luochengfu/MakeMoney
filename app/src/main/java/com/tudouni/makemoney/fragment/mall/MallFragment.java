package com.tudouni.makemoney.fragment.mall;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.databinding.FragmentMallBinding;
import com.tudouni.makemoney.fragment.BaseFragment;
import com.tudouni.makemoney.model.MallAlbumModel;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseMallObserver;
import com.tudouni.makemoney.utils.TDLog;

import java.util.List;

/**
 * 商城页
 * Jaron.Wu
 *     2018/04/24
 */

public class MallFragment extends BaseFragment {

    private FragmentMallBinding mMallBinding;

    @Override
    protected int getContentView() {
        return 0;
    }

    @Override
    protected View getLayoutView() {
        mMallBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.fragment_mall,null,false);
       return mMallBinding.getRoot();
    }

    @Override
    protected void initView(View view) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),5);
        mMallBinding.lrvHome.setLayoutManager(layoutManager);
    }

    @Override
    protected void initData() {
        CommonScene.getMallBanner(new BaseMallObserver<MallAlbumModel>() {
            @Override
            public void OnSuccess(List<MallAlbumModel> t) {
                TDLog.e(t);
            }

            @Override
            public void OnFail(int code, String err) {
                TDLog.e(code,err);
            }
        });
    }
}
