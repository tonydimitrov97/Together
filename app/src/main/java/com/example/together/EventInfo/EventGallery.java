package com.example.together.EventInfo;

import com.example.together.R;

import java.util.ArrayList;

public class EventGallery {


    ArrayList<EventImage> myGallery;


    public EventGallery() {
        myGallery = new ArrayList<EventImage>();
    }


    public void initializeGallery() {
        int count = 500;
        int imageId = 0;

        for(int i = 0; i < count; i++) {
            myGallery.add(new EventImage(R.drawable.imageex1));
        }

    }

    public ArrayList<EventImage> getPhotoList() {
        return myGallery;
    }



}
