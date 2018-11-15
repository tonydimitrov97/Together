package com.example.together.network.service;

import com.example.together.network.response.EventImageResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

    public interface EventImageService {

        @GET("api/photo")
        Single<EventImageResponse> getPhotos();

        @GET("api/photo/{id}")
        Single<EventImageResponse> getPhotoById(@Path("id") int id);

        @GET("api/photo/{eventId}")
        Single<EventImageResponse> getPhotosByEventId(@Path("id") int eventId);

    /*@GET("group/{id}/users")
    Call<List<EventResponse>> groupList(@Path("id") int groupId, @Query("sort") String sort);

    @POST("users/new")
    Call<EventResponse> createUser(@Body EventResponse event);*/



}
