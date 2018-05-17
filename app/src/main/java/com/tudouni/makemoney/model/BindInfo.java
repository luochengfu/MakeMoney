package com.tudouni.makemoney.model;

import java.io.Serializable;

/**
 * Created by houjingwei on 2017/1/26.
 */

public class BindInfo implements Serializable {
    private String qq;
    private String phone;
    private String sina;
    private String weixin;
    private String phoneHid;
    private String phoneNum;

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSina() {
        return sina;
    }

    public void setSina(String sina) {
        this.sina = sina;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getPhoneHid() {
        return phoneHid;
    }

    public void setPhoneHid(String phoneHid) {
        this.phoneHid = phoneHid;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
