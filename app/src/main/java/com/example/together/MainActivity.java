package com.example.together;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.together.configuration.Configuration;
import com.example.together.event.Event;
import com.example.together.network.ApiClient;
import com.example.together.network.response.EventResponse;
import com.example.together.network.response.PhotoResponse;
import com.example.together.network.service.EventService;
import com.example.together.network.service.PhotoService;
import com.example.together.user.User;
import com.google.android.cameraview.CameraView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import cz.msebera.android.httpclient.Header;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    CameraView mCameraView = null;
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    ImageView eventInfoView;
    ImageView eventListView;
    ImageView captureButton;
    ImageButton switchCamera;
    ImageButton flashButton;
    private Handler mBackgroundHandler;
    private User user;
    ProgressDialog prgDialog;
    String currentEvent;
    Gson gson;
    RequestParams params = new RequestParams();
    private EventService eventService;
    private PhotoService photoService;
    private CompositeDisposable disposable = new CompositeDisposable();
    private Event event;
    private ArrayList<String> eventNames;
    private List<Event> eventList;
    Spinner eventSwitcher;


    private static final int[] FLASH_OPTIONS = {
            CameraView.FLASH_OFF,
            CameraView.FLASH_ON,
    };

    private static final int[] FLASH_ICONS = {
            R.drawable.flash_off,
            R.drawable.flash_on,
    };

    private int mCurrentFlash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gson = new Gson();

        /* Capture Intent */
        Intent intent = getIntent();
        String json = intent.getStringExtra("userObject");
        user = gson.fromJson(json, User.class);

        eventSwitcher = ((Spinner)findViewById(R.id.currentEventSpinner));
        eventService = ApiClient.getClient(getApplicationContext()).create(EventService.class);
        getEvents();


        mCameraView = findViewById(R.id.camera);
        if (mCameraView != null) {
            mCameraView.addCallback(mCallback);
        }

        prgDialog = new ProgressDialog(this);
        prgDialog.setCancelable(false);

        eventService = ApiClient.getClient(getApplicationContext()).create(EventService.class);
        photoService = ApiClient.getClient(getApplicationContext()).create(PhotoService.class);

        disposable.add(
                eventService.getEventById(user.getActiveEvent())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<EventResponse>() {
                            @Override
                            public void onSuccess(EventResponse eventResponse) {
                                setEvent(eventResponse.getResponse().get(0));
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        })
        );

        eventInfoView = findViewById(R.id.eventInfo);
        eventInfoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EventInfoActivity.class);
                String userJson = gson.toJson(user);
                intent.putExtra("userObject", userJson);
                intent.putExtra("eventObject", currentEvent);
                v.getContext().startActivity(intent);
            }
        });

        eventListView = findViewById(R.id.eventList);
        eventListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EventListActivity.class);
                String userJson = gson.toJson(user);
                intent.putExtra("userObject", userJson);
                v.getContext().startActivity(intent);
            }
        });

        captureButton = findViewById(R.id.captureButton);
        if (captureButton != null) {
            captureButton.setOnClickListener(mOnClickListener);
        }

        switchCamera = findViewById(R.id.switchCamera);
        switchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int facing = mCameraView.getFacing();
                mCameraView.setFacing(facing == CameraView.FACING_FRONT ?
                        CameraView.FACING_BACK : CameraView.FACING_FRONT);
            }
        });

        flashButton = findViewById(R.id.flash_off);
        flashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentFlash = (mCurrentFlash + 1) % FLASH_OPTIONS.length;
                flashButton.setImageResource(FLASH_ICONS[mCurrentFlash]);
                mCameraView.setFlash(FLASH_OPTIONS[mCurrentFlash]);
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
        disposable.dispose();
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        disposable.add(
                photoService.uploadPhoto(event.getId(), user.getId(), event.getLocation(), dateFormat.format(date),
                        "")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<PhotoResponse>() {
                            @Override
                            public void onSuccess(PhotoResponse photoResponse) {

                                int id = photoResponse.getResponse().get(0).getId();
                                params.put("id", id);
                                AsyncHttpClient client = new AsyncHttpClient();
                                // Don't forget to change the IP address to your LAN address. Port no as well.
                                client.post(Configuration.UPLOAD_IP,
                                        params, new AsyncHttpResponseHandler() {
                                            @Override
                                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                                prgDialog.hide();

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

                            @Override
                            public void onError(Throwable e) {

                            }
                        })
        );
        System.out.println("Image Uploaded.");
    }


    private void getEvents() {
        disposable.add(
                eventService.getEventsByUserId(this.user.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<EventResponse>() {

                            @Override
                            public void onSuccess(EventResponse response) {
                                setLists(response.getResponse());
                            }

                            @Override
                            public void onError(Throwable e) {
                                System.out.println("Error fetching events.");
                            }
                        })
        );
    }

    private void setLists(List<Event> eventList) {
        this.eventList = eventList;
        ArrayList<String> namesList = new ArrayList<String>();
        for(int i = 0; i < eventList.size(); i++) {
            namesList.add(eventList.get(i).getTitle());
        }
        this.eventNames = namesList;
        setupSpinner();
    }

    public void setupSpinner() {
        ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, eventNames);

        eventSwitcher.setAdapter(adp);

        for(int i = 0; i < eventNames.size(); i++) {
            if(eventList.get(i).getId() == user.getActiveEvent()) {
                eventSwitcher.setSelection(adp.getPosition(eventList.get(i).getTitle()));
                break;
            }
        }

        eventSwitcher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3) {
               setEvent(eventList.get(position));
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }


    public void setEvent(Event currEvent) {
        this.currentEvent = gson.toJson(currEvent);
        user.setActiveEvent(currEvent.getId());
        event = currEvent;
    }
}
