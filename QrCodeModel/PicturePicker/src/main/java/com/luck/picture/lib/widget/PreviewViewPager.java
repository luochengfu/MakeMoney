package com.luck.picture.lib.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * author：luck
 * project：PreviewViewPager
 * package：com.luck.picture.ui
 * email：893855882@qq.com
 * data：16/12/31
 */

public class PreviewViewPager extends ViewPager {

    private float mLastX;
    private float mLastY;

    public PreviewViewPager(Context context) {
        super(context);
    }

    public PreviewViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        boolean validVerticalDrag = false;
//        boolean validHorizontalDrag=false;
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mLastX = getViewX(ev);
//                mLastY = getViewY(ev);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                final float x = getViewX(ev);
//                final float dx = x - mLastX;
//                final float xDiff = Math.abs(dx);
//                final float y = getViewY(ev);
//                final float dy = y - mLastY;
//                final float yDiff = Math.abs(dy);
//
//                 validHorizontalDrag = xDiff > 10 && xDiff > yDiff;
////                validVerticalDrag = yDiff > 10 && yDiff > xDiff;
//
//                break;
//        }
//
//        return super.onInterceptTouchEvent(ev)
//                && validHorizontalDrag;
        return super.onInterceptTouchEvent(ev);
//                && validHorizontalDrag;
    }

    private float getViewX(MotionEvent event) {
        return event.getRawX();
    }

    private float getViewY(MotionEvent event) {
        return event.getRawY();
    }
}
