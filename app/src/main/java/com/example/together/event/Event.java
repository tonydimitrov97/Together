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
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("public")
    @Expose
    private Integer _public;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    /**
     * No args constructor for use in serialization
     *
     */
    public Event() {
    }

    /**
     *
     * @param id
     * @param startDate
     * @param title
     * @param thumbnail
     * @param _public
     * @param location
     * @param description
     * @param creatorId
     * @param eventCode
     * @param endDate
     */
    public Event(Integer id, String title, String eventCode, String description, String location,
                 Integer creatorId, String startDate, String endDate, Integer _public, String thumbnail) {
        super();
        this.id = id;
        this.title = title;
        this.eventCode = eventCode;
        this.description = description;
        this.location = location;
        this.creatorId = creatorId;
        this.startDate = startDate;
        this.endDate = endDate;
        this._public = _public;
        this.thumbnail = thumbnail;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getPublic() {
        return _public;
    }

    public void setPublic(Integer _public) {
        this._public = _public;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
