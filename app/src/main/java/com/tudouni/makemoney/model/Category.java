package com.tudouni.makemoney.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.tudouni.makemoney.BR;

import java.util.List;

public class Category extends BaseObservable {
    private String imgUrl;
    private String id;
    private String name;
    private List<Category> categorys;
    @Bindable
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        notifyPropertyChanged(BR.selected);
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getCategorys() {
        return categorys;
    }

    public void setCategorys(List<Category> categorys) {
        this.categorys = categorys;
    }

    @Override
    public String toString() {
        return "Category{" +
                "imgUrl='" + imgUrl + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", categorys=" + categorys +
                '}';
    }
}
