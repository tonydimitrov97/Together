package com.example.together;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.example.together.user.User;
import com.google.gson.Gson;

public class JoinEventActivity extends AppCompatActivity {

    private User user;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_event);

        /* Capture Intent */
        gson = new Gson();
        Intent getIntent = getIntent();
        String json = getIntent.getStringExtra("userObject");
        user = gson.fromJson(json, User.class);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }

}
