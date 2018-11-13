package com.example.together.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("eventCode")
    @Expose
    private String eventCode;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("creatorId")
    @Expose
    private Integer creatorId;

    /**
     * No args constructor for use in serialization
     */
    public Event() {
    }

    /**
     * @param id
     * @param title
     * @param location
     * @param description
     * @param creatorId
     * @param eventCode
     */
    public Event(Integer id, String title, String eventCode, String description, String location, Integer creatorId) {
        super();
        this.id = id;
        this.title = title;
        this.eventCode = eventCode;
        this.description = description;
        this.location = location;
        this.creatorId = creatorId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }
}