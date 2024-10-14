package com.sandun.app.service;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ItemService {
    @GET("{endPoint}")
    Call<JsonObject> getItemsByPage(@Path("endPoint") String endPoint, @Query("page") int page, @Query("itemCount") int count);
}
