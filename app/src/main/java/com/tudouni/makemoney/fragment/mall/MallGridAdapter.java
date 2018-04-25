package com.tudouni.makemoney.fragment.mall;

import android.view.LayoutInflater;

import com.tudouni.makemoney.view.BaseRecyclerViewBindingAdapter;

/**
 * Jaron.Wu
 * 2018/4/25
 */
public class MallGridAdapter extends BaseRecyclerViewBindingAdapter<Object> {

    private static final int TYPE_HEADER = 10;
    private static final int TYPE_COMMON = 11;

    public MallGridAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_COMMON;
    }





}
