package com.example.together.event;

import com.example.together.R;

import java.util.ArrayList;
import java.util.Random;

public class EventGallery {

    ArrayList<EventImage> myGallery;
    Random random;
    Integer[] images;


    public EventGallery() {
        myGallery = new ArrayList<EventImage>();
        random = new Random();
        images = new Integer[3];
        images[0] = R.mipmap.image1;
        images[1] = R.mipmap.image2;
        images[2] = R.mipmap.image3;
       /* images[3] = R.drawable.image4;
        images[4] = R.drawable.image5;
        images[5] = R.drawable.image6;
        images[6] = R.drawable.image7;
        images[7] = R.drawable.image8;
        images[8] = R.drawable.image9;
        images[9] = R.drawable.image10;
        images[10] = R.drawable.image11;*/


    }

    public void initializeGallery() {
        int count = 25;
        int imageId = 0;
            for(int i = 0; i < 25; i++) {
                int num = random.nextInt(3);
                myGallery.add(new EventImage(images[num]));
            }
    }

    public ArrayList<EventImage> getPhotoList() {
        return myGallery;
    }



}
