package com.tudouni.makemoney.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.activity.H5Activity;
import com.tudouni.makemoney.activity.WebvewRefreshActivity;
import com.tudouni.makemoney.activity.search.SearchActivity;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.NetConfig;
import com.tudouni.makemoney.utils.ClipboardUtil;
import com.tudouni.makemoney.utils.ScreenUtils;

import java.net.URLEncoder;

/**
 * Created by Administrator on 2018/5/2 0002.
 */

public class SearchGoodDialog extends Dialog implements View.OnClickListener
{
    private Context mContext;
    private LinearLayout parentLayout;
    private RelativeLayout imgLayout;
    private TextView tvMsg, mNoButton, mYesButton;
    private String searchStr;

    public SearchGoodDialog(Context context) {
        this(context, null, true);
    }

    public SearchGoodDialog(Context context, String msg) {
        this(context, msg, true);
    }

    public SearchGoodDialog(Context context, String search, boolean cancelable) {
        super(context, R.style.loading_dialog);
        this.mContext = context;
        this.searchStr = search;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.good_search_pop);

        initView();
        initDatas();
    }

    private void initView() {
        tvMsg = (TextView) findViewById(R.id.tvMsg);
        mNoButton = (TextView) findViewById(R.id.tvNo);
        mYesButton = (TextView) findViewById(R.id.tvYes);
        parentLayout = (LinearLayout) findViewById(R.id.good_search_pop);
        imgLayout = (RelativeLayout) findViewById(R.id.good_search_pop_rl);
    }

    private void initDatas() {
        tvMsg.setText(searchStr);
        mNoButton.setOnClickListener(this);
        mYesButton.setOnClickListener(this);


        int popWidth = (ScreenUtils.getScreenWidth(mContext) * 7) / 10;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(popWidth,RelativeLayout.LayoutParams.WRAP_CONTENT);
        parentLayout.setLayoutParams(params);

        int imgHeight = popWidth * 83 / 218;
        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(popWidth,imgHeight);
        imgLayout.setLayoutParams(imgParams);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvNo:
                MyApplication.mClipStr = "";
                ClipboardUtil.getInstance().copyText("","");
                dismiss();
                break;
            case R.id.tvYes:
                try {
                    String url = NetConfig.getBaseTuDouNiH5Url() + "html/resultlist.html?unionid=" + MyApplication.getLoginUser().getUnionid()+"&search=" + URLEncoder.encode(searchStr,"utf-8").replaceAll("\\+",  "%20");
                    Intent intent = new Intent(mContext, H5Activity.class);
                    intent.putExtra("titleStatus",1);
                    intent.putExtra("url", url);
                    mContext.startActivity(intent);
                } catch (Exception e){}
                ClipboardUtil.getInstance().copyText("","");
                MyApplication.mClipStr = "";
                dismiss();
                break;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        ClipboardUtil.getInstance().copyText("","");
        MyApplication.mClipStr = "";
    }

}
