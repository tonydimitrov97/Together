package com.example.together.network.response;

import java.util.List;
import com.example.together.event.UserEventMap;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserEventMapResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("response")
    @Expose
    private List<UserEventMap> response = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserEventMapResponse() {
    }

    /**
     *
     * @param response
     * @param error
     * @param status
     */
    public UserEventMapResponse(Integer status, String error, List<UserEventMap> response) {
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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<UserEventMap> getResponse() {
        return response;
    }

    public void setResponse(List<UserEventMap> response) {
        this.response = response;
    }

}
