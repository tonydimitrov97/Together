package com.example.together.network;

import android.util.Log;

import com.example.together.R;
import com.example.together.event.Event;
import com.example.together.network.endpoint.EventEndpoint;
import com.example.together.network.endpoint.EventImageEndpoint;
import com.example.together.network.endpoint.UserEndpoint;
import com.example.together.network.response.EventResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.constraint.Constraints.TAG;

public class RequestHandler {

    // Trailing slash is needed
    public static final String BASE_URL = "http://10.131.67.241:3000/";

    private Retrofit retrofit;
    private EventEndpoint eventService;
    private EventImageEndpoint eventImageService;
    private UserEndpoint userService;

    public RequestHandler() {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        this.eventService = retrofit.create(EventEndpoint.class);
        this.eventImageService = retrofit.create(EventImageEndpoint.class);
        this.userService = retrofit.create(UserEndpoint.class);



    }

    public EventEndpoint getEventService() {
        return eventService;
    }

    public EventImageEndpoint getEventImageService() {
        return eventImageService;
    }

    public UserEndpoint getUserService() {
        return userService;
    }

}
