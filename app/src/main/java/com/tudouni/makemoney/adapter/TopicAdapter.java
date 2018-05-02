package com.tudouni.makemoney.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.interfaces.IActionListener;
import com.tudouni.makemoney.interfaces.IItemClickListener;
import com.tudouni.makemoney.model.FoundTopicBean;
import com.tudouni.makemoney.model.RecommendTopicBean;
import com.tudouni.makemoney.utils.glideUtil.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/25 0025.
 */

public class TopicAdapter extends RecyclerView.Adapter<FoundAdapter.FoundViewHolder>
{
    private Context mContext;
    private List<FoundTopicBean> mDatas = new ArrayList<>();
    private IItemClickListener mItemClickListener;

    public TopicAdapter(Context context) {
        mContext = context;
    }

    @Override
    public FoundAdapter.FoundViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.found_topic_item_layout, viewGroup, false);
        return new FoundAdapter.FoundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoundAdapter.FoundViewHolder holder, int position) {
        FoundTopicBean itemBean = mDatas.get(position);
        GlideUtil.getInstance().loadImage(mContext,itemBean.getImageUrl(),holder.itemIV,R.mipmap.found_default_banner);
        holder.itemIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mItemClickListener) {
                    String url = itemBean.getUrl() + "?title="+itemBean.getTitle();
                    if(itemBean.getHasSubTopic() == 1) {
                        url = url + "&id=" + itemBean.getId();
                    }
                    mItemClickListener.action(url);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addData(List<FoundTopicBean> data) {
        mDatas.clear();
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

    public void setItemClickListener(IItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}

