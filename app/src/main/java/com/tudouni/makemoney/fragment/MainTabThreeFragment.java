package com.tudouni.makemoney.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.activity.AccountSecurityActivity;
import com.tudouni.makemoney.activity.H5Activity;
import com.tudouni.makemoney.adapter.FoundAdapter;
import com.tudouni.makemoney.adapter.TopicAdapter;
import com.tudouni.makemoney.interfaces.DownFileCallBack;
import com.tudouni.makemoney.interfaces.IItemClickListener;
import com.tudouni.makemoney.model.Banner;
import com.tudouni.makemoney.model.FoundTopicBean;
import com.tudouni.makemoney.model.NineRecommendBean;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.Constants;
import com.tudouni.makemoney.utils.ScreenUtils;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.utils.glideUtil.GlideUtil;
import com.tudouni.makemoney.view.CenterLoadingView;
import com.tudouni.makemoney.view.MZBannerViewHolder;
import com.tudouni.makemoney.view.MineRefreshHeader;
import com.tudouni.makemoney.view.MyTitleBar;
import com.tudouni.makemoney.view.RecyclerViewDivider;
import com.tudouni.makemoney.widget.downLoad.DownloadItem;
import com.tudouni.makemoney.widget.downLoad.DownloadManager;
import com.tudouni.makemoney.widget.sharePart.ShareWindow_v3;
import com.tudouni.makemoney.widget.sharePart.model.Share;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/20 0020.
 */

public class MainTabThreeFragment extends BaseFragment {
    private MZBannerView mBanner;
    private View mHeadView;
    private LRecyclerView mLRecyclerView;
    private RecyclerView mRecyclerView;
    private ImageView mHeadImageView;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private TopicAdapter mTopticAdapter;
    private FoundAdapter mAdapter;
    private GridLayoutManager mLayoutManager;
    private MyTitleBar title_bar;
    private List<Banner> bannerList = new ArrayList<>();
    private CenterLoadingView loadingDialog = null;
    private ShareWindow_v3 shareWindow_v3 = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.GET_SHARE_IMAGE_START:
                    loadingDialog.show();
                    break;
                case Constants.GET_SHARE_IMAGE_END:
                    loadingDialog.dismiss();
                    if (msg.obj == null) return;
                    Share share = (Share) msg.obj;
                    if (shareWindow_v3 == null) {
                        shareWindow_v3 = new ShareWindow_v3(getActivity(), Share.Type.IMAGE_MULTIPL, share, null, null);
                    } else {
                        shareWindow_v3.setmShare(share);
                    }
                    shareWindow_v3.show(getActivity());
                    break;
            }
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.main_fragment_tab_three;
    }

    @Override
    protected void initView(View view) {
        mLRecyclerView = (LRecyclerView) view.findViewById(R.id.three_tab_rv);
        title_bar = (MyTitleBar) view.findViewById(R.id.title_bar);
    }

    @Override
    protected void initData() {
        title_bar.setMiddleText("发现");
        title_bar.setLeftVisible(View.GONE);
        title_bar.setRightVisible(View.GONE);
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
        mAdapter = new FoundAdapter(getContext());
        mAdapter.setHandler(handler);
        mLayoutManager = new GridLayoutManager(getActivity(), 1);
        mLRecyclerView.setLayoutManager(mLayoutManager);
        mLRecyclerView.setRefreshHeader(new MineRefreshHeader(getContext()));
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mLRecyclerViewAdapter.addHeaderView(mHeadView);
        mLRecyclerView.setAdapter(mLRecyclerViewAdapter);
        //去掉footview
        mLRecyclerViewAdapter.removeFooterView();
    }

    private void initBanner() {
        if (null == loadingDialog) {
            loadingDialog = new CenterLoadingView(getActivity());
            loadingDialog.setTitle("正在加载");
        }
        mHeadView = getActivity().getLayoutInflater().inflate(R.layout.three_tab_header_layout, null, false);
        mBanner = (MZBannerView) mHeadView.findViewById(R.id.banner);
        mRecyclerView = (RecyclerView) mHeadView.findViewById(R.id.three_tab_rv_2);
        mHeadImageView = (ImageView) mHeadView.findViewById(R.id.iv_banner);

        mTopticAdapter = new TopicAdapter(getContext());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(mTopticAdapter);
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(1));
        mTopticAdapter.setItemClickListener(new IItemClickListener() {
            @Override
            public void action(String url) {
                Intent intent = new Intent(getActivity(), H5Activity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

        mBanner.setIndicatorRes(R.mipmap.banner_white_icon, R.mipmap.banner_red_icon);

        mBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                Banner banner = bannerList.get(position);
                Intent intent = new Intent(getActivity(), H5Activity.class);
                intent.putExtra("url", banner.getUrl() + "?title=" + banner.getTitle());
                startActivity(intent);
            }
        });

        int height = ScreenUtils.getScreenWidth(getContext()) * 177 / 375 + ScreenUtils.dp2px(getContext(), 8);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        mBanner.setLayoutParams(params);
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
                if (bannerList.size() == 0) {
                    mHeadImageView.setVisibility(View.GONE);
                    mBanner.setVisibility(View.GONE);
                }
                if (bannerList != null && bannerList.size() > 0) {
                    if (bannerList.size() == 1) {
                        mHeadImageView.setVisibility(View.VISIBLE);
                        mBanner.setVisibility(View.GONE);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                ScreenUtils.getScreenWidth(getContext()) * 177 / 375 + ScreenUtils.dp2px(getContext(), 8));
//                        params.setMargins(0, 0, 0, ScreenUtils.dp2px(getContext(), 10));
                        mHeadImageView.setLayoutParams(params);

                        mHeadImageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Banner banner = data.get(0);
                                Intent intent = new Intent(getActivity(), H5Activity.class);
                                intent.putExtra("url", banner.getUrl() + "?title=" + banner.getTitle());
                                startActivity(intent);
                            }
                        });

                        GlideUtil.getInstance().loadImage(getContext(), bannerList.get(0).getImageUrl(), mHeadImageView, R.mipmap.found_default_banner);
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
        CommonScene.getNineRecommend(new BaseObserver<List<NineRecommendBean>>() {
            @Override
            public void OnSuccess(List<NineRecommendBean> banners) {
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
            }
        });
    }

    private RecyclerView.ItemDecoration mItemDecoration = new RecyclerView.ItemDecoration() {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) % 2 == 1) {
                outRect.left = ScreenUtils.dp2px(getContext(), 4);
            } else {
                outRect.right = ScreenUtils.dp2px(getContext(), 4);
            }
            outRect.bottom = ScreenUtils.dp2px(getContext(), 2);
            outRect.top = ScreenUtils.dp2px(getContext(), 2);
        }
    };

    private RecyclerView.ItemDecoration mItemDecoration2 = new RecyclerView.ItemDecoration() {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = ScreenUtils.dp2px(getContext(), 8);
            outRect.top = 0;
        }
    };
}
