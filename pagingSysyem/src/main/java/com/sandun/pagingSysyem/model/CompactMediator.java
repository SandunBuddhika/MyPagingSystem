package com.sandun.pagingSysyem.model;


public abstract class CompactMediator<R, D> {
    protected PagingSourceCompact<R, D> source;

    public void setSource(PagingSourceCompact<R, D> source) {
        this.source = source;
    }
}
