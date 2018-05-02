package com.tudouni.makemoney.activity.search;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.activity.BaseActivity;
import com.tudouni.makemoney.activity.H5Activity;
import com.tudouni.makemoney.databinding.ActivitySearchBinding;
import com.tudouni.makemoney.model.SearchHistory;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.NetConfig;
import com.tudouni.makemoney.utils.SPUtil;
import com.tudouni.makemoney.utils.StringUtil;
import com.tudouni.makemoney.utils.TDLog;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.viewModel.SearchViewModel;
import com.tudouni.makemoney.viewModel.VMResultCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Jaron.Wu
 * 2018/4/28
 */
public class SearchActivity extends BaseActivity {

    private List<SearchHistory> mSearchhistory = new ArrayList<>();
    private Gson mGson = new Gson();
    private SearchHistoryAdapter mSearchHistoryAdapter;
    private SearchViewModel mSearchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySearchBinding searchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        mSearchHistoryAdapter = new SearchHistoryAdapter(getLayoutInflater());
        mSearchHistoryAdapter.setOnItemClickListener((position, itemData) -> toSearchResultPage(itemData.getContent()));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        searchBinding.recycler.setLayoutManager(layoutManager);
        searchBinding.recycler.setAdapter(mSearchHistoryAdapter);

        mSearchViewModel = new SearchViewModel();

        loadHistory();
        searchBinding.tvSearch.setOnClickListener(l -> {
            String keyWord = searchBinding.etSearch.getText().toString();
            if (StringUtil.isEmpty(keyWord)){
                ToastUtil.show("搜索关键字不能为空~");
                return;
            }
            saveHistoryToService(keyWord);
            toSearchResultPage(keyWord);
        });

        searchBinding.ivBack.setOnClickListener(l -> finish());

    }

    private void saveHistoryToService(String searchKey) {
        if (mSearchViewModel != null) {
            mSearchViewModel.saveSearchHistoryToService("ssk",searchKey);
        }
    }

    private void loadHistory() {
        if (mSearchViewModel != null) {
            mSearchViewModel.loadSearchHistory(MyApplication.getLoginUser().getUnionid(), new VMResultCallback<List<SearchHistory>>() {
                @Override
                public void onSuccess(List<SearchHistory> data) {
                    if (mSearchHistoryAdapter != null) {
                        mSearchHistoryAdapter.replaceData(data);
                    }
                }

                @Override
                public void onFailure() {

                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadHistory();
    }

    private void toSearchResultPage(String keyWord) {
        Intent intent = new Intent(this,H5Activity.class);
        intent.putExtra("url", NetConfig.getBaseTuDouNiH5Url() + "html/resultlist.html" + "?uid=" + MyApplication.getLoginUser().getUid()
                + "&token=" + MyApplication.getLoginUser().getToken()
                + "&unionid=" + MyApplication.getLoginUser().getUnionid()
                + "&search=" + keyWord);
        startActivity(intent);
    }

    /**
     * 缓存历史记录到本地【弃用】
     * @param keyWord
     */
    private void cacheSearchHistory(String keyWord) {
//        for (String item : mSearchhistory) {
//            if (item.equals(keyWord)) {
//                return;
//            }
//        }
//        mSearchhistory.add(keyWord);
//        SPUtil.putString(this,"search_history",mGson.toJson(mSearchhistory));
    }

    /**
     * 解析本地历史记录【弃用】
     */
    private void parseSearchHistory() {
//        mSearchhistory.clear();
//        String searchHistoryJson = SPUtil.getString(this,"search_history");
//        TDLog.e(searchHistoryJson,"searchHistoryJson");
//        if (searchHistoryJson == null || StringUtil.isEmpty(searchHistoryJson)) {
//            return;
//        }
//        JsonParser parser = new JsonParser();
//        JsonArray searchArr = parser.parse(searchHistoryJson).getAsJsonArray();
//        for (JsonElement item : searchArr) {
//            String itemStr = mGson.fromJson(item,String.class);
//            mSearchhistory.add(itemStr);
//        }
//        if (mSearchHistoryAdapter != null) {
//            mSearchHistoryAdapter.replaceData(mSearchhistory);
//        }
//        TDLog.e(mSearchHistoryAdapter,mSearchhistory);
    }
}
