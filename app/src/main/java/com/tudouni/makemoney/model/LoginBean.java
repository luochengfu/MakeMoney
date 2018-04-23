package com.tudouni.makemoney.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/31 0031.
 */

public class LoginBean implements Serializable{
    private User user;
    private boolean newer;
    private String handleToken;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean getNewer() {
        return newer;
    }

    public void setNewer(boolean newer) {
        this.newer = newer;
    }

    public String getHandleToken() {
        return handleToken;
    }

    public void setHandleToken(String handleToken) {
        this.handleToken = handleToken;
    }
}
