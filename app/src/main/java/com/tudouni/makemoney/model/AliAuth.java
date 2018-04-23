package com.tudouni.makemoney.model;

import java.io.Serializable;

/**
 * Created by houjingwei on 2017/1/18.
 */

public class AliAuth implements Serializable {

    private String payInfo;

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }
}

