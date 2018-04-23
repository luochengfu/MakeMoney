package com.tudouni.makemoney.model;

public class CategoryNameItem{
    private String name;
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
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "CategoryNameItem{" +
                "name='" + name + '\'' +
                ", isSelected=" + selected +
                '}';
    }
}
