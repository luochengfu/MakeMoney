package com.tudouni.makemoney.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.model.NineRecommendGoodsBean;
import com.tudouni.makemoney.utils.ScreenUtils;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.utils.TuDouTextUtil;
import com.tudouni.makemoney.utils.glideUtil.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangPeng on 2018/5/15.
 */

public class NineRevommendGoodsLayout extends LinearLayout implements View.OnClickListener {
    private LinearLayout mView;
    private Context context;
    private LinearLayout mFristContetnLy;//第一行容器
    private LinearLayout mSecondContetnLy;//第二行容器
    private LinearLayout mThurdContetnLy;//第三行容器
    private int itemSize;
    private int margenSize;
    private int margenSizeDp = 10;//本布局Margen
    private int parentMargenSizeDp = 20;//父布局Margen

    private List<NineRecommendGoodsBean> mData;

    public NineRevommendGoodsLayout(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public NineRevommendGoodsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public NineRevommendGoodsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        margenSize = ScreenUtils.dp2px(context, margenSizeDp);
        itemSize = (ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2px(context, parentMargenSizeDp + margenSizeDp * 2)) / 3;
        mView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.nine_recommend_goods_layout11, null);
        mFristContetnLy = (LinearLayout) mView.findViewById(R.id.frist_content_ly);
        mSecondContetnLy = (LinearLayout) mView.findViewById(R.id.second_content_ly);
        mThurdContetnLy = (LinearLayout) mView.findViewById(R.id.third_content_ly);
        addView(mView);
    }

    /**
     * 设置数据
     *
     * @param list
     */
    public void setData(List<NineRecommendGoodsBean> list) {
        if (list == null) return;
        if (this.mData == null)
            this.mData = new ArrayList<>();
        this.mData.clear();
        this.mData.addAll(list);
        clearView();
        initData();
    }

    /**
     * 清空商品布局
     */
    private void clearView() {
        if (mFristContetnLy != null) {
            mFristContetnLy.removeAllViews();
            mFristContetnLy.setVisibility(GONE);
        }
        if (mSecondContetnLy != null) {
            mSecondContetnLy.removeAllViews();
            mSecondContetnLy.setVisibility(GONE);
        }
        if (mThurdContetnLy != null) {
            mThurdContetnLy.removeAllViews();
            mThurdContetnLy.setVisibility(GONE);
        }
    }

    /**
     * 加载数据
     */
    private void initData() {
        if (mData == null) return;
        LinearLayout linearLayout;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(itemSize, itemSize);
        for (int i = 0; i < mData.size(); i++) {
            NineRecommendGoodsBean item = mData.get(i);
            if (i < 3)
                linearLayout = mFristContetnLy;
            else if (i < 6)
                linearLayout = mSecondContetnLy;
            else
                linearLayout = mThurdContetnLy;
            layoutParams.setMargins(((i % 3 == 1) ? margenSize : 0), 0, ((i % 3 == 1) ? margenSize : 0), 0);
            RelativeLayout view = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.item_nine_recommend_goods_layout, null);
            view.setOnClickListener(this);
            view.getTag(i);
            view.setLayoutParams(layoutParams);
            GlideUtil.getInstance().loadImage(context, item.getPicurl(), ((ImageView) view.getChildAt(0)), R.mipmap.ic_launcher);
            TuDouTextUtil.setTextToTextView(((TextView) ((LinearLayout) view.getChildAt(1)).getChildAt(0)), "¥" + item.getPrice());
            linearLayout.addView(view);
            linearLayout.setVisibility(VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
//        int position = (Integer) v.getTag();
        ToastUtil.show("点击商品");
    }
}
