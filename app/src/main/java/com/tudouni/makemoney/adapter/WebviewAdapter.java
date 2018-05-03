package com.tudouni.makemoney.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.tudouni.makemoney.R;

/**
 * Created by Administrator on 2018/5/3 0003.
 */

public class WebviewAdapter extends RecyclerView.Adapter<WebviewAdapter.WebviewHolder>
{
    private String url;

    public WebviewAdapter(String url) {
        this.url = url;
    }

    @Override
    public WebviewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.refresh_webview_title_layout, viewGroup, false);
        return new WebviewAdapter.WebviewHolder(view);
    }

    @Override
    public void onBindViewHolder(WebviewHolder holder, int position) {
        holder.webview.loadUrl(url);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class WebviewHolder extends RecyclerView.ViewHolder
    {
        public WebView webview;;
        public WebviewHolder(View itemView) {
            super(itemView);
            this.webview = (WebView) itemView.findViewById(R.id.refresh_webView_title);
        }
    }
}
