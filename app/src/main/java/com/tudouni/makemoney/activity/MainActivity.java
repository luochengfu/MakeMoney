package com.tudouni.makemoney.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.content.PermissionChecker;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.activity.search.SearchActivity;
import com.tudouni.makemoney.fragment.mall.MallFragment;
import com.tudouni.makemoney.fragment.MainTabThreeFragment;
import com.tudouni.makemoney.fragment.category.GoodCategoryFragment;
import com.tudouni.makemoney.fragment.MineFragment;
import com.tudouni.makemoney.model.BindUserBean;
import com.tudouni.makemoney.model.LogOut;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.Constants;
import com.tudouni.makemoney.utils.ForwardUtils;
import com.tudouni.makemoney.view.BindInvitationCodeDialog;
import com.tudouni.makemoney.view.MyStatusBar;
import com.tudouni.makemoney.view.SearchGoodDialog;
import com.tudouni.makemoney.view.Tip_dialog;
import com.tudouni.makemoney.widget.versionUpdate.UpdateAPKUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    //权限码请求code
    private final int PERMISSION_REQUEST_CODE = 123;
    private final int PERMISSION_REQUEST_CODE_VIDEO = 124;
    private RelativeLayout tab1, tab2, tab3, tab4;
    private TextView tabTV1, tabTV2, tabTV3, tabTV4;
    private MallFragment mTabOne;
    private GoodCategoryFragment mTabTwo;
    private MainTabThreeFragment mTabThree;
    private MineFragment mTabMine;
    private long lastTipTimeMills = 0L;
    //  Fragment事务
    private FragmentTransaction mTransaction = null;

    private final String[] permissionManifest = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private final int[] noPermissionTip = {
            R.string.no_camera_permission,
            R.string.no_record_audio_permission,
            R.string.no_read_phone_state_permission,
            R.string.no_write_external_storage_permission,
            R.string.no_read_external_storage_permission
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDatas();
        initPermissions();
        EventBus.getDefault().register(this);
        UpdateAPKUtil.uptateAPK(this);
    }

    private void initView() {
        tab1 = (RelativeLayout) findViewById(R.id.tab1);
        tab2 = (RelativeLayout) findViewById(R.id.tab2);
        tab3 = (RelativeLayout) findViewById(R.id.tab3);
        tab4 = (RelativeLayout) findViewById(R.id.tab4);
        tabTV1 = (TextView) findViewById(R.id.gt_home);
        tabTV2 = (TextView) findViewById(R.id.gt_im);
        tabTV3 = (TextView) findViewById(R.id.gt_mall);
        tabTV4 = (TextView) findViewById(R.id.gt_mine);
    }

    private void initDatas() {
        mTabOne = new MallFragment();
        mTabTwo = new GoodCategoryFragment();
        mTabThree = new MainTabThreeFragment();
        mTabMine = new MineFragment();

        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);

        // 把第一个tab设为选中状态
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                setTabSelection(0);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 跳转控制
     */
    public void setTabSelection(int index) {
        if (isFinishing() || isDestroyed())
            return;
        mTransaction = getSupportFragmentManager().beginTransaction();
        hideFragments(mTransaction);
        switch (index) {
            case 0:  //首页
                if (mTabOne != null) {
                    if (!mTabOne.isAdded()) {
                        mTransaction.add(R.id.content, mTabOne);
                    }
                    mTransaction.show(mTabOne);
                    addSelection(0);
                }

                break;
            case 1:  //商城
                if (mTabTwo != null) {
                    if (!mTabTwo.isAdded()) {
                        mTransaction.add(R.id.content, mTabTwo);
                    }
                    mTransaction.show(mTabTwo);
                    addSelection(1);
                }
                break;
            case 2:  //关注
                if (mTabThree != null) {
                    if (!mTabThree.isAdded()) {
                        mTransaction.add(R.id.content, mTabThree);
                    }
                    mTransaction.show(mTabThree);
                    addSelection(2);
                }
                break;
            case 3: //我的
                if (mTabMine != null) {
                    if (!mTabMine.isAdded()) {
                        mTransaction.add(R.id.content, mTabMine);
                    }
                    mTransaction.show(mTabMine);
                    addSelection(3);
                }
                break;
        }
        //防止一个状态丢失崩溃.
        mTransaction.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (mTabOne != null) {
            transaction.hide(mTabOne);
        }
        if (mTabTwo != null) {
            transaction.hide(mTabTwo);
        }
        if (mTabThree != null) {
            transaction.hide(mTabThree);
        }
        if (mTabMine != null) {
            transaction.hide(mTabMine);
        }
    }

    private void addSelection(int index) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1.1f, 1, 1.1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(300);
        ScaleAnimation animation = new ScaleAnimation(
                1.1f, 1, 1.1f, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(300);
        //将AlphaAnimation这个已经设置好的动画添加到 AnimationSet中
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(animation);

        //启动动画
        if (index == 0) {
            tab1.setSelected(true);
            tab1.startAnimation(animationSet);
            tabTV1.setTextColor(getResources().getColor(R.color.color_F23648));
            tabTV2.setTextColor(getResources().getColor(R.color.color_999999));
            tabTV3.setTextColor(getResources().getColor(R.color.color_999999));
            tabTV4.setTextColor(getResources().getColor(R.color.color_999999));

            tab2.setSelected(false);
            tab3.setSelected(false);
            tab4.setSelected(false);
        } else if (index == 1) {
            tab2.setSelected(true);
            tab2.startAnimation(animationSet);
            tabTV2.setTextColor(getResources().getColor(R.color.color_F23648));
            tabTV1.setTextColor(getResources().getColor(R.color.color_999999));
            tabTV3.setTextColor(getResources().getColor(R.color.color_999999));
            tabTV4.setTextColor(getResources().getColor(R.color.color_999999));

            tab1.setSelected(false);
            tab3.setSelected(false);
            tab4.setSelected(false);
        } else if (index == 2) {
            tab3.setSelected(true);
            tab3.startAnimation(animationSet);
            tabTV3.setTextColor(getResources().getColor(R.color.color_F23648));
            tabTV2.setTextColor(getResources().getColor(R.color.color_999999));
            tabTV1.setTextColor(getResources().getColor(R.color.color_999999));
            tabTV4.setTextColor(getResources().getColor(R.color.color_999999));

            tab1.setSelected(false);
            tab2.setSelected(false);
            tab4.setSelected(false);
        } else if (index == 3) {
            tab4.setSelected(true);
            tab4.startAnimation(animationSet);
            tabTV4.setTextColor(getResources().getColor(R.color.color_F23648));
            tabTV2.setTextColor(getResources().getColor(R.color.color_999999));
            tabTV3.setTextColor(getResources().getColor(R.color.color_999999));
            tabTV1.setTextColor(getResources().getColor(R.color.color_999999));

            tab1.setSelected(false);
            tab2.setSelected(false);
            tab3.setSelected(false);
        }
    }

    @Subscriber(tag = "clear", mode = ThreadMode.MAIN)
    private void logout(final LogOut logout) {
        if (logout.getShowDialog()) {
            Tip_dialog confirmDialog = new Tip_dialog(MyApplication.sCurrActivity, logout.getMsg(), false);
            confirmDialog.setLinstener(new Tip_dialog.BtnClickLinstener() {

                @Override
                public void clickOk() {
                    doLogOut();
                }

                @Override
                public void clickCancel() {

                }
            });
            confirmDialog.show();
        } else {
            doLogOut();
        }
    }

    private BindInvitationCodeDialog dialogCode;

    @Subscriber(tag = "bind_code_action", mode = ThreadMode.MAIN)
    private void bindCodeAction(BindUserBean bindUserBean) {
        if (MyApplication.sCurrActivity.getClass().getName().startsWith("com.tudouni") && !(MyApplication.sCurrActivity instanceof SearchGoodActivity) && !(MyApplication.sCurrActivity instanceof LoginActivity) &&
                !(MyApplication.sCurrActivity instanceof TelLoginActivity) && !(MyApplication.sCurrActivity instanceof PwdActivity) &&
                !(MyApplication.sCurrActivity instanceof SplashActivity) && !(MyApplication.sCurrActivity instanceof BindInvitationCodeActivity)) {
            try {
                if (dialogCode != null && dialogCode.isShowing()) {
                    dialogCode.dismiss();
                }
                dialogCode = new BindInvitationCodeDialog(MyApplication.sCurrActivity, bindUserBean, false);
                dialogCode.show();
            } catch (Exception e) {
            }
        }
    }

    private SearchGoodDialog dialog = null;

    @Subscriber(tag = "search_good_action", mode = ThreadMode.MAIN)
    private void searchGoodAction(String url) {
        if (MyApplication.sCurrActivity.getClass().getName().startsWith("com.tudouni") && !(MyApplication.sCurrActivity instanceof SearchGoodActivity) && !(MyApplication.sCurrActivity instanceof LoginActivity) &&
                !(MyApplication.sCurrActivity instanceof TelLoginActivity) && !(MyApplication.sCurrActivity instanceof PwdActivity) &&
                !(MyApplication.sCurrActivity instanceof SplashActivity) && !(MyApplication.sCurrActivity instanceof BindInvitationCodeActivity)) {
            try {
                String searchStr = "";
                if (dialog != null && dialog.isShowing()) {
                    if (dialog.getDialogContext() != MyApplication.sCurrActivity) {
                        dialog.dismiss();
                    } else
                        return;
                }


                if (null != url || !"".equals(url)) {
                    searchStr = url;
                }

                dialog = new SearchGoodDialog(MyApplication.sCurrActivity, searchStr, false);
                dialog.show();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTipTimeMills > 1000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            lastTipTimeMills = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    /**
     * 退出登录
     */
    private void doLogOut() {
//        try {
//            finish();
//            MyApplication.logout();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        ForwardUtils.target(this, Constants.LOGIN);
        MyApplication.logout();
        setTabSelection(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab1://主页
                MobclickAgent.onEvent(this, "hp_live");
                setTabSelection(0);
                break;
            case R.id.tab2://商城
                MobclickAgent.onEvent(this, "hp_mall");
                setTabSelection(1);
                break;
            case R.id.tab3://关注
                MobclickAgent.onEvent(this, "hp_focus");
                setTabSelection(2);
                break;
            case R.id.tab4://我的
                if (MyApplication.needToLogin()) {
                    ForwardUtils.target(MainActivity.this, Constants.NEW_LOGIN);
                } else {
                    MobclickAgent.onEvent(this, "hp_me");
                    setTabSelection(3);
                }
                break;
        }
    }

    /**
     * 初始化权限
     */
    private void initPermissions() {
        if (!permissionCheck()) {
            if (Build.VERSION.SDK_INT >= 23) {
                ActivityCompat.requestPermissions(this, permissionManifest,
                        PERMISSION_REQUEST_CODE);
            }
        }
    }

    /**
     * 权限检查（适配6.0以上手机）
     */
    private boolean permissionCheck() {
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        String permission;
        for (int i = 0; i < permissionManifest.length; i++) {
            permission = permissionManifest[i];
            if (PermissionChecker.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionCheck = PackageManager.PERMISSION_DENIED;
            }
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
