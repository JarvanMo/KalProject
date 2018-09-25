package com.jarvanmo.kal.recyclerview;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
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

        public void onItemRangeChanged(int positionStart, int itemCount) {
            Adapter<?> adapter = getAdapter();
            if (adapter != null && emptyView != null) {
                if (adapter.getItemCount() == 0) {
                    showEmptyView();
                } else {
                    hideEmptyView();
                }
            }

        }

        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            // fallback to onItemRangeChanged(positionStart, itemCount) if app
            // does not override this method.
            onItemRangeChanged(positionStart, itemCount);
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            Adapter<?> adapter = getAdapter();
            if (adapter != null && emptyView != null) {
                if (adapter.getItemCount() == 0) {
                    showEmptyView();
                } else {
                    hideEmptyView();
                }
            }
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            Adapter<?> adapter = getAdapter();
            if (adapter != null && emptyView != null) {
                if (adapter.getItemCount() == 0) {
                    showEmptyView();
                } else {
                    hideEmptyView();
                }
            }
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            Adapter<?> adapter = getAdapter();
            if (adapter != null && emptyView != null) {
                if (adapter.getItemCount() == 0) {
                    showEmptyView();
                } else {
                    hideEmptyView();
                }
            }
        }

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

    private void showEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.VISIBLE);
            SupportEmptyRecyclerView.this.setVisibility(View.GONE);
        }

    }


    private void hideEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.GONE);
            SupportEmptyRecyclerView.this.setVisibility(View.VISIBLE);
        }
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        emptyView.setLayoutParams(layoutParams);
        ViewGroup viewGroup = (ViewGroup) getParent();
        View child;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            child = viewGroup.getChildAt(i);
            if(child == this){
                viewGroup.addView(emptyView,i+1);
                break;
            }
        }


    }
}
