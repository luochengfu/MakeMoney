package com.tudouni.makemoney.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tudouni.makemoney.R;


public class Tip_dialog extends Dialog implements View.OnClickListener {

    /**
     * 上下文对象 *
     */
    private Context context;
    private BtnClickLinstener linstener;
    private TextView tvOk;
    private TextView tvMsg;
    private String msg;
    private boolean cancelable = true;

    public Tip_dialog(Context context) {
        this(context, null, true);
    }

    public Tip_dialog(Context context, String msg) {
        this(context, msg, true);
    }

    public Tip_dialog(Context context, String msg, boolean cancelable) {
        super(context, R.style.loading_dialog);
        this.context = context;
        this.msg = msg;
        this.cancelable = cancelable;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.tip_dialog);
        tvOk = (TextView) findViewById(R.id.tvOk);
        tvMsg = (TextView) findViewById(R.id.tvMsg);
        tvOk.setOnClickListener(this);
        setCanceledOnTouchOutside(cancelable);
        setCancelable(cancelable);
    }

    @Override
    public void show() {
        super.show();
        tvMsg.setText(msg);
    }


    @Override
    public void dismiss() {
        super.dismiss();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvOk:
                if (linstener != null) {
                    linstener.clickOk();
                }
                dismiss();
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

    public interface BtnClickLinstener {
        void clickOk();

        void clickCancel();
    }
}
