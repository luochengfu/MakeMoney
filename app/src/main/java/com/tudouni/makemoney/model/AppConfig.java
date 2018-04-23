package com.doubozhibo.tudouni.model;

import android.text.TextUtils;

import com.doubozhibo.tudouni.Constant;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hjw on 2017/4/15.
 */

public class AppConfig implements Serializable {

    private List<GradeHocs> gradeHocs;
    private String videoRecordMax = "15"; //短视频最大时间
    private String videoRecordMin = "5"; //短视频最小时间
    private String livePlayUrl;         //直播播放地址，rtmp://rtmp.doubozhibo.com/tudouni/{lid}  或  http://flv.doubozhibo.com/tudouni/{lid}.flv

    private String liveFrameRate;   //帧率
    private String liveCodeRateMin; //最小码率，单位kb
    private String liveCodeRateMax; //最大码率，单位kb
    private String liveAudioRate; //音频码率
    private String shutdownMaintain;  //是否维护中 0维护  1正常
    private String shutdownMaintainMsg = "系统维护中，给你带来不便，抱歉。"; //维护信息
    private boolean maintin; //是否维护中 true 维护   false 正常
    private int unfriendCreateChatRoomCondition;
    private int unfriendCreateChatRoomMinLevel;
    @SerializedName("RP_FEE_RATIO")
    private String mServiceCharge; //手续费
    @SerializedName("RP_MIN_COIN")
    private String mMinMoney; //最少金额
    @SerializedName("RP_COIN_RADIX")
    private String mRadix; //倍数
    @SerializedName("redPacketSwitch")
    private String mRedPacketSwitch = "0"; //红包开关


    @SerializedName("spread_ad")
    private SpreadAd spreadAd;//开屏广告

    @SerializedName("invite_share_qcode_domain")
    private String mInviteShareQcodeDomain;//本次邀请分享的域名

    @SerializedName("unvalid_invite_share_qcode_domain")
    private List<String> unvalidInviteShareQcodeDomain;//邀请分享已经失效的域名


    public int getUnfriendCreateChatRoomCondition() {
        return unfriendCreateChatRoomCondition;
    }

    public void setUnfriendCreateChatRoomCondition(int unfriendCreateChatRoomCondition) {
        this.unfriendCreateChatRoomCondition = unfriendCreateChatRoomCondition;
    }

    public int getUnfriendCreateChatRoomMinLevel() {
        return unfriendCreateChatRoomMinLevel;
    }

    public void setUnfriendCreateChatRoomMinLevel(int unfriendCreateChatRoomMinLevel) {
        this.unfriendCreateChatRoomMinLevel = unfriendCreateChatRoomMinLevel;
    }

    private GameConfig gameConfig;

    public class GameConfig {
        private int gamebitrate;
        private int gamefps;
        private int gameheight;
        private int gamewidth;

        public int getGamebitrate() {
            return gamebitrate;
        }

        public void setGamebitrate(int gamebitrate) {
            this.gamebitrate = gamebitrate;
        }

        public int getGamefps() {
            return gamefps;
        }

        public void setGamefps(int gamefps) {
            this.gamefps = gamefps;
        }

        public int getGameheight() {
            return gameheight;
        }

        public void setGameheight(int gameheight) {
            this.gameheight = gameheight;
        }

        public int getGamewidth() {
            return gamewidth;
        }

        public void setGamewidth(int gamewidth) {
            this.gamewidth = gamewidth;
        }
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }

