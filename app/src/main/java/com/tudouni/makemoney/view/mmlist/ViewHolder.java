package com.tudouni.makemoney.view.mmlist;

import android.util.SparseArray;
import android.view.View;

/**
 * @author HeDongDong
 * @Description: SparseArray这个类，优化过的存储integer和object键值对的hashmap
 * 只需静态调用get这个方法填入当前Adapter的 convertView 与 子控件的id,就可以实现复用。
 */
public class ViewHolder {
    /**
     * @param view 父view
     * @param id   子view的id
     * @return 父view上的子view
     */

    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {

        //拿到父view身上背的Tag
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();

        //如果Tag为空就为view背个Tag
        if (viewHolder == null) {

            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }

        //获取父view身上的子view
        View childView = viewHolder.get(id);

        if (childView == null) {

            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
