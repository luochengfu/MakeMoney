package com.tudouni.makemoney.model;

/**
 * 商城分销
 * huang create by 2018/3/21
 */
public class AgentInfo {
    /**
     * 余额
     */
    private double balance;

    /**
     * 一级下线数
     */
    private int firstLevelAgents;

    /**
     * 二级下线数
     */
    private int secondLevelAgents;

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
     * 本月收益
     */
    private double thisMonthIncome;

    /**
     * 本月预估收益
     */
    private double thisMonthExpectedIncome;
    /**
     * 本年预估收益
     */
    private double yesterdayIncome;
    /**
     * 本年预估收益
     */
    private double yesterdayExpectedIncome;


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getFirstLevelAgents() {
        return firstLevelAgents;
    }

    public void setFirstLevelAgents(int firstLevelAgents) {
        this.firstLevelAgents = firstLevelAgents;
    }

    public int getSecondLevelAgents() {
        return secondLevelAgents;
    }

    public void setSecondLevelAgents(int secondLevelAgents) {
        this.secondLevelAgents = secondLevelAgents;
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

    public double getThisMonthIncome() {
        return thisMonthIncome;
    }

    public void setThisMonthIncome(double thisMonthIncome) {
        this.thisMonthIncome = thisMonthIncome;
    }

    public double getTodayExpectedIncome() {
        return todayExpectedIncome;
    }

    public void setTodayExpectedIncome(double todayExpectedIncome) {
        this.todayExpectedIncome = todayExpectedIncome;
    }

    public double getThisMonthExpectedIncome() {
        return thisMonthExpectedIncome;
    }

    public void setThisMonthExpectedIncome(double thisMonthExpectedIncome) {
        this.thisMonthExpectedIncome = thisMonthExpectedIncome;
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
}
