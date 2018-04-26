package com.tudouni.makemoney.fragment.mall;

import android.view.LayoutInflater;

import com.tudouni.makemoney.R;
import com.tudouni.makemoney.databinding.ItemMallAlbumBinding;
import com.tudouni.makemoney.model.MallAlbumModel;
import com.tudouni.makemoney.view.BaseRecyclerViewBindingAdapter;

/**
 * Jaron.Wu
 * 2018/4/26
 */
public class AlbumItemAdapter extends BaseRecyclerViewBindingAdapter<MallAlbumModel> {
    AlbumItemAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_mall_album;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemMallAlbumBinding albumBinding = (ItemMallAlbumBinding) holder.getBinding();
        albumBinding.setItem(this.data.get(position));
        albumBinding.executePendingBindings();
    }
}
