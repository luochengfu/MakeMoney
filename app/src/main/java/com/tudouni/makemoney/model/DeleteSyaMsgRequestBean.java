package com.tudouni.makemoney.model;

import java.util.ArrayList;

/**
 * 删除消息请求的对象
 * Created by ZhangPeng on 2018/5/11.
 */

public class DeleteSyaMsgRequestBean {
    public String uid;// 为用户uid
    public ArrayList<String> msgList;//消息id列表 type:id 以冒号分开

    public DeleteSyaMsgRequestBean(String uid, ArrayList<String> msgList) {
        this.uid = uid;
        this.msgList = msgList;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ArrayList<String> getMsgList() {
        return msgList;
    }

    public void setMsgList(ArrayList<String> msgList) {
        this.msgList = msgList;
    }
}
