package com.example.together;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.together.data.EventPreview;
import com.example.together.data.EventPreviewAdapter;
import com.example.together.event.Event;
import com.example.together.network.ApiClient;
import com.example.together.network.endpoint.EventService;
import com.example.together.network.response.EventResponse;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class EventListActivity extends AppCompatActivity {
    // List of all the event previews
    List<EventPreview> previewList;

    // Used to hold all the event previews
    RecyclerView mRecyclerView;

    private EventService eventService;
    private CompositeDisposable disposable = new CompositeDisposable();
    private EventResponse eventResponse;
    private EventPreviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        eventService = ApiClient.getClient(getApplicationContext()).create(EventService.class);

        getEvents();

        // Getting the RecyclerView from XML
        mRecyclerView = findViewById(R.id.previewRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initializing the event preview list
        previewList = new ArrayList<>();

        // Creating RecyclerView adapter
        adapter = new EventPreviewAdapter(this, previewList);

        // Setting the adapter to RecyclerView
        mRecyclerView.setAdapter(adapter);
    }

    private void getEvents() {
        disposable.add(
                eventService.getEvents()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<EventResponse>() {

                            @Override
                            public void onSuccess(EventResponse event) {
                                eventResponse = event;
                                createEventList(eventResponse.getResponse());
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onError(Throwable e) {
                                System.out.println("Error fetching events.");
                            }
                        })
        );
    }


    public void createEventList(List<Event> eventList) {

        for (int i = 0; i < eventList.size(); i++) {
            previewList.add(
                    new EventPreview(
                            eventList.get(i).getEventCode(),
                            eventList.get(i).getTitle(),
                            eventList.get(i).getDescription(),
                            R.drawable.test
                    )
            );
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

}
