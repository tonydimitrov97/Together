package com.example.together.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserEventMap {

    @SerializedName("event_id")
    @Expose
    private Integer eventId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserEventMap() {
    }

    /**
     *
     * @param eventId
     * @param userId
     */
    public UserEventMap(Integer eventId, Integer userId) {
        super();
        this.eventId = eventId;
        this.userId = userId;
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

}