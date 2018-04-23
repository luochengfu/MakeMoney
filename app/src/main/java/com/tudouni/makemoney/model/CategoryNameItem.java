package com.tudouni.makemoney.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.tudouni.makemoney.BR;

public class CategoryNameItem extends BaseObservable{
    @Bindable
    private String name;
    @Bindable
    private boolean selected;

    public CategoryNameItem(){}

    public CategoryNameItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        notifyPropertyChanged(BR.selected);
    }

    @Override
    public String toString() {
        return "CategoryNameItem{" +
                "name='" + name + '\'' +
                ", isSelected=" + selected +
                '}';
    }
}
