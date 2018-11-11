package com.example.together;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.cameraview.CameraView;


public class EventInfoActivity extends AppCompatActivity {
    CameraView mCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
    }
}
