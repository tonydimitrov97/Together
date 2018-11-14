package com.example.together.network.endpoint;

import com.example.together.network.response.EventImageResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

    public interface EventImageService {

        @GET("api/photo")
        Call<EventImageResponse> getPhotos();

        @GET("api/photo/{id}")
        Call<EventImageResponse> getPhotoById(@Path("id") int id);

        @GET("api/photo/{eventId}")
        Call<EventImageResponse> getPhotosByEventId(@Path("id") int eventId);

    /*@GET("group/{id}/users")
    Call<List<EventResponse>> groupList(@Path("id") int groupId, @Query("sort") String sort);

    @POST("users/new")
    Call<EventResponse> createUser(@Body EventResponse event);*/



}
