package com.tudouni.makemoney.model;

/**
 * Created by ZhangPeng on 2018/5/15.
 */

public class NineRecommendGoodsBean {
    /**
     * picurl : a.jpg
     * itemid : 123
     * coupon : 333.00
     * price : 18.22
     * name : demo
     * commis : 22.00
     * qcodeurl : zzshop.goods.share.qr.url?title=demo&ref=&token=￥kU8Z0u3up7M￥&id=123
     */

    private String picurl;
    private String itemid;
    private String coupon;
    private String price;
    private String name;
    private String commis;
    private String qcodeurl;
    private String source;

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommis() {
        return commis;
    }

    public void setCommis(String commis) {
        this.commis = commis;
    }

    public String getQcodeurl() {
        return qcodeurl;
    }

    public void setQcodeurl(String qcodeurl) {
        this.qcodeurl = qcodeurl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
