package com.tudouni.makemoney.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.jdsjlzx.interfaces.IRefreshHeader;
import com.tudouni.makemoney.R;

/**
 * 自定义下拉刷新动画
 * Created by huang on 2017/9/29.
 */

public class MineRefreshHeader extends LinearLayout implements IRefreshHeader {

    private LinearLayout mContainer;
    private AnimationDrawable animP;
    private ImageView ivLoad;

    private int mState = STATE_NORMAL;
    public int mMeasuredHeight;


    public MineRefreshHeader(Context context) {
        super(context);
        initView();
    }

    public MineRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        // 初始情况，设置下拉刷新view高度为0
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        this.setLayoutParams(lp);
        this.setPadding(0, 0, 0, 0);

        mContainer = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_loading_header, null);
        addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        setGravity(Gravity.BOTTOM);

        ivLoad = (ImageView) findViewById(R.id.ivLoad);
        ivLoad.setImageResource(R.drawable.animation_loading_header);

        measure(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();
    }

    public void setType2(){
//        ivLoad.setImageResource(R.drawable.animation_loading_header2);
    }

    public void setState(int state) {
        if (state == mState) return ;

        if (state == STATE_REFRESHING) {	// 显示进度
            smoothScrollTo(mMeasuredHeight);
        }

        switch(state){

            case STATE_REFRESHING:
                if (mState != STATE_RELEASE_TO_REFRESH)
                    startAnim();
                break;
            case STATE_RELEASE_TO_REFRESH:
                if (mState != STATE_REFRESHING)
                    startAnim();
                break;

            case STATE_NORMAL:
                stopAnim();
                break;
            default:
        }

        mState = state;
    }

    @Override
    public void refreshComplete(){
        new Handler().postDelayed(new Runnable(){
            public void run() {
                reset();
            }
        }, 500);
    }

    @Override
    public View getHeaderView() {
        return this;
    }

    public void setVisibleHeight(int height) {
        if (height < 0) height = 0;
        LayoutParams lp = (LayoutParams) mContainer .getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    public int getVisibleHeight() {
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        return lp.height;
    }

    @Override
    public int getVisibleWidth() {
        return 0;
    }

    @Override
    public void onReset() {
        setState(STATE_NORMAL);
    }

    @Override
    public void onPrepare() {
        setState(STATE_RELEASE_TO_REFRESH);
    }

    @Override
    public void onRefreshing() {
        setState(STATE_REFRESHING);
    }

    @Override
    public void onMove(float offSet, float sumOffSet) {

        if (getVisibleHeight() > 0 || offSet > 0) {
            setVisibleHeight((int) offSet + getVisibleHeight());
            if (mState <= STATE_RELEASE_TO_REFRESH) { // 未处于刷新状态，更新箭头
                if (getVisibleHeight() > mMeasuredHeight) {
                    onPrepare();
                } else {
                    onReset();
                }
            }
        }
    }

    @Override
    public boolean onRelease() {
        boolean isOnRefresh = false;
        int height = getVisibleHeight();
        if (height == 0) {// not visible.
            isOnRefresh = false;
        }

        if(getVisibleHeight() > mMeasuredHeight &&  mState < STATE_REFRESHING){
            setState(STATE_REFRESHING);
            isOnRefresh = true;
        }
        // refreshing and layout_loading_header isn't shown fully. do nothing.
        if (mState == STATE_REFRESHING && height > mMeasuredHeight) {
            smoothScrollTo(mMeasuredHeight);
        }
        if (mState != STATE_REFRESHING) {
            smoothScrollTo(0);
        }

        if (mState == STATE_REFRESHING) {
            int destHeight = mMeasuredHeight;
            smoothScrollTo(destHeight);
        }

        return isOnRefresh;
    }

    public void reset() {
        setState(STATE_NORMAL);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                smoothScrollTo(0);
            }
        }, 200);
    }

    private void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), destHeight);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                setVisibleHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    private void startAnim(){
        if (animP == null)
            animP = (AnimationDrawable) ivLoad.getDrawable();
        animP.start();
    }

    private void stopAnim(){
        if (animP != null) {
            animP.stop();
            animP = null;
        }
    }
}
