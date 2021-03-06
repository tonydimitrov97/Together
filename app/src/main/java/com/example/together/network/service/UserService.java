package com.example.together.network.service;

import com.example.together.network.response.UserResponse;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    @GET("api/user")
    Single<UserResponse> getUsers();

    @GET("api/user/{id}")
    Single<UserResponse> getUserById(@Path("id") int id);

    @GET("api/user/{eventId}")
    Single<UserResponse> getUsersByEventId(@Path("eventId") int eventId);

    @FormUrlEncoded
    @POST("api/user/login")
    Single<UserResponse> verifyLogin(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/user/addUser")
    Single<UserResponse> addUser(@Field("name") String name, @Field("email") String email, @Field("password") String password);

    /* @GET("api/user/{id}")
    Call<UserResponse> getUserById(@Path("id") int id);*/

    /*@GET("group/{id}/users")
    Call<List<EventResponse>> groupList(@Path("id") int groupId, @Query("sort") String sort);

    @POST("users/new")
    Call<EventResponse> createUser(@Body EventResponse event);*/

}
