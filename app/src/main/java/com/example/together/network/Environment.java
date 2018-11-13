package com.example.together.network;

import com.example.together.network.endpoint.EventEndpoint;
import com.example.together.network.endpoint.EventImageEndpoint;
import com.example.together.network.endpoint.UserEndpoint;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Environment {

    // Trailing slash is needed
    public static final String BASE_URL = "http://10.131.67.241:3000/";

    private Retrofit retrofit;
    private EventEndpoint eventService;
    private EventImageEndpoint eventImageService;
    private UserEndpoint userService;

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

    public EventEndpoint getEventService() {
        return this.eventService;
    }

    public EventImageEndpoint getEventImageService() {
        return this.eventImageService;
    }

    public UserEndpoint getUserService() {
        return userService;
    }
}
