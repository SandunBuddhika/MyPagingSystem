package com.sandun.pagingSysyem.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sandun.pagingSysyem.model.callback.PagingCallBack;

import java.util.List;

public class AdapterPagingItem<D> extends RecyclerView.Adapter<AdapterPagingItem.ViewHolder> {

    private int layoutId;
    private PagingCallBack<D> handler;
    private List<D> list;

    public AdapterPagingItem(int layoutId, PagingCallBack<D> handler, List<D> list) {
        this.layoutId = layoutId;
        this.handler = handler;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        D data = list.get(position);
        handler.handleViewCreate(data, holder);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }
    }
}
