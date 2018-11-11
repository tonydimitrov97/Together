package com.example.together.data;

public class EventPreview {
    private String eventCode;
    private String eventName;
    private String shortDesc;
    private int image;

    public EventPreview(String eventCode, String eventName, String shortDesc, int image) {
        this.eventCode = eventCode;
        this.eventName = eventName;
        this.shortDesc = shortDesc;
        this.image = image;
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
}
