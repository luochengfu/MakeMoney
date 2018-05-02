package com.tudouni.makemoney.model;

/**
 * Jaron.Wu
 * 2018/5/2
 */
public class SearchHistory {
    private String id;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SearchHistory{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
