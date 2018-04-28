package com.tudouni.makemoney.activity.search;

import android.view.LayoutInflater;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.databinding.ItemSearchHistoryBinding;
import com.tudouni.makemoney.view.BaseRecyclerViewBindingAdapter;

/**
 * Jaron.Wu
 * 2018/4/28
 */
public class SearchHistoryAdapter extends BaseRecyclerViewBindingAdapter<String> {
    public SearchHistoryAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_search_history;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemSearchHistoryBinding historyBinding = (ItemSearchHistoryBinding) holder.getBinding();
        historyBinding.setItem(this.data.get(position));
        historyBinding.executePendingBindings();
    }
}
