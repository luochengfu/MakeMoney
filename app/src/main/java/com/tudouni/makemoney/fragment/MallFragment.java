package com.tudouni.makemoney.fragment;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.databinding.FragmentMallBinding;

/**
 * 商城页
 * Jaron.Wu
 *     2018/04/24
 */

public class MallFragment extends BaseFragment {
    @Override
    protected int getContentView() {
        return 0;
    }

    @Override
    protected View getLayoutView() {
       FragmentMallBinding mallBinding =  DataBindingUtil.inflate(getActivity().getLayoutInflater(),R.layout.fragment_mall,null,false);
       return mallBinding.getRoot();
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }
}
