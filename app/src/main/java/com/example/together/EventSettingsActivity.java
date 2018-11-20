package com.example.together;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import com.example.together.databinding.ActivityEventSettingsBinding;
import com.example.together.event.Event;
import com.example.together.network.ApiClient;
import com.example.together.network.service.EventService;
import com.example.together.viewmodel.EventSettingsVm;
import com.google.gson.Gson;

public class EventSettingsActivity extends AppCompatActivity {

    private Gson gson;
    private Event event;
    private EventService eventService;
    ActivityEventSettingsBinding binding;
    private EventSettingsVm eventSettingsVm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_settings);

        /* Capture event from EventInfoActivity */
        gson = new Gson();
        Intent intent = getIntent();
        String json = intent.getStringExtra("eventObject");
        event = gson.fromJson(json, Event.class);

        eventService = ApiClient.getClient(getApplicationContext()).create(EventService.class);

        eventSettingsVm = new EventSettingsVm(event);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_settings);
        binding.setEsvm(eventSettingsVm);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }

}
