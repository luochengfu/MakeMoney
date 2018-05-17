package com.tudouni.makemoney.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.utils.InjectView;

public class ConfirmDialog extends Dialog {
    private BtnClickLinstener linstener;
    private String title;
    private String msg;
    private TextView tvTitle;
    private TextView tvMsg;
    private TextView tvSubmit;

    public ConfirmDialog(Context context,String title, String msg) {
        super(context);
        this.title = title;
        this.msg = msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_comfirm);
        initview();
    }

    private void initview() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvMsg = (TextView) findViewById(R.id.tvMsg);
        tvSubmit = (TextView) findViewById(R.id.tvSubmit);
        if (null != title) {
            tvTitle.setText(title);
        }
        if (null != msg) {
            tvMsg.setText(msg);
        }
        tvSubmit.setOnClickListener(view -> {
            if (null != linstener) {
                linstener.clickOk();
            }
        });
    }

    public BtnClickLinstener getLinstener() {
        return linstener;
    }

    public void setLinstener(BtnClickLinstener linstener) {
        this.linstener = linstener;
    }

    public interface BtnClickLinstener {
        void clickOk();
    }
}
