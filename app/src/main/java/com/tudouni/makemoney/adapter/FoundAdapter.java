package com.tudouni.makemoney.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.model.RecommendTopicBean;
import com.tudouni.makemoney.utils.CornersTransform;
import com.tudouni.makemoney.utils.ScreenUtils;
import com.tudouni.makemoney.utils.glideUtil.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/24 0024.
 */

public class FoundAdapter extends RecyclerView.Adapter<FoundAdapter.FoundViewHolder>
{
    private Context mContext;
    private List<RecommendTopicBean> mDatas = new ArrayList<>();
    private int width, height;

    public FoundAdapter(Context context) {
        mContext = context;
        width = ScreenUtils.getScreenWidth(context);
        height = width * 24 / 75;
    }

    @Override
    public FoundViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.found_item_layout, viewGroup, false);
        return new FoundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoundViewHolder holder, int position) {
        RecommendTopicBean itemBean = mDatas.get(position);
        holder.itemIV.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,height));
        GlideUtil.getInstance().loadImage(mContext,itemBean.getImageUrl(),holder.itemIV,ScreenUtils.dp2px(mContext,6),R.mipmap.found_default_banner);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addData(List<RecommendTopicBean> data) {
        if (data.size() > 0) {
            mDatas.addAll(data);
            notifyDataSetChanged();
        }
    }

    public String getUrl(int position) {
        return mDatas.get(position).getUrl() + "?title=" + mDatas.get(position).getTitle();
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
