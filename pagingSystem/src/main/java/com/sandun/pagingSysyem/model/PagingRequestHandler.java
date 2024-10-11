package com.sandun.pagingSysyem.model;

import android.view.View;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.sandun.pagingSysyem.service.CompactPagingService;

public class PagingRequestHandler<R extends CompactPagingService,D> extends CompactMediator<R,D> {

    private RecyclerView recyclerView;
    private NestedScrollView scrollView;

    @Override
    public void setSource(PagingSourceCompact<R,D> source) {
        super.setSource(source);
        recyclerView = source.getRecyclerView();
        scrollView = source.getScrollView();
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    int cCount = recyclerView.getChildCount() - 3;
                    if (cCount < 0) {
                        cCount = 0;
                    }
                    View childV = recyclerView.getChildAt(cCount);
                    int reloadLineY = childV.getTop();
                    if (reloadLineY < scrollY + scrollView.getHeight()) {
                        System.out.println("loading");
                        source.loadData();
                    }
                }
            }
        });
    }
}