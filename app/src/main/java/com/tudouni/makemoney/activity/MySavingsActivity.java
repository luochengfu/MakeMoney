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
import com.tudouni.makemoney.viewModel.SavingsViewModel;

import java.util.List;

public class MySavingsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySavingsBinding savingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_savings);

        EarningsRankAdapter earningsRankAdapter = new EarningsRankAdapter(getLayoutInflater());
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(earningsRankAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        savingsBinding.lrvSavings.setLayoutManager(linearLayoutManager);
        savingsBinding.lrvSavings.setAdapter(lRecyclerViewAdapter);

        HeaderSavingsBinding headerSavingsBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.header_savings,null,false);
        lRecyclerViewAdapter.addHeaderView(headerSavingsBinding.getRoot());


        CommonScene.loadSavingsRank(100, new BaseObserver<List<EarningsRank>>() {
            @Override
            public void OnSuccess(List<EarningsRank> earningsRanks) {
                earningsRankAdapter.replaceData(earningsRanks);
            }
        });

        SavingsViewModel savingsViewModel = new SavingsViewModel();
        savingsViewModel.loadSavings();

        headerSavingsBinding.setSavings(savingsViewModel);


    }
}
