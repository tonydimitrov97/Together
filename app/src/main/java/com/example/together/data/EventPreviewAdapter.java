package com.example.together.data;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.together.EventInfoActivity;
import com.example.together.R;
import com.example.together.event.Event;
import com.example.together.util.IntegerOnClickListener;
import com.google.gson.Gson;

import java.util.List;

public class EventPreviewAdapter extends RecyclerView.Adapter<EventPreviewAdapter.EventPreviewHolder> {

    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<EventPreview> previewList;

    //List of Event objects for each event listed on the screen
    private List<Event> eventList;

    //getting the context and product list with constructor
    public EventPreviewAdapter(Context mCtx, List<EventPreview> previewList) {
        this.mCtx = mCtx;
        this.previewList = previewList;
    }

    @NonNull
    @Override
    public EventPreviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_preview, null);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        return new EventPreviewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventPreviewHolder holder, int position) {
        //getting the product of the specified position
        EventPreview preview = previewList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(preview.getEventName());
        holder.textViewShortDesc.setText(preview.getShortDesc());
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(preview.getImage()));

        /* When an event is clicked, pass the data to the event screen and start the activity */
        holder.itemView.setOnClickListener(new IntegerOnClickListener(position) {
            public void onClick(View v) {
                int index = this.getIndex();
                Intent intent = new Intent(mCtx, EventInfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Gson gson = new Gson();
                String eventJson = gson.toJson(eventList.get(index));

                intent.putExtra("eventObject", eventJson);
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return previewList.size();
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    class EventPreviewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        ImageView imageView;

        public EventPreviewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
