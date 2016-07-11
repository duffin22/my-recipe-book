package com.hfad.myrecipebook;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

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
        Log.d("Position: " , "" + position);

        FrameLayout frameLayout;
        ImageView imageView;
        TextView textView;

        View v =child;

        if (v == null) {
            v = inflater.inflate(R.layout.grid_item, parent, false);
        }

        frameLayout = (FrameLayout) v.findViewById(R.id.frame);

        imageView= (ImageView) v.findViewById(R.id.image);
        textView= (TextView) v.findViewById(R.id.name);

        imageView.setImageResource(mThumbIds[position][0]);
        imageView.setAlpha(0.7f);
        textView.setText(mThumbIds[position][1]);

//        if ((position)%2 ==1) {
//            textView.setTextColor(mContext.getResources().getColor(R.color.white));
//            textView.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDarkSoft));
//            Log.d("GridView item number: ",""+position);
//        }

        return frameLayout;
    }

    // references to images
    private Integer[][] mThumbIds = {
            {R.drawable.food_tester,R.string.fruit_and_veg},
            {R.drawable.burger,R.string.burger},
            {R.drawable.fruit,R.string.fruit},
            {R.drawable.meatballs,R.string.meatballs},
            {R.drawable.donuts,R.string.donuts},
            {R.drawable.food_tester,R.string.fruit_and_veg},
            {R.drawable.burger,R.string.burger},
            {R.drawable.fruit,R.string.fruit},
            {R.drawable.meatballs,R.string.meatballs},
            {R.drawable.donuts,R.string.donuts},
            {R.drawable.food_tester,R.string.fruit_and_veg},
            {R.drawable.burger,R.string.burger},
            {R.drawable.fruit,R.string.fruit},
            {R.drawable.meatballs,R.string.meatballs},
            {R.drawable.donuts,R.string.donuts},
            {R.drawable.food_tester,R.string.fruit_and_veg},
            {R.drawable.burger,R.string.burger},
            {R.drawable.fruit,R.string.fruit},
            {R.drawable.meatballs,R.string.meatballs},
            {R.drawable.donuts,R.string.donuts},
    };

}
