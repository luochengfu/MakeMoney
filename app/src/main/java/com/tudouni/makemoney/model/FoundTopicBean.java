package com.tudouni.makemoney.model;

import com.tudouni.makemoney.interfaces.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/24 0024.
 */

public class FoundTopicBean implements Serializable {
    private String id;
    private String imageUrl;
    private int hasSubTopic;
    private int recommend;
    private int type;
    private String url;
    private int status;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
