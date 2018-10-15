package com.jarvanmo.kal.recyclerview.paged;

import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jarvanmo.kal.R;
import com.jarvanmo.kal.databinding.ItemNetworkStateBinding;
import com.jarvanmo.kal.util.Strings;

import java.util.List;

import androidx.annotation.CallSuper;
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

    private RetryCallback retryCallback;
    private PagingNetworkState pagingNetworkState;

    private List<T> data;

    protected DataBindingPagedListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
    }

    protected DataBindingPagedListAdapter(@NonNull AsyncDifferConfig<T> config) {
        super(config);
    }

    public DataBindingPagedListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback,@Nullable RetryCallback retry ) {
       this(diffCallback);
       retryCallback = retry;
    }

    public DataBindingPagedListAdapter(@NonNull AsyncDifferConfig<T> config,@Nullable RetryCallback retry) {
        this(config);
        retryCallback = retry;
    }

//    private var networkState: NetworkState? = null

//
//    override fun onBindViewHolder(
//            holder: RecyclerView.ViewHolder,
//            position: Int,
//            payloads: MutableList<Any>) {
//        if (payloads.isNotEmpty()) {
//            val item = getItem(position)
//            (holder as RedditPostViewHolder).updateScore(item)
//        } else {
//            onBindViewHolder(holder, position)
//        }
//    }
//


//

//
//    fun setNetworkState(newNetworkState: NetworkState?) {
//        val previousState = this.networkState
//        val hadExtraRow = hasExtraRow()
//        this.networkState = newNetworkState
//        val hasExtraRow = hasExtraRow()
//        if (hadExtraRow != hasExtraRow) {
//            if (hadExtraRow) {
//                notifyItemRemoved(super.getItemCount())
//            } else {
//                notifyItemInserted(super.getItemCount())
//            }
//        } else if (hasExtraRow && previousState != newNetworkState) {
//            notifyItemChanged(itemCount - 1)
//        }
//    }



//        override fun getItemViewType(position: Int): Int {
//        return if (hasExtraRow() && position == itemCount - 1) {
//            R.layout.network_state_item
//        } else {
//            R.layout.reddit_post_item
//        }
//    }

    private boolean hasExtraRow(){
        return pagingNetworkState !=null && pagingNetworkState != PagingNetworkState.LOADED;
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


    //    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        when (getItemViewType(position)) {
//            R.layout.reddit_post_item -> (holder as RedditPostViewHolder).bind(getItem(position))
//            R.layout.network_state_item -> (holder as NetworkStateItemViewHolder).bindTo(
//                    networkState)
//        }
//    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(getItemViewType(position) == R.layout.item_network_state){
            bindNetworkState((ItemNetworkStateBinding) holder.getBinding());
        }else {

        }
    }

    private void bindNetworkState(ItemNetworkStateBinding itemNetworkStateBinding){

        if (pagingNetworkState == null) {
            return;
        }
        itemNetworkStateBinding.progressBar.setVisibility(networkStateToVisibility(pagingNetworkState.getStatus()== Status.RUNNING));
        itemNetworkStateBinding.retryButton.setVisibility(networkStateToVisibility(pagingNetworkState.getStatus() == Status.FAILED));
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


    public interface RetryCallback{
        void retry();
    }
}
