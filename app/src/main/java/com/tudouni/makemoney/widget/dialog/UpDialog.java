package com.tudouni.makemoney.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.tudouni.makemoney.R;
import com.tudouni.makemoney.utils.ToastUtil;
import com.tudouni.makemoney.widget.versionUpdate.Upinfo;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class UpDialog extends Dialog implements View.OnClickListener {


    /**
     * 上下文对象 *
     */
    Activity context;

    BtnClickLinstener linstener;


    TextView tvOk;
    TextView tvCancel;


    TextView tvMsg;
    TextView tvForceUp;
    TextView tvTitle;
    LinearLayout llUp;
    RelativeLayout llForceUp;
    ProgressBar seekBar;

    private Upinfo info;
    private int max = 0;
    private boolean installState = true;//安装状态，true可安装，false不可安装


    public UpDialog(Activity context) {
        super(context);
        this.context = context;

    }

    public UpDialog(Activity context, int theme, Upinfo info) {
        super(context, theme);
        this.context = context;
        this.info = info;
        this.setCancelable(false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.up_dialog);
        tvOk = (TextView) findViewById(R.id.tvOk);
        tvForceUp = (TextView) findViewById(R.id.tvForceUp);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvMsg = (TextView) findViewById(R.id.tvMsg);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        llForceUp = (RelativeLayout) findViewById(R.id.llForceUp);
        llUp = (LinearLayout) findViewById(R.id.llUp);
        seekBar = (ProgressBar) findViewById(R.id.seekBar);
        tvOk.setOnClickListener(this);
        llForceUp.setOnClickListener(this);
        llForceUp.setEnabled(false);
        tvCancel.setOnClickListener(this);
        tvForceUp.setOnClickListener(this);
    }

    public void setclickable(boolean isclick, String txt) {
        llForceUp.setEnabled(isclick);
        tvForceUp.setText(txt);
    }

    @Override
    public void show() {
        super.show();
        if (null != info) {
            if ("1".equals(info.getAlert())) {
                //禁用返回键
                llUp.setVisibility(View.GONE);
                llForceUp.setVisibility(View.VISIBLE);
            } else if ("2".equals(info.getAlert())) {
                llUp.setVisibility(View.VISIBLE);
                llForceUp.setVisibility(View.GONE);
            }
//            else if("0".equals(info.getAlert())){
//                //正常提醒
//                llUp.setVisibility(View.VISIBLE);
//                llForceUp.setVisibility(View.GONE);
//            }
            tvTitle.setText(info.getTitle());
            String desc = info.getDesc();

            tvMsg.setText(desc.replace("|", "\n"));
        }

    }


    @Override
    public void dismiss() {
        super.dismiss();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llForceUp:
                if (null != linstener) {
                    linstener.clickinstall();
                }
                break;

            case R.id.tvOk:
                llUp.setVisibility(View.GONE);
                llForceUp.setVisibility(View.VISIBLE);
                if (null != linstener) {
                    linstener.clickOk();
                }
                break;
            case R.id.tvCancel:
                dismiss();
                if (null != linstener) {
                    linstener.clickCancel();
                }
                break;
            case R.id.tvForceUp:
                if (null != linstener && installState) {
                    linstener.clickOk();
                } else if (!installState) {
                    if (info != null && info.getAlert().equals("1"))
                        System.exit(0);
                    else
                        dismiss();
                }
                break;
            default:
                break;
        }
    }

    public BtnClickLinstener getLinstener() {
        return linstener;
    }

    public void setLinstener(BtnClickLinstener linstener) {
        this.linstener = linstener;
    }

    public void setMax(int max) {
        seekBar.setMax(max);
        this.max = max;
    }

    public void setProgress(int progress) {
        seekBar.setProgress(progress);
        BigDecimal a = new BigDecimal(progress);
        BigDecimal c = a.multiply(new BigDecimal(100));
        BigDecimal b = new BigDecimal(max);
        tvForceUp.setText("已下载" + c.divide(b, RoundingMode.UP) + "%");
        tvOk.setText("已下载" + c.divide(b, RoundingMode.UP) + "%");
    }


    public void setBtnDisable() {
        seekBar.setVisibility(View.VISIBLE);
        tvForceUp.setClickable(false);
        tvForceUp.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));


        tvForceUp.setClickable(false);
        tvOk.setBackgroundColor(context.getResources().getColor(R.color.gray02));

        tvForceUp.setClickable(false);
        tvCancel.setBackgroundColor(context.getResources().getColor(R.color.gray02));


    }

    public void setErrorProgress() {
        seekBar.setProgress(0);
        seekBar.setVisibility(View.GONE);
        tvForceUp.setClickable(true);
        tvForceUp.setBackground(context.getResources().getDrawable(R.drawable.radius5dp_color_primary));
        tvForceUp.setText("点击重试");
        tvForceUp.setClickable(true);
        tvOk.setBackground(context.getResources().getDrawable(R.drawable.radius5dp_color_primary));

        tvForceUp.setClickable(true);
        tvCancel.setBackground(context.getResources().getDrawable(R.drawable.radius5dp_color_primary));
    }

    @Override
    public void onBackPressed() {
        if ("2".equals(info.getAlert())) {
            super.onBackPressed();
            return;
        }
        ToastUtil.show(context, "请更新应用程序在使用");
        super.onBackPressed();
    }

    /**
     * 安装失败
     */
    public void installError(String errortTip) {
        installState = false;
        seekBar.setProgress(0);
        seekBar.setVisibility(View.GONE);
        llUp.setVisibility(View.GONE);
        if (tvMsg != null)
            tvMsg.setText(errortTip);
        tvForceUp.setBackground(context.getResources().getDrawable(R.drawable.radius5dp_color_primary));
        tvForceUp.setText("确定");
        tvForceUp.setClickable(true);
    }

    public interface BtnClickLinstener {
        void clickOk();

        void clickCancel();

        void clickinstall();
    }
}
