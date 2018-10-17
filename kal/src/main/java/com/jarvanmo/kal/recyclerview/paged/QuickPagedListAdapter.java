package com.jarvanmo.kal.recyclerview.paged;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import kotlin.jvm.functions.Function0;

public class QuickPagedListAdapter<T> extends DataBindingPagedListAdapter<T> {
    protected QuickPagedListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
    }

    protected QuickPagedListAdapter(@NonNull AsyncDifferConfig<T> config) {
        super(config);

    }

    private int BR;
    private int layout;
    public QuickPagedListAdapter(int BR,int layout,@NonNull DiffUtil.ItemCallback<T> diffCallback, @Nullable Function0 retry) {
        super(diffCallback, retry);
        this.BR = BR;
        this.layout = layout;
    }

    public QuickPagedListAdapter(int BR,int layout,@NonNull AsyncDifferConfig<T> config, @Nullable Function0 retry) {
        super(config, retry);
        this.BR = BR;
        this.layout = layout;
    }

    @Override
    protected int getItemLayout(int position, T item) {
        return layout;
    }

    @Override
    protected void onAttachViewHolder(@NonNull ViewHolder holder, int position) {
            holder.getBinding().setVariable(BR,getItem(position));
    }
}
