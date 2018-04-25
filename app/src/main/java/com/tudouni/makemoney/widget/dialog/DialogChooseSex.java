package com.tudouni.makemoney.widget.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.utils.ScreenUtils;

/**
 * author: Yangbin
 * time  : 2016/12/9 17:55
 * desc  : 选择体重
 */

public class DialogChooseSex implements View.OnClickListener {

    Activity ac;
    Dialogcallback dialogcallback;
    Dialog dialog;

    TextView tvNv;
    TextView tvNan;



    public DialogChooseSex(Activity con, Dialogcallback callback) {
        this.ac = con;
        this.dialogcallback = callback;
        dialog = new AlertDialog.Builder(con).create();
        dialog.show();

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = ScreenUtils.getScreenWidth(con); //设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(R.layout.dialog_picksex);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);

        initView();
    }

    private void initView() {
        tvNv = (TextView) dialog.findViewById(R.id.tvNv);
        tvNan = (TextView) dialog.findViewById(R.id.tvNan);
        tvNv.setOnClickListener(this);
        tvNan.setOnClickListener(this);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvNan:
                dialogcallback.pickWeightResult("男");
                dialog.dismiss();
                break;
            case R.id.tvNv:
                dialogcallback.pickWeightResult("女");
                dismiss();
                break;
        }
    }

    public void setTitle(String title) {

    }


    public interface Dialogcallback {
        void pickWeightResult(String sex);
    }

    public void show() {
        dialog.show();
    }

    public void hide() {
        dialog.hide();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
