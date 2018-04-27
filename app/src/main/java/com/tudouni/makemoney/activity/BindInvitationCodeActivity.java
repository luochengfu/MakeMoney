package com.tudouni.makemoney.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.model.BindUserBean;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.utils.glideUtil.GlideUtil;

/**
 * Created by Administrator on 2018/3/31 0031.
 */

public class BindInvitationCodeActivity extends FragmentActivity implements View.OnClickListener
{
    private ImageView mHeadView, mCloseBtn;
    private TextView mNickView, mNumView, mBindNumView, mBindButton;
    private String mUserCode;
    private BindUserBean mBindUserBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bind_invitation_code_layout);

        mUserCode = getIntent().getStringExtra("userCode");

        initView();
        initDatas();
    }

    private void initView() {
        mHeadView = (ImageView) findViewById(R.id.ivPhoto);
        mNickView = (TextView) findViewById(R.id.bind_invitation_code_layout_nick);
        mNumView = (TextView) findViewById(R.id.bind_invitation_code_layout_num);
        mBindNumView = (TextView) findViewById(R.id.bind_number_view);
        mBindButton = (TextView) findViewById(R.id.bind_invitation_code_layout_bind_buttom);
        mCloseBtn = (ImageView) findViewById(R.id.bind_invitation_close_button);
    }

    private void initDatas() {
        mBindButton.setOnClickListener(this);
        mCloseBtn.setOnClickListener(this);
        CommonScene.getBindUserInfo(mUserCode, new BaseObserver<BindUserBean>() {
            @Override
            public void OnSuccess(BindUserBean bindUserBean) {
                mBindUserBean = bindUserBean;
                flashView();
            }

            @Override
            public void OnFail(int code, String err) {
                finish();
            }
        });
    }

    private void flashView() {
        if(null != mBindUserBean) {
            String mBindStr = "你的粘贴板存在邀请码  " + mUserCode;
            SpannableStringBuilder ssb = new SpannableStringBuilder(mBindStr);
            ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_FF9933)), 10, mBindStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mBindNumView.setText(ssb);
            GlideUtil.bindImage(mHeadView, mBindUserBean.getAvatar());
            mNickView.setText(mBindUserBean.getNickname());
            mNumView.setText("土豆号： " + mBindUserBean.getUserCode());
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bind_invitation_code_layout_bind_buttom:
                CommonScene.addBindUser(mUserCode, mBindUserBean.getUnionid(), new BaseObserver<String>() {
                    @Override
                    public void OnSuccess(String s) {
                        ToastUtil.show("绑定成功");
                        finish();
                    }

                    @Override
                    public void OnFail(int code, String err) {
                        super.OnFail(code, err);
                    }
                });
                break;
            case R.id.bind_invitation_close_button:
                finish();
        }
    }
}
