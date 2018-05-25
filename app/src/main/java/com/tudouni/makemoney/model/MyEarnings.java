package com.tudouni.makemoney.model;

import android.app.Activity;
import android.content.Intent;
import android.databinding.BaseObservable;

import com.tudouni.makemoney.activity.H5Activity;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.NetConfig;

public class MyEarnings extends BaseObservable{
    /**
     * 结余
     */
    private double balance;

    /**
     * 省钱
     */
    private double economizes;

    /**
     * 总收益(包括预估，结算)
     */
    private double income;

    /**
     * 总预估收益
     */
    private double estimateIncome;

    /**
     * 总结算收益
     */
    private double settledIncome;

    /**
     * 总订单
     */
    private int orders;

    /**
     * 总预估订单
     */
    private int estimateOrders;

    /**
     * 总结算订单
     */
    private int settledOrders;

    /**
     * 等级
     */
    private String series;

    /**
     * 今日收益
     */
    private double todayIncome;

    /**
     * 今日预估收益
     */
    private double todayExpectedIncome;

    /**
     * 今日结算订单
     */
    private int todayCount;

    /**
     * 今日预估订单
     */
    private int todayExpectedCount;

    /**
     * 昨日收益
     */
    private double yesterdayIncome;

    /**
     * 昨日预估收益
     */
    private double yesterdayExpectedIncome;

    /**
     * 昨日结算订单
     */
    private int yesterdayCount;

    /**
     * 昨日预估订单
     */
    private int yesterdayExpectedCount;

    /**
     * 本月收益
     */
    private double thisMonthIncome;

    /**
     * 本月预估收益
     */
    private double thisMonthExpectedIncome;

    /**
     * 本月结算订单
     */
    private int thisMonthCount;
    /**
     * 本月预估订单
     */
    private int thisMonthExpectedCount;

    /**
     * 上月收益
     */
    private double lastMonthIncome;

    /**
     * 上月预估收益
     */
    private double lastMonthExpectedIncome;

    /**
     * 上月结算订单
     */
    private int lastMonthCount;
    /**
     * 上月预估订单
     */
    private int lastMonthExpectedCount;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getEconomizes() {
        return economizes;
    }

