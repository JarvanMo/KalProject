package com.jarvanmo.kal.recyclerview;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.annotation.CallSuper;
import androidx.annotation.Keep;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * My goal is to make creating adapter  easier.
 * So,this adapter is based on {@link DataBindingUtil} and reflection.
 * I never test performance of this adapter
 * if you really concern about performance, you'd better not use this adapter.
 */
@Keep
public abstract class RecyclerViewAdapter<I> extends RecyclerView.Adapter<RecyclerViewAdapter.BaseViewHolder> {

    private List<I> data = new ArrayList<>();


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        return new BaseViewHolder(binding);
    }


    @Override
    public int getItemViewType(int position) {
        return getItemLayout(position, data.get(position));
    }

    @CallSuper
    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        onBind(holder, position, data.get(position));
        holder.getBinding().executePendingBindings();
    }

    public abstract void onBind(BaseViewHolder holder, int position, @NonNull I item);

    @LayoutRes
    protected abstract int getItemLayout(int position, I item);


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    public void setData(List<I> newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    public List<I> getData() {
        return data;
    }

    public void insert(int index, List<I> insertedData) {

        if (index < 0 || index > data.size()) {
            return;
        }

        data.addAll(index, insertedData);
        notifyItemRangeInserted(index, insertedData.size());
//        notifyDataSetChanged();

    }

    public void insert(int index, I item) {

        if (index < 0 || index > data.size()) {
            return;
        }

        data.add(index, item);
        notifyItemChanged(index);
//        notifyDataSetChanged();

    }

    public void add(I item) {
        data.add(item);
        notifyItemInserted(data.size() == 0 ? 0 : data.size() - 1);
    }


    public void addAll(List<I> moreData) {
        if (moreData == null) {
            return;
        }

        int index = data.size();
        data.addAll(moreData);
        notifyItemRangeInserted(index, moreData.size());
    }

    public void set(int position, I item) {
        if (position < 0 || position >= data.size()) {
            return;
        }
        data.set(position, item);
        notifyItemChanged(position);
    }

    @Nullable
    public I getItem(int position) {
        if (position < 0 || position >= data.size()) {
            return null;
        }

        return data.get(position);

    }

    @Nullable
    public I getLast() {
        return getItem(data.size() - 1);
    }

    public void remove(int position) {
        if (position < 0 || position >= data.size()) {
            return;
        }

        data.remove(position);
        notifyItemRemoved(position);
    }


    public void remove(I item) {
        int index = data.indexOf(item);
        if (data.remove(item)) {
            notifyItemRemoved(index);
        }
    }


    public void remove(List<I> subList) {
        data.removeAll(subList);
        notifyDataSetChanged();
    }

    public int indexOf(I item) {
        return data.indexOf(item);
    }

    public boolean contains(I item) {
        return data != null && data.contains(item);
    }

    public boolean isEmpty() {
        return data == null || data.isEmpty();
    }

    public int size() {
        return data == null ? 0 : data.size();
    }

    public void clear() {

        if (data != null) {
            data.clear();
            notifyDataSetChanged();
        }

    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding mBinding;

        BaseViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public ViewDataBinding getBinding() {
            return mBinding;
        }
    }

}

