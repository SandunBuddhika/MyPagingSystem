package com.sandun.pagingSysyem.model;

import android.app.Activity;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.sandun.pagingSysyem.enums.LoadingStates;
import com.sandun.pagingSysyem.service.CompactPagingService;

import java.util.ArrayList;
import java.util.List;

public abstract class PagingSourceCompact<R,D> {

    private final List<D> LIST = new ArrayList<>();
    protected int currentPage = 1;
    protected int itemSize;
    protected LoadingStates isLoading = LoadingStates.DONE;
    protected Activity activity;
    private RecyclerView recyclerView;
    private NestedScrollView scrollView;
    private RecyclerView.Adapter adapter;
    protected LoadingStateListener<R,D> stateListener;

    protected R service;


    public PagingSourceCompact(R service, int itemSize, NestedScrollView scrollView, RecyclerView recyclerView, Activity activity) {
        this.itemSize = itemSize;
        this.recyclerView = recyclerView;
        this.scrollView = scrollView;
        this.activity = activity;
        this.service = service;
        stateListener = new LoadingStateListener<R,D>();
        stateListener.setSource(this);
        init();
        superInit();
    }

    protected abstract void init();

    private void superInit() {
        new PagingRequestHandler<R,D>().setSource(this);
    }

    protected List<D> getList() {
        return LIST;
    }

    protected void addItem(D item) {
        this.LIST.add(item);
    }

    protected RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    protected void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    protected RecyclerView getRecyclerView() {
        return recyclerView;
    }

    protected void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    protected NestedScrollView getScrollView() {
        return scrollView;
    }

    protected void setScrollView(NestedScrollView scrollView) {
        this.scrollView = scrollView;
    }

    public abstract void loadData();

    protected abstract void changeLoadingState(LoadingStates state);
}
