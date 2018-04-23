package com.tudouni.makemoney.fragment;

import android.databinding.DataBindingUtil;
import android.view.View;
import com.tudouni.makemoney.databinding.FragmentGoodCategoryBinding;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.viewModel.GoodCategoryViewModel;

/**
 *  品类
 *  Jaron.Wu
 *     2018/4/23
 */

public class GoodCategoryFragment extends BaseFragment {

    private FragmentGoodCategoryBinding mCategoryBinding;

    @Override
    protected int getContentView() {
        return 0;
    }

    @Override
    protected View getLayoutView() {
        mCategoryBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.fragment_good_category,null,false);
        GoodCategoryViewModel categoryViewModel = new GoodCategoryViewModel();
        categoryViewModel.getGoodList();
        return mCategoryBinding.getRoot();
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }
}
