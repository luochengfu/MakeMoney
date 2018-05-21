package com.tudouni.makemoney.model;

import android.app.Activity;
import android.content.Intent;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.activity.H5Activity;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.NetConfig;

public class EarningsBean {
    private double income;
    private int orderCount;
    private double expectedIncome;
    private int incomeType;
    private long startTime;
    private long endTime;

    public int getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(int incomeType) {
        this.incomeType = incomeType;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public EarningsBean(double income, int orderCount, double expectedIncome) {
        this.income = income;
        this.orderCount = orderCount;
        this.expectedIncome = expectedIncome;
    }

    public EarningsBean(double income, int orderCount, double expectedIncome, int incomeType) {
        this.income = income;
        this.orderCount = orderCount;
        this.expectedIncome = expectedIncome;
        this.incomeType = incomeType;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public double getExpectedIncome() {
        return expectedIncome;
    }

    public void setExpectedIncome(double expectedIncome) {
        this.expectedIncome = expectedIncome;
    }

    @Override
    public String toString() {
        return "EarningsBean{" +
                "income=" + income +
                ", orderCount=" + orderCount +
                ", expectedIncome=" + expectedIncome +
                '}';
    }

    public interface IncomeType{
        int TYPE_TODAY = 1;
        int TYPE_YESTERDAY = 2;
        int TYPE_THIS_MONTH = 3;
        int TYPE_LAST_MONTH = 4;
    }

    public String getDesc(){
        switch (incomeType){
            case IncomeType.TYPE_TODAY:
                return "  今日收益";
            case IncomeType.TYPE_YESTERDAY:
                return "  昨日收益";
            case IncomeType.TYPE_THIS_MONTH:
                return "  本月收益";
            case IncomeType.TYPE_LAST_MONTH:
                return "  上月收益";
            default:
                return "";
        }
    }

    public int getIcon(){
        switch (incomeType){
            case IncomeType.TYPE_TODAY:
                return R.drawable.today_earnings;
            case IncomeType.TYPE_YESTERDAY:
                return R.drawable.yesterday_earnings;
            case IncomeType.TYPE_THIS_MONTH:
                return R.drawable.last_month_intro;
            case IncomeType.TYPE_LAST_MONTH:
                return R.drawable.last_month_earnings;
            default:
                return R.drawable.today_earnings;
        }
    }

    public String getFormatterIncome(double income){
        if (this.expectedIncome > 9999) {
            int w = (int) (this.expectedIncome / 10000);
            return "￥" + w + "万";
        }
        return "￥" + this.expectedIncome;
    }

    public void toOrderPage(){
        Activity currActivity = MyApplication.sCurrActivity;
        if (currActivity != null) {
            Intent intent = new Intent(currActivity,H5Activity.class);
            intent.putExtra("url", NetConfig.getBaseTuDouNiH5Url() + "mycenter/order.html?startTime=" + getStartTime() + "&endTime=" + getEndTime() + "&unionid=" + MyApplication.getLoginUser().getUnionid());
            currActivity.startActivity(intent);
        }
    }

}
