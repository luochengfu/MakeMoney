package com.tudouni.makemoney.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.model.FoundTopicBean;
import com.tudouni.makemoney.utils.glideUtil.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/24 0024.
 */

public class FoundTopicAdapter extends BaseAdapter
{
    private LayoutInflater mInflater;
    private List<FoundTopicBean> mDatas = new ArrayList<>();

    public FoundTopicAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(null == convertView) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.found_topic_item_layout,null);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.found_item_iv);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        FoundTopicBean bean = mDatas.get(position);
        GlideUtil.bindImage(viewHolder.icon,bean.getImageUrl());
        return convertView;
    }

    public void addDatas(List<FoundTopicBean> data) {
        mDatas.clear();
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    class ViewHolder{
        public ImageView icon;
    }
}
