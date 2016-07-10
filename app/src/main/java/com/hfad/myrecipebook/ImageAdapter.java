package com.hfad.myrecipebook;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by matthewtduffin on 09/07/16.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private final LayoutInflater inflater;

    public ImageAdapter(Context c) {
        mContext = c;
        inflater = LayoutInflater.from(c);
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View child, ViewGroup parent) {
        Log.d("Postion: " , "" + position);

        ImageView imageView;

        View v =child;

        if (v == null) {
            v = inflater.inflate(R.layout.grid_item, parent, false);
        }

        imageView= (ImageView) v.findViewById(R.id.image);

        imageView.setImageResource(mThumbIds[position]);
        imageView.setAlpha(0.5f);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.food_tester, R.drawable.burger,
            R.drawable.donuts, R.drawable.fruit,
            R.drawable.meatballs, R.drawable.burger,
            R.drawable.fruit, R.drawable.food_tester,
            R.drawable.donuts, R.drawable.meatballs,R.drawable.food_tester, R.drawable.burger,
            R.drawable.donuts, R.drawable.fruit,
            R.drawable.meatballs, R.drawable.burger,
            R.drawable.fruit, R.drawable.food_tester,
            R.drawable.donuts, R.drawable.meatballs,R.drawable.food_tester, R.drawable.burger,
            R.drawable.donuts, R.drawable.fruit,
            R.drawable.meatballs, R.drawable.burger,
            R.drawable.fruit, R.drawable.food_tester,
            R.drawable.donuts, R.drawable.meatballs,R.drawable.food_tester, R.drawable.burger,
            R.drawable.donuts, R.drawable.fruit,
            R.drawable.meatballs, R.drawable.burger,
            R.drawable.fruit, R.drawable.food_tester,
            R.drawable.donuts, R.drawable.meatballs,R.drawable.food_tester,
            R.drawable.donuts, R.drawable.meatballs};
}
