package com.tudouni.makemoney.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.model.Invite;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.BitMapUtils;
import com.tudouni.makemoney.utils.Constants;
import com.tudouni.makemoney.utils.DialogUtils;
import com.tudouni.makemoney.utils.ForwardUtils;
import com.tudouni.makemoney.utils.InjectView;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.utils.TuDouLogUtils;
import com.tudouni.makemoney.utils.base.AppUtils;
import com.tudouni.makemoney.utils.glideUtil.GlideUtil;
import com.tudouni.makemoney.view.MyTitleBar;
import com.tudouni.makemoney.widget.sharePart.ShareWindow_v3;
import com.tudouni.makemoney.widget.sharePart.model.Share;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.utils.CommonUtil;

/**
 * 邀请豆粉一起玩界面
 *
 * @author ZhangPeng
 */
public class InvitationDouFenActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(id = R.id.tv_invitation_code)
    TextView mTvInvitationCode;
    @InjectView(id = R.id.ly_invitation_info)
    LinearLayout mLyInvitationInfo;

    @InjectView(id = R.id.tv_inv_code)
    TextView mTvInvCode;
    @InjectView(id = R.id.tv_time)
    TextView mTvTime;
    @InjectView(id = R.id.iv_picture)
    ImageView iv_picture;
    @InjectView(id = R.id.im_potatoes)
    ImageView mImPotatoes;//海报
    private String mInvitationCode;//上界面传递过来的邀请code
    private Bitmap mPotatoesBitmap;//海报

    @InjectView(id = R.id.title_bar)
    MyTitleBar myTitleBar;//我的标题

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_dou_fen);
        initData();
        initView();
    }

    private void initData() {
//        mInvitationCode = getIntent().getStringExtra("code");
        mInvitationCode = (TextUtils.isEmpty(mInvitationCode)) ? (MyApplication.getLoginUser().getInvistCode()) : (mInvitationCode);
    }

    private void initView() {
        findViewById(R.id.bt_copy_invitation_code).setOnClickListener(this);
        findViewById(R.id.bt_share_link).setOnClickListener(this);
        findViewById(R.id.bt_share_poster).setOnClickListener(this);
        mTvInvitationCode.setText(mInvitationCode);
        myTitleBar.setRightText("规则");
        myTitleBar.setOnRightClickListener(this);
        mImPotatoes.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toSaveImage();
                return false;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_copy_invitation_code:
                MobclickAgent.onEvent(this, "me_indfcopy");
                if (!TextUtils.isEmpty(mTvInvitationCode.getText().toString())) {
                    String copyStr = MyApplication.getLoginUser().getNickName() + "邀请您加入赚赚，自动搜索淘宝天猫优惠券！先领券，再购物，更划算！\n" +
                            "---下载链接： http://url.cn/5HF5SYU -----\n" +
                            "复制邀请码： " + mTvInvitationCode.getText().toString() + " \n" +
                            "打开赚赚，注册领取优惠券";
                    AppUtils.copyToClipboard(InvitationDouFenActivity.this, copyStr);
                    showToast("复制成功");
                }
                break;
            case R.id.bt_share_link:
//                MobclickAgent.onEvent(this, "me_indfshare");
//                new ShareWindow_v3(this, Share.Type.INVITE, App.getLoginUser(), null, null).show(this);
                break;
            case R.id.bt_share_poster:
                MobclickAgent.onEvent(this, "me_indfshare2");
                if (mPotatoesBitmap == null && mPotatoesBitmap.isRecycled())
                    startRequest();
                Share share = Share.obtain(Share.Type.IMAGE_POTATOES, mPotatoesBitmap);
                share.setCanShareToQQ(false);
                new ShareWindow_v3(this, Share.Type.IMAGE_POTATOES, share, null, null).show(this);
//                shareWindow_v2.showPotatesLy();
//                shareWindow_v2.show(this);
                break;
            case R.id.right_layer:
                ForwardUtils.target(this, Constants.h5_inviterule);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startRequest();
    }

    private void startRequest() {
        mPotatoesBitmap = BitMapUtils.createPotatoesBitMap(this);
        mImPotatoes.setImageBitmap(mPotatoesBitmap);
        CommonScene.getBind(new BaseObserver<Invite>() {
            @Override
            public void OnFail(int code, String err) {
                mLyInvitationInfo.setVisibility(View.GONE);
            }

            @Override
            public void OnSuccess(Invite invite) {
                if (invite == null) return;
                mLyInvitationInfo.setVisibility((invite == null) ? View.GONE : View.VISIBLE);
                GlideUtil.getInstance().loadImage(InvitationDouFenActivity.this, invite.getPhoto(), iv_picture, R.mipmap.default_head2);
                mTvTime.setText(invite.getInviteTime());
                mTvInvCode.setText(invite.getInviteCode());
            }
        });
    }

    /**
     * 把一个view转化成bitmap对象
     */

    public Bitmap getViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (mPotatoesBitmap != null && !mPotatoesBitmap.isRecycled())
                mPotatoesBitmap.recycle();
        } catch (Exception e) {
            TuDouLogUtils.e("", "海报释放报错");
        }
    }

    /**
     * 保存图片
     */
    private void toSaveImage() {
        DialogUtils.showCommonDialog(this, "是否保存图片", "保存", "取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPotatoesBitmap == null && mPotatoesBitmap.isRecycled())
                    startRequest();
                BitMapUtils.saveIamge(InvitationDouFenActivity.this, mPotatoesBitmap, "我的土豆海报", new BitMapUtils.SaveImageCallBack() {
                    @Override
                    public void saveSucess() {
                        ToastUtil.show(InvitationDouFenActivity.this, "图片已保存到相册");
                    }

                    @Override
                    public void saveFail() {
                        ToastUtil.show(InvitationDouFenActivity.this, "保存失败");
                    }
                });
            }
        }, null);
    }
}
