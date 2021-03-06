package com.example.together.viewmodel;

import android.arch.lifecycle.ViewModel;
import com.example.together.event.Event;
import com.example.together.event.EventImage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventInfoVm extends ViewModel {

    private Event event;
    private String eventTitle;
    private String eventDescription;
    private String eventLocation;
    private int userCount;
    private List<EventImage> eventGallery;
    private int photoCount;
    private int publicEvent;
    private int eventId;
    private String startDate;
    private String endDate;

    public EventInfoVm(Event event) {
        this.userCount = 1;
        this.event = event;
        this.eventTitle = event.getTitle();
        this.eventDescription = event.getDescription();
        this.eventLocation = event.getLocation();
        this.eventGallery = new ArrayList<EventImage>();
        this.publicEvent = event.getPublic();
        this.eventId = event.getId();
        this.startDate = event.getStart_date();
        this.endDate = event.getEnd_date();
    }

    public boolean getPublicEvent() {
        return (publicEvent == 1);
    }

    public String getEventId() {
        return "Event ID: " + eventId;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public Event getEvent() {
        return event;
    }

    public List<EventImage> getEventGallery() {
        return eventGallery;
    }

    public void setEventGallery(List<EventImage> eventGallery) {
        this.eventGallery = eventGallery;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public String getDates() {

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println(this.startDate);
            Date newStartDate = format.parse(this.startDate);
            Date newEndDate = format.parse(this.endDate);
            format = new SimpleDateFormat("MM/dd/yyyy");
            String newStartDate1 = format.format(newStartDate);
            String newEndDate1 = format.format(newEndDate);

            return newStartDate1 + "   -   " + newEndDate1;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
