package com.tudouni.makemoney.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.databinding.ActivitySearchBinding;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.NetConfig;
import com.tudouni.makemoney.utils.ToastUtil;

/**
 * Jaron.Wu
 * 2018/4/28
 */
public class SearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySearchBinding searchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        searchBinding.tvSearch.setOnClickListener(l -> {
            String keyWord = searchBinding.etSearch.getText().toString();
            if (keyWord.trim().equals("")){
                ToastUtil.show("搜索关键字不能为空~");
                return;
            }
            Intent intent = new Intent(this,H5Activity.class);
            intent.putExtra("url",NetConfig.getBaseTuDouNiH5Url() + "html/resultlist.html" + "?uid=" + MyApplication.getLoginUser().getUid()
                    + "&token=" + MyApplication.getLoginUser().getToken()
                    + "&unionid=" + MyApplication.getLoginUser().getUnionid()
                    + "&search=" + keyWord);
            startActivity(intent);
        });

        searchBinding.ivBack.setOnClickListener(l -> finish());
    }
}
