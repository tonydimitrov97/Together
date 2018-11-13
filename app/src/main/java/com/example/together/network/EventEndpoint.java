package com.example.together.network;

import com.example.together.network.model.EventModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EventEndpoint {

    @GET("api/event")
    Call<EventModel> getEvent();

    @GET("api/event/{id}")
    Call<EventModel> getEvent(@Path("id") int id);

    /*@GET("group/{id}/users")
    Call<List<EventModel>> groupList(@Path("id") int groupId, @Query("sort") String sort);

    @POST("users/new")
    Call<EventModel> createUser(@Body EventModel event);*/

}
