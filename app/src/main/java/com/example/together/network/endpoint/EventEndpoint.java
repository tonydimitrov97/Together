package com.example.together.network.endpoint;

import com.example.together.network.response.EventResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EventEndpoint {

    @GET("api/event")
    Call<EventResponse> getEvents();

    @GET("api/event/{id}")
    Call<EventResponse> getEventById(@Path("id") int id);

    /*@GET("group/{id}/users")
    Call<List<EventResponse>> groupList(@Path("id") int groupId, @Query("sort") String sort);

    @POST("users/new")
    Call<EventResponse> createUser(@Body EventResponse event);*/

}
