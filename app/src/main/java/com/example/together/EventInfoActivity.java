package com.example.together;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import com.example.together.event.EventGallery;
import com.example.together.event.EventImage;
import com.example.together.event.GalleryAdapter;
import java.util.ArrayList;

public class EventInfoActivity extends AppCompatActivity {


    private GalleryAdapter adapter;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        EventGallery eventGallery = new EventGallery(1);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.eventGallery);
        recyclerView.setHasFixedSize(true);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 4);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<EventImage> imageGallery = eventGallery.getPhotoList();
        adapter = new GalleryAdapter(getApplicationContext(), imageGallery, width);
        recyclerView.setAdapter(adapter);

    }

}
