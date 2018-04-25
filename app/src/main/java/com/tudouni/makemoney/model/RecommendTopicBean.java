package com.tudouni.makemoney.model;

import com.tudouni.makemoney.interfaces.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/24 0024.
 */

public class RecommendTopicBean implements Serializable {
    private String parent;
    private String publishTime;
    private int hasSubTopic;
    private int recommend;
    private int type;
    private String url;
    private String imageUrl;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public int getHasSubTopic() {
        return hasSubTopic;
    }

    public void setHasSubTopic(int hasSubTopic) {
        this.hasSubTopic = hasSubTopic;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
