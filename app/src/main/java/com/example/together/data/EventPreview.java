package com.example.together.data;

public class EventPreview {
    private String eventCode;
    private String eventName;
    private String shortDesc;
    private int image;
    private String thumbnail;
    private int _public;
    private int eventId;
    private int creatorId;

    public EventPreview(String eventCode, String eventName, String shortDesc, int image, String thumbnail,
                        int _public, int eventId, int creatorId) {
        this.eventCode = eventCode;
        this.eventName = eventName;
        this.shortDesc = shortDesc;
        this.image = image;
        this.thumbnail = thumbnail;
        this._public = _public;
        this.eventId = eventId;
        this.creatorId = creatorId;
    }

    public String getEventCode() {
        return eventCode;
    }

    public String getEventName() {
        return eventName;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public int getImage() {
        return image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public int get_public() {
        return _public;
    }

    public int getEventId() {
        return eventId;
    }

    public int getCreatorId() {
        return creatorId;
    }
}
