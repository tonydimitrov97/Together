package com.example.together;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import com.example.together.databinding.ActivityEventInfoBinding;
import com.example.together.event.Event;
import com.example.together.event.GalleryAdapter;
import com.example.together.network.ApiClient;
import com.example.together.network.response.PhotoResponse;
import com.example.together.network.response.UserEventMapResponse;
import com.example.together.network.service.PhotoService;
import com.example.together.network.service.UserEventMapService;
import com.example.together.user.User;
import com.example.together.viewmodel.EventInfoVm;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class EventInfoActivity extends AppCompatActivity {

    private GalleryAdapter adapter;
    private PhotoService photoService;
    private CompositeDisposable disposable = new CompositeDisposable();
    private PhotoResponse photoResponse;
    private UserEventMapResponse userEventMapResponse;
    private UserEventMapService userEventMapService;
    private EventInfoVm eventInfoVm;
    private Event event;
    ActivityEventInfoBinding binding;
    private Gson gson;
    private User user;
    private ImageLoader imageLoader;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Capture Intent */
        gson = new Gson();
        Intent getIntent = getIntent();
        String eventJson = getIntent.getStringExtra("eventObject");
        String userJson = getIntent.getStringExtra("userObject");
        event = gson.fromJson(eventJson, Event.class);
        user = gson.fromJson(userJson, User.class);

        /* Get all images for event */
        photoService = ApiClient.getClient(getApplicationContext()).create(PhotoService.class);
        getImages(event.getId());

        /* Setup view model and data binding */
        eventInfoVm = new EventInfoVm(event);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_info);
        binding.setEvm(eventInfoVm);

        /* Get user count */
        userEventMapService = ApiClient.getClient(getApplicationContext()).create(UserEventMapService.class);
        getUserAmount(event.getId());

        final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.eventGallery);
        recyclerView.setHasFixedSize(true);

        /* Get fixed width for each picture based on screen size */
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 4);
        recyclerView.setLayoutManager(layoutManager);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(EventInfoActivity.this));
        imageLoader = ImageLoader.getInstance();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        myToolbar.findViewById(R.id.eventSettingsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EventSettingsActivity.class);

                String eventJson = gson.toJson(event);
                String userJson = gson.toJson(user);
                intent.putExtra("eventObject", eventJson);
                intent.putExtra("userObject", userJson);
                startActivity(intent);
            }
        });

        if(!event.getCreatorId().equals(user.getId())) {
            myToolbar.findViewById(R.id.eventSettingsButton).setVisibility(View.GONE);
        }

        adapter = new GalleryAdapter(getApplicationContext(), eventInfoVm.getEventGallery(), width, imageLoader, user);
        recyclerView.setAdapter(adapter);

    }

    public void updateUserCount(int userCount) {
        eventInfoVm.setUserCount(userCount);
        binding.invalidateAll();
    }

    public void updatePhotoCount(int photoCount) {
        eventInfoVm.setPhotoCount(photoCount);
        binding.invalidateAll();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }

    private void getImages(int eventId) {
        disposable.add(
                photoService.getPhotosByEventId(eventId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<PhotoResponse>() {

                            @Override
                            public void onSuccess(PhotoResponse image) {
                                photoResponse = image;
                                eventInfoVm.setEventGallery(photoResponse.getResponse());
                                adapter.setGalleryList(photoResponse.getResponse());
                                adapter.notifyDataSetChanged();
                                updatePhotoCount(photoResponse.getResponse().size());
                            }

                            @Override
                            public void onError(Throwable e) {
                                System.out.println("Error fetching events.");
                            }
                        })
        );
    }

    private void getUserAmount(int eventId) {
        disposable.add(
                userEventMapService.getUsersByEventId(eventId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<UserEventMapResponse>() {

                            @Override
                            public void onSuccess(UserEventMapResponse response) {
                                userEventMapResponse = response;
                                updateUserCount(userEventMapResponse.getResponse().size());
                            }

                            @Override
                            public void onError(Throwable e) {
                                System.out.println("Error fetching users.");
                            }
                        })
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
        imageLoader.destroy();
    }
}
