package com.tudouni.makemoney.fragment.category;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.tudouni.makemoney.databinding.FragmentGoodCategoryBinding;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.fragment.BaseFragment;
import com.tudouni.makemoney.model.CategoryNameItem;
import com.tudouni.makemoney.viewModel.GoodCategoryViewModel;

import java.util.List;

/**
 *  品类
 *  Jaron.Wu
 *     2018/4/23
 */

public class GoodCategoryFragment extends BaseFragment {

    private FragmentGoodCategoryBinding mCategoryBinding;
    private CategoryNameAdapter mNameAdapter;

    @Override
    protected int getContentView() {
        return 0;
    }

    @Override
    protected View getLayoutView() {
        mCategoryBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.fragment_good_category,null,false);
        return mCategoryBinding.getRoot();
    }

    @Override
    protected void initView(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mCategoryBinding.rvCategories.setLayoutManager(layoutManager);
        mNameAdapter = new CategoryNameAdapter(getActivity().getLayoutInflater());
        mCategoryBinding.rvCategories.setAdapter(mNameAdapter);
    }

    @Override
    protected void initData() {
        GoodCategoryViewModel categoryViewModel = new GoodCategoryViewModel();
        categoryViewModel.getGoodList(new ResultCallback(){

            @Override
            public void onSuccess(List<CategoryNameItem> categoryNameList) {
                mNameAdapter.replaceData(categoryNameList);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    public interface ResultCallback{
        void onSuccess(List<CategoryNameItem> categoryNameList);

        void onFailure();
    }
}
