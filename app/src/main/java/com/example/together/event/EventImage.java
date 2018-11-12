package com.example.together.event;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import com.example.together.R;

import java.util.Random;

public class EventImage {

    private Integer imageId;
    private String owner;
    private String caption;
    private boolean isPublic;
    private int numLikes;

    public EventImage(Integer imageId) {
        Random random = new Random();
        this.imageId = imageId;
        this.numLikes = random.nextInt(50);
        this.isPublic = true;
        this.caption = "Hey, this is a caption.";

    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer android_image_url) {
        this.imageId = android_image_url;
    }

    public void setCreator(String owner) {
        this.owner = owner;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

}