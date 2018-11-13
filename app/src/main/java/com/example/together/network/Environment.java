package com.example.together.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Environment {

    // Trailing slash is needed
    public static final String BASE_URL = "https://little-owl-38.localtunnel.me/";

    private Retrofit retrofit;
    private EventEndpoint eventService;

    public Environment() {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();


        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.eventService = retrofit.create(EventEndpoint.class);
    }

    public EventEndpoint getEvents() {
        return this.eventService;
    }

}
