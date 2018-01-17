package com.samboy.contactproject.retrofitclient;

import com.samboy.contactproject.rectroresponce.AndroidList;
import com.samboy.contactproject.rectroresponce.ContactList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Hari Group Unity on 05-01-2018.
 */

public interface ApiServices {

    @GET("/contacts//")
    Call<ContactList> getContactList();

    @GET("/android/jsonandroid")
    Call<AndroidList> getAndroidList();
}
