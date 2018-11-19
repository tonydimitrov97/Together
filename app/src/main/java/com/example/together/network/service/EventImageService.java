package com.example.together.network.service;

import com.example.together.network.response.EventImageResponse;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

    public interface EventImageService {

        @GET("api/photo")
        Single<EventImageResponse> getPhotos();

        @GET("api/photo/{id}")
        Single<EventImageResponse> getPhotoById(@Path("id") int id);

        @GET("api/photo/eventPhotos/{eventId}")
        Single<EventImageResponse> getPhotosByEventId(@Path("eventId") int eventId);

        @FormUrlEncoded
        @POST("api/photo/upload")
        Call<byte[]> uploadPhoto(
                @Field("image") byte[] data);

    /*@GET("group/{id}/users")
    Call<List<EventResponse>> groupList(@Path("id") int groupId, @Query("sort") String sort);

    @POST("users/new")
    Call<EventResponse> createUser(@Body EventResponse event);*/



}
