package com.tudouni.makemoney.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.model.BindUserBean;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.utils.glideUtil.GlideUtil;

/**
 * Created by Administrator on 2018/5/2 0002.
 */

public class BindInvitationCodeDialog extends Dialog implements View.OnClickListener
{
    private Context mContext;
    private ImageView mHeadView, mCloseBtn;
    private TextView mNickView, mNumView, mBindNumView, mBindButton;
    private BindUserBean mBindUserBean;

    public BindInvitationCodeDialog(Context context) {
        this(context, null, true);
    }

    public BindInvitationCodeDialog(Context context, String msg) {
        this(context, null, true);
    }

    public BindInvitationCodeDialog(Context context, BindUserBean bindUserBean, boolean cancelable) {
        super(context, R.style.loading_dialog);
        this.mContext = context;
        mBindUserBean = bindUserBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.bind_invitation_code_layout);

        initView();
        initDatas();
        flashView();
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
    }

    private void flashView() {
        if(null != mBindUserBean) {
            String mBindStr = "你的粘贴板存在邀请码  " + mBindUserBean.getUserCode();
            SpannableStringBuilder ssb = new SpannableStringBuilder(mBindStr);
            ssb.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_FEED00)), 10, mBindStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mBindNumView.setText(ssb);
            GlideUtil.getInstance().loadCircle(getContext(), mBindUserBean.getAvatar(), mHeadView, R.mipmap.default_head);
            mNickView.setText(mBindUserBean.getNickname());
            mNumView.setText("ID： " + mBindUserBean.getUserCode());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bind_invitation_code_layout_bind_buttom:
                CommonScene.addBindUser(mBindUserBean.getUserCode(), mBindUserBean.getUnionid(), new BaseObserver<String>() {
                    @Override
                    public void OnSuccess(String s) {
                        ToastUtil.show("绑定成功");
                        dismiss();
                    }

                    @Override
                    public void OnFail(int code, String err) {
                        super.OnFail(code, err);
                    }
                });
                break;
            case R.id.bind_invitation_close_button:
                dismiss();
        }
    }

}
