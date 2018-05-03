package com.tudouni.makemoney.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.NetConfig;
import com.tudouni.makemoney.utils.ClipboardUtil;
import com.tudouni.makemoney.utils.ScreenUtils;
import com.umeng.analytics.MobclickAgent;

import java.net.URLEncoder;

/**
 * Created by Administrator on 2018/3/22 0022.
 */

public class SearchGoodActivity extends FragmentActivity implements View.OnClickListener
{
    private LinearLayout parentLayout;
    private RelativeLayout imgLayout;
    private TextView tvMsg, mNoButton, mYesButton;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.good_search_pop);

        url = getIntent().getStringExtra("url");

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
        tvMsg.setText(url);
        mNoButton.setOnClickListener(this);
        mYesButton.setOnClickListener(this);


        int popWidth = (ScreenUtils.getScreenWidth(this) * 7) / 10;
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
                finish();
                break;
            case R.id.tvYes:
                MobclickAgent.onEvent(SearchGoodActivity.this, "mall_clicksch");
                try {
                    String url = NetConfig.getBaseTuDouNiH5Url() + "/shopHome/search-ending.html?unionid=" + MyApplication.getLoginUser().getUnionid()+"&search=" + URLEncoder.encode(MyApplication.mClipStr,"utf-8").replaceAll("\\+",  "%20");
                    Intent intent = new Intent(this, WebvewRefreshActivity.class);
                    intent.putExtra("titleStatus",1);
                    intent.putExtra("url", url);
                    startActivity(intent);
//                   startActivity(H5Activity.newIntent(this, NetConfig.getShoppingMailUrl() + "/shopHome/search-ending.html?unionid=" + App.getLoginUser().getUnionid()+"&search=" + URLEncoder.encode(App.mClipStr,"utf-8"), "",1));
                } catch (Exception e){}
                ClipboardUtil.getInstance().copyText("","");
                MyApplication.mClipStr = "";
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            MyApplication.mClipStr = "";
            finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void finish() {
        super.finish();
        ClipboardUtil.getInstance().copyText("","");
        MyApplication.mClipStr = "";
    }
}
