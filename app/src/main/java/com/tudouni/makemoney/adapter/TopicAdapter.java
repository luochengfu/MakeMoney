package com.tudouni.makemoney.adapter;

import android.content.Context;
import android.graphics.Outline;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.interfaces.IActionListener;
import com.tudouni.makemoney.interfaces.IItemClickListener;
import com.tudouni.makemoney.model.FoundTopicBean;
import com.tudouni.makemoney.model.RecommendTopicBean;
import com.tudouni.makemoney.utils.ScreenUtils;
import com.tudouni.makemoney.utils.glideUtil.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/25 0025.
 */

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.FoundViewHolder>
{
    private Context mContext;
    private List<FoundTopicBean> mDatas = new ArrayList<>();
    private IItemClickListener mItemClickListener;
    private int width, heigh;

    public TopicAdapter(Context context) {
        mContext = context;
        width = (ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2px(context,20)) / 2;
        heigh = width * 63 / 158;
    }

    @Override
    public FoundViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.found_topic_item_layout, viewGroup, false);

        return new FoundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoundViewHolder holder, int position) {
        FoundTopicBean itemBean = mDatas.get(position);
        holder.itemIV.setLayoutParams(new FrameLayout.LayoutParams(width ,heigh));

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
        public CardView mCardView;
        public FoundViewHolder(View itemView) {
            super(itemView);
            this.itemIV = (ImageView) itemView.findViewById(R.id.found_item_iv);
            this.mCardView = (CardView) itemView.findViewById(R.id.cardview_layout);
        }
    }

    public void setItemClickListener(IItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}

