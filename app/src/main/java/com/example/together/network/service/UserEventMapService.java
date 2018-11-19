package com.example.together.network.service;

import com.example.together.network.response.UserEventMapResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserEventMapService {

    @GET("api/userEventMap/{eventId}")
    Single<UserEventMapResponse> getUsersByEventId(@Path("eventId") int eventId);

    @GET("api/userEventMap/{userId}")
    Single<UserEventMapResponse> getEventsByUserId(@Path("userId") int userId);

}
