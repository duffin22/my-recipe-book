package com.hfad.myrecipebook;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by matthewtduffin on 10/07/16.
 */
public class IngredientListAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private final ArrayList<String> listItems;
    private final Context context;

    public IngredientListAdapter(Context context, ArrayList<String> listItems) {
        inflater = LayoutInflater.from(context);
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public int getCount() {

        Log.d("Pos","Entered getCount method");
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View child, ViewGroup parent) {

        Log.d("Postion: ", "" + position);

        View v = child;
        ViewGroup vG=parent;
        TextView words;

        if (v == null) {
            v = inflater.inflate(R.layout.ingredient_list_item, parent, false);
        }

        words = (TextView) v.findViewById(R.id.ingredientItem);

        words.setText(String.valueOf(listItems.get(position)));

        Log.d("Pointer","getView used at position " +position);

        if (position==(listItems.size()-1)) {
            vG.setMinimumHeight(23*position);
        }

        return v;
    }
}

