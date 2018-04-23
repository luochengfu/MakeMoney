package com.tudouni.makemoney.fragment.category;

import android.view.LayoutInflater;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.databinding.ItemCategoryNameBinding;
import com.tudouni.makemoney.model.Category;
import com.tudouni.makemoney.view.BaseRecyclerViewBindingAdapter;

public class CategoryNameAdapter extends BaseRecyclerViewBindingAdapter<Category> {

    private int lastSelectedItem = 0;

    CategoryNameAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ((ItemCategoryNameBinding)holder.getBinding()).setItem(this.data.get(position));
        ((ItemCategoryNameBinding)holder.getBinding()).tvNameItem.setOnClickListener(l -> {
            this.data.get(lastSelectedItem).setSelected(false);
            this.data.get(holder.getAdapterPosition()).setSelected(true);
            lastSelectedItem = holder.getAdapterPosition();
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(position,this.data.get(position));
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_category_name;
    }

}
