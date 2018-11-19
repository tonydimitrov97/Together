package com.example.together.network.service;

import com.example.together.network.response.UserResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {

    @GET("api/user")
    Single<UserResponse> getUsers();

    @GET("api/user/{id}")
    Single<UserResponse> getUserById(@Path("id") int id);

    @GET("api/user/userEvent/{eventId}")
    Single<UserResponse> getUsersByEventId(@Path("eventId") int eventId);

   /* @GET("api/user/{id}")
    Call<UserResponse> getUserById(@Path("id") int id);*/

    /*@GET("group/{id}/users")
    Call<List<EventResponse>> groupList(@Path("id") int groupId, @Query("sort") String sort);

    @POST("users/new")
    Call<EventResponse> createUser(@Body EventResponse event);*/

}
