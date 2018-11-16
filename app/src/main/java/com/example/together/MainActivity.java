package com.example.together;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.cameraview.CameraView;


public class MainActivity extends AppCompatActivity {
    CameraView mCameraView = null;
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    ImageView eventInfoView;
    ImageView eventListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCameraView = findViewById(R.id.camera);
        eventInfoView = findViewById(R.id.eventInfo);
        eventInfoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EventInfoActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        eventListView = findViewById(R.id.eventList);
        eventListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EventListActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            mCameraView.start();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        }
    }

    @Override
    protected void onPause() {
        if (mCameraView != null) {
            mCameraView.stop();
        }
        super.onPause();
    }
}
