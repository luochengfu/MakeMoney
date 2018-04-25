package com.tudouni.makemoney.widget.versionUpdate;

import java.io.Serializable;

/**
 * Created by hjw on 2017/4/15.
 */

public class Upinfo implements Serializable {

    private String alert;
    private String appUrl;
    private String desc;
    private String review;
    private String title = "";


    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
