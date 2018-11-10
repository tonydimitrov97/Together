package com.example.together;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.cameraview.CameraView;


public class MainActivity extends AppCompatActivity {
    CameraView mCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // mCameraView = findViewById(R.id.camera);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mCameraView.start();
    }

    @Override
    protected void onPause() {
       // mCameraView.stop();
        super.onPause();
    }
}
