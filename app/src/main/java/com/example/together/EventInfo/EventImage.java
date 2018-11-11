package com.example.together.EventInfo;

public class EventImage {

    private Integer imageId;
    private String creator;
    private String caption;
    private boolean isPublic;
    private int numLikes;

    public EventImage(Integer imageId) {
        this.imageId = imageId;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer android_image_url) {
        this.imageId = android_image_url;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setPublic(boolean isPublic) {

    }
}