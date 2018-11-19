package com.example.together;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.together.network.ApiClient;
import com.example.together.network.response.EventResponse;
import com.example.together.network.service.EventService;
import com.example.together.user.User;
import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CreateEventActivity extends AppCompatActivity {

    CompositeDisposable disposable = new CompositeDisposable();
    EventService eventService;
    private Gson gson;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);


        /* Capture Intent */
        gson = new Gson();
        Intent getIntent = getIntent();
        String json = getIntent.getStringExtra("userObject");
        user = gson.fromJson(json, User.class);

        Button createEventButton = (Button)findViewById(R.id.createEventButton);

        eventService = ApiClient.getClient(getApplicationContext()).create(EventService.class);

        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEvent();
                Intent putIntent = new Intent();
                String json = gson.toJson(user);
                putIntent.putExtra("userObject", json);
                startActivity(putIntent);
                finish();
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

    private void createEvent() {

        String title = ((EditText)findViewById(R.id.eventTitleInput)).getText().toString();
        String start_date = ((EditText)findViewById(R.id.startDateInput)).getText().toString();
        String end_date = ((EditText)findViewById(R.id.endDateInput)).getText().toString();
        String location = ((EditText)findViewById(R.id.locationInput)).getText().toString();
        int creatorId = this.user.getId();
        String description = ((EditText)findViewById(R.id.descriptionInput)).getText().toString();
        boolean _public = ((Switch)findViewById(R.id.privacySwitch)).isChecked();

        disposable.add(
                eventService.addEvent(title, start_date, end_date, location, creatorId, description, _public)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<EventResponse>() {

                            @Override
                            public void onSuccess(EventResponse eventResponse) {
                                System.out.println("Created Event");
                            }

                            @Override
                            public void onError(Throwable e) {
                                System.out.println("Error fetching events.");
                            }
                        })
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
