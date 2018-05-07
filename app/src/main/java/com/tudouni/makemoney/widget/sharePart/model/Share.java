package com.tudouni.makemoney.widget.sharePart.model;

import android.graphics.Bitmap;
import android.text.TextUtils;


import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.utils.Constants;
import com.tudouni.makemoney.utils.TuDouLogUtils;

import java.io.Serializable;
import java.net.URLEncoder;

/**
 * 分享内容
 * Created by ZhangPeng on 2018/4/21.
 */
public class Share implements Serializable {

    public static final String SHARE_TITLE = "快来玩转赚赚";
    public static final String SHARE_INVITE = "shoplite/html/invistor.html?code=%1$s&nickName=%2$s&unionid=%3$s";
    public static final String SHARE_USER = Constants.BASE_H5_URL + "/tudouni/html/ucenter.html?uid=%1$s";
    public static final String SHARE_LIVE = Constants.BASE_H5_URL + "/m/live.html?lid=%1$s&category=%2$s";
    public static final String SHARE_DYNAMIC = Constants.BASE_H5_URL + "/tudouni/html/dynamics.html?did=%1$s&type=%2$s";


    /**
     * 分享类型
     */
    public enum Type {

        /**
         * 分享我的邀请
         */
        INVITE,

        /**
         * 分享个人主页
         */
        USER,

        /**
         * 分享直播
         */
        LIVE,

        /**
         * 分享视频动态
         */
        VIDEO,

        /**
         * 分享图片动态
         */
        IMAGE,

        /**
         * 分享海报
         */
        IMAGE_POTATOES;

        /**
         * 分享窗口是否有保存选项
         */
        public boolean hasSave() {
            return this == VIDEO || this == IMAGE;
        }
    }


    private String title;
    private String content;
    private String circleContent;   //土豆泥朋友圈内容
    private String cover;
    private String targetUrl;
    private String targetId;
    private Bitmap minePotatoes;//海报

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCircleContent() {
        return TextUtils.isEmpty(circleContent) ? content : circleContent;
    }

    public void setCircleContent(String circleContent) {
        this.circleContent = circleContent;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public Share() {
    }

    public Share(String title, String content, String cover, String targetUrl) {
        this(title, content, cover, targetUrl, null);
    }

    public Share(String title, String content, String cover, String targetUrl, String circleContent) {
        this.title = title;
        this.content = content;
        this.cover = cover;
        this.targetUrl = targetUrl;
        this.circleContent = circleContent;
    }

    public Bitmap getMinePotatoes() {
        return minePotatoes;
    }

    public void setMinePotatoes(Bitmap minePotatoes) {
        this.minePotatoes = minePotatoes;
    }


    /**
     * 根据分享类型组装分享内容
     *
     * @param type   分享类型
     * @param object 原始数据对象
     * @return
     */
    public static Share obtain(Type type, Object object) {
        if (type == Type.IMAGE_POTATOES) {//生成海报分享对象
            return obtainPotatoesImage((Bitmap) object);
        }
        throw new IllegalArgumentException("分享参数错误");
    }

    /**
     * 生成海报分享
     *
     * @return
     */
    public static Share obtainPotatoesImage(Bitmap bitmap) {
//        String title = App.getLoginUser().getNickName() + "带你玩转土豆泥直播";
        Share share = new Share(SHARE_TITLE, "赚赚", null, null, null);
        share.setMinePotatoes(bitmap);
        return share;
    }

    /**
     * 获取分享邀请的的地址
     *
     * @return
     */
    public static String getShareInviteUrl() {
        try {
            return MyApplication.appConfig.getmInviteShareQcodeDomain() + SHARE_INVITE;
        } catch (Exception e) {
            TuDouLogUtils.e("Share", "Get Share Url Error：" + e.getMessage());
            return Constants.BASE_H5_URL + "/";
        }
    }
}
