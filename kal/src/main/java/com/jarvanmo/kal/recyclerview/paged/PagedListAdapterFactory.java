package com.jarvanmo.kal.recyclerview.paged;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;

/***
 * Created by mo on 2018/10/11
 * 冷风如刀，以大地为砧板，视众生为鱼肉。
 * 万里飞雪，将穹苍作烘炉，熔万物为白银。
 **/
public class PagedListAdapterFactory {

    public static  <D> DataBindingPagedListAdapter<D> withDiffCallback(@NonNull DiffUtil.ItemCallback<D> diffCallback){
        return new DataBindingPagedListAdapter<>(diffCallback);
    }

    public static  <D> DataBindingPagedListAdapter<D> withDefaultDiffCallback(){
        return new DataBindingPagedListAdapter<>(new DataBindingPagedListAdapter.DefaultItemCallback<D>());
    }

    public static  <D> DataBindingPagedListAdapter<D> withAsyncDifferConfig(@NonNull AsyncDifferConfig<D> config){
        return new DataBindingPagedListAdapter<>(config);
    }

}
