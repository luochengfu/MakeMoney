package com.tudouni.makemoney.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.model.MineMessage;
import com.tudouni.makemoney.model.RecommendTopicBean;
import com.tudouni.makemoney.utils.glideUtil.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息列表的适配器
 * Created by ZhangPeng on 2018/4/26
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<MineMessage> mDatas = new ArrayList<>();

    public MessageAdapter() {

    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.found_item_layout, viewGroup, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        MineMessage itemBean = mDatas.get(position);
//        GlideUtil.bindImage(holder.itemIV, itemBean.getImageUrl());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addData(List<MineMessage> data) {
        if (data.size() > 0) {
            mDatas.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemIV;

        public MessageViewHolder(View itemView) {
            super(itemView);
            this.itemIV = (ImageView) itemView.findViewById(R.id.found_item_iv);
        }
    }
}
