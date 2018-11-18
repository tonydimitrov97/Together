package com.example.together.util;

import android.view.View;

import com.example.together.event.Event;

public class EventOnClickListener implements View.OnClickListener
{
    private Event event;

    public EventOnClickListener(Event event) {
        this.event = event;
    }

    @Override
    public void onClick(View v)
    {
        //read your lovely variable
    }

    public Event getEvent() {
        return this.event;
    }

};