package com.hfad.myrecipebook;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by matthewtduffin on 09/07/16.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private final LayoutInflater inflater;
    ArrayList<Recipe> recipe;

    public ImageAdapter(Context c, ArrayList<Recipe> recipes) {
        mContext = c;
        inflater = LayoutInflater.from(c);
        this.recipe=recipes;
    }

    public int getCount() {
        if (!this.recipe.isEmpty())
            return recipe.size();
        else {
            return 0;
        }
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View child, ViewGroup parent) {
        Log.d("Position: " , "" + position);

        FrameLayout frameLayout;
        ImageView imageView, currentStar;
        TextView textView;
        LinearLayout ratings;

        View v =child;

        if (v == null) {
            v = inflater.inflate(R.layout.grid_item, parent, false);
        }

        frameLayout = (FrameLayout) v.findViewById(R.id.frame);

        imageView= (ImageView) v.findViewById(R.id.image);
        textView= (TextView) v.findViewById(R.id.name);


        try {
            imageView.setImageURI(recipe.get(position).getUri());
        } catch (Exception e) {
        }



        imageView.setAlpha(0.7f);
        textView.setText(recipe.get(position).title);

        ratings=(LinearLayout) v.findViewById(R.id.rating);
        for (int i=0;i<5;i++) {
            currentStar=(ImageView) ratings.getChildAt(i);
            if (recipe.get(position).rating > i) {
                currentStar.setImageResource(R.drawable.star_full);
            } else {
                currentStar.setImageResource(R.drawable.star_empty);
            }
        }


        for (int i=1; i<=5; i++) {
            if (i<=recipe.get(position).rating) {

            }
        }

        return frameLayout;
    }






}
