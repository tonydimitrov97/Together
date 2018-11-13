package com.example.together;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import com.example.together.databinding.ActivityPhotoScreenBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.together.viewmodel.PhotoScreenVm;

public class PhotoScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_photo_screen);

        Intent intent = getIntent();
        String json = intent.getStringExtra("imageObject");

        PhotoScreenVm photoScreenVm = new PhotoScreenVm(json); //ViewModelProviders.of(this).get(PhotoScreenVm.class);

        ActivityPhotoScreenBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_screen);

        binding.setPsvm(photoScreenVm);

    }

}
