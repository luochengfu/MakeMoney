package com.tudouni.makemoney.view.mmlist;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import android.widget.AbsListView;

import com.tudouni.makemoney.R;

public class MmListView extends ListView implements AbsListView.OnScrollListener {
    private LoadMoreListener loadMoreListener;
    //是否可加载更多
    private boolean canLoadMore = true;
    //加载更多布局
    private LoadingMoreFooter loadingMoreFooter;
    //正在加载数据中
    private boolean isLoadingData = false;
    private int firstVisibleItem;

    public MmListView(Context context) {
        super(context, null);
        init(context);
    }

    public MmListView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init(context);
    }

    public MmListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        loadingMoreFooter = new LoadingMoreFooter(context);
        //设置自定义加载中效果
        ProgressView progressView = new ProgressView(context);
        progressView.setIndicatorId(ProgressView.BallPulse); //动画效果 自己可以尝试各种风格
        progressView.setIndicatorColor(ContextCompat.getColor(context, R.color.colorAccent));//动画颜色
        loadingMoreFooter.addFootLoadingView(progressView);
    }

    //移除加载更多布局
    public void removeFooter() {
        canLoadMore = false;
        this.removeFooterView(loadingMoreFooter);
    }

    //添加加载更多布局 在removeFooter()后调用
    public void addFooter() {
        canLoadMore = true;
        this.addFooterView(loadingMoreFooter);
    }

    //下拉刷新后初始化底部状态
    public void refreshComplete() {
        if (loadingMoreFooter != null) {
            loadingMoreFooter.setGone();
        }
        isLoadingData = false;
    }

    //上拉加载后初始化底部状态
    public void loadMoreComplete() {
        if (loadingMoreFooter != null) {
            loadingMoreFooter.setGone();
        }
        isLoadingData = false;
    }


    //到底了
    public void loadMoreEnd() {
        if (loadingMoreFooter != null) {
            loadingMoreFooter.setEnd();
        }
    }

    //设置底部加载中效果
    public void setFootLoadingView(View view) {
        if (loadingMoreFooter != null) {
            loadingMoreFooter.addFootLoadingView(view);
        }
    }

    //设置加载更多监听
    public void setLoadMoreListener(LoadMoreListener listener) {
        loadMoreListener = listener;
        addFooterView(loadingMoreFooter);
        loadingMoreFooter.setGone();
        setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //判断 isLoadingData:不在加载中 ,scrollState:整个滚动事件结束，firstVisibleItem:条目至少上滑一条  canLoadMore:可以加载更多
        if (!isLoadingData && scrollState == OnScrollListener.SCROLL_STATE_IDLE &&
                firstVisibleItem != 0 && canLoadMore) {
            if (loadMoreListener != null) {
                if (loadingMoreFooter != null) {
                    loadingMoreFooter.setVisible();
                }
                isLoadingData = true;
                loadMoreListener.onLoadMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int
            totalItemCount) {
        //firstVisibleItem表示在屏幕第一个ListItem(部分显示的ListItem也算)在整个ListView的位置（下标从0开始）
        this.firstVisibleItem = firstVisibleItem;
    }
}
