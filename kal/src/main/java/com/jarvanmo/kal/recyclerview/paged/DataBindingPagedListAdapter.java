package com.jarvanmo.kal.recyclerview.paged;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

/***
 * Created by mo on 2018/10/11
 * 冷风如刀，以大地为砧板，视众生为鱼肉。
 * 万里飞雪，将穹苍作烘炉，熔万物为白银。
 **/
public abstract class DataBindingPagedListAdapter<T> extends PagedListAdapter<T,DataBindingPagedListAdapter.ViewHolder> {

    private List<T> data;

    protected DataBindingPagedListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
    }

    protected DataBindingPagedListAdapter(@NonNull AsyncDifferConfig<T> config) {
        super(config);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return null;
        }
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return getItemLayout(position,getItem(position));
    }

    @LayoutRes
    protected abstract int getItemLayout(int position, T item);

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding mBinding;

        ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public ViewDataBinding getBinding() {
            return mBinding;
        }
    }


    public static class DefaultItemCallback<O> extends DiffUtil.ItemCallback<O>{


        @Override
        public boolean areItemsTheSame(@NonNull O oldItem, @NonNull O newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull O oldItem, @NonNull O newItem) {
            return oldItem.equals(newItem);
        }

    }
}
