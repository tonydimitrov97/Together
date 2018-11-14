package com.example.together;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.together.event.Event;
import com.example.together.event.EventGallery;
import com.example.together.event.EventImage;
import com.example.together.event.GalleryAdapter;
import com.example.together.network.RequestHandler;
import com.example.together.network.response.EventResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        RequestHandler requestHandler = new RequestHandler();

        Call<EventResponse> call = requestHandler.getEventService().getEventById(1);

        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventResponse> call, @NonNull Response<EventResponse> response) {

            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                System.out.println("Error calling event API.");
            }
        });

        EventGallery eventGallery = new EventGallery(1);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.eventGallery);
        recyclerView.setHasFixedSize(true);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 4);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<EventImage> imageGallery = eventGallery.getPhotoList();
        GalleryAdapter adapter = new GalleryAdapter(getApplicationContext(), imageGallery, width);
        recyclerView.setAdapter(adapter);
    }
}
