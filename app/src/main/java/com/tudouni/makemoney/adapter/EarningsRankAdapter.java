package com.tudouni.makemoney.adapter;

import android.view.LayoutInflater;
import android.view.View;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.databinding.ItemEarningsRankBinding;
import com.tudouni.makemoney.model.EarningsRank;
import com.tudouni.makemoney.view.BaseRecyclerViewBindingAdapter;

public class EarningsRankAdapter extends BaseRecyclerViewBindingAdapter<EarningsRank> {
    public EarningsRankAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    protected int getLayoutId() {

        return R.layout.item_earnings_rank;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder,position);
        ItemEarningsRankBinding itemEarningsRankBinding = (ItemEarningsRankBinding)holder.getBinding();
        itemEarningsRankBinding.setItem(this.data.get(position));
        itemEarningsRankBinding.executePendingBindings();
        itemEarningsRankBinding.tvRank.setVisibility(View.GONE);
        itemEarningsRankBinding.ivRank.setVisibility(View.VISIBLE);
        switch (position){
            case 0:
                itemEarningsRankBinding.ivRank.setImageResource(R.mipmap.earnings_first);
                break;
            case 1:
                itemEarningsRankBinding.ivRank.setImageResource(R.mipmap.earnings_second);
                break;
            case 2:
                itemEarningsRankBinding.ivRank.setImageResource(R.mipmap.earnings_third);
                break;
            default:
                itemEarningsRankBinding.tvRank.setVisibility(View.VISIBLE);
                itemEarningsRankBinding.ivRank.setVisibility(View.GONE);
                itemEarningsRankBinding.tvRank.setText(String.valueOf(position + 1));
        }

    }
}
