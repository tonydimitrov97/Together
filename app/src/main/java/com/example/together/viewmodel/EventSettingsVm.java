package com.example.together.viewmodel;

import android.arch.lifecycle.ViewModel;
import com.example.together.event.Event;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventSettingsVm extends ViewModel {

    private Event event;
    private String eventTitle;
    private String eventDescription;
    private String eventLocation;
    private String start_date;
    private String end_date;
    private int _public;

    public EventSettingsVm(Event event) {
        this.event = event;
        this.eventTitle = event.getTitle();
        this.eventDescription = event.getDescription();
        this.eventLocation = event.getLocation();
        this.start_date = event.getStart_date();
        this.end_date = event.getEnd_date();
        this._public = event.getPublic();
    }

    public String getEnd_date() {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date currentEndDate = format.parse(this.end_date);
            format = new SimpleDateFormat("yyyy-MM-dd");
            String newEndDate = format.format(currentEndDate);
            return newEndDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getStart_date() {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date currentStartDate = format.parse(this.start_date);
            format = new SimpleDateFormat("yyyy-MM-dd");
            String newStartDate = format.format(currentStartDate);
            return newStartDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
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

    public boolean get_public() {
        return _public == 1;
    }
}
