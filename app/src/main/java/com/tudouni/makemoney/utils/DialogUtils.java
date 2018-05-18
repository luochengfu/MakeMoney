package com.tudouni.makemoney.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tudouni.makemoney.R;
import com.wang.avi.AVLoadingIndicatorView;


/**
 * 通用弹出提示
 * Created by huang on 2017/11/17.
 */

public class DialogUtils {



    public static Dialog showCommonDialog(Context ctx, String msg, String confirmTx, String cancelTx, final View.OnClickListener confirmListener, final View.OnClickListener cancelListener) {
        View contentView = LayoutInflater.from(ctx).inflate(R.layout.common_dialog, null);
        final Dialog dialog = showDialog(ctx, contentView);
        ((TextView)contentView.findViewById(R.id.tv_msg)).setText(msg);
        ((TextView)contentView.findViewById(R.id.tv_confirm)).setText(confirmTx);
        ((TextView)contentView.findViewById(R.id.tv_cancel)).setText(cancelTx);
        contentView.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (confirmListener != null)
                    confirmListener.onClick(view);
            }
        });
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (cancelListener!=null)
                    cancelListener.onClick(view);
            }
        });
        return dialog;
    }

    public static Dialog showDialog(Context ctx, View contentView){
        return showDialog(ctx, contentView, true, true, true);
    }

    /**
     * 公共Dialog
     * @param contentView        显示的dialog的内容
     * @param cancelable         按返回键dialog是否消失
     * @param cancelTouchOutside 点击dialog外部dialog是否消失
     * @param isShow             是否立刻显示
     */
    private static Dialog showDialog(Context ctx, View contentView, boolean cancelable, boolean cancelTouchOutside, boolean isShow) {
        Dialog dialog = new Dialog(ctx, R.style.dlg_common);
        dialog.setContentView(contentView);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelTouchOutside);

        Window window = dialog.getWindow();
        if (window == null) return null;
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        if (isShow)
            dialog.show();
        return dialog;
    }

    public static Dialog showVerify(Activity activity,String title,String okMsg ,String cancelMsg){
        Dialog sDialog =  new Dialog(activity);
        sDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view  = inflater.inflate(R.layout.dialog_verify,null);
        sDialog.setContentView(view);
        Window window = sDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            int layoutSize = (int) ScreenUtils.dp2px(activity, 110);
            window.setLayout(layoutSize,layoutSize);
        }
        sDialog.setCancelable(false);
        sDialog.show();
        return sDialog;
    }


}
