package com.example.together;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import com.example.together.databinding.ActivityEventSettingsBinding;
import com.example.together.event.Event;
import com.example.together.network.ApiClient;
import com.example.together.network.response.EventResponse;
import com.example.together.network.service.EventService;
import com.example.together.user.User;
import com.example.together.viewmodel.EventSettingsVm;
import com.google.gson.Gson;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class EventSettingsActivity extends AppCompatActivity {

    private Gson gson;
    private Event event;
    private EventService eventService;
    ActivityEventSettingsBinding binding;
    private EventSettingsVm eventSettingsVm;
    private CompositeDisposable disposable;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_settings);

        /* Capture event from EventInfoActivity */
        gson = new Gson();
        Intent intent = getIntent();
        String userJson = intent.getStringExtra("userObject");
        String eventJson = intent.getStringExtra("eventObject");
        event = gson.fromJson(eventJson, Event.class);
        user = gson.fromJson(userJson, User.class);

        eventService = ApiClient.getClient(getApplicationContext()).create(EventService.class);
        disposable = new CompositeDisposable();

        eventSettingsVm = new EventSettingsVm(event);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_settings);
        binding.setEsvm(eventSettingsVm);

        ((Button)findViewById(R.id.updateEventButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEvent();
            }
        });

        ((Button)findViewById(R.id.deleteEventButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEvent();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }

    public void updateEvent() {
        int eventId = event.getId();
        String eventTitle = ((EditText)findViewById(R.id.eventTitleInput)).getText().toString();
        String startDate = ((EditText)findViewById(R.id.startDateInput)).getText().toString();
        String endDate = ((EditText)findViewById(R.id.endDateInput)).getText().toString();
        String location = ((EditText)findViewById(R.id.locationInput)).getText().toString();
        int creatorId = event.getCreatorId();
        String description = ((EditText)findViewById(R.id.descriptionInput)).getText().toString();
        boolean _public = ((Switch)findViewById(R.id.privacySwitch)).isChecked();
        String thumbnail = event.getThumbnail();
        String eventCode = event.getEventCode();

        disposable.add(
                eventService.updateEvent(eventId, eventTitle, startDate, endDate, location, creatorId, description, _public, thumbnail, eventCode)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<EventResponse>() {

                            @Override
                            public void onSuccess(EventResponse eventResponse) {
                                goBackToEventInfo(eventResponse.getResponse().get(0));
                                System.out.println("Successfully updated event.");
                            }

                            @Override
                            public void onError(Throwable e) {
                                System.out.println("Failed to update event.");
                            }

                        })
        );

    }

    public void deleteEvent() {
        disposable.add(
                eventService.deleteEvent(event.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Response>() {

                            @Override
                            public void onSuccess(Response response) {
                                goBackToEventList();
                                System.out.println("Successfully deleted event.");
                            }

                            @Override
                            public void onError(Throwable e) {
                                System.out.println("Failed to delete event.");
                            }
                        })
        );
    }

    public void goBackToEventInfo(Event myEvent) {
        Intent intent = new Intent(getApplicationContext(), EventInfoActivity.class);
        String eventJson = gson.toJson(myEvent);
        String userJson = gson.toJson(user);
        intent.putExtra("eventObject", eventJson);
        intent.putExtra("userObject", userJson);
        startActivity(intent);
        finish();
    }

    public void goBackToEventList() {
        Intent intent = new Intent(getApplicationContext(), EventListActivity.class);
        String userJson = gson.toJson(user);
        intent.putExtra("userObject", userJson);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

}
