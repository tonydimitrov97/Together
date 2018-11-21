package com.example.together;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.together.network.ApiClient;
import com.example.together.network.response.EventResponse;
import com.example.together.network.response.UserEventMapResponse;
import com.example.together.network.service.EventService;
import com.example.together.network.service.UserEventMapService;
import com.example.together.user.User;
import com.google.gson.Gson;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class JoinEventActivity extends AppCompatActivity {

    CompositeDisposable disposable = new CompositeDisposable();
    private User user;
    private Gson gson;
    UserEventMapService mappingService;
    EventService eventService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_event);

        /* Capture Intent */
        gson = new Gson();
        Intent getIntent = getIntent();
        String json = getIntent.getStringExtra("userObject");
        user = gson.fromJson(json, User.class);

        Button joinEventButton = findViewById(R.id.joinEventButton);

        mappingService = ApiClient.getClient(getApplicationContext()).create(UserEventMapService.class);
        eventService = ApiClient.getClient(getApplicationContext()).create(EventService.class);

        joinEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptJoinEvent();
            }
        });
    }

    public void attemptJoinEvent() {
        final int eventId = Integer.parseInt(((EditText)findViewById(R.id.eventId)).getText().toString());

        disposable.add(
                eventService.getEventById(eventId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<EventResponse>() {
                            @Override
                            public void onSuccess(EventResponse eventResponse) {
                                if(eventResponse.getResponse().get(0).getPublic() == 1)
                                    joinEvent(eventId);
                                else {
                                    failToJoinEvent();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                System.out.println("Error attempting to join event.");
                            }
                        })
        );
    }

    private void joinEvent(int eventId) {
        int userId = this.user.getId();

        disposable.add(
                mappingService.joinEvent(eventId, userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<UserEventMapResponse>() {

                            @Override
                            public void onSuccess(UserEventMapResponse eventResponse) {
                                successfullyJoinEvent();
                                System.out.println("Joined Event");
                            }

                            @Override
                            public void onError(Throwable e) {
                                System.out.println("Error joining event.");
                            }
                        })
        );
    }

    public void successfullyJoinEvent() {
        EventListActivity.eventListActivity.finish();
        Intent putIntent = new Intent(getApplicationContext(), EventListActivity.class);
        String json = gson.toJson(user);
        putIntent.putExtra("userObject", json);
        startActivity(putIntent);
        finish();
    }

    public void failToJoinEvent() {
        final TextView error = ((TextView)findViewById(R.id.joinErrorMessage));
        error.setText(getResources().getString(R.string.failJoin));

        error.postDelayed(new Runnable() {
            public void run() {
                error.setVisibility(View.INVISIBLE);
            }
        }, 3000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

}
