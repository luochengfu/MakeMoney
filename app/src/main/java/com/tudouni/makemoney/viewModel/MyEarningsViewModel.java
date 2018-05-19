package com.tudouni.makemoney.viewModel;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableField;

import com.tudouni.makemoney.activity.H5Activity;
import com.tudouni.makemoney.activity.MyEarningsActivity;
import com.tudouni.makemoney.activity.withdrawmoney.WithdrawMoneyActivity;
import com.tudouni.makemoney.model.EarningsBean;
import com.tudouni.makemoney.model.EarningsRank;
import com.tudouni.makemoney.model.MyEarnings;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.NetConfig;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.TDLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyEarningsViewModel extends LoadingViewModel {

    public ObservableField<MyEarnings> earnings = new ObservableField<>();


    public void loadIncomeProfile(MyEarningsActivity.IncomeDataCallback callback) {
        CommonScene.loadIncomeProfile(MyApplication.getLoginUser().getUid(), new BaseObserver<MyEarnings>() {
            @Override
            public void OnSuccess(MyEarnings myEarnings) {
                Calendar calendar1 = Calendar.getInstance();
                int currDayOfMonth = calendar1.get(Calendar.DAY_OF_MONTH);
                int currMonth = calendar1.get(Calendar.MONTH);
                int currYear = calendar1.get(Calendar.YEAR);
                calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH),
                        0, 0, 0);
                long todayStart = calendar1.getTimeInMillis() / 1000;
                long currentSecond = System.currentTimeMillis() / 1000;
                TDLog.e(calendar1.getTime(), todayStart, currentSecond, currDayOfMonth, currMonth);
                long yesterdayStart = todayStart - 24 * 60 * 60;
                long yesterdayEnd = todayStart - 1;
                long currMonthStart = todayStart - (currDayOfMonth - 1) * 24 * 60 * 60;
                long currMonthEnd = currentSecond;
                if (currMonth > 0) {
                    calendar1.set(Calendar.MONTH, currMonth - 1);
                } else {
                    calendar1.set(Calendar.MONTH, 11);
                    calendar1.set(Calendar.YEAR, currYear - 1);
                }
                int maxDayOfThisMonth = calendar1.getActualMaximum(Calendar.DATE);
                long lastMonthStart = currMonthStart - maxDayOfThisMonth * 24 * 60 * 60;
                long lastMonthEnd = currMonthStart - 1;

                TDLog.e(maxDayOfThisMonth, transForDate(todayStart), transForDate(currentSecond), transForDate(yesterdayStart), transForDate(yesterdayEnd), transForDate(currMonthStart), transForDate(currMonthEnd), transForDate(lastMonthStart), transForDate(lastMonthEnd));


                EarningsBean todayEarnings = new EarningsBean(myEarnings.getTodayIncome(), myEarnings.getTodayCount(), myEarnings.getTodayExpectedIncome(), EarningsBean.IncomeType.TYPE_TODAY);
                todayEarnings.setStartTime(todayStart);
                todayEarnings.setEndTime(currentSecond);

                EarningsBean yesterdayEarnings = new EarningsBean(myEarnings.getYesterdayIncome(), myEarnings.getYesterdayCount(), myEarnings.getYesterdayExpectedIncome(), EarningsBean.IncomeType.TYPE_YESTERDAY);
                yesterdayEarnings.setStartTime(yesterdayStart);
                yesterdayEarnings.setEndTime(yesterdayEnd);

                EarningsBean thisMonthEarnings = new EarningsBean(myEarnings.getLastMonthIncome(), myEarnings.getLastMonthCount(), myEarnings.getLastMonthExpectedIncome(), EarningsBean.IncomeType.TYPE_THIS_MONTH);
                thisMonthEarnings.setStartTime(currMonthStart);
                thisMonthEarnings.setEndTime(currMonthEnd);

                EarningsBean lastMonthEarnings = new EarningsBean(myEarnings.getLastMonthIncome(), myEarnings.getLastMonthCount(), myEarnings.getLastMonthExpectedIncome(), EarningsBean.IncomeType.TYPE_LAST_MONTH);
                lastMonthEarnings.setStartTime(lastMonthStart);
                lastMonthEarnings.setEndTime(lastMonthEnd);

                earnings.set(myEarnings);
                if (callback != null) {
                    callback.onSuccess(todayEarnings, yesterdayEarnings, thisMonthEarnings, lastMonthEarnings);
                }


            }
        });
    }


    public Date transForDate(long ms) {
        long msl = ms * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date temp = null;
        try {
            String str = sdf.format(msl);
            temp = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public void loadRankData(VMResultCallback<List<EarningsRank>> callback) {
        CommonScene.loadEarningsRank(100, new BaseObserver<List<EarningsRank>>() {
            @Override
            public void OnSuccess(List<EarningsRank> myEarnings) {
                if (callback != null) {
                    callback.onSuccess(myEarnings);
                }
            }
        });
    }

    public double getBalance() {
        return earnings.get().getBalance();
    }

    public void finish() {
        Activity currActivity = MyApplication.sCurrActivity;
        if (currActivity != null) {
            currActivity.finish();
        }
    }


}
