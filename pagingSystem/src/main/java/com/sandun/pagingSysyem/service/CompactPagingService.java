package com.sandun.pagingSysyem.service;

import com.google.gson.JsonObject;

import retrofit2.Call;

public interface CompactPagingService {
    Call<JsonObject> getItemsByPage(String endPoint, int page, int count);
}
