package com.tudouni.makemoney.fragment.category;

import android.view.LayoutInflater;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.databinding.ItemGoodBinding;
import com.tudouni.makemoney.model.Category;
import com.tudouni.makemoney.view.BaseRecyclerViewBindingAdapter;

public class GoodItemAdapter extends BaseRecyclerViewBindingAdapter<Category> {
    public GoodItemAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_good;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ((ItemGoodBinding)holder.getBinding()).setItem(this.data.get(position));
        holder.getBinding().executePendingBindings();
    }
}
