package com.example.together.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.together.R;

import java.util.List;

public class EventPreviewAdapter extends RecyclerView.Adapter<EventPreviewAdapter.EventPreviewHolder> {

    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<EventPreview> previewList;

    //getting the context and product list with constructor
    public EventPreviewAdapter(Context mCtx, List<EventPreview> previewList) {
        this.mCtx = mCtx;
        this.previewList = previewList;
    }

    @Override
    public EventPreviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_preview, null);
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

    }

    @Override
    public int getItemCount() {
        return previewList.size();
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
