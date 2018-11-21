package com.example.together.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.together.EventInfoActivity;
import com.example.together.R;
import com.example.together.configuration.Configuration;
import com.example.together.event.Event;
import com.example.together.network.ApiClient;
import com.example.together.network.response.UserEventMapResponse;
import com.example.together.network.service.UserEventMapService;
import com.example.together.user.User;
import com.example.together.util.IntegerOnClickListener;
import com.google.gson.Gson;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class EventPreviewAdapter extends RecyclerView.Adapter<EventPreviewAdapter.EventPreviewHolder> {

    private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec

    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<EventPreview> previewList;

    //List of Event objects for each event listed on the screen
    private List<Event> eventList;

    List<EventPreview> itemsPendingRemoval;
    private boolean undoOn = true;

    private User user;
    private Gson gson;

    private Handler handler = new Handler(); // hanlder for running delayed runnables
    private HashMap<EventPreview, Runnable> pendingRunnables = new HashMap<>(); // map of items to pending runnables, so we can cancel a removal if need be
    private ImageLoader imageLoader;
    private UserEventMapService userEventMapService;
    CompositeDisposable disposable;

    //getting the context and product list with constructor
    public EventPreviewAdapter(Context mCtx, List<EventPreview> previewList, User user,
                               ImageLoader imageLoader) {
        this.mCtx = mCtx;
        this.previewList = previewList;
        this.itemsPendingRemoval = new ArrayList<>();
        this.user = user;
        this.imageLoader = imageLoader;
        this.gson = new Gson();
        this.disposable = new CompositeDisposable();
    }

    public boolean isUndoOn() {
        return this.undoOn;
    }

    @NonNull
    @Override
    public EventPreviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_preview, null);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
       // this.myErrorView = parent.findViewById(R.id.errorLayout);
        return new EventPreviewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventPreviewHolder holder, int position) {
        //getting the product of the specified position
        final EventPreview preview = previewList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(preview.getEventName());
        holder.textViewShortDesc.setText(preview.getShortDesc());
        String eventId = "Event Id: " + preview.getEventId();
        holder.textViewEventId.setText(eventId);
        if(preview.getThumbnail() != null) {
            this.imageLoader.displayImage(Configuration.SERVER_IP + "images/" + preview.getThumbnail(), holder.imageView);
        } else {
            holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(preview.getImage()));
        }

        if(preview.getEventId() == user.getActiveEvent()) {
            ((CircularImageView)holder.imageView).setBorderColor(mCtx.getResources().getColor(R.color.current_event));
        }
        if(preview.get_public() == 1) {
            holder.lockIconView.setImageResource(R.drawable.unlock);
        }

        if (itemsPendingRemoval.contains(preview)) {
            // we need to show the "undo" state of the row

            holder.itemView.findViewById(R.id.myCardView).setBackgroundColor(mCtx.getResources().getColor(R.color.delete_red));
            holder.itemView.findViewById(R.id.relativeLayout).setVisibility(View.INVISIBLE);

            /* Only allow users who are not the creator to leave an event */
            holder.itemView.findViewById(R.id.removeUserWarning).setVisibility(View.VISIBLE);
            holder.textViewTitle.setVisibility(View.GONE);
            holder.undoButton.setVisibility(View.VISIBLE);
            holder.undoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // user wants to undo the removal, let's cancel the pending task
                    Runnable pendingRemovalRunnable = pendingRunnables.get(preview);
                    pendingRunnables.remove(preview);
                    if (pendingRemovalRunnable != null)
                        handler.removeCallbacks(pendingRemovalRunnable);
                    itemsPendingRemoval.remove(preview);
                    // this will rebind the row in "normal" state
                    notifyItemChanged(previewList.indexOf(preview));
                }
            });
        } else {
            // we need to show the "normal" state
            holder.itemView.findViewById(R.id.relativeLayout).setVisibility(View.VISIBLE);
            holder.itemView.findViewById(R.id.myCardView).setBackgroundColor(Color.WHITE);
            holder.itemView.findViewById(R.id.removeUserWarning).setVisibility(View.GONE);
            holder.textViewTitle.setVisibility(View.VISIBLE);
            holder.undoButton.setVisibility(View.GONE);
            holder.undoButton.setOnClickListener(null);
        }

        /* When an event is clicked, pass the data to the event screen and start the activity */
        holder.itemView.setOnClickListener(new IntegerOnClickListener(position) {
            public void onClick(View v) {
                int index = this.getIndex();
                Intent intent = new Intent(mCtx, EventInfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String eventJson = gson.toJson(eventList.get(index));
                String userJson = gson.toJson(user);
                intent.putExtra("userObject", userJson);
                intent.putExtra("eventObject", eventJson);
                mCtx.startActivity(intent);
            }
        });
    }

    public void pendingRemoval(int position) {
        final EventPreview item = previewList.get(position);
            if (!itemsPendingRemoval.contains(item)) {
                itemsPendingRemoval.add(item);
                // this will redraw row in "undo" state
                notifyItemChanged(position);
                // let's create, store and post a runnable to remove the item
                Runnable pendingRemovalRunnable = new Runnable() {
                    @Override
                    public void run() {
                        remove(previewList.indexOf(item));
                        removeUserFromEvent(item.getEventId(), user.getId());
                    }
                };
                handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
                pendingRunnables.put(item, pendingRemovalRunnable);
            }
    }

    private void removeUserFromEvent(int eventId, int userId) {
        userEventMapService = ApiClient.getClient(mCtx).create(UserEventMapService.class);

        disposable.add(
                userEventMapService.removeUserFromEvent(userId, eventId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<UserEventMapResponse>() {

                            @Override
                            public void onSuccess(UserEventMapResponse response) {
                                System.out.println("Removed user from event");
                            }

                            @Override
                            public void onError(Throwable e) {
                                System.out.println("Error removing user from event.");
                            }
                        })
        );
    }

    public void remove(int position) {
        EventPreview item = previewList.get(position);
        if (itemsPendingRemoval.contains(item)) {
            itemsPendingRemoval.remove(item);
        }
        if (previewList.contains(item)) {
            previewList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public boolean isPendingRemoval(int position) {
        EventPreview item = previewList.get(position);
        return itemsPendingRemoval.contains(item);
    }

    @Override
    public int getItemCount() {
        return previewList.size();
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    class EventPreviewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewEventId;
        ImageView imageView, lockIconView;
        Button undoButton;

        public EventPreviewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            imageView = itemView.findViewById(R.id.imageView);
            undoButton = itemView.findViewById(R.id.undo_button);
            lockIconView = itemView.findViewById(R.id.lockIcon);
            textViewEventId = itemView.findViewById(R.id.eventId);
        }
    }
}
