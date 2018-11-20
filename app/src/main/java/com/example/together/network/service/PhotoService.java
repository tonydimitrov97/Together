package com.example.together.network.service;

import com.example.together.network.response.PhotoResponse;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

    public interface PhotoService {

        @GET("api/photo")
        Single<PhotoResponse> getPhotos();

        @GET("api/photo/{id}")
        Single<PhotoResponse> getPhotoById(@Path("id") int id);

        @GET("api/photo/eventPhotos/{eventId}")
        Single<PhotoResponse> getPhotosByEventId(@Path("eventId") int eventId);

        @FormUrlEncoded
        @POST("api/photo/uploadPhoto")
        Single<PhotoResponse> uploadPhoto(
                @Field("eventId") int eventId, @Field("userId") int userId, @Field("location") String location,
                @Field("date_created") String date, @Field("caption") String caption);

    /*@GET("group/{id}/users")
    Call<List<EventResponse>> groupList(@Path("id") int groupId, @Query("sort") String sort);

    @POST("users/new")
    Call<EventResponse> createUser(@Body EventResponse event);*/



}
