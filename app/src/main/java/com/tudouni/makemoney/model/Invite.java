package com.tudouni.makemoney.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/7/24 0024.
 */

public class Invite {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    @SerializedName(value = "avatar", alternate = {"photo"})
    private String photo;
    /**
     * 用户code
     */
    @SerializedName(value = "code")
    private String code;

    @SerializedName(value = "userCode")
    private String userCode;
    /**
     * 用户等级
     */
    private String grade;
    /**
     * 用户邀请码
     */
    private String inviteCode;
    /**
     * 创建时间
     */
    private String inviteTime;

    private String sex;//性别0：女；1：男

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getInviteTime() {
        return inviteTime;
    }

    public void setInviteTime(String inviteTime) {
        this.inviteTime = inviteTime;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
