package com.tudouni.makemoney.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.adapter.FoundAdapter;
import com.tudouni.makemoney.model.Banner;
import com.tudouni.makemoney.model.FoundItemBean;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.InjectView;
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
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
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
        initAdapter();
        initBanner();
        getBanner();
    }

    private void initAdapter() {
        mAdapter = new FoundAdapter();
        mLayoutManager = new GridLayoutManager(getActivity(), 1);

        mLRecyclerView.setLayoutManager(mLayoutManager);
        mHeadView = getActivity().getLayoutInflater().inflate(R.layout.three_tab_header_layout, mLRecyclerView, false);
        mBanner = (MZBannerView) mHeadView.findViewById(R.id.banner);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mLRecyclerViewAdapter.addHeaderView(mHeadView);
        mLRecyclerView.setAdapter(mLRecyclerViewAdapter);
        List<FoundItemBean> list = new ArrayList<>();
        FoundItemBean itemBean = new FoundItemBean();
        itemBean.setImg("http://baidu.com");
        list.add(itemBean);
        mAdapter.addData(list);
    }

    private void initBanner() {

    }

    private void getBanner() {
        CommonScene.getFoundBanner(new BaseObserver<List<Banner>>() {
            @Override
            public void OnSuccess(List<Banner> data) {
                bannerList.clear();
                bannerList.addAll(data);
                if (bannerList != null && bannerList.size() > 0) {
                    if (bannerList.size() == 1) {
//                        ImageUtils.glidRadiusImage(mLiveHeadBinding.ivBanner, bannerList.get(0).getImage(), 15);
                    } else {
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
}
