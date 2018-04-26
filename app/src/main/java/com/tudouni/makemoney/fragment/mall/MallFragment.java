package com.tudouni.makemoney.fragment.mall;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.databinding.FragmentMallBinding;
import com.tudouni.makemoney.databinding.MallHeaderViewBinding;
import com.tudouni.makemoney.fragment.BaseFragment;
import com.tudouni.makemoney.model.MallAlbumModel;
import com.tudouni.makemoney.model.MallGoodItem;
import com.tudouni.makemoney.utils.TDLog;
import com.tudouni.makemoney.utils.glideUtil.GlideUtil;
import com.tudouni.makemoney.viewModel.MallViewModel;
import com.tudouni.makemoney.viewModel.VMResultCallback;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 商城页
 * Jaron.Wu
 *     2018/04/24
 */

public class MallFragment extends BaseFragment {

    private FragmentMallBinding mMallBinding;
    private MallViewModel mMallViewModel;
    private MallHeaderViewBinding mMallHeaderViewBinding;
    private AlbumItemAdapter mAlbumItemAdapter;

    private RecommendGoodItemAdapter mRecommendGoodItemAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;

    @Override
    protected int getContentView() {
        return 0;
    }

    @Override
    protected View getLayoutView() {
        mMallBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.fragment_mall,null,false);
        mMallViewModel = new MallViewModel();
       return mMallBinding.getRoot();
    }

    @Override
    protected void initView(View view) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        mMallBinding.lrvHome.setLayoutManager(layoutManager);

        mRecommendGoodItemAdapter = new RecommendGoodItemAdapter(getActivity().getLayoutInflater());
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mRecommendGoodItemAdapter);
        mMallBinding.lrvHome.setAdapter(mLRecyclerViewAdapter);
        initHeaderView();
    }

    @Override
    protected void initData() {
        loadBannerData();
        loadMallAlbum();
        loadSelfGood();
        loadRecommendGood();
    }

    private void loadSelfGood() {
        if (mMallViewModel != null) {
            mMallViewModel.loadSelfGood(new VMResultCallback<ObservableArrayList<MallAlbumModel>>() {
                @Override
                public void onSuccess(ObservableArrayList<MallAlbumModel> data) {
                    if (mMallHeaderViewBinding != null) {
                        //编译器提示错误，不影响实际运行
                        mMallHeaderViewBinding.setSelfData(data);
                    }
                }

                @Override
                public void onFailure() {

                }
            });
        }
    }

    private void loadRecommendGood() {
        if (mMallViewModel != null) {
            mMallViewModel.loadRecommendGoodData(new VMResultCallback<List<MallGoodItem>>() {
                @Override
                public void onSuccess(List<MallGoodItem> data) {
                    if (mRecommendGoodItemAdapter != null) {
                        mRecommendGoodItemAdapter.replaceData(data);
                    }
                }

                @Override
                public void onFailure() {

                }
            });
        }
    }

    private void loadMallAlbum() {
        mMallViewModel.loadAlbumData(new VMResultCallback<List<MallAlbumModel>>() {
            @Override
            public void onSuccess(List<MallAlbumModel> data) {
                if (mAlbumItemAdapter != null) {
                    mAlbumItemAdapter.replaceData(data);
                }
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void loadBannerData() {
        if (mMallViewModel != null) {
            mMallViewModel.loadMallBannerData(new VMResultCallback<List<MallAlbumModel>>() {
                @Override
                public void onSuccess(List<MallAlbumModel> data) {
                    TDLog.e(data);
                    mMallHeaderViewBinding.mzMallBanner.setPages(data, () -> new MallBannerViewHolder());
                }

                @Override
                public void onFailure() {

                }
            });
        }
    }


    private void initHeaderView(){
        mMallHeaderViewBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.mall_header_view,mMallBinding.lrvHome,false);

        mLRecyclerViewAdapter.addHeaderView(mMallHeaderViewBinding.getRoot());

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),5);
        mMallHeaderViewBinding.rvAlbum.setLayoutManager(layoutManager);
        mAlbumItemAdapter = new AlbumItemAdapter(getActivity().getLayoutInflater());
        mMallHeaderViewBinding.rvAlbum.setAdapter(mAlbumItemAdapter);

    }

    class MallBannerViewHolder implements MZViewHolder<MallAlbumModel>{
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_mall_banner,null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, MallAlbumModel data) {
            // 数据绑定
            GlideUtil.getInstance().loadImage(context,data.getImageUrl(),mImageView,0);
        }
    }
}
