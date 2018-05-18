package com.tudouni.makemoney.activity.withdrawmoney;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tudouni.makemoney.BuildConfig;
import com.tudouni.makemoney.R;
import com.tudouni.makemoney.activity.BaseActivity;
import com.tudouni.makemoney.activity.realname.RealnameActivity2;
import com.tudouni.makemoney.model.User;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.Constants;
import com.tudouni.makemoney.utils.ForwardUtils;
import com.tudouni.makemoney.utils.InjectView;
import com.tudouni.makemoney.view.MyTitleBar;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;


public class WithdrawMoneyActivity extends BaseActivity {
    @InjectView(id = R.id.etMoneyNumber)
    private EditText etMoneyNumber;
    @InjectView(id = R.id.tvMaxNumber)
    private TextView tvMaxNumber;
    @InjectView(id = R.id.tvAllNumber)
    private TextView tvAllNumber;
    @InjectView(id = R.id.tvNextStep)
    private TextView tvNextStep;
    @InjectView(id = R.id.tvPayRate)
    private TextView tvPayRate;

    private double allNumber;
    private String payRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_money);
        Bundle extras = null;
        try {
            extras = getIntent().getExtras();
        } catch (Exception e) {
            finish();
            return;
        }
        if (extras == null) {
            finish();
            return;
        }
        allNumber = extras.getDouble("balance");
        initview();
        initData();
    }

    private void initview() {
        /**
         * 限制只能输入金额
         */
        class InputMoney implements InputFilter {

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                if(source.equals(".") && dest.toString().length() == 0){
                    return "0.";
                }
                if(dest.toString().contains(".")){
                    int index = dest.toString().indexOf(".");
                    int mlength = dest.toString().substring(index).length();
                    if(mlength == 3){
                        return "";
                    }
                }
                return null;
            }
        }

        //设置过滤器
        etMoneyNumber.setFilters(new InputFilter[]{new InputMoney()});

        etMoneyNumber.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
                       {
                           public void run()
                           {
                               InputMethodManager inputManager = (InputMethodManager)etMoneyNumber.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(etMoneyNumber, 0);
                           }
                       },
                200);

        tvMaxNumber.setText("可提现金额" + allNumber + "元");

        tvAllNumber.setOnClickListener(view -> {
            DecimalFormat df = new DecimalFormat("##.##");
            etMoneyNumber.setText(df.format(allNumber));
        });

        tvNextStep.setOnClickListener(view -> {
            try {
                String moneyNumber = etMoneyNumber.getText().toString().trim();
                double doubleNumber = Double.parseDouble(moneyNumber);
                if (doubleNumber < 10.0) {
                    Toast.makeText(WithdrawMoneyActivity.this, "提现金额应该大于10元", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(WithdrawMoneyActivity.this, TelAuthenticationActivity.class);
                intent.putExtra("moneyNumber", moneyNumber);
                WithdrawMoneyActivity.this.startActivity(intent);
                finish();
            } catch (Exception e) {}
        });
    }

    private void initData() {
        CommonScene.payRate(new BaseObserver<String>() {
            @Override
            public void OnSuccess(String s) {
                payRate = s;
                String payRateText = "提示：提现含" + payRate + "%的手续费，将从提现金额中扣除，手续费由支付通道收取，赚赚不收取";
                tvPayRate.setText(payRateText);
            }

            @Override
            public void OnFail(int code, String err) {
                Toast.makeText(WithdrawMoneyActivity.this, "获取手续费率失败，不能够提现", Toast.LENGTH_SHORT).show();
                super.OnFail(code, err);
            }
        });
    }
}
