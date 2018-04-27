package com.tudouni.makemoney.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.activity.H5Activity;
import com.tudouni.makemoney.activity.RefreshWebViewActivity;
import com.tudouni.makemoney.adapter.FoundAdapter;
import com.tudouni.makemoney.adapter.TopicAdapter;
import com.tudouni.makemoney.interfaces.IItemClickListener;
import com.tudouni.makemoney.model.Banner;
import com.tudouni.makemoney.model.FoundTopicBean;
import com.tudouni.makemoney.model.RecommendTopicBean;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.ScreenUtils;
import com.tudouni.makemoney.utils.glideUtil.GlideUtil;
import com.tudouni.makemoney.view.MZBannerViewHolder;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/20 0020.
 */

public class MainTabThreeFragment extends BaseFragment
{
    private MZBannerView mBanner;
    private View mHeadView;
    private LRecyclerView mLRecyclerView;
    private RecyclerView mRecyclerView;
    private ImageView mHeadImageView;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private TopicAdapter mTopticAdapter;
    private FoundAdapter mAdapter;
    private GridLayoutManager mLayoutManager;
    private List<Banner> bannerList = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.main_fragment_tab_three;
    }

    @Override
    protected void initView(View view) {
        mLRecyclerView = (LRecyclerView) view.findViewById(R.id.three_tab_rv);
    }

    @Override
    protected void initData() {
        initBanner();
        initAdapter();
        getBanner();
        getServerDatas();
        reflash();
    }

    private void getServerDatas() {
        getBanner();
        getRecommendTopic();
        getFoundTopic();
    }


    private void initAdapter() {
        mAdapter = new FoundAdapter();
        mLayoutManager = new GridLayoutManager(getActivity(), 1);
        mLRecyclerView.setLayoutManager(mLayoutManager);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mLRecyclerViewAdapter.addHeaderView(mHeadView);
        mLRecyclerView.setAdapter(mLRecyclerViewAdapter);
        //去掉footview
        mLRecyclerViewAdapter.removeFooterView();
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), H5Activity.class);
                intent.putExtra("url",mAdapter.getUrl(position));
                startActivity(intent);
            }
        });
    }

    private void initBanner() {
        mHeadView = getActivity().getLayoutInflater().inflate(R.layout.three_tab_header_layout, null, false);
        mBanner = (MZBannerView) mHeadView.findViewById(R.id.banner);
        mRecyclerView = (RecyclerView) mHeadView.findViewById(R.id.three_tab_rv_2);
        mHeadImageView = (ImageView) mHeadView.findViewById(R.id.iv_banner);

        mTopticAdapter = new TopicAdapter();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(mTopticAdapter);
        mRecyclerView.addItemDecoration(mItemDecoration);

        mTopticAdapter.setItemClickListener(new IItemClickListener() {
            @Override
            public void action(String url) {
                Intent intent = new Intent(getActivity(), RefreshWebViewActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });

        mBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                Banner banner = bannerList.get(position);
                Intent intent = new Intent(getActivity(), H5Activity.class);
                intent.putExtra("url",banner.getUrl() + "?title=" + banner.getTitle());
                startActivity(intent);
            }
        });
    }

    private void reflash() {
        mLRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getServerDatas();
            }
        });
    }

    private void getBanner() {
        CommonScene.getFoundBanner(new BaseObserver<List<Banner>>() {
            @Override
            public void OnSuccess(List<Banner> data) {
                bannerList.clear();
                bannerList.addAll(data);
                if(bannerList.size() == 0) {
                    mHeadImageView.setVisibility(View.GONE);
                    mBanner.setVisibility(View.GONE);
                }
                if (bannerList != null && bannerList.size() > 0) {
                    if (bannerList.size() == 1) {
                        mHeadImageView.setVisibility(View.VISIBLE);
                        mBanner.setVisibility(View.GONE);
                        GlideUtil.bindImage(mHeadImageView,bannerList.get(0).getImageUrl());
                    } else {
                        mHeadImageView.setVisibility(View.GONE);
                        mBanner.setVisibility(View.VISIBLE);
                        mBanner.setPages(bannerList, new MZHolderCreator<MZBannerViewHolder>() {
                            @Override
                            public MZBannerViewHolder createViewHolder() {
                                return new MZBannerViewHolder();
                            }
                        });
                        mBanner.start();
                    }
                }
            }
        });
    }

    private void getRecommendTopic() {
        CommonScene.getRecommendTopic(new BaseObserver<List<RecommendTopicBean>>() {
            @Override
            public void OnSuccess(List<RecommendTopicBean> banners) {
                mAdapter.clear();
                mAdapter.addData(banners);
                mLRecyclerView.refreshComplete(banners.size());
            }

            @Override
            public void OnFail(int code, String err) {
                mLRecyclerView.refreshComplete(mAdapter.getItemCount());
            }
        });
    }

    /**
     * 获取方格数据
     */
    private void getFoundTopic() {
        CommonScene.getFoundTopic(new BaseObserver<List<FoundTopicBean>>() {
            @Override
            public void OnSuccess(List<FoundTopicBean> recommendTopicBeans) {
                mTopticAdapter.addData(recommendTopicBeans);
//                FoundItemBean bean = new FoundItemBean();
//                bean.setBean(recommendTopicBeans);
//                mAdapter.addFirst(bean);
            }
        });
    }

    private RecyclerView.ItemDecoration mItemDecoration = new RecyclerView.ItemDecoration() {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) % 2 == 1) {
                outRect.left = ScreenUtils.dp2px(getContext(), 3);
            } else {
                outRect.right = ScreenUtils.dp2px(getContext(), 3);
            }
            outRect.bottom = ScreenUtils.dp2px(getContext(),6);
        }
    };
}
