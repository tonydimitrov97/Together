package com.example.together.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventImage {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("eventId")
    @Expose
    private Integer eventId;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("caption")
    @Expose
    private String caption;

    /**
     * No args constructor for use in serialization
     *
     */
    public EventImage() {
    }

    /**
     *
     * @param id
     * @param eventId
     * @param location
     * @param likes
     * @param userId
     * @param path
     * @param dateCreated
     * @param caption
     */
    public EventImage(Integer id, Integer eventId, Integer userId, String path, Integer likes, String location, String dateCreated, String caption) {
        super();
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.path = path;
        this.likes = likes;
        this.location = location;
        this.dateCreated = dateCreated;
        this.caption = caption;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

}
