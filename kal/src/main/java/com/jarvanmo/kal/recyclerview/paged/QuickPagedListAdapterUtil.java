package com.jarvanmo.kal.recyclerview.paged;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import kotlin.jvm.functions.Function0;

final public class QuickPagedListAdapterUtil {
    private QuickPagedListAdapterUtil(){
      throw new RuntimeException("not supported");
    }


    public static <T>  QuickPagedListAdapter<T>  createWithDefaultItemCallback(int BR, int layout){
        return createWithDefaultItemCallback( BR,  layout,  null);
    }


    public static <T>  QuickPagedListAdapter<T>  createWithDefaultItemCallback(int BR, int layout, @Nullable  Function0 retry){
        return new QuickPagedListAdapter<T>(BR,layout,DEFAULT_ITEM_CALLBACK,retry);
    }



    public static final DiffUtil.ItemCallback DEFAULT_ITEM_CALLBACK = new DiffUtil.ItemCallback() {
        @Override
        public boolean areItemsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
            return newItem.equals(oldItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
            return newItem == oldItem;
        }
    };

}
