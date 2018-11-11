package com.example.together;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.together.data.EventPreview;
import com.example.together.data.EventPreviewAdapter;

import java.util.ArrayList;
import java.util.List;


public class EventListActivity extends AppCompatActivity {
    // List of all the event previews
    List<EventPreview> previewList;

    // Used to hold all the event previews
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        // Getting the RecyclerView from XML
        mRecyclerView = findViewById(R.id.previewRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initializing the event preview list
        previewList = new ArrayList<>();

        // Adding some event previews to the list (for testing purposes)
        previewList.add(
                new EventPreview(
                    "123456",
                        "Colorado Trip",
                        "Having fun times in CO +",
                        R.drawable.test
                )
        );
        previewList.add(
                new EventPreview(
                        "123456",
                        "Minnesota Trip",
                        "Having fun times in MN +",
                        R.drawable.test
                )
        );
        previewList.add(
                new EventPreview(
                        "123456",
                        "California Trip",
                        "Having fun times in CA +",
                        R.drawable.test
                )
        );
        previewList.add(
                new EventPreview(
                        "123456",
                        "Washington Trip",
                        "Having fun times in WA +",
                        R.drawable.test
                )
        );

        // Creating RecyclerView adapter
        EventPreviewAdapter adapter = new EventPreviewAdapter(this, previewList);

        // Setting the adapter to RecyclerView
        mRecyclerView.setAdapter(adapter);
    }
}
