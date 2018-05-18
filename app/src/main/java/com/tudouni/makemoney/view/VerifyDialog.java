package com.tudouni.makemoney.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.databinding.DialogVerifyBinding;
import com.tudouni.makemoney.utils.ScreenUtils;


public class VerifyDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private OnDialogClickLinstener linstener;
    private String content;
    private String title;
    private String mBtnPositiveText = "确定";
    private String mBtnNegativeText = "取消";



    DialogVerifyBinding verifyBinding;


    public VerifyDialog(Context context) {
        super(context, R.style.base_dialog);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        verifyBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.dialog_verify,null,false);
        this.setContentView(verifyBinding.getRoot());
        Window window = this.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            int width = (int) ScreenUtils.dp2px(context, 284);
            int height = (int) ScreenUtils.dp2px(context, 171);
            window.setLayout(width,height);
        }

        verifyBinding.tvCancel.setOnClickListener(this);
        verifyBinding.tvOk.setOnClickListener(this);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);
        verifyBinding.tvOk.setText(mBtnPositiveText);
        verifyBinding.tvCancel.setText(mBtnNegativeText);
    }

    @Override
    public void show() {
        super.show();
        if (verifyBinding != null) {
            verifyBinding.tvContent.setText(content);
        }
    }

    public void setMessage(String text) {
        content = text;
    }

    public void setPositiveText(String text) {
        mBtnPositiveText = text;
    }

    public void setNegativeText(String text) {
        mBtnNegativeText = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_ok) {
            if (null != linstener) {
                linstener.onPositiveClick();
                dismiss();
            }

        } else if (i == R.id.tv_cancel) {
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
