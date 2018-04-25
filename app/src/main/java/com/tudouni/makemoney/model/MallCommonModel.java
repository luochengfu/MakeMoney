package com.tudouni.makemoney.model;

import java.util.List;

/**
 * Jaron.Wu
 * 2018/4/25
 */
public class MallCommonModel<T> {
    private int errorCode;
    private String errorHint;
    private List<T> list;
    private Page page;
    private boolean success;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorHint() {
        return errorHint;
    }

    public void setErrorHint(String errorHint) {
        this.errorHint = errorHint;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "MallCommonModel{" +
                "errorCode=" + errorCode +
                ", errorHint='" + errorHint + '\'' +
                ", list=" + list +
                ", page=" + page +
                ", success=" + success +
                '}';
    }
}
