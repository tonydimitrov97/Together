package com.example.together.event;

import android.support.annotation.NonNull;

import com.example.together.R;
import com.example.together.network.response.EventImageResponse;
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
        //this.getEventImages(eventId);
        this.myGallery = new ArrayList<EventImage>();
        this.myGallery.add(new EventImage(R.mipmap.image1));
        this.myGallery.add(new EventImage(R.mipmap.image2));
    }



    public ArrayList<EventImage> getPhotoList() {
        return myGallery;
    }

}
