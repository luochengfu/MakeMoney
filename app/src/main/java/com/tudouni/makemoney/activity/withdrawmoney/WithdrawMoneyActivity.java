package com.tudouni.makemoney.activity.withdrawmoney;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.activity.BaseActivity;
import com.tudouni.makemoney.model.PayBindingInfo;
import com.tudouni.makemoney.network.CommonScene;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.InjectView;

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

    private double minNubmer;   // 提现最小金额限制
    private double maxNubmer;   // 提现最大金额限制

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

    @SuppressLint("SetTextI18n")
    private void initview() {
        //限制只能输入金额
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
                               try {
                                   InputMethodManager inputManager = (InputMethodManager)etMoneyNumber.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                   inputManager.showSoftInput(etMoneyNumber, 0);
                               } catch (Exception ignored) {}
                           }
                       },
                200);

        tvMaxNumber.setText("可提现金额" + allNumber + "元");

        tvAllNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DecimalFormat df = new DecimalFormat("##.##");
                    etMoneyNumber.setText(df.format(allNumber));
                    etMoneyNumber.setSelection(etMoneyNumber.getText().length());
                } catch (Exception ignored) {}
            }
        });

        tvNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String moneyNumber = etMoneyNumber.getText().toString().trim();
                    double doubleNumber = Double.parseDouble(moneyNumber);
                    if (doubleNumber > allNumber) {
                        Toast.makeText(WithdrawMoneyActivity.this, "输入金额超过了可提现金额", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (doubleNumber < minNubmer || doubleNumber > maxNubmer) {
                        Toast.makeText(WithdrawMoneyActivity.this, "提现金额范围为" + (int)minNubmer + "-" + (int)maxNubmer + "元", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent intent = new Intent(WithdrawMoneyActivity.this, TelAuthenticationActivity.class);
                    intent.putExtra("moneyNumber", moneyNumber);
                    WithdrawMoneyActivity.this.startActivity(intent);
                    finish();
                } catch (Exception ignored) {}
            }
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

        CommonScene.payStatus(new BaseObserver<PayBindingInfo>() {
            @Override
            public void OnSuccess(PayBindingInfo payBindingInfo) {
                minNubmer = payBindingInfo.getAlipayMinNumber();
                maxNubmer = payBindingInfo.getAlipayMaxNumber();
            }
        });
    }
}
