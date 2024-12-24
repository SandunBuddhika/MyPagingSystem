package com.sandun.pagingSysyem.model;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sandun.pagingSysyem.adapter.AdapterPagingItem;
import com.sandun.pagingSysyem.enums.LoadingStates;
import com.sandun.pagingSysyem.model.callback.PagingCallBack;
import com.sandun.pagingSysyem.model.callback.PagingCustomViewCallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PagingSource<D> {

    private OkHttpClient client;
    private final List<D> LIST = new ArrayList<>();
    protected int currentPage;
    protected int itemSize;
    protected LoadingStates isLoading = LoadingStates.DONE;
    protected Activity activity;
    private RecyclerView recyclerView;
    private NestedScrollView scrollView;
    private RecyclerView.Adapter adapter;
    protected LoadingStateListener<D> stateListener;
    protected HttpUrl.Builder getData;

    protected PagingCallBack<D> callBack;
    protected int layoutId;

    private boolean pagingStatus = true;

    public PagingSource(int layoutId, HttpUrl.Builder getData, int itemSize, NestedScrollView scrollView, RecyclerView recyclerView, Activity activity, PagingCallBack<D> callBack) {
        this.itemSize = itemSize;
        this.client = new OkHttpClient.Builder().build();
        this.recyclerView = recyclerView;
        this.scrollView = scrollView;
        this.getData = getData;
        this.activity = activity;
        stateListener = new LoadingStateListener<D>();
        stateListener.setSource(this);
        this.callBack = callBack;
        this.layoutId = layoutId;
        init();
    }

    public PagingSource(HttpUrl.Builder getData, int itemSize, NestedScrollView scrollView, RecyclerView recyclerView, Activity activity, PagingCustomViewCallBack<D> callBack) {
        this.itemSize = itemSize;
        this.client = new OkHttpClient.Builder().build();
        this.recyclerView = recyclerView;
        this.scrollView = scrollView;
        this.getData = getData;
        this.activity = activity;
        stateListener = new LoadingStateListener<D>();
        stateListener.setSource(this);
        this.callBack = callBack;
        init();
    }

    private void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, true) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
//        if (isReverse) {
//            layoutManager.setStackFromEnd(true);
//            layoutManager.setReverseLayout(true);
//        }
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new AdapterPagingItem<>(layoutId, callBack, LIST);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        new PagingRequestHandler<D>().setSource(this);
        loadData();
    }


    public void loadData() {
        if (pagingStatus && isLoading == LoadingStates.DONE) {
            changeLoadingState(LoadingStates.LOADING);
            getData.removeAllQueryParameters("page").removeAllQueryParameters("itemCount").addQueryParameter("page", String.valueOf(currentPage)).addQueryParameter("itemCount", String.valueOf(itemSize));
            String url = getData.build().toString();

            Request request = new Request.Builder().url(url).get().build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    callBack.handleRequestData(response.body().string(), PagingSource.this, adapter, LIST);
                    changeLoadingState(LoadingStates.DONE);
                    currentPage++;
                }

                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    callBack.handleRequestError(e.getCause());
                    changeLoadingState(LoadingStates.DONE);
                }
            });
        }

    }


    public void removeAllData() {
        LIST.removeAll(LIST);
        adapter.notifyDataSetChanged();
    }

    public void removeItem(int index) {
        LIST.remove(index);
    }

    protected void changeLoadingState(LoadingStates state) {
        isLoading = state;
        stateListener.listener();
    }

    public boolean isPagingStatus() {
        return pagingStatus;
    }

    public void setPagingStatus(boolean pagingStatus) {
        this.pagingStatus = pagingStatus;
    }

    public List<D> getLIST() {
        return LIST;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
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
}
