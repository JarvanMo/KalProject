package com.jarvanmo.kal.swipe;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

/**
 * Created by mo on 17-5-26.
 * Copyright © 2017, cnyanglao, Co,. Ltd. All Rights Reserve
 */

public class RecycleRefreshLayout extends SwipeRefreshLayout {

    private static final String TAG = RecycleRefreshLayout.class.getCanonicalName();

    public RecycleRefreshLayout(Context context) {
        super(context);
    }

    public RecycleRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean canChildScrollUp() {
        return canChildScrollUp(this);
    }

    public boolean canChildScrollUp(ViewGroup v) {
        for (int i = 0; i < v.getChildCount(); i++) {
            View temp = v.getChildAt(i);
            if (temp instanceof RecyclerView) {
                if (canRecycleViewScroll((RecyclerView) temp)) return true;
            } else if (temp instanceof AbsListView) {
                if (temp.canScrollVertically( -1)) return true;
            } else if (temp instanceof ViewGroup) {
                if (canChildScrollUp((ViewGroup) temp)) return true;
            } else if (temp.canScrollVertically( -1)) return true;
        }
        return false;
    }

    public boolean canRecycleViewScroll(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        return ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition() != 0;

    }
}
