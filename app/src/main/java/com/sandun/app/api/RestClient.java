package com.sandun.app.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient<T> {
    private Retrofit client;
    private Class<T> serviceClass;
    private static final String BASE_URL = "http://192.168.1.27/test/";

    public RestClient(Class<T> serviceClass,String endPoint) {
        this.serviceClass = serviceClass;
        OkHttpClient client = new OkHttpClient.Builder().build();
        this.client = new Retrofit.Builder().client(client).baseUrl(BASE_URL+endPoint).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public T createService(){
        return this.client.create(serviceClass);
    }
}
