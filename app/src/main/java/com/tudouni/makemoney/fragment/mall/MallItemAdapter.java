package com.tudouni.makemoney.fragment.mall;

import android.view.LayoutInflater;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.model.MallGoodItem;
import com.tudouni.makemoney.view.BaseRecyclerViewBindingAdapter;

/**
 * Jaron.Wu
 * 2018/4/26
 */
public class MallItemAdapter extends BaseRecyclerViewBindingAdapter<MallGoodItem> {
    public MallItemAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_mall_good;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

    }
}
