package com.tudouni.makemoney.model;

import java.io.Serializable;

/**
 * Created by suteng on 2017/6/13.
 */

public class PayBindingInfo implements Serializable {

    /**支付宝绑定状态*/
    private String alipay;

    /**支付宝提现最小值*/
    private String alipayMin;

    /**支付宝提现最大值*/
    private String alipayMax;

    /**微信绑定状态*/
    private String weixin;

    /**微信提现最小值*/
    private String weixinMin;

    /**微信提现最大值*/
    private String weixinMax;

    /** @see #alipay */
    public String getAlipay() {
        return alipay;
    }

    /** @see #alipay */
    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    /** @see #alipayMin */
    public String getAlipayMin() {
        return alipayMin;
    }

    /** @see #alipayMin */
    public void setAlipayMin(String alipayMin) {
        this.alipayMin = alipayMin;
    }

    /** @see #alipayMax */
    public String getAlipayMax() {
        return alipayMax;
    }

    /** @see #alipayMax */
    public void setAlipayMax(String alipayMax) {
        this.alipayMax = alipayMax;
    }

    /** @see #weixin */
    public String getWeixin() {
        return weixin;
    }

    /** @see #weixin */
    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    /** @see #weixinMin */
    public String getWeixinMin() {
        return weixinMin;
    }

    /** @see #weixinMin */
    public void setWeixinMin(String weixinMin) {
        this.weixinMin = weixinMin;
    }

    /** @see #weixinMax */
    public String getWeixinMax() {
        return weixinMax;
    }

    /** @see #weixinMax */
    public void setWeixinMax(String weixinMax) {
        this.weixinMax = weixinMax;
    }

    public double getAlipayMinNumber(){
        try {
            return Double.parseDouble(alipayMin);
        }catch (Exception e){
            return 0d;
        }
    }

    public double getAlipayMaxNumber(){
        try {
            return Double.parseDouble(alipayMax);
        }catch (Exception e){
            return 0d;
        }
    }

    public double getWeixinMinNumber(){
        try {
            return Double.parseDouble(weixinMin);
        }catch (Exception e){
            return 0d;
        }
    }

    public double getWeixinMaxNumber(){
        try {
            return Double.parseDouble(weixinMax);
        }catch (Exception e){
            return 0d;
        }
    }
}
