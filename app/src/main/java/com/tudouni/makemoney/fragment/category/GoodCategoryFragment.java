package com.tudouni.makemoney.fragment.category;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.databinding.FragmentGoodCategoryBinding;
import com.tudouni.makemoney.databinding.ItemCategoryHeaderImageBinding;
import com.tudouni.makemoney.fragment.BaseFragment;
import com.tudouni.makemoney.model.Category;
import com.tudouni.makemoney.utils.TDLog;
import com.tudouni.makemoney.utils.glideUtil.GlideUtil;
import com.tudouni.makemoney.viewModel.GoodCategoryViewModel;

import java.util.List;

/**
 *  品类
 *  Jaron.Wu
 *     2018/4/23
 */

public class GoodCategoryFragment extends BaseFragment {

    private FragmentGoodCategoryBinding mCategoryBinding;
    /**
     * 一级品类Adapter
     */
    private CategoryNameAdapter mNameAdapter;
    private GoodCategoryViewModel mCategoryViewModel;
    private GoodListAdapter mGoodListAdapter;
    private ItemCategoryHeaderImageBinding mHeaderImageBinding;

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

        initFirstClassCategoryView();
        initGoodListRecyclerView();
    }

    /**
     * 初始化右边商品列表view
     * */
    private void initGoodListRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mCategoryBinding.rvGood.setLayoutManager(layoutManager);
        mGoodListAdapter = new GoodListAdapter(getActivity().getLayoutInflater());
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(mGoodListAdapter);
        mHeaderImageBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.item_category_header_image,null,false);
        lRecyclerViewAdapter.addHeaderView(mHeaderImageBinding.getRoot());
        mCategoryBinding.rvGood.setAdapter(lRecyclerViewAdapter);
    }

    /**
     * 初始化一级目录列表view
     * */
    private void initFirstClassCategoryView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mCategoryBinding.rvCategories.setLayoutManager(layoutManager);
        mNameAdapter = new CategoryNameAdapter(getActivity().getLayoutInflater());
        mCategoryBinding.rvCategories.setAdapter(mNameAdapter);
        mNameAdapter.setOnItemClickListener((position, itemData) -> {
            //一级品类Item被点击
            mGoodListAdapter.replaceData(itemData.getCategorys());
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
