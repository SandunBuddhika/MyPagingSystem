package com.sandun.pagingSysyem.model;

import android.os.Build;
import android.view.View;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

public class PagingRequestHandler< D> extends CompactMediator< D> {

    private RecyclerView recyclerView;
    private NestedScrollView scrollView;

    @Override
    public void setSource(PagingSource<D> source) {
        super.setSource(source);
        recyclerView = source.getRecyclerView();
        scrollView = source.getScrollView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener((View.OnScrollChangeListener) this::handle);
        } else {
            scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) this::handle);
        }
    }

    private void handle(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (recyclerView != null && scrollY > oldScrollY) {
            int cCount = recyclerView.getChildCount() - 3;
            if (cCount < 0) {
                cCount = 0;
            }
            View childV = recyclerView.getChildAt(cCount);
            int reloadLineY = childV.getTop();
            if (reloadLineY < scrollY + scrollView.getHeight()) {
                source.loadData();
            }
        }
    }
}
