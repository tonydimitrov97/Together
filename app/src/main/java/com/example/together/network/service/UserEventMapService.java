package com.example.together.network.service;

import com.example.together.network.response.EventResponse;
import com.example.together.network.response.UserEventMapResponse;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserEventMapService {

    @GET("api/userEventMap/{eventId}")
    Single<UserEventMapResponse> getUsersByEventId(@Path("eventId") int eventId);

    @FormUrlEncoded
    @POST("api/userEventMap/join")
    Single<UserEventMapResponse> joinEvent(@Field("eventId") int eventId, @Field("id") int user_id);
}
