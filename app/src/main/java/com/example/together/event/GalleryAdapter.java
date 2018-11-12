package com.example.together.event;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.together.PhotoScreenActivity;
import com.example.together.R;
import com.example.together.util.CustomOnClickListener;
import com.google.gson.Gson;

import java.util.ArrayList;


public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private ArrayList<EventImage> galleryList;
    private Context context;
    private int width;
    private int height;
    

    public GalleryAdapter(Context context, ArrayList<EventImage> galleryList, int width, int height) {
        this.galleryList = galleryList;
        this.context = context;
        this.width = width;
        this.height = height;
    }

    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryAdapter.ViewHolder viewHolder, int i) {
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.img.setImageResource((galleryList.get(i).getImageId()));
        viewHolder.img.setOnClickListener(new CustomOnClickListener(i) {
            public void onClick(View v) {
                int index = this.getIndex();
                Intent intent = new Intent(context, PhotoScreenActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Gson gson = new Gson();
                String json = gson.toJson(galleryList.get(index));

                intent.putExtra("imageObject", json);
                context.startActivity(intent);
            }
        });
        int dimension = (width/4);
        viewHolder.img.setLayoutParams(new RelativeLayout.LayoutParams(dimension, dimension));
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        public ViewHolder(View view) {
            super(view);

            img = (ImageView) view.findViewById(R.id.img);
        }
    }
}