package com.tudouni.makemoney.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.model.Invite;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.CommonUtil;
import com.tudouni.makemoney.utils.ForwardUtils;
import com.tudouni.makemoney.utils.InjectView;
import com.tudouni.makemoney.utils.ScreenUtils;
import com.tudouni.makemoney.utils.TuDouLogUtils;
import com.tudouni.makemoney.utils.glideUtil.GlideUtil;
import com.tudouni.makemoney.view.ShapeImageView;
import com.tudouni.makemoney.widget.sharePart.model.Share;
import com.umeng.analytics.MobclickAgent;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.net.URLEncoder;

/**
 * 面对面
 *
 * @author ZhangPeng
 */
public class FaceToFaceActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(id = R.id.iv_head)
    ShapeImageView mUserHead;
    @InjectView(id = R.id.im_user_gender)
    ImageView mImUserGender;
    @InjectView(id = R.id.tv_userName)
    TextView mTvUserName;
    @InjectView(id = R.id.tv_userCode)
    TextView mTvUserCode;
    @InjectView(id = R.id.im_qr_code)
    ImageView mImQrCode;
    @InjectView(id = R.id.ll_sao, onClick = true)
    LinearLayout llSao;
    @InjectView(id = R.id.back, onClick = true)
    ImageView back;

    @InjectView(id = R.id.ly_invitation_info)
    LinearLayout mLyInvitationInfo;
    @InjectView(id = R.id.tv_inv_code)
    TextView mTvInvCode;
    @InjectView(id = R.id.tv_time)
    TextView mTvTime;
    @InjectView(id = R.id.iv_picture)
    ImageView iv_picture;

    private String mesg = "";
    public static final int REQUEST_CODE = 469;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_to_face);
        try {
            mesg = String.format(Share.getShareInviteUrl(), MyApplication.getLoginUser().getInvistCode(), URLEncoder.encode(MyApplication.getLoginUser().getNickName(), "utf-8"), MyApplication.getLoginUser().getUnionid());
        } catch (Exception e) {
            TuDouLogUtils.e("FaceToFaceActivity", "二维码链接报错 e:" + e.getMessage());
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        CommonScene.getBind(new BaseObserver<Invite>() {
            @Override
            public void OnFail(int code, String err) {
                mLyInvitationInfo.setVisibility(View.GONE);
            }

            @Override
            public void OnSuccess(Invite invite) {
                if (invite == null) return;
                mLyInvitationInfo.setVisibility((invite == null) ? View.GONE : View.VISIBLE);
                GlideUtil.getInstance().loadCircle(FaceToFaceActivity.this, invite.getPhoto(), iv_picture, R.mipmap.default_head2);
                mTvTime.setText(invite.getInviteTime());
                mTvInvCode.setText(invite.getInviteCode());
            }
        });
        initView();
    }

    private void initView() {
        int mar = CommonUtil.dp2px(this, 50);
        int w = ScreenUtils.getScreenWidth(this) - mar * 2;
        Glide.with(this).load(MyApplication.getLoginUser().getPhoto()).error(R.mipmap.default_head2).fallback(R.mipmap.default_head2).into(mUserHead);
        mImUserGender.setImageResource(("1".equals(MyApplication.getLoginUser().getSex())) ? R.mipmap.public_gender_man : R.mipmap.public_gender_woman);
        mTvUserName.setText(MyApplication.getLoginUser().getNickName());
        mTvUserCode.setText(MyApplication.getLoginUser().getUnumber());
        mImQrCode.setImageBitmap(CodeUtils.createImage(mesg, w / 2, w / 2, null));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.ll_sao:
                MobclickAgent.onEvent(this, "me_inf2fs");
                Intent intent = new Intent(this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    if (result.startsWith("tudouni://tudouni/qrgroup?gid=")) {
//                        String s = result.substring(result.indexOf("=") + 1, result.length());
//                        startActivity(GroupInfoActivity.newIntent(this, s));
//                    } else if (result.startsWith(Constant.FRENDINFO + "?uid=") || result.startsWith(Constant.UER_CENTER + "?uid=")) {
//                        String uid = result.substring(result.indexOf("=") + 1, result.length());
//                        ForwardUtils.toFrendcenter(this, uid);
                    } else if (MyApplication.appConfig.isShareInvistor(result)) {
                        //绑定界面
                        ForwardUtils.target(this, result);
                    }
                    TuDouLogUtils.e("", "扫描结果：" + result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(this, "未发现土豆泥二维码", Toast.LENGTH_LONG).show();
                }
            }
        }

    }
}
