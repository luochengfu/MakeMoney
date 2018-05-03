package com.tudouni.makemoney.model;

import com.tudouni.makemoney.R;

/**
 * Created by ZhangPeng on 2018/4/26.
 */

public class MineMessage {
    private int uid;
    private String id;
    private long time;
    private String title;
    private int type = -1;
    private String content;
    private String url;
    private String showTitle;
    private int showIcon = R.mipmap.ic_note_system;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public int getShowIcon() {
        return showIcon;
    }

    public void setShowIcon(int showIcon) {
        this.showIcon = showIcon;
    }

    public void setShowTitle() {
        switch (type) {
            case 0:
                showTitle = "系统公告";
                showIcon = R.mipmap.ic_note_system;
                break;
            case 1:
                showTitle = "活动通知";
                showIcon = R.mipmap.ic_note_system;
                break;
            case 2:
                showTitle = "升级";
                showIcon = R.mipmap.ic_note_up_leve;
                break;
            case 3:
                showTitle = "佣金提示";
                showIcon = R.mipmap.ic_note_money;
                break;
        }
    }
}
