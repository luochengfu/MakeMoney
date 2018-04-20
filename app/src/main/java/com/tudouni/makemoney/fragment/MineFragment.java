package com.tudouni.makemoney.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.utils.BitMapUtils;
import com.tudouni.makemoney.utils.Constants;
import com.tudouni.makemoney.utils.ForwardUtils;
import com.tudouni.makemoney.utils.InjectView;
import com.tudouni.makemoney.utils.TuDouLogUtils;
import com.tudouni.makemoney.utils.TuDouTextUtil;
import com.tudouni.makemoney.utils.base.ACache;
import com.tudouni.makemoney.utils.glideUtil.GlideUtil;
import com.umeng.analytics.MobclickAgent;


/**
 * v3.0 我的UI调整
 * Created by huang on 2017/12/7.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(id = R.id.ivPhoto)
    ImageView ivPhoto;
    @InjectView(id = R.id.tvName)
    private TextView tvName;
    @InjectView(id = R.id.im_user_gender)
    private ImageView mImUserGender;
    @InjectView(id = R.id.tvAccount)
    private TextView tvAccount;
    @InjectView(id = R.id.tv_fans_count)
    private TextView tv_fans_count;
    @InjectView(id = R.id.tv_follow_count)
    private TextView tv_follow_count;
    @InjectView(id = R.id.tv_dynamic_count)
    private TextView tv_dynamic_count;
    @InjectView(id = R.id.tv_chat_num)
    private TextView tv_chat_num;
    @InjectView(id = R.id.tv_chat_dot)
    private View tv_chat_dot;
    @InjectView(id = R.id.tv_balance)
    private TextView tv_balance;
    @InjectView(id = R.id.tv_earn_today)
    private TextView tv_earn_today;
    @InjectView(id = R.id.tv_earn_month)
    private TextView tv_earn_month;
    @InjectView(id = R.id.tv_doufen_count)
    private TextView mTvDoufenCount;//我的豆粉个数
    @InjectView(id = R.id.tv_mine_invitation_count)
    private TextView mTvMineInvitationCount;//我的邀请中豆粉个数
    @InjectView(id = R.id.tv_shop_level)
    private TextView mTvShopLevel;//我的商城等级
    @InjectView(id = R.id.ly_mine_invitation, onClick = true)
    private LinearLayout mLyMineInvitation;//我的邀请码
    @InjectView(id = R.id.ly_doufen, onClick = true)
    private LinearLayout mLyDoufen;//我的豆粉
    private Bitmap mPotatoesBitmap;//海报

    private boolean canClick = true;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            canClick = true;
        }
    };


    @Override
    protected int getContentView() {
//        return R.layout.fragment_user_ex;
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view) {
        setOnclickToView(view);
//        EventBus.getDefault().register(this);
    }

    private void setOnclickToView(View view) {
        view.findViewById(R.id.iv_setting).setOnClickListener(this);
        view.findViewById(R.id.ly_shop_level).setOnClickListener(this);
        view.findViewById(R.id.ivPhoto).setOnClickListener(this);
        view.findViewById(R.id.ly_follows).setOnClickListener(this);
        view.findViewById(R.id.ly_fans).setOnClickListener(this);
        view.findViewById(R.id.ly_chat).setOnClickListener(this);
        view.findViewById(R.id.ly_invitation_douyou).setOnClickListener(this);

//        view.findViewById(R.id.ly_mine_shop).setOnClickListener(this);
        view.findViewById(R.id.ly_earn_month).setOnClickListener(this);
        view.findViewById(R.id.ly_earn_today).setOnClickListener(this);
        view.findViewById(R.id.ly_tv_balance).setOnClickListener(this);
        view.findViewById(R.id.llMyOrder).setOnClickListener(this);

        view.findViewById(R.id.ly_share_wx).setOnClickListener(this);
        view.findViewById(R.id.ly_share_weixin_circle).setOnClickListener(this);
        view.findViewById(R.id.ly_share_qq).setOnClickListener(this);
        view.findViewById(R.id.ly_share_face_to_face).setOnClickListener(this);
        view.findViewById(R.id.ly_new_user_raiders).setOnClickListener(this);
        view.findViewById(R.id.ly_common_problem).setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        EventBus.getDefault().unregister(this);
        try {
            if (mPotatoesBitmap != null && !mPotatoesBitmap.isRecycled())
                mPotatoesBitmap.recycle();
        } catch (Exception e) {
            TuDouLogUtils.e("", "海报释放报错");
        }
    }

    @Override
    protected void initData() {
//        User user = MyApplication.getLoginUser();
//        if (user != null) {
//            GlideUtil.getInstance().loadCircle(getContext(), user.getPhoto(), ivPhoto, R.mipmap.default_head);
//            tvName.setText(user.getNickName());
//            tvAccount.setText(String.valueOf("ID " + user.getUnumber()));
//            tv_fans_count.setText(user.getFans());
//            tv_follow_count.setText(user.getFollows());
//            tv_dynamic_count.setText(user.getDynamicCount());
//            TuDouTextUtil.setTextToTextViewFormatWan(mTvTudoubiCount, user.getCoins());
//            mImUserGender.setImageResource(("1".equals(user.getSex())) ? R.mipmap.public_gender_man : R.mipmap.public_gender_woman);
//
//            TuDouTextUtil.setTextToTextViewFormatWan(mTvDoufenCount, user.getInviteCount() + "", false);
//            TuDouTextUtil.setTextToTextView(mTvMineInvitationCount, user.getInviteCount() + "");
//            TuDouTextUtil.setTextToTextView(mTvShopLevel, user.getAgentSeriesName());
//
//        }
//
//        getTopFansData(App.getLoginUser().getUid());

    }

    @Override
    public void onClick(final View view) {
        if (!canClick) return;
        canClick = false;
        handler.postDelayed(null, 500);
        doOnClick(view);
    }

    /**
     * 处理点击方法
     *
     * @param view
     */
    private void doOnClick(View view) {
        Intent intent;
        String statisticsType = null;
//        String para = "?uid=" + MyApplication.getLoginUser().getUid() + "&token=" + App.getLoginUser().getToken() + "&unionid=" + App.getLoginUser().getUnionid();
        switch (view.getId()) {
            case R.id.iv_setting://设置
                statisticsType = "me_set";
                ForwardUtils.target(getActivity(), Constants.SETTING);
                break;
//            case R.id.ivPhoto:      //点击用户头像
//                ForwardUtils.target(getActivity(), Constant.USERINFO);
//                break;
//            case R.id.ly_follows://我的关注
//                ForwardUtils.target(getActivity(), Constant.FOLLOW);
//                break;
//            case R.id.ly_fans://我的粉丝
//                ForwardUtils.target(getActivity(), Constant.FANS);
//                break;
//            case R.id.ly_chat:  //豆聊
//                intent = new Intent(getActivity(), DouIMActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.ly_invitation_code: //绑定邀请码
//                ForwardUtils.target(getActivity(), Constant.BINDING_INVITATION);
//                break;
//            case R.id.ly_invitation_douyou: //邀请豆友一起玩
//                statisticsType = "me_intogether";
//                ForwardUtils.target(getActivity(), Constant.INVISIT_POSTER);
//                break;
//            case R.id.ly_mine_shop: //商城收益
//            case R.id.ly_tv_balance: //商城余额
//                statisticsType = "me_balance";
//                intent = new Intent(getActivity(), FreeShopActivity.class);
//                intent.putExtra("tagUrl", Constant.H5_MALL_INCOME + para);
//                startActivity(intent);
//                break;
//            case R.id.ly_earn_today: //商城今日收益
//                statisticsType = "me_toincome";
//                intent = new Intent(getActivity(), FreeShopActivity.class);
//                intent.putExtra("tagUrl", Constant.H5_MALL_INCOME + para);
//                startActivity(intent);
//                break;
//            case R.id.ly_earn_month: //商城本月预估收益
//                statisticsType = "me_moincome";
//                intent = new Intent(getActivity(), FreeShopActivity.class);
//                intent.putExtra("tagUrl", Constant.H5_MALL_INCOME + para);
//                startActivity(intent);
//                break;
//            case R.id.llMyOrder: //商城我的订单
////                ForwardUtils.target(getActivity(), Constant.ALL_ORDER_URL + para + "&closeWebView=1");
//                statisticsType = "me_tran";
//                intent = new Intent(getActivity(), FreeShopActivity.class);
//                intent.putExtra("tagUrl", Constant.H5_MALL_DINGDAN + para + "&closeWebView=1&isPage=true");
//                startActivity(intent);
//                break;
//            case R.id.ly_shop_level://商城我的等级
//                statisticsType = "me_grade";
//                ForwardUtils.target(getActivity(), Constant.h5_mall_grade + para);
//                break;
//            case R.id.ly_share_wx:
//            case R.id.ly_share_weixin_circle:
//            case R.id.ly_share_qq:
//                doShare(view.getId());
//                break;
//            case R.id.ly_share_face_to_face://面对面
//                statisticsType = "me_inf2f";
//                ForwardUtils.target(getActivity(), Constant.BINDING_FACE_TO_FACE);
//                break;
//            case R.id.ly_mine_invitation://我的邀请
//                statisticsType = "me_inviate";
//                intent = new Intent(getActivity(), FreeShopActivity.class);
//                intent.putExtra("tagUrl", Constant.h5_doufen + para);
//                startActivity(intent);
//                break;
//            case R.id.ly_doufen://豆粉
////                ForwardUtils.target(getActivity(), Constant.h5_doufen + para);
//                statisticsType = "me_df";
//                intent = new Intent(getActivity(), FreeShopActivity.class);
//                intent.putExtra("tagUrl", Constant.h5_doufen + para);
//                startActivity(intent);
//                break;
//            case R.id.ly_new_user_raiders:
//                ForwardUtils.target(getActivity(), Constant.h5_novice + para);
//                break;
//            case R.id.ly_common_problem:
//                ForwardUtils.target(getActivity(), Constant.h5_faq + para);
//                break;
        }
        if (!TextUtils.isEmpty(statisticsType))
            MobclickAgent.onEvent(getContext(), statisticsType);
    }

    private void toShop() {
//        RxUtils.rx(com.doubozhibo.tudouni.shop.net.Api.getService().haveShop(), new OnNext<Response<Boolean>>() {
//            @Override
//            public void onNext(Response<Boolean> booleanResponse) {
//                Intent intent = new Intent();
//                intent.putExtra("appVersion", CommonHelper.getAppVersionName(App.getContext()));
//                intent.putExtra("deviceId", App.deviceId);
//                intent.putExtra("deviceModel", App.deviceModel);
//                intent.putExtra("osType", App.osType);
//                intent.putExtra("osVersion", App.osVersion);
//                String province = "";
//                String city = "";
//                if (App.sLocal != null) {
//                    province = App.sLocal.getProvince();
//                    city = App.sLocal.getCity();
//                }
//                intent.putExtra("province", province);
//                intent.putExtra("city", city);
//                if (booleanResponse.result) {
//                    intent.setClass(getActivity(), MyShopActivity.class);
//                } else {
//                    intent.setClass(getActivity(), CreateShopActivity.class);
//                }
//                startActivity(intent);
//            }
//        });
    }

    public void refresh() {
    }

    @Override
    public void onResume() {
        super.onResume();
        setOnclickToView(mContentView);
//        Map<String, String> params = new HashMap<String, String>();
//        RequestUtils.sendPostRequest(Api.GETUSERINFO, params, new ResponseCallBack<User2>() {
//            @Override
//            public void onSuccess(User2 user) {
//                super.onSuccess(user);
//                if (!App.getLoginUser().getNickName().equals(user.getNickName()) || null == user.getPhoto() || !App.getLoginUser().getPhoto().equals(user.getPhoto())) {
//                    if (TextUtils.isEmpty(user.getPhoto()) || user.getPhoto().equals("null")) {
//                        user.setPhoto(Constant.DEFALT_HEAD);
//                    }
//                    UserInfo ifo = new UserInfo(user.getUid(), user.getNickName(), Uri.parse(user.getPhoto()));
//                    AppUserInfoManager.getInstance().refreshUserInfo(ifo);
//                }
//                Logger.e("", user.toString());
//                saveLoginInfo(user);
//            }
//
//            @Override
//            public void onFailure(ServiceException e) {
//                super.onFailure(e);
////                ToastUtil.show("我的信息加载失败！（" + e.getCode() + ")");
//            }
//        });
//
//        CommonScene.getAgentInfo(new BaseObserver<AgentInfo>() {
//            @Override
//            public void OnSuccess(AgentInfo agentInfo) {
//                if (agentInfo != null) {
//                    TuDouTextUtil.setTextToTextView(tv_balance, (long) agentInfo.getBalance());
//                    TuDouTextUtil.setTextToTextView(tv_earn_today, (long) agentInfo.getTodayIncome());
//                    TuDouTextUtil.setTextToTextView(tv_earn_month, (long) agentInfo.getThisMonthExpectedIncome());
//                }
//            }
//
//            @Override
//            public void OnFail(int code, String err) {
////                ToastUtil.show("商城收益" + err + ":（" + code + ")");
//            }
//        });
//        sessionMsg(null);
//
    }

    /**
     * 分享数据
     *
     * @param viewId
     */
    private void doShare(int viewId) {
        String type = "me_inqq";
        if (viewId == R.id.ly_share_wx || viewId == R.id.ly_share_weixin_circle)
            type = (viewId == R.id.ly_share_wx) ? "me_inwx" : "me_infriend";
        MobclickAgent.onEvent(getContext(), type);

//        SHARE_MEDIA platform = SHARE_MEDIA.QQ;
//        if (viewId == R.id.ly_share_wx || viewId == R.id.ly_share_weixin_circle) {
//            type = (viewId == R.id.ly_share_wx) ? "me_inwx" : "me_infriend";
//            if (!CommonUtil.isWXInstall(getActivity())) {
//                ToastUtil.show(getActivity(), "请安装微信客户端");
//                return;
//            }
//            platform = (viewId == R.id.ly_share_wx) ? SHARE_MEDIA.WEIXIN : SHARE_MEDIA.WEIXIN_CIRCLE;
//        }
//        if (viewId == R.id.ly_share_qq) {
//            if (!CommonUtil.isWXInstall(getActivity())) {
//                ToastUtil.show(getActivity(), "请安装微信客户端");
//                return;
//            }
//        }
//        if (mPotatoesBitmap != null && !mPotatoesBitmap.isRecycled())
//            mPotatoesBitmap.recycle();
//        mPotatoesBitmap = BitMapUtils.createPotatoesBitMap(getContext());
//        Share mShare = Share.obtain(Share.Type.IMAGE_POTATOES, mPotatoesBitmap);
//        CommonHelper.shareURL2(getActivity(), true, platform, mShare, new ApiCallback<Share>() {
//            @Override
//            public void onSuccess(Share data) {
//                ToastUtils.showToast(getActivity(), "分享成功");
//            }
//
//            @Override
//            public void onFailure(ServiceException e) {
//                super.onFailure(e);
//                ToastUtils.showToast(getActivity(), "分享失败");
//            }
//        });
    }

//    private void saveLoginInfo(User2 user) {
//        User saveUser = user.toLoginUser();
//        App.saveLoginUser(saveUser);
//        initData();
//    }
}
