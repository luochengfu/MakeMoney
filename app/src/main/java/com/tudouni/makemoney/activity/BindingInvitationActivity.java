package com.tudouni.makemoney.activity;

import android.os.Bundle;

import com.tudouni.makemoney.R;


import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tudouni.makemoney.model.Invite;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.Result;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.InjectView;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.utils.TuDouTextUtil;
import com.tudouni.makemoney.view.ShapeImageView;
import com.umeng.analytics.MobclickAgent;

/**
 * 绑定邀请码界面
 * 1、扫描结果跳转界面
 * 2、豆聊点击邀请链接跳转界面
 * 3、动态点击邀请链链接跳转界面
 * * @author ZhangPeng
 */
public class BindingInvitationActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(id = R.id.top_tip)
    TextView top_tip;

    @InjectView(id = R.id.iv_sao_head)
    ShapeImageView mIvSaoHead;
    @InjectView(id = R.id.im_sao_user_gender)
    ImageView mImSaoUserGender;
    @InjectView(id = R.id.tv_sao_userName)
    TextView mTvSaoUserName;
    @InjectView(id = R.id.tv_sao_userCode)
    TextView mTvSaoUserCode;

    @InjectView(id = R.id.bt_invitation)
    Button mBtInvitation;
    @InjectView(id = R.id.tv_add_firend, onClick = true)
    TextView mTvAddFirend;
    @InjectView(id = R.id.tv_bottom_tip)
    ImageView mTvBottomTip;
    @InjectView(id = R.id.ly_binding)
    LinearLayout mLyBinding;
    @InjectView(id = R.id.ly_invitation_result)
    RelativeLayout mLyInvitationResult;
    @InjectView(id = R.id.im_invitation_result)
    ImageView mImInvitationResult;

    @InjectView(id = R.id.ly_invited_tip)
    RelativeLayout mLyInvitedTip;


    private String inviterUnionid;
    private String userId;
    private String userCode;
    private boolean canJumpShop = false;//添加成后是否可以跳转到商城
    private Invite inviteInfo;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mLyInvitationResult.setVisibility(View.GONE);
            mLyBinding.setVisibility(View.GONE);
            finish();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding_invitation);
        initData();
    }

    private void initData() {
        inviterUnionid = getIntent().getStringExtra("unionid");//新版本才会有

        userId = getIntent().getStringExtra("uid");//老版本
        if (TextUtils.isEmpty(userCode))
            userId = getIntent().getStringExtra("userId");//新版本

        userCode = getIntent().getStringExtra("code");//老版本
        if (TextUtils.isEmpty(userCode))
            userCode = getIntent().getStringExtra("userCode");//新版本

        canJumpShop = getIntent().getBooleanExtra("canJumpShop", false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_invitation:
                MobclickAgent.onEvent(this, "me_inaccept");
                CommonScene.addBinding(inviterUnionid, userCode, new BaseObserver<Result>() {
                    @Override
                    public void OnSuccess(Result result) {
                        ToastUtil.show("绑定成功");
                        bindingButtonStatar(false);
                        mLyInvitationResult.setVisibility(View.VISIBLE);
                        handler.postDelayed(null, 300);
                    }

                    @Override
                    public void OnFail(int code, String err) {
                        ToastUtil.show(err + "（" + code + "）");
                    }
                });
                break;
            case R.id.tv_add_firend:
//                MobclickAgent.onEvent(this, "me_inf2fadd");
//                ForwardUtils.toFrendcenter(this, inviteInfo.getUserId() + "");
                break;
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        CommonScene.getBind(new BaseObserver<Invite>() {
            @Override
            public void OnFail(int code, String err) {
                bindingButtonStatar(true);
            }

            @Override
            public void OnSuccess(Invite invite) {
                if (invite == null) return;
                bindingButtonStatar(false);
            }
        });
        CommonScene.getInviteInfo(inviterUnionid, userId, userCode, new BaseObserver<Invite>() {
            @Override
            public void OnSuccess(Invite invite) {
                if (invite == null) return;
                inviteInfo = invite;
                Glide.with(BindingInvitationActivity.this).load(invite.getPhoto()).error(R.mipmap.default_head2).fallback(R.mipmap.default_head2).into(mIvSaoHead);
                mImSaoUserGender.setImageResource(("1".equals(invite.getSex())) ? R.mipmap.public_gender_man : R.mipmap.public_gender_woman);
                TuDouTextUtil.setTextToTextView(mTvSaoUserName, invite.getNickname());
                TuDouTextUtil.setTextToTextView(mTvSaoUserCode, invite.getInviteCode());
            }

            @Override
            public void OnFail(int code, String err) {
                super.OnFail(code, err);
            }
        });


    }

    private void bindingButtonStatar(boolean canUser) {
        mBtInvitation.setBackgroundResource(canUser ? R.drawable.bg_btn_red : R.drawable.bg_btn_red_gray);
        mBtInvitation.setOnClickListener(canUser ? (BindingInvitationActivity.this) : null);
        mBtInvitation.setTextColor(Color.parseColor(canUser ? "#ffffff" : "#C9C9C9"));
        mLyInvitedTip.setVisibility(canUser ? View.GONE : View.VISIBLE);
        mTvBottomTip.setVisibility(canUser ? View.VISIBLE : View.GONE);
    }

}

