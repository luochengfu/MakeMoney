package com.tudouni.makemoney.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zp on 2018/4/26
 */

public class MessageResponsBean implements Serializable {
    private List<MineMessage> sysmsg;
    private List<MineMessage> gsysmsg;
    private boolean res;

    public List<MineMessage> getSysmsg() {
        return sysmsg;
    }

    public void setSysmsg(List<MineMessage> sysmsg) {
        this.sysmsg = sysmsg;
    }

    public List<MineMessage> getGsysmsg() {
        return gsysmsg;
    }

    public void setGsysmsg(List<MineMessage> gsysmsg) {
        this.gsysmsg = gsysmsg;
    }

    public boolean isRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }

    @Override
    public String toString() {
        return "MessageResponsBean{" +
                "sysmsg=" + sysmsg +
                ", gsysmsg=" + gsysmsg +
                ", res=" + res +
                '}';
    }
}
