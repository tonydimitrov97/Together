package com.example.together.network.response;

import com.example.together.event.EventImage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventImageResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("error")
    @Expose
    private Object error;
    @SerializedName("response")
    @Expose
    private List<EventImage> response = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public EventImageResponse() {
    }

    /**
     *
     * @param response
     * @param error
     * @param status
     */
    public EventImageResponse(Integer status, Object error, List<EventImage> response) {
        super();
        this.status = status;
        this.error = error;
        this.response = response;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public List<EventImage> getResponse() {
        return response;
    }

    public void setResponse(List<EventImage> response) {
        this.response = response;
    }

}