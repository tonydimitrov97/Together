package com.example.together.viewmodel;

import android.arch.lifecycle.ViewModel;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PhotoScreenVm extends ViewModel {

    private int numLikes;
    private String caption;
    private int photoId;

    public PhotoScreenVm(String intentData) {
        JsonElement jelement = new JsonParser().parse(intentData);
        JsonObject jobject = jelement.getAsJsonObject();
        this.photoId = jobject.get("imageId").getAsInt();
        this.numLikes = jobject.get("numLikes").getAsInt();
       // this.caption = jobject.get("caption").toString();
    }

   public int getLikes() {
       return this.numLikes;
   }

   public int getPhotoId() {
        return this.photoId;
   }

   public String getCaption() {
        return this.caption;
   }

}
