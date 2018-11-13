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
     * @param likes
     * @param userId
     * @param path
     */
    public EventImage(Integer id, Integer eventId, Integer userId, String path, Integer likes) {
        super();
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.path = path;
        this.likes = likes;
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

}