package com.example.together;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import com.example.together.configuration.Configuration;
import com.google.android.cameraview.CameraView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    CameraView mCameraView = null;
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    ImageView eventInfoView;
    ImageView eventListView;
    ImageView captureButton;
    private Handler mBackgroundHandler;
    ProgressDialog prgDialog;
    RequestParams params = new RequestParams();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCameraView = findViewById(R.id.camera);
        if (mCameraView != null) {
            mCameraView.addCallback(mCallback);
        }

        prgDialog = new ProgressDialog(this);
        prgDialog.setCancelable(false);


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

        captureButton = findViewById(R.id.captureButton);
        if (captureButton != null) {
            captureButton.setOnClickListener(mOnClickListener);
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBackgroundHandler != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                mBackgroundHandler.getLooper().quitSafely();
            } else {
                mBackgroundHandler.getLooper().quit();
            }
            mBackgroundHandler = null;
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.captureButton:
                    if (mCameraView != null) {
                        mCameraView.takePicture();
                    }
                    break;
            }
        }
    };

    private Handler getBackgroundHandler() {
        if (mBackgroundHandler == null) {
            HandlerThread thread = new HandlerThread("background");
            thread.start();
            mBackgroundHandler = new Handler(thread.getLooper());
        }
        return mBackgroundHandler;
    }

    private CameraView.Callback mCallback
            = new CameraView.Callback() {

        @Override
        public void onPictureTaken(CameraView cameraView, final byte[] data) {

            String encodedString = Base64.encodeToString(data, 0);
            params.put("image", encodedString);

            prgDialog.setMessage("Uploading...");
            prgDialog.show();

            uploadPhoto();


        }
    };

    // Make Http call to upload Image to Php server
    public void uploadPhoto() {
        AsyncHttpClient client = new AsyncHttpClient();
        // Don't forget to change the IP address to your LAN address. Port no as well.
        client.post(Configuration.UPLOAD_IP,
                params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        prgDialog.hide();
                        System.out.println("Image Uploaded.");
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        // Hide Progress Dialog
                        prgDialog.hide();

                        if (statusCode == 404) {
                            System.out.println("Requested resource not found.");
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            System.out.println("Something went wrong at server end.");
                        }
                        // When Http response code other than 404, 500
                        else {
                            System.out.println("Error Occured n Most Common Error: n1. Device not connected to Internetn2. Web App is not deployed in App servern3. App server is not runningn HTTP Status code : "
                            + statusCode);
                        }
                    }

                });
    }

}
