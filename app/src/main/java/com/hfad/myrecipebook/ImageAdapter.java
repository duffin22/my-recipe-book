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
    Recipe[] recipes;

    public ImageAdapter(Context c) {
        mContext = c;
        inflater = LayoutInflater.from(c);
    }

    public int getCount() {

        ArrayList<String> ingredients=new ArrayList<>();
        ingredients.add("2 tsp of plain flour");

        recipes=new Recipe[15];
        recipes[0]=new Recipe("Burger",R.drawable.burger,5,"Lunch",ingredients);
        recipes[1]=new Recipe("Meatballs",R.drawable.meatballs,3,"Lunch",ingredients);
        recipes[2]=new Recipe("Donuts",R.drawable.donuts,0,"Lunch",ingredients);
        recipes[3]=new Recipe("Fruit",R.drawable.fruit,2,"Lunch",ingredients);
        recipes[4]=new Recipe("Vegetables",R.drawable.food_tester,4,"Lunch",ingredients);
        recipes[5]=new Recipe("Burger",R.drawable.burger,2,"Lunch",ingredients);
        recipes[6]=new Recipe("Meatballs",R.drawable.meatballs,3,"Lunch",ingredients);
        recipes[7]=new Recipe("Donuts",R.drawable.donuts,5,"Lunch",ingredients);
        recipes[8]=new Recipe("Fruit",R.drawable.fruit,1,"Lunch",ingredients);
        recipes[9]=new Recipe("Vegetables",R.drawable.food_tester,0,"Lunch",ingredients);
        recipes[10]=new Recipe("Burger",R.drawable.burger,5,"Lunch",ingredients);
        recipes[11]=new Recipe("Meatballs",R.drawable.meatballs,1,"Lunch",ingredients);
        recipes[12]=new Recipe("Donuts",R.drawable.donuts,0,"Lunch",ingredients);
        recipes[13]=new Recipe("Fruit",R.drawable.fruit,1,"Lunch",ingredients);
        recipes[14]=new Recipe("Vegetables",R.drawable.food_tester,2,"Lunch",ingredients);
        return recipes.length;
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

        imageView.setImageResource(recipes[position].picture);
        imageView.setAlpha(0.7f);
        textView.setText(recipes[position].title);

        ratings=(LinearLayout) v.findViewById(R.id.rating);
        for (int i=0;i<5;i++) {
            currentStar=(ImageView) ratings.getChildAt(i);
            if (recipes[position].rating > i) {
                currentStar.setImageResource(R.drawable.star_full);
            } else {
                currentStar.setImageResource(R.drawable.star_empty);
            }
        }


        for (int i=1; i<=5; i++) {
            if (i<=recipes[position].rating) {

            }
        }




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
