package com.jarvanmo.kal.recyclerview.paged;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jarvanmo.kal.R;
import com.jarvanmo.kal.databinding.ItemNetworkStateBinding;
import com.jarvanmo.kal.util.Strings;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.jvm.functions.Function0;

/***
 * Created by mo on 2018/10/11
 * 冷风如刀，以大地为砧板，视众生为鱼肉。
 * 万里飞雪，将穹苍作烘炉，熔万物为白银。
 **/
public abstract class DataBindingPagedListAdapter<T> extends PagedListAdapter<T,DataBindingPagedListAdapter.ViewHolder> {

    private Function0 retryCallback;
    private PagingNetworkState pagingNetworkState;


    protected DataBindingPagedListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
    }

    protected DataBindingPagedListAdapter(@NonNull AsyncDifferConfig<T> config) {
        super(config);
    }

    public DataBindingPagedListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback,@Nullable Function0 retry ) {
       this(diffCallback);
       retryCallback = retry;
    }

    public DataBindingPagedListAdapter(@NonNull AsyncDifferConfig<T> config,@Nullable Function0 retry) {
        this(config);
        retryCallback = retry;
    }



    private boolean hasExtraRow(){
        return pagingNetworkState !=null && pagingNetworkState != PagingNetworkState.LOADED;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(getItemViewType(position) == R.layout.item_network_state){
            bindNetworkState((ItemNetworkStateBinding) holder.getBinding());
        }else {
            onAttachViewHolder(holder,position);
        }
    }

    private void bindNetworkState(ItemNetworkStateBinding itemNetworkStateBinding){

        if (pagingNetworkState == null) {
            return;
        }
        itemNetworkStateBinding.progressBar.setVisibility(networkStateToVisibility(pagingNetworkState.getStatus()== Status.RUNNING));
        itemNetworkStateBinding.retryButton.setVisibility(networkStateToVisibility(pagingNetworkState.getStatus() == Status.FAILED));
        itemNetworkStateBinding.retryButton.setOnClickListener(v -> {
            retryCallback.invoke();
        });
        itemNetworkStateBinding.errorMsg.setVisibility(networkStateToVisibility(!Strings.isBlank(pagingNetworkState.getMsg())));
        itemNetworkStateBinding.errorMsg.setText(pagingNetworkState.getMsg());

    }

    private int networkStateToVisibility(boolean constraint){
        if(constraint){
            return View.VISIBLE;
        }else {
            return View.GONE;
        }
    }

    @CallSuper
    @Override
    public int getItemViewType(int position) {
        if(hasExtraRow() && position == getItemCount()-1){
            return R.layout.item_network_state;
        }else {
            return getItemLayout(position, getItem(position));
        }
    }


    @Override
    public int getItemCount() {
        return super.getItemCount()+(hasExtraRow()?1:0);
    }


    public void setPagingNetworkState(PagingNetworkState pagingNetworkState) {
        PagingNetworkState previousState = this.pagingNetworkState;
        boolean hadExtraRow = hasExtraRow();
        this.pagingNetworkState = pagingNetworkState;
        boolean hasExtraRow = hasExtraRow();
        if(hadExtraRow != hasExtraRow){
                  if(hadExtraRow){
                      notifyItemRemoved(super.getItemCount());
                  }else {
                      notifyItemInserted(super.getItemCount());
                  }

        }else if (hasExtraRow && previousState != pagingNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    @LayoutRes
    protected abstract int getItemLayout(int position, T item);
    protected abstract void onAttachViewHolder(@NonNull ViewHolder holder, int position);

    public void replaceAll(List<T> newData){
        List<T> data = getCurrentList();
        if (data == null) {
            return;
        }
        data.clear();
        addAll(newData);
    }

    public void addAll(List<T> dataToAppend){
        List<T> data = getCurrentList();
        if (data == null) {
            return;
        }
        int oldSize= data.size();
        data.addAll(dataToAppend);
        notifyItemRangeChanged(oldSize,dataToAppend.size());
    }


    public void removeAt(int index){
        List<T> data = getCurrentList();
        if (data == null) {
            return;
        }

        if (index < 0 || index >= data.size()) {
            return;
        }

        notifyItemChanged(index);
    }


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


    public interface RetryCallback{
        void retry();
    }
}
