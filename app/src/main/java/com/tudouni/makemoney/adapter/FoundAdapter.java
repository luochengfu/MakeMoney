package com.tudouni.makemoney.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.model.FoundItemBean;
import com.tudouni.makemoney.utils.glideUtil.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/24 0024.
 */

public class FoundAdapter extends RecyclerView.Adapter<FoundAdapter.FoundViewHolder>
{
    private List<FoundItemBean> mDatas = new ArrayList<>();

    @Override
    public FoundViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.found_item_layout, viewGroup, false);
        return new FoundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoundViewHolder holder, int position) {
        FoundItemBean itemBean = mDatas.get(position);
        GlideUtil.bindImage(holder.itemIV,itemBean.getImg());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addData(List<FoundItemBean> data) {
        if (data.size() > 0) {
            mDatas.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void removeData(int position) {
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public static class FoundViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView itemIV;
        public FoundViewHolder(View itemView) {
            super(itemView);
            this.itemIV = (ImageView) itemView.findViewById(R.id.found_item_iv);
        }
    }
}
