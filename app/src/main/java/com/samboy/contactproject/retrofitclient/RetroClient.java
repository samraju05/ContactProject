package com.samboy.contactproject.retrofitclient;

import com.samboy.contactproject.webservices.NetworUrls;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hari Group Unity on 05-01-2018.
 */


public class RetroClient {

    private static Retrofit getRetrofitInstance(String URL) {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiServices getApiService(String URL) {
        return getRetrofitInstance(URL).create(ApiServices.class);
    }

}
