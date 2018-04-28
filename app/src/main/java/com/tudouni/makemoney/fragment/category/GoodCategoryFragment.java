package com.tudouni.makemoney.fragment.category;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.activity.H5Activity;
import com.tudouni.makemoney.databinding.FragmentGoodCategoryBinding;
import com.tudouni.makemoney.databinding.ItemCategoryHeaderImageBinding;
import com.tudouni.makemoney.fragment.BaseFragment;
import com.tudouni.makemoney.model.Category;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.NetConfig;
import com.tudouni.makemoney.viewModel.GoodCategoryViewModel;
import com.tudouni.makemoney.viewModel.VMResultCallback;

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
    private Category mCurrentRightCategory;

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
        initSearchBar();
    }

    private void initSearchBar() {
        mCategoryBinding.tvSearchBar.setOnClickListener(l -> {
            //TODO：
        });
    }

    /**
     * 初始化右边商品列表view
     * */
    private void initGoodListRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mCategoryBinding.rvGood.setLayoutManager(layoutManager);
        mCategoryBinding.rvGood.setPullRefreshEnabled(false);
        mGoodListAdapter = new GoodListAdapter(getActivity().getLayoutInflater());
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(mGoodListAdapter);
        mHeaderImageBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.item_category_header_image,mCategoryBinding.rvGood,false);
        lRecyclerViewAdapter.addHeaderView(mHeaderImageBinding.getRoot());
        mCategoryBinding.rvGood.setAdapter(lRecyclerViewAdapter);

        mHeaderImageBinding.ivBanner.setOnClickListener(l -> {
            Intent intent = new Intent(getActivity(),H5Activity.class);
            intent.putExtra("url", NetConfig.getBaseTuDouNiH5Url()+ "html/resultlist.html" + "?uid=" + MyApplication.getLoginUser().getUid()
                    + "&token=" + MyApplication.getLoginUser().getToken()
                    + "&unionid=" + MyApplication.getLoginUser().getUnionid()
                    + "&search=" + mCurrentRightCategory.getName());
            startActivity(intent);
        });
    }

    /**
     * 初始化一级目录列表view（左侧品类）
     * */
    private void initFirstClassCategoryView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mCategoryBinding.rvCategories.setLayoutManager(layoutManager);
        mNameAdapter = new CategoryNameAdapter(getActivity().getLayoutInflater());
        mCategoryBinding.rvCategories.setAdapter(mNameAdapter);
        mNameAdapter.setOnItemClickListener((position, itemData) -> {
            //一级品类Item被点击(左侧品类)后填充右侧数据
            mHeaderImageBinding.setImageUrl(itemData.getImgUrl());
            mCurrentRightCategory = itemData;
            mGoodListAdapter.replaceData(itemData.getCategorys());
        });
    }

    @Override
    protected void initData() {
        getData();
    }

    /**
     * 加载页面数据
     */
    private void getData() {
        if (mCategoryViewModel != null) {
            mCategoryViewModel.getGoodList(new VMResultCallback<List<Category>>(){
                @Override
                public void onSuccess(List<Category> categoryNameList) {
                    mCurrentRightCategory = categoryNameList.get(0);
                    //填充左侧品类数据
                    mNameAdapter.replaceData(categoryNameList);
                    //填充Banner数据
                    mHeaderImageBinding.setImageUrl(categoryNameList.get(0).getCategorys().get(0).getImgUrl());
                    //填充右侧品类数据
                    mGoodListAdapter.replaceData(categoryNameList.get(0).getCategorys());
                }

                @Override
                public void onFailure() {

                }
            });
        }
    }

}
