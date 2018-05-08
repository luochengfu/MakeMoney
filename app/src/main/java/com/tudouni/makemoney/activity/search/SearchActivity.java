package com.tudouni.makemoney.activity.search;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.activity.BaseActivity;
import com.tudouni.makemoney.activity.H5Activity;
import com.tudouni.makemoney.databinding.ActivitySearchBinding;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.NetConfig;
import com.tudouni.makemoney.utils.SPUtil;
import com.tudouni.makemoney.utils.StringUtil;
import com.tudouni.makemoney.utils.TDLog;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.viewModel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Jaron.Wu
 * 2018/4/28
 */
public class SearchActivity extends BaseActivity {

    private List<String> mSearchhistory = new ArrayList<>();
    private Gson mGson = new Gson();
    private SearchHistoryAdapter mSearchHistoryAdapter;
    private SearchViewModel mSearchViewModel;
    private ActivitySearchBinding mSearchBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        mSearchHistoryAdapter = new SearchHistoryAdapter(getLayoutInflater());
        mSearchHistoryAdapter.setOnItemClickListener((position, itemData) -> toSearchResultPage(itemData));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mSearchBinding.recycler.setLayoutManager(layoutManager);
        mSearchBinding.recycler.setAdapter(mSearchHistoryAdapter);

        mSearchViewModel = new SearchViewModel();

        loadHistory();
        parseSearchHistory();
        //搜索
        mSearchBinding.tvSearch.setOnClickListener(l -> search());

        //软键盘搜索
        mSearchBinding.etSearch.setOnEditorActionListener((v,actionId,event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
               search();
               return true;
            }
            return false;
        });

        //清除搜索历史记录
        mSearchBinding.ivClearSearch.setOnClickListener(l -> clearSearchHistory());

        mSearchBinding.ivBack.setOnClickListener(l -> finish());

    }

    private void search() {
        String keyWord = mSearchBinding.etSearch.getText().toString();
        if (StringUtil.isEmpty(keyWord)) {
            ToastUtil.show("搜索关键字不能为空~");
            return;
        }
        cacheSearchHistory(keyWord);
        saveHistoryToService(keyWord);
        toSearchResultPage(keyWord);
    }

    private void saveHistoryToService(String searchKey) {
        if (mSearchViewModel != null) {
            mSearchViewModel.saveSearchHistoryToService("ssk", searchKey);
        }
    }

    private void loadHistory() {
//        if (mSearchViewModel != null) {
//            mSearchViewModel.loadSearchHistory(MyApplication.getLoginUser().getUnionid(), new VMResultCallback<List<SearchHistory>>() {
//                @Override
//                public void onSuccess(List<SearchHistory> data) {
//                    if (mSearchHistoryAdapter != null) {
//                        mSearchHistoryAdapter.replaceData(data);
//                    }
//                }
//
//                @Override
//                public void onFailure() {
//
//                }
//            });
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        parseSearchHistory();
    }

    private void toSearchResultPage(String keyWord) {
        hideSoftBoard(mSearchBinding.etSearch);
        Intent intent = new Intent(this, H5Activity.class);
        intent.putExtra("url", NetConfig.getBaseTuDouNiH5Url() + "html/resultlist.html" + "?uid=" + MyApplication.getLoginUser().getUid()
                + "&token=" + MyApplication.getLoginUser().getToken()
                + "&unionid=" + MyApplication.getLoginUser().getUnionid()
                + "&search=" + keyWord);
        startActivity(intent);
    }

    private void hideSoftBoard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen) {
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    public EditText getmSearchBinding() {
        return mSearchBinding.etSearch;
    }

    /**
     * 缓存历史记录到本地【弃用】
     *
     * @param keyWord
     */
    private void cacheSearchHistory(String keyWord) {
        for (String item : mSearchhistory) {
            if (item.equals(keyWord)) {
                return;
            }
        }
        mSearchhistory.add(keyWord);
        SPUtil.putString(this, "search_history", mGson.toJson(mSearchhistory));
    }

    /**
     * 解析本地历史记录【弃用】
     */
    private void parseSearchHistory() {
        mSearchhistory.clear();
        String searchHistoryJson = SPUtil.getString(this, "search_history");
        TDLog.e(searchHistoryJson, "searchHistoryJson");
        if (searchHistoryJson == null || StringUtil.isEmpty(searchHistoryJson)) {
            return;
        }
        JsonParser parser = new JsonParser();
        JsonArray searchArr = parser.parse(searchHistoryJson).getAsJsonArray();
        for (JsonElement item : searchArr) {
            String itemStr = mGson.fromJson(item, String.class);
            mSearchhistory.add(itemStr);
        }
        if (mSearchHistoryAdapter != null) {
            mSearchHistoryAdapter.replaceData(mSearchhistory);
        }
        TDLog.e(mSearchHistoryAdapter, mSearchhistory);
    }

    private void clearSearchHistory() {
        mSearchhistory.clear();
        if (mSearchHistoryAdapter != null) {
            mSearchHistoryAdapter.replaceData(mSearchhistory);
        }
        SPUtil.putString(getApplicationContext(), "search_history", "[]");
    }
}
