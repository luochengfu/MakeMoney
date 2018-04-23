package com.tudouni.makemoney.fragment;

import android.databinding.DataBindingUtil;
import android.view.View;
import com.tudouni.makemoney.databinding.FragmentGoodCategoryBinding;
import com.tudouni.makemoney.R;

/**
 * Created by Administrator on 2018/4/20 0020.
 */

public class GoodCategoryFragment extends BaseFragment {
    @Override
    protected int getContentView() {
        return 0;
    }

    @Override
    protected View getLayoutView() {
        FragmentGoodCategoryBinding  categoryBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(),R.layout.fragment_good_category,null,false);
        return categoryBinding.getRoot();
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }
}
