package com.tudouni.makemoney.fragment.mall;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.activity.H5Activity;
import com.tudouni.makemoney.activity.MessageActivity;
import com.tudouni.makemoney.activity.search.SearchActivity;
import com.tudouni.makemoney.databinding.FragmentMallBinding;
import com.tudouni.makemoney.databinding.MallHeaderViewBinding;
import com.tudouni.makemoney.fragment.BaseFragment;
import com.tudouni.makemoney.model.MallAlbumModel;
import com.tudouni.makemoney.model.MallGoodItem;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.NetConfig;
import com.tudouni.makemoney.utils.Constants;
import com.tudouni.makemoney.utils.TDLog;
import com.tudouni.makemoney.utils.glideUtil.GlideUtil;
import com.tudouni.makemoney.view.MineRefreshHeader;
import com.tudouni.makemoney.viewModel.MallViewModel;
import com.tudouni.makemoney.viewModel.VMResultCallback;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.List;

/**
 * 商城页
 * Jaron.Wu
 * 2018/04/24
 */

public class MallFragment extends BaseFragment {

    private FragmentMallBinding mMallBinding;
    private MallViewModel mMallViewModel;
    private MallHeaderViewBinding mMallHeaderViewBinding;
    private AlbumItemAdapter mAlbumItemAdapter;

    private int mCurrentPage = 1;

    private RecommendGoodItemAdapter mRecommendGoodItemAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;

    private List<MallAlbumModel> mBannerData = new ObservableArrayList<>();

    @Override
    protected int getContentView() {
        return 0;
    }

    @Override
    protected View getLayoutView() {
        mMallBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.fragment_mall, null, false);
        mMallViewModel = new MallViewModel();
        return mMallBinding.getRoot();
    }

    @Override
    protected void initView(View view) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mMallBinding.lrvHome.setLayoutManager(layoutManager);

        mMallBinding.lrvHome.setRefreshHeader(new MineRefreshHeader(getContext()));

        mRecommendGoodItemAdapter = new RecommendGoodItemAdapter(getActivity().getLayoutInflater());
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mRecommendGoodItemAdapter);
        mMallBinding.lrvHome.setAdapter(mLRecyclerViewAdapter);
        mMallBinding.lrvHome.setOnRefreshListener(this::loadMallData);
        mMallBinding.lrvHome.setLoadMoreEnabled(true);
        mMallBinding.lrvHome.setOnLoadMoreListener(this::loadMore);

        mRecommendGoodItemAdapter.setOnItemClickListener((position, itemData) -> {
            Intent intent = new Intent(getActivity(), H5Activity.class);
            String url = NetConfig.getBaseTuDouNiH5Url() + "html/detail.html" + "?uid=" + MyApplication.getLoginUser().getUid()
                    + "&token=" + MyApplication.getLoginUser().getToken() + "&unionid=" +
                    MyApplication.getLoginUser().getUnionid() + "&itemid=" + itemData.getItem_id()
                    + "&source=" + itemData.getSource();
            intent.putExtra("url", url);
            TDLog.e(url);
            startActivity(intent);
        });
        initHeaderView();

        mMallBinding.tvSearchBar.setOnClickListener(l -> startActivity(new Intent(getActivity(), SearchActivity.class)));

        mMallBinding.ivMsg.setOnClickListener(l -> startActivity(new Intent(getActivity(), MessageActivity.class)));
    }

    private void loadMore() {
        loadRecommendGood(mCurrentPage++,Constants.DEFAULT_PAGE_SIZE);
    }

    @Override
    protected void initData() {
        loadMallData();
    }

    private void loadMallData() {
        loadBannerData();
        loadMallAlbum();
        loadSelfGood();
        loadRecommendGood(mCurrentPage,Constants.DEFAULT_PAGE_SIZE);
    }

    private void loadSelfGood() {
        if (mMallViewModel != null) {
            mMallViewModel.loadSelfGood(new VMResultCallback<ObservableArrayList<MallAlbumModel>>() {
                @Override
                public void onSuccess(ObservableArrayList<MallAlbumModel> data) {
                    if (mMallHeaderViewBinding != null) {
                        //编译器提示错误，不影响实际运行
//                        mBannerData = data;
                        mMallHeaderViewBinding.setSelfData(data);
                    }
                }

                @Override
                public void onFailure() {

                }
            });
        }
    }

    public void onSelfCategoryClick(View view, MallAlbumModel albumModel) {
        if (albumModel != null) {
            Intent intent = new Intent(getActivity(), H5Activity.class);
            intent.putExtra("url", albumModel.getUrl());
            startActivity(intent);
        }
    }

    private void loadRecommendGood(int page,int pageSize) {
        if (mMallViewModel != null) {
            mMallViewModel.loadRecommendGoodData(new VMResultCallback<List<MallGoodItem>>() {
                @Override
                public void onSuccess(List<MallGoodItem> data) {
                        if (mRecommendGoodItemAdapter != null) {
                            if (page == 1) {
                                mCurrentPage = 1;
                                mRecommendGoodItemAdapter.replaceData(data);
                            }else{
                                mRecommendGoodItemAdapter.addData(data);
                                if (data.size() < Constants.DEFAULT_PAGE_SIZE) {
                                    mMallBinding.lrvHome.setNoMore(true);
                                }
                            }
                            mMallBinding.lrvHome.refreshComplete(Constants.DEFAULT_PAGE_SIZE);
                        }
                }

                @Override
                public void onFailure() {
                    if (page != 1) {
                        mCurrentPage --;
                    }
                    mMallBinding.lrvHome.refreshComplete(Constants.DEFAULT_PAGE_SIZE);
                }
            },page,pageSize);
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
                    mBannerData = data;
                    mMallHeaderViewBinding.mzMallBanner.setPages(data, () -> new MallBannerViewHolder());
                }

                @Override
                public void onFailure() {

                }
            });
        }
    }


    private void initHeaderView() {
        mMallHeaderViewBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.mall_header_view, mMallBinding.lrvHome, false);
        mMallHeaderViewBinding.setSelfCategory(this);
        mLRecyclerViewAdapter.addHeaderView(mMallHeaderViewBinding.getRoot());

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 5);
        mMallHeaderViewBinding.rvAlbum.setLayoutManager(layoutManager);
        mAlbumItemAdapter = new AlbumItemAdapter(getActivity().getLayoutInflater());
        mMallHeaderViewBinding.rvAlbum.setAdapter(mAlbumItemAdapter);
        mAlbumItemAdapter.setOnItemClickListener((position, itemData) -> {
            Intent intent = new Intent(getActivity(), H5Activity.class);
            intent.putExtra("url", itemData.getUrl());
            startActivity(intent);
        });

        mMallHeaderViewBinding.mzMallBanner.setBannerPageClickListener((view, i) -> {
            TDLog.e("onBannerPageClick",i);
            Intent intent = new Intent(getActivity(), H5Activity.class);
            intent.putExtra("url", mBannerData.get(i).getUrl());
            startActivity(intent);
        });


    }


    class MallBannerViewHolder implements MZViewHolder<MallAlbumModel> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_mall_banner, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, MallAlbumModel data) {
            // 数据绑定
            GlideUtil.getInstance().loadImage(context, data.getImageUrl(), mImageView, 0);
        }
    }
}
