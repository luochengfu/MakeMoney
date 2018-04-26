package com.tudouni.makemoney.model;

/**
 * Jaron.Wu
 * 2018/4/26
 */
public class MallGoodItem {
    /**
     * "item_id": "10093784470",
     "pic_url": "https://img.alicdn.com/imgextra/i4/856017888/TB2RL_obmtYBeNjSspkXXbU8VXa_!!856017888.jpg",
     "title": "春游野餐垫防潮垫户外便携野外露营加厚垫子野炊地垫郊游用品必备",
     "org_price": "39",
     "coupon_price": "10",
     "price": "29",
     "source": "tb",
     "sales_num": "2066",
     "commission": "30",
     "cid": "7",
     "create_by": "lianglv",
     "id": "5adeef4cafbc944c73413da8"
     */
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
}
