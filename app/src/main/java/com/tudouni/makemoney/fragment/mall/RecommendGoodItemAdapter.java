package com.tudouni.makemoney.fragment.mall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.databinding.ItemMallRecommendGoodBinding;
import com.tudouni.makemoney.model.MallGoodItem;
import com.tudouni.makemoney.utils.ScreenUtils;
import com.tudouni.makemoney.view.BaseRecyclerViewBindingAdapter;

/**
 * Jaron.Wu
 * 2018/4/26
 */
public class RecommendGoodItemAdapter extends BaseRecyclerViewBindingAdapter<MallGoodItem> {
    RecommendGoodItemAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_mall_recommend_good;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemMallRecommendGoodBinding recommendGoodBinding = (ItemMallRecommendGoodBinding) holder.getBinding();
        Context context = recommendGoodBinding.ivGoodImg.getContext();

        ViewGroup.LayoutParams paramsImg = recommendGoodBinding.ivGoodImg.getLayoutParams();
        int height = (ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2px(context, 12)) / 2;
        paramsImg.height = paramsImg.width =  height;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height = paramsImg.width = height;
        recommendGoodBinding.rlImg.setLayoutParams(layoutParams);
        recommendGoodBinding.setItem(this.data.get(position));
        recommendGoodBinding.executePendingBindings();
    }
}
