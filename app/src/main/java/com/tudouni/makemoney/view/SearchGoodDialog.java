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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.activity.H5Activity;
import com.tudouni.makemoney.activity.WebvewRefreshActivity;
import com.tudouni.makemoney.activity.search.SearchActivity;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.NetConfig;
import com.tudouni.makemoney.utils.ClipboardUtil;
import com.tudouni.makemoney.utils.SPUtil;
import com.tudouni.makemoney.utils.ScreenUtils;
import com.tudouni.makemoney.utils.StringUtil;
import com.tudouni.makemoney.utils.TDLog;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/2 0002.
 */

public class SearchGoodDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private LinearLayout parentLayout;
    private RelativeLayout imgLayout;
    private TextView tvMsg, mNoButton, mYesButton;
    private String searchStr;
    private Gson mGson;
    private List<String> mSearchhistory;

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

    public Context getDialogContext() {
        return mContext;
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
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(popWidth, RelativeLayout.LayoutParams.WRAP_CONTENT);
        parentLayout.setLayoutParams(params);

        int imgHeight = popWidth * 83 / 218;
        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(popWidth, imgHeight);
        imgLayout.setLayoutParams(imgParams);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvNo:
                MyApplication.mClipStr = "";
                ClipboardUtil.getInstance().copyText("", "");
                dismiss();
                break;
            case R.id.tvYes:
                try {
                    saveSearchHistory(searchStr);
//                    String url = NetConfig.getBaseTuDouNiH5Url() + "html/resultlist.html?unionid=" + MyApplication.getLoginUser().getUnionid()+"&search=" + URLEncoder.encode(searchStr,"utf-8").replaceAll("\\+",  "%20");
                    String url = NetConfig.getBaseTuDouNiH5Url() + "html/resultlist.html?unionid=" + MyApplication.getLoginUser().getUnionid() + "&search=" + searchStr;
                    Intent intent = new Intent(mContext, H5Activity.class);
                    intent.putExtra("titleStatus", 1);
                    intent.putExtra("url", url);
                    mContext.startActivity(intent);
                } catch (Exception e) {
                }
                ClipboardUtil.getInstance().copyText("", "");
                MyApplication.mClipStr = "";
                dismiss();
                break;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        ClipboardUtil.getInstance().copyText("", "");
        MyApplication.mClipStr = "";
    }

    /**
     * 保存搜索记录
     */
    private void saveSearchHistory(String search) {
        if (mGson == null)
            mGson = new Gson();

        if (mSearchhistory == null)
            mSearchhistory = new ArrayList<>();

        String searchHistoryJson = SPUtil.getString(mContext, "search_history");
        if (searchHistoryJson == null || StringUtil.isEmpty(searchHistoryJson)) {
            mSearchhistory.add(search);
            SPUtil.putString(mContext, "search_history", mGson.toJson(mSearchhistory));
            return;
        }
        //查看是否存在
        JsonParser parser = new JsonParser();
        JsonArray searchArr = parser.parse(searchHistoryJson).getAsJsonArray();
        for (JsonElement item : searchArr) {
            String itemStr = mGson.fromJson(item, String.class);
            if (search.equals(itemStr))
                return;
            mSearchhistory.add(itemStr);
        }

        mSearchhistory.add(search);
        SPUtil.putString(mContext, "search_history", mGson.toJson(mSearchhistory));

    }

}
