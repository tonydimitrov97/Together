package com.example.together;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.together.network.ApiClient;
import com.example.together.network.response.UserEventMapResponse;
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

        joinEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventListActivity.eventListActivity.finish();
                joinEvent();
                Intent putIntent = new Intent(getApplicationContext(), EventListActivity.class);
                String json = gson.toJson(user);
                putIntent.putExtra("userObject", json);
                startActivity(putIntent);
                finish();
            }
        });

    }

    private void joinEvent() {
        int userId = this.user.getId();
        int eventId = Integer.parseInt(((EditText)findViewById(R.id.eventId)).getText().toString());

        disposable.add(
                mappingService.joinEvent(eventId, userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<UserEventMapResponse>() {

                            @Override
                            public void onSuccess(UserEventMapResponse eventResponse) {
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
