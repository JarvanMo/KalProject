package com.jarvanmo.kal.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mo on 17-3-21.
 * Copyright © 2017, cnyanglao, Co,. Ltd. All Rights Reserve
 */

public class SupportEmptyRecyclerView extends RecyclerView {
    private View emptyView;

    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {


        @Override
        public void onChanged() {
            Adapter<?> adapter = getAdapter();
            if (adapter != null && emptyView != null) {
                if (adapter.getItemCount() == 0) {
                        showEmptyView();
                } else {
                  hideEmptyView();
                }
            }

        }
    };

    public SupportEmptyRecyclerView(Context context) {
        super(context);
    }

    public SupportEmptyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SupportEmptyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if (adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
        }

        emptyObserver.onChanged();
    }

    private void showEmptyView(){
        if (emptyView != null) {
            emptyView.setVisibility(View.VISIBLE);
            SupportEmptyRecyclerView.this.setVisibility(View.GONE);
        }

    }


    private void hideEmptyView(){
        if (emptyView != null) {
            emptyView.setVisibility(View.GONE);
            SupportEmptyRecyclerView.this.setVisibility(View.VISIBLE);
        }
    }
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        emptyView.setLayoutParams(layoutParams);
        emptyView.requestLayout();
    }
}
