package com.tudouni.makemoney.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.model.Banner;
import com.tudouni.makemoney.utils.glideUtil.GlideUtil;
import com.zhouwei.mzbanner.holder.MZViewHolder;


/**
 * 首页BannerHolder
 * Created by YSL on 2017/10/31.
 */

public class MZBannerViewHolder implements MZViewHolder<Banner> {
    private ImageView mImageView;

    @Override
    public View createView(Context context) {
        // 返回页面布局文件
        View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
        mImageView = (ImageView) view.findViewById(R.id.banner_image);

        return view;
    }

    @Override
    public void onBind(Context context, int position, Banner data) {
        // 数据绑定
        GlideUtil.bindImage(mImageView,data.getImageUrl());
    }
}
