package com.tudouni.makemoney.model;

public class SavingsProfile {

    /**
     * 订单数
     */
    private long orders;
    /**
     * 应付
     */
    private double payable;
    /**
     * 实付
     */
    private double realPay;
    /**
     * 省钱
     */
    private double economizes;

    public long getOrders() {
        return orders;
    }

    public void setOrders(long orders) {
        this.orders = orders;
    }

    public double getPayable() {
        return payable;
    }

    public void setPayable(double payable) {
        this.payable = payable;
    }

    public double getRealPay() {
        return realPay;
    }

    public void setRealPay(double realPay) {
        this.realPay = realPay;
    }

    public double getEconomizes() {
        return economizes;
    }

    public void setEconomizes(double economizes) {
        this.economizes = economizes;
    }

    @Override
    public String toString() {
        return "SavingsProfile{" +
                "orders=" + orders +
                ", payable=" + payable +
                ", realPay=" + realPay +
                ", economizes=" + economizes +
                '}';
    }
}
