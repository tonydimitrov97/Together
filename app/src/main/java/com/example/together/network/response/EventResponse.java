package com.example.together.network.response;

import java.util.List;

import com.example.together.event.Event;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("error")
    @Expose
    private Object error;
    @SerializedName("response")
    @Expose
    private List<Event> response = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public EventResponse() {
    }

    /**
     *
     * @param response
     * @param error
     * @param status
     */
    public EventResponse(Integer status, Object error, List<Event> response) {
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

    public List<Event> getResponse() {
        return response;
    }

    public void setResponse(List<Event> response) {
        this.response = response;
    }

}

