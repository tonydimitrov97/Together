package com.example.together;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.KeyEvent;

import com.example.together.EventInfo.EventGallery;
import com.example.together.EventInfo.EventImage;
import com.example.together.EventInfo.GalleryAdapter;
import java.util.ArrayList;

public class EventInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        EventGallery eventGallery = new EventGallery();
        eventGallery.initializeGallery();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.eventGallery);
        recyclerView.setHasFixedSize(true);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 4);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<EventImage> imageList = eventGallery.getPhotoList();
        GalleryAdapter adapter = new GalleryAdapter(getApplicationContext(), imageList, width, height);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }
}
