package com.tudouni.makemoney.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.activity.realname.RealnameActivity2;
import com.tudouni.makemoney.activity.withdrawmoney.WithdrawMoneyActivity;
import com.tudouni.makemoney.adapter.EarningsRankAdapter;
import com.tudouni.makemoney.databinding.ActivityMyEarningsBinding;
import com.tudouni.makemoney.databinding.HeaderMyEarningsBinding;
import com.tudouni.makemoney.model.BindPayInfo;
import com.tudouni.makemoney.model.EarningsBean;
import com.tudouni.makemoney.model.EarningsRank;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.NetConfig;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.TDLog;
import com.tudouni.makemoney.view.VerifyDialog;
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


        mHeaderMyEarningsBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.header_my_earnings, null, false);
        mHeaderMyEarningsBinding.setEarnings(mEarningsViewModel);
        lRecyclerViewAdapter.addHeaderView(mHeaderMyEarningsBinding.getRoot());


        myEarningsBinding.rvEarnings.setAdapter(lRecyclerViewAdapter);
        myEarningsBinding.rvEarnings.setPullRefreshEnabled(false);
        myEarningsBinding.rvEarnings.setLoadMoreEnabled(false);


        mHeaderMyEarningsBinding.tvRule.setOnClickListener(l -> {
            toRulePage();

        });

        mHeaderMyEarningsBinding.tvRecord.setOnClickListener(l -> {
            //提现记录
            ForwardUtils.target(MyEarningsActivity.this, Constants.NATIVE_INCOME);
        });

        mHeaderMyEarningsBinding.tvWithdraw.setOnClickListener(l -> {
            if (!checkRealnameVerify()) {
                //去认证
                VerifyDialog dialog = new VerifyDialog(this);
                dialog.setTitle("您未实名认证");
                dialog.setMessage("为了您的资金安全请先实名认证");
                dialog.setNegativeText("取消");
                dialog.setPositiveText("去认证");
                dialog.setLinstener(new VerifyDialog.OnDialogClickLinstener() {
                    @Override
                    public void onPositiveClick() {
                        startActivity(new Intent(MyEarningsActivity.this, RealnameActivity2.class));
                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return;
            }
            checkBindAlipay();
        });

    }

    private void toWithdrawPage() {
        Intent intent = new Intent(this, WithdrawMoneyActivity.class);
        intent.putExtra("balance", mEarningsViewModel.getBalance());
        startActivity(intent);
    }

    private void toRulePage() {
        Intent intent = new Intent(this, H5Activity.class);
        intent.putExtra("url", NetConfig.getBaseTuDouNiH5Url() + "mycenter/drawrule.html");
        startActivity(intent);
    }

    private void checkBindAlipay() {
        CommonScene.checkBindAlipay(MyApplication.getLoginUser().getUid(), new BaseObserver<BindPayInfo>() {
            @Override
            public void OnSuccess(BindPayInfo bindPayInfo) {
                if (bindPayInfo != null && bindPayInfo.getAlipay() == 1 ) {
                    toWithdrawPage();
                }else{
                    //绑定
                    VerifyDialog dialog = new VerifyDialog(MyEarningsActivity.this);
                    dialog.setTitle("您未绑定支付宝");
                    dialog.setMessage("您未绑定提现支付宝帐号，请先绑定");
                    dialog.setNegativeText("取消");
                    dialog.setPositiveText("去绑定");
                    dialog.setLinstener(new VerifyDialog.OnDialogClickLinstener() {
                        @Override
                        public void onPositiveClick() {
                            startActivity(new Intent(MyEarningsActivity.this, AccountSecurityActivity.class));
                        }

                        @Override
                        public void onNegativeClick() {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }

            @Override
            public void OnFail(int code, String err) {
                TDLog.e(code,err);
            }
        });
    }

    private boolean checkRealnameVerify() {
        return MyApplication.getLoginUser().getRole().equals("1");
    }

    public interface IncomeDataCallback {
        void onSuccess(EarningsBean today, EarningsBean yesterday, EarningsBean thisMonth, EarningsBean lastMonth);
    }
}
