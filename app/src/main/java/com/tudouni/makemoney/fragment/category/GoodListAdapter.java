package com.tudouni.makemoney.fragment.category;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.activity.H5Activity;
import com.tudouni.makemoney.databinding.ItemGoodGridBinding;
import com.tudouni.makemoney.model.Category;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.NetConfig;
import com.tudouni.makemoney.utils.ScreenUtils;
import com.tudouni.makemoney.utils.TDLog;
import com.tudouni.makemoney.view.BaseRecyclerViewBindingAdapter;

/**
 * 二级品类（右侧）的数据适配器
 * Jaron.Wu
 *    2018/04/23
 */
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
                int gap = ScreenUtils.dp2px(view.getContext(), 3);
                if (parent.getChildAdapterPosition(view) % 3 == 0) {
                    outRect.right = gap;
                } else if (parent.getChildAdapterPosition(view) % 3 == 1) {
                    outRect.left = gap;
                    outRect.right = gap;
                }else{
                    outRect.left = gap;
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
        itemAdapter.setOnItemClickListener((index,good) -> {
            //TODO:
            TDLog.e(good,index);
            Intent intent = new Intent(MyApplication.sCurrActivity, H5Activity.class);
            intent.putExtra("url", NetConfig.getBaseTuDouNiH5Url() + "?uid=" + MyApplication.getLoginUser().getUid() +
            "&token="+MyApplication.getLoginUser().getToken()+
            "&unionid=" + MyApplication.getLoginUser().getUnionid() +
            "&search=" + good.getName());
            MyApplication.sCurrActivity.startActivity(intent);
        });
        itemAdapter.replaceData(this.data.get(position).getCategorys());
    }
}
