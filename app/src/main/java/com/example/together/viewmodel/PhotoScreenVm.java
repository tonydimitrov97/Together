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
    private boolean liked;

    public PhotoScreenVm(EventImage eventImage) {
        this.eventImage = eventImage;
        this.numLikes = eventImage.getLikes();
        this.liked = false;
    }

   public int getLikes() {
       return this.numLikes;
   }

    public boolean handleLike() {
        this.numLikes = this.liked ? this.numLikes - 1 : this.numLikes + 1;
        this.liked = !this.liked;

        return this.liked;
    }

   public String getCaption() {
        return this.caption;
   }

}
