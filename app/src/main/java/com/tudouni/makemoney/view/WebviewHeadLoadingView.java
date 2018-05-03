package com.tudouni.makemoney.view;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.handmark.pulltorefresh.library.LoadingLayoutBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.tudouni.makemoney.R;


/**
 * create by hjw
 */
public class WebviewHeadLoadingView extends LoadingLayoutBase {

    static final String LOG_TAG = "PullToRefresh-HuixiangLoadingLayout";

    private LinearLayout flRefresh;
    private ImageView ivLoad;
    private AnimationDrawable animP;

    public WebviewHeadLoadingView(Context context) {
        this(context, PullToRefreshBase.Mode.PULL_FROM_START);
    }

    public WebviewHeadLoadingView(Context context, PullToRefreshBase.Mode mode) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.layout_loading_header, this);
        flRefresh = (LinearLayout) findViewById(R.id.refresh_head_ll);
        ivLoad = (ImageView) flRefresh.findViewById(R.id.ivLoad);
        LayoutParams lp = (LayoutParams) flRefresh.getLayoutParams();
        lp.gravity = mode == PullToRefreshBase.Mode.PULL_FROM_END ? Gravity.TOP : Gravity.BOTTOM;
        reset();
    }

    @Override
    public final int getContentSize() {
        return flRefresh.getHeight();
    }

    public ImageView getLoadImageView() {
        return ivLoad;
    }

    @Override
    public final void pullToRefresh() {
        ivLoad.setVisibility(VISIBLE);
        ivLoad.setImageResource(R.drawable.cartoon_refresh_00000);
//        if (animP == null) {
//            animP = (AnimationDrawable) ivLoad.getDrawable();
//        }
//        animP.start();
    }

    @Override
    public final void onPull(float scaleOfLayout) {
    }

    @Override
    public final void refreshing() {
        ivLoad.setImageResource(R.drawable.animation_loading_header);
        if (animP == null) {
            animP = (AnimationDrawable) ivLoad.getDrawable();
        }
        animP.start();
    }

    @Override
    public final void releaseToRefresh() {
    }

    @Override
    public final void reset() {
        if (animP != null) {
            animP.stop();
            animP = null;
        }
    }

    @Override
    public void setLastUpdatedLabel(CharSequence label) {

    }

    @Override
    public void setPullLabel(CharSequence pullLabel) {

    }

    @Override
    public void setRefreshingLabel(CharSequence refreshingLabel) {

    }

    @Override
    public void setReleaseLabel(CharSequence releaseLabel) {

    }

    @Override
    public void setTextTypeface(Typeface tf) {

    }
}