package com.example.together;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.together.configuration.Configuration;
import com.example.together.databinding.ActivityPhotoScreenBinding;
import com.example.together.event.EventImage;
import com.example.together.viewmodel.PhotoScreenVm;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class PhotoScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String json = intent.getStringExtra("imageObject");
        EventImage eventImage = new Gson().fromJson(json, EventImage.class);

        final PhotoScreenVm photoScreenVm = new PhotoScreenVm(eventImage); //ViewModelProviders.of(this).get(PhotoScreenVm.class);

        final ActivityPhotoScreenBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_screen);
        binding.setPsvm(photoScreenVm);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(PhotoScreenActivity.this));
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(Configuration.SERVER_IP + "images/" + eventImage.getPath(), (ImageView) findViewById(R.id.photoScreenImage));

        ImageButton likeButton = findViewById(R.id.likeButton);
        likeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(photoScreenVm.handleLike())
                    ((ImageButton)v).setImageResource(R.drawable.ic_favorite_black_24dp);
                else
                    ((ImageButton)v).setImageResource(R.drawable.like_outline);

                binding.invalidateAll();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }

}
