package com.example.together.network.endpoint;

import com.example.together.event.Event;
import com.example.together.network.response.EventResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EventService {

    @GET("api/event")
    Single<EventResponse> getEvents();

    @GET("api/event/{id}")
    Single<EventResponse> getEventById(@Path("id") int id);

    /*@GET("group/{id}/users")
    Call<List<EventResponse>> groupList(@Path("id") int groupId, @Query("sort") String sort);

    @POST("users/new")
    Call<EventResponse> createUser(@Body EventResponse event);*/

}
