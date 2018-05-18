package com.tudouni.makemoney.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.adapter.EarningsRankAdapter;
import com.tudouni.makemoney.databinding.ActivityMyEarningsBinding;
import com.tudouni.makemoney.databinding.HeaderMyEarningsBinding;
import com.tudouni.makemoney.model.EarningsBean;
import com.tudouni.makemoney.model.EarningsRank;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.NetConfig;
import com.tudouni.makemoney.utils.Constants;
import com.tudouni.makemoney.utils.ForwardUtils;
import com.tudouni.makemoney.viewModel.MyEarningsViewModel;
import com.tudouni.makemoney.viewModel.VMResultCallback;

import java.util.List;

public class MyEarningsActivity extends BaseActivity {

    private ActivityMyEarningsBinding myEarningsBinding;
    private EarningsRankAdapter mEarningsRankAdapter;
    HeaderMyEarningsBinding mHeaderMyEarningsBinding;
    private MyEarningsViewModel mEarningsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myEarningsBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_earnings);
        mEarningsViewModel = new MyEarningsViewModel();

        mEarningsViewModel.loadIncomeProfile((today, yesterday, thisMonth, lastMonth) -> {
            if (mHeaderMyEarningsBinding != null) {
                mHeaderMyEarningsBinding.layoutTodayEarnings.setIncome(today);
                mHeaderMyEarningsBinding.layoutYesterdayEarnings.setIncome(yesterday);
                mHeaderMyEarningsBinding.layoutThismonthEarnings.setIncome(thisMonth);
                mHeaderMyEarningsBinding.layoutLastmonthEarnings.setIncome(lastMonth);
            }
        });

        mEarningsViewModel.loadRankData(new VMResultCallback<List<EarningsRank>>() {
            @Override
            public void onSuccess(List<EarningsRank> data) {
                if (mEarningsRankAdapter != null) {
                    mEarningsRankAdapter.replaceData(data);
                }
            }

            @Override
            public void onFailure() {

            }
        });

        mEarningsRankAdapter = new EarningsRankAdapter(getLayoutInflater());
        initEarningsRankRecyclerView();


    }

    private void initEarningsRankRecyclerView() {
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(mEarningsRankAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        myEarningsBinding.rvEarnings.setLayoutManager(manager);


        mHeaderMyEarningsBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.header_my_earnings,null,false);
        mHeaderMyEarningsBinding.setEarnings(mEarningsViewModel);
        lRecyclerViewAdapter.addHeaderView(mHeaderMyEarningsBinding.getRoot());



        myEarningsBinding.rvEarnings.setAdapter(lRecyclerViewAdapter);
        myEarningsBinding.rvEarnings.setPullRefreshEnabled(false);
        myEarningsBinding.rvEarnings.setLoadMoreEnabled(false);


        mHeaderMyEarningsBinding.tvRule.setOnClickListener(l -> {
            Intent intent = new Intent(this,H5Activity.class);
            intent.putExtra("url", NetConfig.getBaseTuDouNiH5Url() + "mycenter/drawrule.html");
            startActivity(intent);
        });

        mHeaderMyEarningsBinding.tvRecord.setOnClickListener(l -> {
            //提现记录
            ForwardUtils.target(MyEarningsActivity.this, Constants.NATIVE_INCOME);
        });

    }

    public interface IncomeDataCallback{
        void onSuccess(EarningsBean today,EarningsBean yesterday,EarningsBean thisMonth,EarningsBean lastMonth);
    }
}
