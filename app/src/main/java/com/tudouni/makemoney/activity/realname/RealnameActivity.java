package com.tudouni.makemoney.activity.realname;

import com.tudouni.makemoney.BuildConfig;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.activity.BaseActivity;
import com.tudouni.makemoney.model.BindInfo;
import com.tudouni.makemoney.model.User;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.Constants;
import com.tudouni.makemoney.utils.ForwardUtils;
import com.tudouni.makemoney.utils.InjectView;
import com.tudouni.makemoney.view.HintDialog;
import com.tudouni.makemoney.view.MyTitleBar;

import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;


public class RealnameActivity extends BaseActivity {
    @InjectView(id = R.id.title_bar)
    private MyTitleBar title_bar;
    @InjectView(id = R.id.tv_other)
    private TextView buttomTV;
    @InjectView(id = R.id.tv_prove)
    private TextView tv_prove;
    @InjectView(id = R.id.tv_kefu)
    private TextView tv_kefu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname);
        initview();
        initData();
    }

    private void initData() {
        new Handler().post(new Runnable() {
            public void run() {
                CommonScene.accountBind(new BaseObserver<BindInfo>() {
                    @Override
                    public void OnSuccess(BindInfo bindInfo) {
                        if (null != bindInfo) {
                            if ("0".equals(bindInfo.getPhone())) {  //没有绑定
                                User user = MyApplication.getLoginUser();
                                user.setBindPhoneStatus("1");
                                MyApplication.saveLoginUser(user);
                            } else {
                                MyApplication.getLoginUser().setBindPhoneStatus("0");  //已绑定
                                User user = MyApplication.getLoginUser();
                                user.setBindPhoneStatus("0");
                                MyApplication.saveLoginUser(user);
                            }
                        }
                    }

                    @Override
                    public void OnFail(int code, String err) {

                    }
                });
            }
        });
    }

    private void initview() {
        title_bar.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HintDialog hintDialog = new HintDialog(RealnameActivity.this);
                hintDialog.setMessage("确定退出实名认证吗？");
                hintDialog.setLinstener(new HintDialog.OnDialogClickLinstener() {
                    @Override
                    public void onPositiveClick() {
                        finish();
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
                hintDialog.show();
            }
        });

        tv_prove.setOnClickListener(view ->  {
            ForwardUtils.target(RealnameActivity.this, Constants.REALNAME2);
            finish();
        });

        buttomTV = (TextView) findViewById(R.id.tv_other);
        String hint = "开始认证即代表同意《土豆泥用户协议》";
        SpannableStringBuilder mBuilder = new SpannableStringBuilder(hint);
        int color = getApplication().getResources().getColor(R.color.color_feac4b);
        mBuilder.setSpan(new ForegroundColorSpan(color), 9, 18, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        buttomTV.setText(mBuilder);
        buttomTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForwardUtils.target(RealnameActivity.this, Constants.h5_loginXy);
            }
        });

        tv_kefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForwardUtils.target(RealnameActivity.this, Constants.UPLOAD_IDCARD);
                finish();
            }
        });
    }
}
