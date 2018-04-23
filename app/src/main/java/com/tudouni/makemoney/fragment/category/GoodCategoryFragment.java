package com.tudouni.makemoney.fragment.category;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.databinding.FragmentGoodCategoryBinding;
import com.tudouni.makemoney.fragment.BaseFragment;
import com.tudouni.makemoney.model.Category;
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
    private GoodCategoryViewModel mCategoryViewModel;

    @Override
    protected int getContentView() {
        return 0;
    }

    @Override
    protected View getLayoutView() {
        mCategoryBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.fragment_good_category,null,false);
        mCategoryViewModel = new GoodCategoryViewModel();
        return mCategoryBinding.getRoot();
    }

    @Override
    protected void initView(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mCategoryBinding.rvCategories.setLayoutManager(layoutManager);
        mNameAdapter = new CategoryNameAdapter(getActivity().getLayoutInflater());
        mCategoryBinding.rvCategories.setAdapter(mNameAdapter);
        mNameAdapter.setListener((position, itemData) -> {
            //一级品类Item被点击

        });
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        if (mCategoryViewModel != null) {
            mCategoryViewModel.getGoodList(new ResultCallback(){
                @Override
                public void onSuccess(List<Category> categoryNameList) {
                    mNameAdapter.replaceData(categoryNameList);
                }

                @Override
                public void onFailure() {

                }
            });
        }
    }

    public interface ResultCallback{
        void onSuccess(List<Category> categoryNameList);

        void onFailure();
    }

    @Override
    protected void onFragResume() {
        getData();
        super.onFragResume();
    }
}
