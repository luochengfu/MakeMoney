package com.tudouni.makemoney.widget.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;


import com.tudouni.makemoney.R;

import org.xutils.common.util.DensityUtil;

import java.util.ArrayList;


/**
 * 菜单对话框<br>
 * <br>
 * 设置菜单选项 setMenu/setMenuText/setMenuTextId<br>
 * 获取菜单选项 {@link #getMenu()}，可以用于设置选项的颜色、背景、禁用等<br>
 * 设置菜单选项点击监听器 {@link #setOnItemClickListener(OnItemClickListener)}<br>
 * 设置是否点击选项之后隐藏菜单 {@link #setHideOnItemClick(boolean)}<br>
 * 设置是否显示取消选项 {@link #setShowCancel(boolean)}<br>
 *
 * @author Storm
 */
public class MenuDialog extends Dialog implements View.OnClickListener {

    /** 菜单选项数据 */
    private ArrayList<Menu> mMenus;

    /** 是否点击菜单选项之后隐藏菜单 */
    private boolean mHideOnItemClick = true;

    /** 是否显示取消选项 */
    private boolean mShowCancel = true;

    /** 菜单选项点击监听器 */
    private OnItemClickListener mListener;

    /** 从底部弹出 */
    public MenuDialog(Context context) {
        super(context, R.style.facebeauty_dialog);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM | Gravity.LEFT;
        onWindowAttributesChanged(layoutParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout layout = new FrameLayout(getContext());
        View view = getView(layout);
        int width = getContext().getResources().getDisplayMetrics().widthPixels;
        view.setMinimumWidth(width);
        layout.addView(view);
        setContentView(layout);
    }


    /**
     * 获取菜单选项数据
     */
    public ArrayList<Menu> getMenu() {
        return mMenus;
    }

    /**
     * 设置菜单选项数据
     */
    public MenuDialog setMenu(ArrayList<Menu> menus) {
        mMenus = menus;
        return this;
    }

    /**
     * 设置菜单选项文字
     *
     * @param menus 菜单选项文字列表
     */
    public MenuDialog setMenuText(ArrayList<String> menus) {
        if (menus == null)
            mMenus = null;
        else {
            mMenus = new ArrayList<>();
            for (String string : menus) {
                mMenus.add(new Menu(string));
            }
        }
        return this;
    }

    /**
     * 设置菜单选项文字
     *
     * @param menus 菜单选项文字数组
     */
    public MenuDialog setMenuText(String... menus) {
        if (menus == null)
            mMenus = null;
        else {
            mMenus = new ArrayList<>();
            for (String string : menus) {
                mMenus.add(new Menu(string));
            }
        }
        return this;
    }

    /**
     * 设置菜单选项文字资源id
     *
     * @param menus 菜单选项文字资源id列表
     */
    public MenuDialog setMenuTextId(ArrayList<Integer> menus) {
        if (menus == null)
            mMenus = null;
        else {
            mMenus = new ArrayList<>();
            for (int textId : menus) {
                mMenus.add(new Menu(getContext().getString(textId)));
            }
        }
        return this;
    }

    /**
     * 设置菜单选项文字资源id
     *
     * @param menus 菜单选项文字资源id数组
     */
    public MenuDialog setMenuTextId(int... menus) {
        if (menus == null)
            mMenus = null;
        else {
            this.mMenus = new ArrayList<>();
            for (int textId : menus) {
                mMenus.add(new Menu(getContext().getString(textId)));
            }
        }
        return this;
    }

    /**
     * 设置是否点击菜单选项之后隐藏菜单，默认true
     *
     * @param hideOnItemClick
     */
    public MenuDialog setHideOnItemClick(boolean hideOnItemClick) {
        mHideOnItemClick = hideOnItemClick;
        return this;
    }

    /**
     * 设置是否显示取消选项，默认true
     *
     * @param showCancel
     */
    public MenuDialog setShowCancel(boolean showCancel) {
        mShowCancel = showCancel;
        return this;
    }

    /**
     * 设置菜单选项点击监听器
     *
     * @param listener
     */
    public MenuDialog setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
        return this;
    }

    protected View getView(ViewGroup parent) {
        if (mMenus == null || mMenus.size() == 0)
            throw new RuntimeException("Set menu data first!");
        LinearLayout layout = (LinearLayout) View.inflate(getContext(), R.layout.dialog_menu, null);
        LayoutParams lp1 = new LayoutParams(LayoutParams.MATCH_PARENT, DensityUtil.dip2px(44));
        lp1.bottomMargin = 1;

        for (int i = 0; i < mMenus.size(); i++) {
            Menu menu = mMenus.get(i);
            View view = menu.getView(getContext());
            view.setTag(i);
            view.setOnClickListener(this);
            if (menu.view == null) {
                layout.addView(view, i, lp1);
            } else {
                layout.addView(view, i);
            }
        }

        View cancel = layout.findViewById(R.id.menu_cancel_button);
        if (mShowCancel) {
            cancel.setTag(-1);
            cancel.setOnClickListener(this);
        } else {
            cancel.setVisibility(View.GONE);
        }
        return layout;
    }

    @Override
    public void onClick(View v) {
        int i = Integer.parseInt(v.getTag().toString());
        if (i < 0) { // 取消
            cancel();
            if (mListener != null && mListener instanceof OnItemClickListener2) {
                ((OnItemClickListener2) mListener).onCancel();
            }
            return;
        }
        if (!mMenus.get(i).enabled) {
            return;
        }
        if (mHideOnItemClick) {
            dismiss();
        }
        if (mListener != null) {
            mListener.onItemClick(this, i);
        }
    }

    /**
     * 菜单选项数据模型
     */
    public static class Menu {

        /** 选项view，如果设置了，则会使用此view */
        public View view;

        /** 选项文字 */
        public String text = "";

        /** 选项文字颜色 */
        public int textColor = 0;

        /** 选项字体大小，默认单位sp */
        public float textSize = 0;

        /** 选项文字大小的单位，默认sp */
        public int textSizeUnit = TypedValue.COMPLEX_UNIT_SP;

        /** 选项是否启用 */
        public boolean enabled = true;

        public Menu() {
        }

        public Menu(View view) {
            this.view = view;
        }

        public Menu(String text) {
            this.text = text;
        }

        public Menu(String text, boolean enabled) {
            this.text = text;
            this.enabled = enabled;
        }

        public Menu(String text, int textColor, int textSize, boolean enabled) {
            this.text = text;
            this.textColor = textColor;
            this.textSize = textSize;
            this.enabled = enabled;
        }

        /**
         * 获取选项view
         */
        public View getView(Context context) {
            if (view != null)
                return view;
            TextView button = new TextView(new ContextThemeWrapper(context, R.style.ButtonMenu)); // 带样式的context
            button.setText(text);
            if (textSize > 0) {
                button.setTextSize(textSizeUnit, textSize);
            }
            if (textColor != 0) {
                button.setTextColor(textColor);
            }
            if (!enabled) {
                button.setEnabled(false);
            }
            return button;
        }

    }

    /**
     * 菜单选项点击监听器
     */
    public interface OnItemClickListener {

        public void onItemClick(MenuDialog dialog, int index);
    }

    public interface OnItemClickListener2 extends OnItemClickListener {

        public void onCancel();
    }

}
