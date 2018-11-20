package com.example.together.network.service;

import com.example.together.network.response.EventResponse;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
public interface EventService {

    @GET("api/event")
    Single<EventResponse> getEvents();

    @GET("api/event/{id}")
    Single<EventResponse> getEventById(@Path("id") int id);

    @FormUrlEncoded
    @POST("api/event")
    Single<EventResponse> addEvent(@Field("title") String title,
                                   @Field("start_date") String start_date,
                                   @Field("end_date") String end_date,
                                   @Field("location") String location,
                                   @Field("creatorId") int creatorId,
                                   @Field("description") String description,
                                   @Field("public") boolean _public);

    @GET("api/event/userEvent/{userId}")
    Single<EventResponse> getEventsByUserId(@Path("userId") int userId);

    @FormUrlEncoded
    @PUT("api/event/updateEvent")
    Single<EventResponse> updateEvent(@Field("id") int id,
                                      @Field("title") String title,
                                      @Field("start_date") String start_date,
                                      @Field("end_date") String end_date,
                                      @Field("location") String location,
                                      @Field("creatorId") int creatorId,
                                      @Field("description") String description,
                                      @Field("public") boolean _public,
                                      @Field("thumbnail") String thumbnail,
                                      @Field("eventCode") String eventCode);

    @HTTP(method = "DELETE", path = "/api/event/{eventId}", hasBody = true)
    Single<EventResponse> deleteEvent(@Path("eventId") int eventId);

}
