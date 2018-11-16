package com.example.together.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.example.together.event.EventImage;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PhotoScreenVm extends ViewModel {

    private EventImage eventImage;
    private int numLikes;
    private String caption;

    public PhotoScreenVm(EventImage eventImage) {
        this.eventImage = eventImage;
        this.numLikes = eventImage.getLikes();
    }

   public int getLikes() {
       return this.numLikes;
   }

   public String getCaption() {
        return this.caption;
   }

}
