package com.example.together.viewmodel;

import android.arch.lifecycle.ViewModel;
import com.example.together.event.Event;

public class EventSettingsVm extends ViewModel {

    private Event event;
    private String eventTitle;
    private String eventDescription;
    private String eventLocation;
    private String startDate;
    private String endDate;

    public EventSettingsVm(Event event) {
        this.event = event;
        this.eventTitle = event.getTitle();
        this.eventDescription = event.getDescription();
        this.eventLocation = event.getLocation();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStartDate() {
        return startDate;
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
}
