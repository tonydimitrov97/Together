package com.example.together.event;

import android.support.annotation.NonNull;

import com.example.together.network.Environment;
import com.example.together.network.response.EventImageResponse;
import com.example.together.network.response.EventResponse;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventGallery {

    private ArrayList<EventImage> myGallery;
    private Random random;
    private Integer[] images;

    public EventGallery(int eventId) {
        this.getEventImages(eventId);
    }

    private void getEventImages(int eventId) {
        Environment env = new Environment();
        Call<EventImageResponse> call = env.getEventImageService().getPhotosByEventId(eventId);

        /* Create custom callback for each response */
        call.enqueue(new Callback<EventImageResponse>() {

            @Override
            public void onResponse(@NonNull Call<EventImageResponse> call, @NonNull Response<EventImageResponse> response) {
                EventImageResponse eventImageResponse = response.body();
                if (eventImageResponse != null) {
                    System.out.println("RESPONSE: " + eventImageResponse.getResponse().get(0).getLikes());
                } else {
                    System.out.println("Response received but encountered an error in parsing.");
                }
            }

            @Override
            public void onFailure(Call<EventImageResponse> call, Throwable t) {
                System.out.println("Error accessing Together API: " + t.getMessage());
            }
        });
    }

    public ArrayList<EventImage> getPhotoList() {
        return myGallery;
    }

}
