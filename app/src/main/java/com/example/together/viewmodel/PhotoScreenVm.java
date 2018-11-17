package com.example.together.viewmodel;

import android.arch.lifecycle.ViewModel;
import com.example.together.event.EventImage;
import java.util.Random;

public class PhotoScreenVm extends ViewModel {

    private EventImage eventImage;
    private int numLikes;
    private int numComments;
    private String caption;
    private boolean liked;

    public PhotoScreenVm(EventImage eventImage) {
        Random random = new Random();
        this.numComments = random.nextInt(15);
        this.eventImage = eventImage;
        this.numLikes = eventImage.getLikes();
        this.caption = eventImage.getCaption();
        this.liked = false;
    }

   public int getNumLikes() {
       return this.numLikes;
   }

    /* Like and unlike */
    public boolean handleLike() {
        this.numLikes = this.liked ? this.numLikes - 1 : this.numLikes + 1;
        this.liked = !this.liked;

        return this.liked;
    }

   public String getCaption() {
        return this.caption;
   }

   public int getNumComments() {
        return this.numComments;
   }

}
