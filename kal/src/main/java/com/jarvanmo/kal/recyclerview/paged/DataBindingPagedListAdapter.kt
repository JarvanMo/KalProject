package com.jarvanmo.kal.recyclerview.paged

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jarvanmo.kal.R
import com.jarvanmo.kal.databinding.ItemNetworkStateBinding
import com.jarvanmo.kal.util.Strings

import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/***
 * Created by mo on 2018/10/11
 * 冷风如刀，以大地为砧板，视众生为鱼肉。
 * 万里飞雪，将穹苍作烘炉，熔万物为白银。
 */
abstract class DataBindingPagedListAdapter<T> : PagedListAdapter<T, DataBindingPagedListAdapter.ViewHolder> {

    private var retryCallback: Function0<*>? = null
    private var pagingNetworkState: PagingNetworkState? = null


    protected constructor(diffCallback: DiffUtil.ItemCallback<T>) : super(diffCallback) {}

    protected constructor(config: AsyncDifferConfig<T>) : super(config) {}

    constructor(diffCallback: DiffUtil.ItemCallback<T>, retry: Function0<*>?) : this(diffCallback) {
        retryCallback = retry
    }

    constructor(config: AsyncDifferConfig<T>, retry: Function0<*>?) : this(config) {
        retryCallback = retry
    }


    private fun hasExtraRow(): Boolean {
        return pagingNetworkState != null && pagingNetworkState != PagingNetworkState.LOADED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) == R.layout.item_network_state) {
            bindNetworkState(holder.binding as ItemNetworkStateBinding)
        } else {
            onAttachViewHolder(holder, position)
        }
    }

    private fun bindNetworkState(itemNetworkStateBinding: ItemNetworkStateBinding) {

        if (pagingNetworkState == null) {
            return
        }
        itemNetworkStateBinding.progressBar.visibility = networkStateToVisibility(pagingNetworkState!!.status === Status.RUNNING)
        itemNetworkStateBinding.retryButton.visibility = networkStateToVisibility(pagingNetworkState!!.status === Status.FAILED)
        itemNetworkStateBinding.retryButton.setOnClickListener { _ -> retryCallback!!.invoke() }
        itemNetworkStateBinding.errorMsg.visibility = networkStateToVisibility(!Strings.isBlank(pagingNetworkState!!.msg))
        itemNetworkStateBinding.errorMsg.text = pagingNetworkState!!.msg

    }

    private fun networkStateToVisibility(constraint: Boolean): Int {
        return if (constraint) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    @CallSuper
    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            getItemLayout(position, getItem(position))
        }
    }


    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }


    fun setPagingNetworkState(pagingNetworkState: PagingNetworkState?) {
        val previousState = this.pagingNetworkState
        val hadExtraRow = hasExtraRow()
        this.pagingNetworkState = pagingNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }

        } else if (hasExtraRow && previousState !== pagingNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    @LayoutRes
    protected abstract fun getItemLayout(position: Int, item: T?): Int

    protected abstract fun onAttachViewHolder(holder: ViewHolder, position: Int)

    fun replaceAll(newData: List<T>) {
        val data = currentList ?: return
        data.clear()
        addAll(newData)
    }

    fun addAll(dataToAppend: List<T>) {
        val data = currentList ?: return
        val oldSize = data.size
        data.addAll(dataToAppend)
        notifyItemRangeChanged(oldSize, dataToAppend.size)
    }


    fun removeAt(index: Int) {
        val data = currentList ?: return

        if (index < 0 || index >= data.size) {
            return
        }

        notifyItemChanged(index)
    }

    fun remove(item:T){
        val data = currentList ?: return

        removeAt(data.indexOf(item))
    }

    class ViewHolder internal constructor(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)




}
