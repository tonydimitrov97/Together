package com.example.together;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.example.together.event.EventGallery;
import com.example.together.event.EventImage;
import com.example.together.event.GalleryAdapter;
import com.example.together.network.Environment;
import com.example.together.network.EventEndpoint;
import com.example.together.network.model.EventModel;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        Environment env = new Environment();

        Call<EventModel> call = env.getEvents().getEvent(1);



        call.enqueue(new Callback<EventModel>() {

            @Override
            public void onResponse(Call<EventModel> call, Response<EventModel> response) {
                EventModel myEvent = response.body();
                System.out.println("RESPONSE: " + myEvent.getId());
            }

            @Override
            public void onFailure(Call<EventModel> call, Throwable t) {
                System.out.println("Error accessing api: " + t.getMessage());
            }
        });



        EventGallery eventGallery = new EventGallery();
        eventGallery.initializeGallery();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.eventGallery);
        recyclerView.setHasFixedSize(true);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 4);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<EventImage> imageList = eventGallery.getPhotoList();
        GalleryAdapter adapter = new GalleryAdapter(getApplicationContext(), imageList, width);
        recyclerView.setAdapter(adapter);
    }
}
