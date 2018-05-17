package com.tudouni.makemoney.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.adapter.EarningsRankAdapter;
import com.tudouni.makemoney.databinding.ActivitySavingsBinding;
import com.tudouni.makemoney.databinding.HeaderSavingsBinding;
import com.tudouni.makemoney.model.EarningsRank;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;

import java.util.List;

public class MySavingsActivity extends BaseActivity {
    private ActivitySavingsBinding mSavingsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_savings);

        EarningsRankAdapter earningsRankAdapter = new EarningsRankAdapter(getLayoutInflater());
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(earningsRankAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        mSavingsBinding.lrvSavings.setLayoutManager(linearLayoutManager);
        mSavingsBinding.lrvSavings.setAdapter(lRecyclerViewAdapter);

        HeaderSavingsBinding headerSavingsBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.header_savings,null,false);
        lRecyclerViewAdapter.addHeaderView(headerSavingsBinding.getRoot());


        CommonScene.loadSavingsRank(100, new BaseObserver<List<EarningsRank>>() {
            @Override
            public void OnSuccess(List<EarningsRank> earningsRanks) {
                earningsRankAdapter.replaceData(earningsRanks);
            }
        });


    }
}
