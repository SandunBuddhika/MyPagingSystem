package com.sandun.pagingSysyem.model.callback;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.sandun.pagingSysyem.adapter.AdapterPagingItem;
import com.sandun.pagingSysyem.model.PagingSource;

import java.util.List;

public interface PagingCallBack<D> {
    void handleViewCreate(D data, AdapterPagingItem.ViewHolder viewHolder);

    void handleRequestData(String data, PagingSource<D> source, RecyclerView.Adapter adapter, List<D> list);

    void handleRequestError(Throwable throwable);
}
