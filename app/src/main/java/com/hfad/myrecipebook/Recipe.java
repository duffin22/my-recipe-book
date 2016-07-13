package com.hfad.myrecipebook;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by matthewtduffin on 11/07/16.
 */
public class Recipe {
    Integer picture;
    String title, category;
    int rating;
    ArrayList<String> ingredients;
    Uri uri;

    public Recipe(String title, Integer picture, int rating, String category, ArrayList<String> ingredients) {
        this.title=title;
        this.picture=picture;
        this.category=category;
        this.rating=rating;
        this.ingredients=ingredients;
        this.uri=Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/DCIM/",title + ".png"));
    }

    public Recipe(String title, int rating, String category, ArrayList<String> ingredients) {
        this.title=title;
        this.category=category;
        this.rating=rating;
        this.ingredients=ingredients;
        int rando=(int)Math.random()*1000000;
        this.uri=Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/DCIM/",title +rando+".png"));

    }




    public Uri getUri() {
        return this.uri;
    }

}
