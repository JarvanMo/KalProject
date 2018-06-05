package com.jarvanmo.kal.recyclerview;

import android.support.annotation.NonNull;

public class QuickAdapter<I> extends RecyclerViewAdapter<I> {
    private int br;
    private int itemLayoutId;

    public QuickAdapter(int br, int itemLayoutId) {
        this.br = br;
        this.itemLayoutId = itemLayoutId;
    }

    @Override
    public void onBind(@NonNull BaseViewHolder holder, int position, @NonNull I item) {
        holder.getBinding().setVariable(br, item);
    }

    @Override
    protected int getItemLayout(int position, I item) {
        return itemLayoutId;
    }

}
