package com.tudouni.makemoney.model;

import com.tudouni.makemoney.utils.TuDouLogUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 九宫格推荐类
 * Created by Zhangpeng on 2018/5/15
 */

public class NineRecommendBean implements Serializable {

    /**
     * indexNum : 1
     * createBy : abc
     * createTime : 1499650985000
     * id : 123
     * text : aaaa
     * list : [{"picurl":"a.jpg","itemid":"123","coupon":"333.00","price":"18.22","name":"demo","commis":"22.00","qcodeurl":"zzshop.goods.share.qr.url?title=demo&ref=&token=￥kU8Z0u3up7M￥&id=123"},{"picurl":"a.jpg","itemid":"124","coupon":"333.00","price":"18.22","name":"demo","commis":"22.00","qcodeurl":"zzshop.goods.share.qr.url?title=demo&ref=&token=￥kU8Z0u3up7M￥&id=124"}]
     * title : abc
     * status : 1
     */

    private int indexNum;
    private String createBy;
    private long createTime;
    private String id;
    private String text;
    private String title;
    private String icon;
    private int status;
    private List<NineRecommendGoodsBean> list;

    public int getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(int indexNum) {
        this.indexNum = indexNum;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<NineRecommendGoodsBean> getList() {
        return list;
    }

    public void setList(List<NineRecommendGoodsBean> list) {
        this.list = list;
    }

    /**
     * 获取请求分享的图片的Bean
     *
     * @return
     */
    public RequestNineRecommendShareBean getRequestNineRecommendShareBean() {
        RequestNineRecommendShareBean requestNineRecommendShareBean = null;
        try {
            List<RequestNineRecommendShareBean.ItemsBean> items = new ArrayList<>();
            for (NineRecommendGoodsBean nineRecommendGoodsBean : list) {
                items.add(new RequestNineRecommendShareBean.ItemsBean(nineRecommendGoodsBean.getItemid(), nineRecommendGoodsBean.getSource()));
            }
            requestNineRecommendShareBean = new RequestNineRecommendShareBean(items);
        } catch (Exception e) {
            TuDouLogUtils.e("NineRecommendBean", "获取请求Bean报错：" + e.getMessage());
        } finally {
            return requestNineRecommendShareBean;
        }
    }
}
