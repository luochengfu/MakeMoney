package com.tudouni.makemoney.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Jaron.Wu
 * 2018/4/26
 */
public class MallGoodItem {

    private String item_id;
    private String pic_url;
    private String title;
    private String org_price;
    private String coupon_price;
    private String source;
    private String sales_num;
    private String commission;
    private String cid;
    private String create_by;
    private String id;
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrg_price() {
        return org_price;
    }

    public void setOrg_price(String org_price) {
        this.org_price = org_price;
    }

    public String getCoupon_price() {
        return coupon_price;
    }

    public void setCoupon_price(String coupon_price) {
        this.coupon_price = coupon_price;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSales_num() {
        return sales_num;
    }

    public void setSales_num(String sales_num) {
        this.sales_num = sales_num;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MallGoodItem{" +
                "item_id='" + item_id + '\'' +
                ", pic_url='" + pic_url + '\'' +
                ", title='" + title + '\'' +
                ", org_price='" + org_price + '\'' +
                ", coupon_price='" + coupon_price + '\'' +
                ", source='" + source + '\'' +
                ", sales_num='" + sales_num + '\'' +
                ", commission='" + commission + '\'' +
                ", cid='" + cid + '\'' +
                ", create_by='" + create_by + '\'' +
                ", id='" + id + '\'' +
                '}';
    }


    public String calcCommission(){
        double commissionRate = Double.valueOf(commission);
        double price = Double.valueOf(this.price);
        double commission = (commissionRate/100.0 * price)/2;
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        return numberFormat.format(commission);
    }

    public String calcSalesCount(){
        int sales = Integer.valueOf(this.sales_num);
        if (sales > 9999) {
            int w = sales / 10000;
            return "月销" + w + "万+";
        }
        return "月销" + this.sales_num;
    }
}