    public void setEconomizes(double economizes) {
        this.economizes = economizes;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getEstimateIncome() {
        return estimateIncome;
    }

    public void setEstimateIncome(double estimateIncome) {
        this.estimateIncome = estimateIncome;
    }

    public double getSettledIncome() {
        return settledIncome;
    }

    public void setSettledIncome(double settledIncome) {
        this.settledIncome = settledIncome;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public int getEstimateOrders() {
        return estimateOrders;
    }

    public void setEstimateOrders(int estimateOrders) {
        this.estimateOrders = estimateOrders;
    }

    public int getSettledOrders() {
        return settledOrders;
    }

    public void setSettledOrders(int settledOrders) {
        this.settledOrders = settledOrders;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public double getTodayIncome() {
        return todayIncome;
    }

    public void setTodayIncome(double todayIncome) {
        this.todayIncome = todayIncome;
    }

    public double getTodayExpectedIncome() {
        return todayExpectedIncome;
    }

    public void setTodayExpectedIncome(double todayExpectedIncome) {
        this.todayExpectedIncome = todayExpectedIncome;
    }

    public int getTodayCount() {
        return todayCount;
    }

    public void setTodayCount(int todayCount) {
        this.todayCount = todayCount;
    }

    public int getTodayExpectedCount() {
        return todayExpectedCount;
    }

    public void setTodayExpectedCount(int todayExpectedCount) {
        this.todayExpectedCount = todayExpectedCount;
    }

    public double getYesterdayIncome() {
        return yesterdayIncome;
    }

    public void setYesterdayIncome(double yesterdayIncome) {
        this.yesterdayIncome = yesterdayIncome;
    }

    public double getYesterdayExpectedIncome() {
        return yesterdayExpectedIncome;
    }

    public void setYesterdayExpectedIncome(double yesterdayExpectedIncome) {
        this.yesterdayExpectedIncome = yesterdayExpectedIncome;
    }

    public int getYesterdayCount() {
        return yesterdayCount;
    }

    public void setYesterdayCount(int yesterdayCount) {
        this.yesterdayCount = yesterdayCount;
    }

    public int getYesterdayExpectedCount() {
        return yesterdayExpectedCount;
    }

    public void setYesterdayExpectedCount(int yesterdayExpectedCount) {
        this.yesterdayExpectedCount = yesterdayExpectedCount;
    }

    public double getThisMonthIncome() {
        return thisMonthIncome;
    }

    public void setThisMonthIncome(double thisMonthIncome) {
        this.thisMonthIncome = thisMonthIncome;
    }

    public double getThisMonthExpectedIncome() {
        return thisMonthExpectedIncome;
    }

    public void setThisMonthExpectedIncome(double thisMonthExpectedIncome) {
        this.thisMonthExpectedIncome = thisMonthExpectedIncome;
    }

    public int getThisMonthCount() {
        return thisMonthCount;
    }

    public void setThisMonthCount(int thisMonthCount) {
        this.thisMonthCount = thisMonthCount;
    }

    public int getThisMonthExpectedCount() {
        return thisMonthExpectedCount;
    }

    public void setThisMonthExpectedCount(int thisMonthExpectedCount) {
        this.thisMonthExpectedCount = thisMonthExpectedCount;
    }

    public double getLastMonthIncome() {
        return lastMonthIncome;
    }

    public void setLastMonthIncome(double lastMonthIncome) {
        this.lastMonthIncome = lastMonthIncome;
    }

    public double getLastMonthExpectedIncome() {
        return lastMonthExpectedIncome;
    }

    public void setLastMonthExpectedIncome(double lastMonthExpectedIncome) {
        this.lastMonthExpectedIncome = lastMonthExpectedIncome;
    }

    public int getLastMonthCount() {
        return lastMonthCount;
    }

    public void setLastMonthCount(int lastMonthCount) {
        this.lastMonthCount = lastMonthCount;
    }

    public int getLastMonthExpectedCount() {
        return lastMonthExpectedCount;
    }

    public void setLastMonthExpectedCount(int lastMonthExpectedCount) {
        this.lastMonthExpectedCount = lastMonthExpectedCount;
    }

    @Override
    public String toString() {
        return "MyEarnings{" +
                "balance=" + balance +
                ", economizes=" + economizes +
                ", income=" + income +
                ", estimateIncome=" + estimateIncome +
                ", settledIncome=" + settledIncome +
                ", orders=" + orders +
                ", estimateOrders=" + estimateOrders +
                ", settledOrders=" + settledOrders +
                ", series='" + series + '\'' +
                ", todayIncome=" + todayIncome +
                ", todayExpectedIncome=" + todayExpectedIncome +
                ", todayCount=" + todayCount +
                ", todayExpectedCount=" + todayExpectedCount +
                ", yesterdayIncome=" + yesterdayIncome +
                ", yesterdayExpectedIncome=" + yesterdayExpectedIncome +
                ", yesterdayCount=" + yesterdayCount +
                ", yesterdayExpectedCount=" + yesterdayExpectedCount +
                ", thisMonthIncome=" + thisMonthIncome +
                ", thisMonthExpectedIncome=" + thisMonthExpectedIncome +
                ", thisMonthCount=" + thisMonthCount +
                ", thisMonthExpectedCount=" + thisMonthExpectedCount +
                ", lastMonthIncome=" + lastMonthIncome +
                ", lastMonthExpectedIncome=" + lastMonthExpectedIncome +
                ", lastMonthCount=" + lastMonthCount +
                ", lastMonthExpectedCount=" + lastMonthExpectedCount +
                '}';
    }

    public String getIncomeStr(){
        return "累计收益：￥" + this.settledIncome;
    }


}
