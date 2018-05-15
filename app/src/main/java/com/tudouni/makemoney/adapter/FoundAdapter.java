package com.tudouni.makemoney.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.model.NineRecommendBean;
import com.tudouni.makemoney.model.NineRecommendGoodsBean;
import com.tudouni.makemoney.model.RecommendTopicBean;
import com.tudouni.makemoney.utils.CornersTransform;
import com.tudouni.makemoney.utils.ScreenUtils;
import com.tudouni.makemoney.utils.TimeUtil;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.utils.TuDouTextUtil;
import com.tudouni.makemoney.utils.glideUtil.GlideUtil;
import com.tudouni.makemoney.view.NineRevommendGoodsLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 九宫格推荐适配器
 * Created by Zhangpeng on 2018/5/15
 */

public class FoundAdapter extends RecyclerView.Adapter<FoundAdapter.FoundViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<NineRecommendBean> mDatas = new ArrayList<>();
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
        NineRecommendBean itemBean = mDatas.get(position);
        GlideUtil.getInstance().loadImage(mContext, itemBean.getIcon(), holder.mImgIcon, R.mipmap.ic_launcher);
        TuDouTextUtil.setTextToTextView(holder.mTvTitle, itemBean.getTitle());
        TuDouTextUtil.setTextToTextView(holder.mTvTime,
                TimeUtil.formatDisplayTime(TimeUtil.longToString(itemBean.getCreateTime(), "yyyy-MM-dd HH:mm:ss"),
                        "yyyy-MM-dd HH:mm:ss"));
        TuDouTextUtil.setTextToTextView(holder.mTvRecommendation, itemBean.getText());
        holder.mContentLy.setData(itemBean.getList());
        holder.mShareLy.setTag(position);
        holder.mShareLy.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addData(List<NineRecommendBean> data) {
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

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        ToastUtil.show("点击了分享~" + position);

    }

    public static class FoundViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImgIcon;
        public TextView mTvTitle;
        public TextView mTvTime;//时间
        public View mShareLy;//分享
        public TextView mTvRecommendation;//九宫格介绍
        public RecyclerView mRvNineList;//商品列表
        public NineRevommendGoodsLayout mContentLy;


        public FoundViewHolder(View itemView) {
            super(itemView);
            this.mImgIcon = (ImageView) itemView.findViewById(R.id.img_icon);
            this.mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            this.mTvTime = (TextView) itemView.findViewById(R.id.tv_time);
            this.mShareLy = itemView.findViewById(R.id.share_ly);
            this.mTvRecommendation = (TextView) itemView.findViewById(R.id.tv_recommendation);
            this.mRvNineList = (RecyclerView) itemView.findViewById(R.id.rv_nine_list);
            this.mContentLy = (NineRevommendGoodsLayout) itemView.findViewById(R.id.content_ly);
        }
    }
}
