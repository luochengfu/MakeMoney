package com.tudouni.makemoney.fragment.category;

import android.view.LayoutInflater;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.databinding.ItemCategoryNameBinding;
import com.tudouni.makemoney.model.CategoryNameItem;
import com.tudouni.makemoney.utils.TDLog;
import com.tudouni.makemoney.view.BaseRecyclerViewBindingAdapter;

public class CategoryNameAdapter extends BaseRecyclerViewBindingAdapter<CategoryNameItem> {

    private int lastSelectedItem = 0;

    CategoryNameAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewBindingAdapter.BaseViewHolder holder, int position) {
        ((ItemCategoryNameBinding)holder.getBinding()).setItem(this.data.get(position));
        ((ItemCategoryNameBinding)holder.getBinding()).tvNameItem.setOnClickListener(l -> {
            this.data.get(lastSelectedItem).setSelected(false);
            this.data.get(holder.getAdapterPosition()).setSelected(true);
            lastSelectedItem = holder.getAdapterPosition();
            TDLog.e("item Click");
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_category_name;
    }

}
