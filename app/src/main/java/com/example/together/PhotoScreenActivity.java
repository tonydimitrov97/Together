package com.example.together;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PhotoScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_screen);

        Intent intent = getIntent();
        String json = intent.getStringExtra("imageObject");

        JsonElement jelement = new JsonParser().parse(json);
        JsonObject jobject = jelement.getAsJsonObject();

        int numLikes = Integer.parseInt(jobject.get("numLikes").toString());
        String caption = jobject.get("caption").toString();
        Integer photoId = Integer.parseInt(jobject.get("imageId").toString());

        System.out.println(json);

        TextView captionView = findViewById(R.id.photoCaptionLabel);
        ImageView photoView = findViewById(R.id.picture);

        captionView.setText(caption);
        photoView.setImageResource(photoId);



    }

}
