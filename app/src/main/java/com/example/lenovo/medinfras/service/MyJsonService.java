package com.example.lenovo.medinfras.service;

import com.example.lenovo.medinfras.pojo.Event;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Lenovo on 3/22/2018.
 */

public interface MyJsonService {

    @GET("/1cd83j")
    void listEvents(Callback<List<Event>> eventsCallback);
}
