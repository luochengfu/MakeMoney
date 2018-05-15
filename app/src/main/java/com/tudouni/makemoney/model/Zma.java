package com.tudouni.makemoney.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/25 0025.
 */

public class Zma implements Serializable {
    private String bizNo;
    private String bizurl;
    private String bizcall;


    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getBizurl() {
        return bizurl;
    }

    public void setBizurl(String bizurl) {
        this.bizurl = bizurl;
    }

    public String getBizcall() {
        return bizcall;
    }

    public void setBizcall(String bizcall) {
        this.bizcall = bizcall;
    }
}
