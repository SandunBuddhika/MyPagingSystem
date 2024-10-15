package com.sandun.pagingSysyem.model;


public abstract class CompactMediator<D> {
    protected PagingSource<D> source;

    public void setSource(PagingSource<D> source) {
        this.source = source;
    }
}
