package com.tudouni.makemoney.model;

public class BindPayInfo {
    private int alipay;
    private int alipayMin;
    private int alipayMax;
    private int weixin;
    private int weixinMin;
    private int weixinMax;

    public int getAlipay() {
        return alipay;
    }

    public void setAlipay(int alipay) {
        this.alipay = alipay;
    }

    public int getAlipayMin() {
        return alipayMin;
    }

    public void setAlipayMin(int alipayMin) {
        this.alipayMin = alipayMin;
    }

    public int getAlipayMax() {
        return alipayMax;
    }

    public void setAlipayMax(int alipayMax) {
        this.alipayMax = alipayMax;
    }

    public int getWeixin() {
        return weixin;
    }

    public void setWeixin(int weixin) {
        this.weixin = weixin;
    }

    public int getWeixinMin() {
        return weixinMin;
    }

    public void setWeixinMin(int weixinMin) {
        this.weixinMin = weixinMin;
    }

    public int getWeixinMax() {
        return weixinMax;
    }

    public void setWeixinMax(int weixinMax) {
        this.weixinMax = weixinMax;
    }

    @Override
    public String toString() {
        return "BindPayInfo{" +
                "alipay=" + alipay +
                ", alipayMin=" + alipayMin +
                ", alipayMax=" + alipayMax +
                ", weixin=" + weixin +
                ", weixinMin=" + weixinMin +
                ", weixinMax=" + weixinMax +
                '}';
    }
}
