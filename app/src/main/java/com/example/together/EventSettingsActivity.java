package com.example.together;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.example.together.event.Event;
import com.example.together.viewmodel.EventSettingsVm;
import com.google.gson.Gson;

public class EventSettingsActivity extends AppCompatActivity {

    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_settings);

        /* Capture event from EventInfoActivity */
        Intent intent = getIntent();
        String json = intent.getStringExtra("eventObject");
        event = new Gson().fromJson(json, Event.class);

        EventSettingsVm eventSettingsVm = new EventSettingsVm(event);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }

}
