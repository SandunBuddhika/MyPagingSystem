package com.sandun.pagingSysyem.model;


import com.sandun.pagingSysyem.service.CompactPagingService;

public abstract class CompactMediator<R extends CompactPagingService, D> {
    protected PagingSourceCompact<R, D> source;

    public void setSource(PagingSourceCompact<R, D> source) {
        this.source = source;
    }
}
