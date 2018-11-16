package com.example.together.viewmodel;

import android.arch.lifecycle.ViewModel;
import com.example.together.event.Event;
import com.example.together.event.EventImage;
import java.util.ArrayList;
import java.util.List;

public class EventInfoVm extends ViewModel {

    private Event event;
    private String eventTitle;
    private String eventDescription;
    private String eventLocation;
    private List<EventImage> eventGallery;
    private int numPhotos;

    public EventInfoVm(Event event) {
        this.event = event;
        this.eventTitle = event.getTitle();
        this.eventDescription = event.getDescription();
        this.eventLocation = event.getLocation();
        this.eventGallery = new ArrayList<EventImage>();
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

}
