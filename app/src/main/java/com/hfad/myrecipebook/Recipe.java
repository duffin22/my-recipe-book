package com.hfad.myrecipebook;

import android.net.Uri;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by matthewtduffin on 11/07/16.
 */
public class Recipe implements Parcelable {
    String title, category;
    int rating;
    ArrayList<String> ingredients;
    Uri uri;
    int rando;

//recipe constructor
    public Recipe(String title, int rating, String category, ArrayList<String> ingredients) {
        this.title=title;
        this.category=category;
        this.rating=rating;
        this.ingredients=ingredients;
        this.rando= (int) (Math.random()*1000000000);
        this.uri=Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/DCIM/",title +this.rando+".png"));
    }
//recipe constructor using parcelable
    public Recipe(Parcel in) {
        this.title = in.readString();
        this.category = in.readString();
        this.rating = in.readInt();
        this.ingredients= (ArrayList<String>) in.readSerializable();
        this.uri =Uri.parse(in.readString());
        this.rando=in.readInt();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int i) {
            return new Recipe[i];
        }
    };

    public Recipe[] newArray(int size) {
        return new Recipe[size];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(category);
        dest.writeInt(rating);
        dest.writeSerializable(ingredients);
        dest.writeString(uri.getPath());
        dest.writeInt(rando);
    }



    public Uri getUri() {
        return this.uri;
    }

}
