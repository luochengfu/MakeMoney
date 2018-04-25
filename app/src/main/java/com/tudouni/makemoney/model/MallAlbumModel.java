package com.tudouni.makemoney.model;

/**
 * Jaron.Wu
 * 2018/4/25
 */
public class MallAlbumModel {


    private String id;
    private String title;
    private String imageUrl;
    private String url;
    private String locationKey;
    private int playIndex;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocationKey() {
        return locationKey;
    }

    public void setLocationKey(String locationKey) {
        this.locationKey = locationKey;
    }

    public int getPlayIndex() {
        return playIndex;
    }

    public void setPlayIndex(int playIndex) {
        this.playIndex = playIndex;
    }

    @Override
    public String toString() {
        return "MallAlbumModel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", url='" + url + '\'' +
                ", locationKey='" + locationKey + '\'' +
                ", playIndex=" + playIndex +
                '}';
    }
}