    public void setGameConfig(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    public boolean isMaintain() {
        if ("0".equals(shutdownMaintain)) {
            maintin = true;
        } else {
            maintin = false;
        }
        return maintin;
    }

    public String getShutdownMaintainMsg() {
        return shutdownMaintainMsg;
    }

    public void setShutdownMaintainMsg(String shutdownMaintainMsg) {
        this.shutdownMaintainMsg = shutdownMaintainMsg;
    }

    public String getLivePlayUrl() {
        return livePlayUrl;
    }

    public List<GradeHocs> getGradeHocs() {
        return gradeHocs;
    }

    public void setGradeHocs(List<GradeHocs> gradeHocs) {
        this.gradeHocs = gradeHocs;
    }

    public String getVideoRecordMax() {
        return videoRecordMax;
    }

    public String getVideoRecordMin() {
        return videoRecordMin;
    }

    public String getServiceCharge() {
        return mServiceCharge;
    }

    public String getmInviteShareQcodeDomain() {
        return (TextUtils.isEmpty(mInviteShareQcodeDomain)) ? (Constant.BASE_H5_URL + "/") : mInviteShareQcodeDomain;
    }

    public void setmInviteShareQcodeDomain(String mInviteShareQcodeDomain) {
        this.mInviteShareQcodeDomain = mInviteShareQcodeDomain;
    }

    public List<String> getUnvalidInviteShareQcodeDomain() {
        return unvalidInviteShareQcodeDomain;
    }

    public void setUnvalidInviteShareQcodeDomain(List<String> unvalidInviteShareQcodeDomain) {
        this.unvalidInviteShareQcodeDomain = unvalidInviteShareQcodeDomain;
    }

    public String getMinMoney() {
        if (TextUtils.isEmpty(mMinMoney)) {
            return "100";
        } else
            return mMinMoney;
    }

    public String getRadix() {
        if (TextUtils.isEmpty(mRadix)) {
            return "1";
        } else
            return mRadix;
    }

    public String getRedPacketSwitch() {
        return mRedPacketSwitch;
    }

    public int getLiveFrameRate() {
        int n = 0;
        try {
            n = Integer.parseInt(liveFrameRate);
        } catch (Exception e) {
        }
        return n > 0 ? n : 20;
    }

    public int getLiveCodeRateMin() {
        int n = 0;
        try {
            n = Integer.parseInt(liveCodeRateMin);
        } catch (Exception e) {
        }
        return (n > 0 ? n : 500) * 1024;
    }

    public int getLiveCodeRateMax() {
        int n = 0;
        try {
            n = Integer.parseInt(liveCodeRateMax);
        } catch (Exception e) {
        }
        if (n > 0) {
            return n * 1000;
        }
        return 800 * 1000;
    }

    /**
     * 获取音频码率
     *
     * @return
     */
    public int getAudioRate() {
        int n = 0;
        try {
            n = Integer.parseInt(liveAudioRate);
        } catch (Exception e) {
        }
        if (n > 0) {
            return n;
        }
        return 48;
    }

    public SpreadAd getSpreadAd() {
        return spreadAd;
    }

    public void setSpreadAd(SpreadAd spreadAd) {
        this.spreadAd = spreadAd;
    }

    @Override
    public String toString() {
        return "AppConfig{" +
                "gradeHocs=" + gradeHocs +
                ", videoRecordMax='" + videoRecordMax + '\'' +
                ", videoRecordMin='" + videoRecordMin + '\'' +
                ", livePlayUrl='" + livePlayUrl + '\'' +
                ", liveFrameRate='" + liveFrameRate + '\'' +
                ", liveCodeRateMin='" + liveCodeRateMin + '\'' +
                ", liveCodeRateMax='" + liveCodeRateMax + '\'' +
                ", liveAudioRate='" + liveAudioRate + '\'' +
                ", shutdownMaintain='" + shutdownMaintain + '\'' +
                ", shutdownMaintainMsg='" + shutdownMaintainMsg + '\'' +
                ", maintin=" + maintin +
                ", unfriendCreateChatRoomCondition=" + unfriendCreateChatRoomCondition +
                ", unfriendCreateChatRoomMinLevel=" + unfriendCreateChatRoomMinLevel +
                ", gameConfig=" + gameConfig +
                ", spreadAd=" + ((getSpreadAd() != null) ? (getSpreadAd().toString()) : (null)) +
                '}';
    }

    /**
     * 是否是分享邀请链接
     *
     * @param url 当前跳转的
     * @return
     */
    public boolean isShareInvistor(String url) {
        if (TextUtils.isEmpty(url)) return false;
        boolean back = false;
        if (url.startsWith(mInviteShareQcodeDomain) || url.startsWith(Constant.SHARE_INVISTOR))
            return true;
        if (unvalidInviteShareQcodeDomain != null || !unvalidInviteShareQcodeDomain.isEmpty()) {
            for (String s : unvalidInviteShareQcodeDomain) {
                if (url.startsWith(mInviteShareQcodeDomain)) {
                    back = true;
                    break;
                }
            }
        }
        return back;
    }
}
