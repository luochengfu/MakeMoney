package com.tudouni.makemoney.model;

public class EarningsRank {
    private String uid;
    private String nickname;
    private String photo;
    private String userCode;
    private String phone;
    private double totalIncome;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    @Override
    public String toString() {
        return "EarningsRank{" +
                "uid='" + uid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", photo='" + photo + '\'' +
                ", userCode='" + userCode + '\'' +
                ", phone='" + phone + '\'' +
                ", totalIncome=" + totalIncome +
                '}';
    }
}
