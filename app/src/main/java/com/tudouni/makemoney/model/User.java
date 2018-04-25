package com.tudouni.makemoney.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hjw on 17/1/24.
 */
public class User implements Serializable {
    private String birthday;
    private String city;
    private String follows;
    private String grade;
    @SerializedName(value = "nickName", alternate = {"nickname"})
    private String nickName;
    private String photo;
    private String sex;
    private String signature;
    private String token;
    private String uid;
    private String phone = "";
    private String invistCode;
    private String unumber;
    private String gradeName;
    private String expense;
    private String stamp;
    private String experience;
    private String isFollowFan;
    //背景
    private String background;
    //真实名字
    private String realname;
    //身份证号码
    private String idNumber;
    private String frontPhoto;
    private String backPhoto;
    private String profit;      //邀请佣金
    private boolean Zma;
    private int seq;
    private String closeCommon;
    private String verifyToken;
    private String unionid;//邀请人在豆播平台的身份标识符3.0.0
    private boolean headAgent;//是否头部代理
    private Integer agentSeries;//商城等级
    private String agentSeriesName;//商城等级
    private long inviteCount = 0;//邀请数量 (豆粉数量)
    private boolean newUser;
    private String bindPhoneStatus;
    private String pwd;
    private boolean setAlias;//是否设置过别名

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public boolean isZma() {
        return Zma;
    }

    public void setZma(boolean zma) {
        Zma = zma;
    }

    public boolean commmit;

    public boolean isCommmit() {
        return commmit;
    }

    public void setCommmit(boolean commmit) {
        this.commmit = commmit;
    }

    public boolean isNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

    public String getFrontPhoto() {
        return frontPhoto;
    }

    public void setFrontPhoto(String frontPhoto) {
        this.frontPhoto = frontPhoto;
    }

    public String getBackPhoto() {
        return backPhoto;
    }

    public void setBackPhoto(String backPhoto) {
        this.backPhoto = backPhoto;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }


    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getUnumber() {
        return unumber;
    }

    public void setUnumber(String unumber) {
        this.unumber = unumber;
    }

    public String getInvistCode() {
        return invistCode;
    }

    public void setInvistCode(String invistCode) {
        this.invistCode = invistCode;
    }

    public String getBindPhoneStatus() {
        return bindPhoneStatus;
    }

    public void setBindPhoneStatus(String bindPhoneStatus) {
        this.bindPhoneStatus = bindPhoneStatus;
    }

    public String getIsFollowFan() {
        return isFollowFan;
    }

    public void setIsFollowFan(String isFollowFan) {
        this.isFollowFan = isFollowFan;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getFollows() {
        return follows;
    }

    public void setFollows(String follows) {
        this.follows = follows;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getNickName() {
        return TextUtils.isEmpty(nickName) ? "" : nickName.replaceAll("\n", "");
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


    public boolean getCloseCommon() {
        return "1".equals(closeCommon);
    }

    public void setCloseCommon(String closeCommon) {
        this.closeCommon = closeCommon;
    }

    public String getVerifyToken() {
        return verifyToken;
    }

    public void setVerifyToken(String verifyToken) {
        this.verifyToken = verifyToken;
    }

    public Boolean getHeadAgent() {
        return headAgent;
    }

    public void setHeadAgent(Boolean headAgent) {
        this.headAgent = headAgent;
    }

    public Integer getAgentSeries() {
        return agentSeries;
    }

    public void setAgentSeries(Integer agentSeries) {
        this.agentSeries = agentSeries;
    }

    public String getAgentSeriesName() {
        return agentSeriesName;
    }

    public void setAgentSeriesName(String agentSeriesName) {
        this.agentSeriesName = agentSeriesName;
    }

    public long getInviteCount() {
        return inviteCount;
    }

    public void setInviteCount(long inviteCount) {
        this.inviteCount = inviteCount;
    }

    public boolean isSetAlias() {
        return setAlias;
    }

    public void setSetAlias(boolean setAlias) {
        this.setAlias = setAlias;
    }
}
