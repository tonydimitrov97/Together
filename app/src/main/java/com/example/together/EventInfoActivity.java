package com.example.together;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.databinding.DataBindingUtil;
import android.view.View;
import com.example.together.databinding.ActivityEventInfoBinding;
import com.example.together.event.Event;
import com.example.together.event.GalleryAdapter;
import com.example.together.network.ApiClient;
import com.example.together.network.response.EventImageResponse;
import com.example.together.network.service.EventImageService;
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
    private EventImageService eventImageService;
    private CompositeDisposable disposable = new CompositeDisposable();
    private EventImageResponse eventImageResponse;
    private EventInfoVm eventInfoVm;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Capture Intent */
        Intent intent = getIntent();
        String json = intent.getStringExtra("eventObject");
        Event event = new Gson().fromJson(json, Event.class);

        eventImageService = ApiClient.getClient(getApplicationContext()).create(EventImageService.class);
        getImages();

        /* Setup view model and data binding */
        eventInfoVm = new EventInfoVm(event);
        ActivityEventInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_event_info);
        binding.setEvm(eventInfoVm);

        final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.eventGallery);
        recyclerView.setHasFixedSize(true);

        /* Get fixed width for each picture based on screen size */
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 4);
        recyclerView.setLayoutManager(layoutManager);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(EventInfoActivity.this));
        ImageLoader imageLoader = ImageLoader.getInstance();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        myToolbar.findViewById(R.id.eventSettingsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EventSettingsActivity.class);

                startActivity(intent);
            }
        });

        adapter = new GalleryAdapter(getApplicationContext(), eventInfoVm.getEventGallery(), width, imageLoader);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }

    private void getImages() {
        disposable.add(
                eventImageService.getPhotos()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<EventImageResponse>() {

                            @Override
                            public void onSuccess(EventImageResponse image) {
                                eventImageResponse = image;
                                eventInfoVm.setEventGallery(eventImageResponse.getResponse());
                                adapter.setGalleryList(eventImageResponse.getResponse());
                                adapter.notifyDataSetChanged();
                                //Check response
                            }

                            @Override
                            public void onError(Throwable e) {
                                System.out.println("Error fetching events.");
                            }
                        })
        );
    }
}
