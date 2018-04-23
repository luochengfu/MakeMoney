package com.tudouni.makemoney.fragment.category;

import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.databinding.ItemGoodGridBinding;
import com.tudouni.makemoney.model.Category;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.view.BaseRecyclerViewBindingAdapter;

public class GoodListAdapter extends BaseRecyclerViewBindingAdapter<Category> {

    private GridLayoutManager mGridLayoutManager = new GridLayoutManager(MyApplication.getContext(),3);
    public GoodListAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_good_grid;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ((ItemGoodGridBinding)holder.getBinding()).rvGoodList.setLayoutManager(mGridLayoutManager);
        ((ItemGoodGridBinding)holder.getBinding()).setItem(this.data.get(position));
        holder.getBinding().executePendingBindings();
        GoodItemAdapter itemAdapter = new GoodItemAdapter(mInflater);
        ((ItemGoodGridBinding)holder.getBinding()).rvGoodList.setAdapter(itemAdapter);
        itemAdapter.replaceData(this.data.get(position).getCategorys());
    }
}
