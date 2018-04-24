package com.tudouni.makemoney.fragment.category;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.databinding.ItemGoodGridBinding;
import com.tudouni.makemoney.model.Category;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.view.BaseRecyclerViewBindingAdapter;

public class GoodListAdapter extends BaseRecyclerViewBindingAdapter<Category> {

    GoodListAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_good_grid;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemGoodGridBinding itemBinding = ((ItemGoodGridBinding)holder.getBinding());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MyApplication.getContext(),3);
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) % 3 == 0) {
                    outRect.right = 8;
                } else if (parent.getChildAdapterPosition(view) % 3 == 1) {
                    outRect.left = 8;
                    outRect.right = 8;
                }else{
                    outRect.left = 8;
                }
            }
        };
        itemBinding.rvGoodList.setLayoutManager(gridLayoutManager);
        RecyclerView.ItemDecoration lastDecoration = (RecyclerView.ItemDecoration) itemBinding.rvGoodList.getTag();
        if(lastDecoration != null){
            itemBinding.rvGoodList.removeItemDecoration(lastDecoration);
        }
        itemBinding.rvGoodList.addItemDecoration(itemDecoration);
        itemBinding.rvGoodList.setTag(itemDecoration);
        itemBinding.setItem(this.data.get(position));
        holder.getBinding().executePendingBindings();
        GoodItemAdapter itemAdapter = new GoodItemAdapter(mInflater);
        itemBinding.rvGoodList.setAdapter(itemAdapter);
        itemAdapter.replaceData(this.data.get(position).getCategorys());
    }
}
