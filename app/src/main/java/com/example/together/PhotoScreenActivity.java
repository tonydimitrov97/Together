package com.example.together;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import com.example.together.configuration.Configuration;
import com.example.together.databinding.ActivityPhotoScreenBinding;
import android.os.Bundle;
import android.view.KeyEvent;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.example.together.event.EventImage;
import com.example.together.util.ImageDownloader;
import com.example.together.viewmodel.PhotoScreenVm;
import com.google.gson.Gson;

public class PhotoScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String json = intent.getStringExtra("imageObject");
        EventImage eventImage = new Gson().fromJson(json, EventImage.class);

        PhotoScreenVm photoScreenVm = new PhotoScreenVm(eventImage); //ViewModelProviders.of(this).get(PhotoScreenVm.class);

        ActivityPhotoScreenBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_screen);

        binding.setPsvm(photoScreenVm);

        new ImageDownloader((ImageView) findViewById(R.id.photoScreenImage))
                .execute(Configuration.SERVER_IP + eventImage.getPath());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }

}
