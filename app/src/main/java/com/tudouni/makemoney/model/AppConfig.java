package com.tudouni.makemoney.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.tudouni.makemoney.utils.Constants;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hjw on 2017/4/15.
 */

public class AppConfig implements Serializable {

    @SerializedName("invite_share_qcode_domain")
    private String mInviteShareQcodeDomain;//本次邀请分享的域名

    @SerializedName("unvalid_invite_share_qcode_domain")
    private List<String> unvalidInviteShareQcodeDomain;//邀请分享已经失效的域名

    public void setmInviteShareQcodeDomain(String mInviteShareQcodeDomain) {
        this.mInviteShareQcodeDomain = mInviteShareQcodeDomain;
    }

    public List<String> getUnvalidInviteShareQcodeDomain() {
        return unvalidInviteShareQcodeDomain;
    }

    public void setUnvalidInviteShareQcodeDomain(List<String> unvalidInviteShareQcodeDomain) {
        this.unvalidInviteShareQcodeDomain = unvalidInviteShareQcodeDomain;
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
        if (url.startsWith(mInviteShareQcodeDomain) || url.startsWith(Constants.SHARE_INVISTOR)) {
            if (url.contains(Constants.OLD_INVISTOR) || url.contains(Constants.INVISTOR)) {
                return true;
            }
        }
        if (unvalidInviteShareQcodeDomain != null || !unvalidInviteShareQcodeDomain.isEmpty()) {
            for (String s : unvalidInviteShareQcodeDomain) {
                if (url.startsWith(s) && (url.contains(Constants.OLD_INVISTOR) || url.contains(Constants.INVISTOR))) {
                    back = true;
                    break;
                }
            }
        }
        return back;
    }
}
