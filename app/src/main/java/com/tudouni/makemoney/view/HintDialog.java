package com.tudouni.makemoney.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tudouni.makemoney.R;


public class HintDialog extends Dialog implements View.OnClickListener {


    /**
     * 上下文对象 *
     */
    private Context context;

    private OnDialogClickLinstener linstener;


    private TextView tvOk, tvno;
    private TextView tvMsg;

    private String msg;

    private String mBtnPositiveText = "确定";
    private String mBtnNegativeText = "取消";


    public HintDialog(Context context) {
        super(context, R.style.base_dialog);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.tip_dialog_ok);
        tvOk = (TextView) findViewById(R.id.tvOk);
        tvno = (TextView) findViewById(R.id.tvno);
        tvMsg = (TextView) findViewById(R.id.tvMsg);

        tvno.setOnClickListener(this);
        tvOk.setOnClickListener(this);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);
        tvOk.setText(mBtnPositiveText);
        tvno.setText(mBtnNegativeText);
    }

    @Override
    public void show() {
        super.show();
        tvMsg.setText(msg);
    }

    public void setMessage(String text) {
        msg = text;
    }

    public void setPositiveText(String text) {
        mBtnPositiveText = text;
    }

    public void setNegativeText(String text) {
        mBtnNegativeText = text;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tvOk) {
            if (null != linstener) {
                linstener.onPositiveClick();
                dismiss();
            }

        } else if (i == R.id.tvno) {
            if (null != linstener) {
                linstener.onNegativeClick();
                dismiss();
            }

        } else {
        }
    }

    public OnDialogClickLinstener getLinstener() {
        return linstener;
    }

    public void setLinstener(OnDialogClickLinstener linstener) {
        this.linstener = linstener;
    }

    public interface OnDialogClickLinstener {
        void onPositiveClick();

        void onNegativeClick();
    }

}
