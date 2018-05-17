package com.tudouni.makemoney.model;

import java.io.Serializable;

public class Withdraw implements Serializable {
    /**
     * amount 申请提现金额
     * createTime 申请提现时间
     * reviewStatus 审核提现状态
     */
    private double amount;
    private String createTime;
    private String reviewStatus;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }
}
