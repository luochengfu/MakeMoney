package com.tudouni.makemoney.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 *  通用Adapter，适用于使用数据绑定填充数据。
 *    Jaron.Wu
 *       2018/04/23
 * @param <T>  列表数据类型
 */
public abstract class BaseRecyclerViewBindingAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewBindingAdapter.BaseViewHolder> {
    protected List<T> data = new ArrayList<>();
    protected LayoutInflater mInflater;
    protected OnItemClickListener<T> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public BaseRecyclerViewBindingAdapter(LayoutInflater inflater) {
        mInflater = inflater;
    }

    public void addData(List<T> data){
        int startPosition = this.data.size();
        if (data != null) {
            this.data.addAll(data);
            if (startPosition == 0) {
                notifyDataSetChanged();
            }else {
                notifyItemRangeChanged(startPosition - 1, data.size());
            }
        }
    }

    public void replaceData(List<T> data){
        if (data != null) {
            this.data.clear();
            addData(data);
        }
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.getBinding().getRoot().setOnClickListener(l -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(position,this.data.get(position));
            }
        });
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(mInflater,getLayoutId(),parent,false);
        return new BaseViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    protected abstract int getLayoutId();

    public class BaseViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder{
        private T mBinding;

        public T getBinding() {
            return mBinding;
        }

        public BaseViewHolder(T binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }

    public interface OnItemClickListener<T>{
        void onItemClick(int position, T itemData);
    }
}
