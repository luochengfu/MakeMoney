package com.tudouni.makemoney.widget.pop;

import android.app.Dialog;
import android.widget.PopupWindow;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by ZhangPeng on 2018/4/21.
 */
public class PopManger {

    private CopyOnWriteArrayList<PopupWindow> popupWindows = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<Dialog> dialogs = new CopyOnWriteArrayList<>();

    public void addPopupWindow(PopupWindow pop) {
        if (pop != null) {
            popupWindows.add(pop);
        }
    }

    public void removePopupWindow(PopupWindow pop) {
        if (pop != null) {
            popupWindows.remove(pop);
        }
    }

    public void addDialog(Dialog dialog) {
        if (dialog != null) {
            dialogs.add(dialog);
        }
    }

    public void removeDialog(Dialog dialog) {
        if (dialog != null) {
            dialogs.remove(dialog);
        }
    }

    public void dismissAll() {
        for (PopupWindow pop : popupWindows) {
            if (pop.isShowing()) {
                pop.dismiss();
            }
        }
        for (Dialog dialog : dialogs) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}
