package com.sandun.pagingSysyem.model.callback;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.sandun.pagingSysyem.adapter.AdapterPagingItem;
import com.sandun.pagingSysyem.model.PagingSource;

import java.util.List;

public interface PagingCustomViewCallBack<D> extends PagingCallBack<D> {
    void handleViewCreate(D data, AdapterPagingItem.ViewHolder viewHolder);

    void handleRequestData(String data, PagingSource<D> source, RecyclerView.Adapter adapter, List<D> list);

    void handleRequestError(Throwable throwable);

    int handleViewType(int position,List<D> list);

    AdapterPagingItem.ViewHolder createViewHolder(ViewGroup parent, int viewType);

}
