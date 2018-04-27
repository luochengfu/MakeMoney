package com.tudouni.makemoney.adapter;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.model.MineMessage;
import com.tudouni.makemoney.model.RecommendTopicBean;
import com.tudouni.makemoney.utils.TimeUtil;
import com.tudouni.makemoney.utils.TuDouTextUtil;
import com.tudouni.makemoney.utils.glideUtil.GlideUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息列表的适配器
 * Created by ZhangPeng on 2018/4/26
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<MineMessage> mDatas = new ArrayList<>();
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public MessageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message, viewGroup, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        MineMessage itemBean = mDatas.get(position);
        holder.mIcon.setImageResource((itemBean.getType() == 2) ? R.mipmap.ic_note_system : R.mipmap.ic_note_up_leve);
        TuDouTextUtil.setTextToTextView(holder.mTvTitle, itemBean.getTitle());
        TuDouTextUtil.setTextToTextView(holder.mMessageContent, itemBean.getContent());
        TuDouTextUtil.setTextToTextView(holder.mTvTime, TimeUtil.getTimeFromMillisecond(itemBean.getTime()));

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }
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
        public ImageView mIcon;
        public TextView mTvTitle;
        public TextView mTvTime;
        public TextView mMessageContent;

        public MessageViewHolder(View itemView) {
            super(itemView);
            this.mIcon = (ImageView) itemView.findViewById(R.id.iteam_icon);
            this.mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            this.mTvTime = (TextView) itemView.findViewById(R.id.tv_date);
            this.mMessageContent = (TextView) itemView.findViewById(R.id.tv_message_content);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }
}
