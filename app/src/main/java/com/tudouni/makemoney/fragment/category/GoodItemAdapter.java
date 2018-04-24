package com.tudouni.makemoney.fragment.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.databinding.ItemGoodBinding;
import com.tudouni.makemoney.model.Category;
import com.tudouni.makemoney.utils.ScreenUtils;
import com.tudouni.makemoney.view.BaseRecyclerViewBindingAdapter;

public class GoodItemAdapter extends BaseRecyclerViewBindingAdapter<Category> {
    GoodItemAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_good;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemGoodBinding itemGoodBinding = ((ItemGoodBinding)holder.getBinding());
        itemGoodBinding.setItem(this.data.get(position));
        ViewGroup.LayoutParams goodPicLayoutParams = itemGoodBinding.ivGoodPic.getLayoutParams();
        Context context = itemGoodBinding.ivGoodPic.getContext();
        goodPicLayoutParams.height = (ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2px(context,95 + 16)) / 3;
        holder.getBinding().executePendingBindings();
    }
}
