package com.tudouni.makemoney.view;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.View;


/**
 * 自定义状态栏的高度
 */
public class MyStatusBar extends View {
	Context context;
	public MyStatusBar(Context context) {
		super(context);
	}
	
	public MyStatusBar(Context context, AttributeSet attrs)  {
		super(context, attrs);
		this.context = context;
		
//		setVisibility(View.VISIBLE);
	}
	
	public MyStatusBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}

	@Override
	public void setVisibility(int visibility) {
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			super.setVisibility(View.GONE);
		} else {
			super.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		LayoutParams params = this.getLayoutParams();
//		params.height = 0;//改为0  去掉所有页面statusbar
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}

	public void setVisible(boolean visible){
		setVisibility(visible ? View.VISIBLE : View.GONE);
	}

}
