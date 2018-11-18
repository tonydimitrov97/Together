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
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.together.network.response.EventImageResponse;
import com.example.together.network.service.EventImageService;
import com.google.android.cameraview.CameraView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    CameraView mCameraView = null;
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    ImageView eventInfoView;
    ImageView eventListView;
    ImageView captureButton;
    private Handler mBackgroundHandler;
    ProgressDialog progressDialog;
    private EventImageService eventImageService;
    private CompositeDisposable disposable = new CompositeDisposable();
    private EventImageResponse eventImageResponse;
    String URL = "http://10.0.0.40:3000/api/photo/upload";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCameraView = findViewById(R.id.camera);
        if (mCameraView != null) {
            mCameraView.addCallback(mCallback);
        }

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
            Toast.makeText(cameraView.getContext(), R.string.picture_taken, Toast.LENGTH_SHORT)
                    .show();

            eventImageService.uploadPhoto(data).enqueue(new Callback<byte[]>() {
                @Override
                public void onResponse(Call<byte[]> call, retrofit2.Response<byte[]> response) {
                    if (response.isSuccessful()) {
                        System.out.println("Success");
                    }
                }


                @Override
                public void onFailure(Call<byte[]> call, Throwable t) {
                    System.out.println("Failure");
                }
            });
        }

            //progressDialog = new ProgressDialog(MainActivity.this);
            //progressDialog.setMessage("Uploading, please wait...");
            //progressDialog.show();

            //final String imageString = Base64.encodeToString(data, Base64.DEFAULT);

            //sending image to server
            /*StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    progressDialog.dismiss();
                    if(s.equals("true")){
                        Toast.makeText(MainActivity.this, "Uploaded Successful", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Some error occurred!", Toast.LENGTH_LONG).show();
                    }
                }
            } ,new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(MainActivity.this, "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();;
                }
            }) {
                //adding parameters to send
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("image", imageString);
                    return parameters;
                }
            };

            RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
            rQueue.add(request);
        }*/
    };
}
