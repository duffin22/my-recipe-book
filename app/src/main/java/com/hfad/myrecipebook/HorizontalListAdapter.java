package com.hfad.myrecipebook;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by matthewtduffin on 10/07/16.
 */
public class HorizontalListAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private final ArrayList<String> listItems;
    private final Context context;

    public HorizontalListAdapter(Context context, ArrayList<String> listItems) {
        inflater = LayoutInflater.from(context);
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public int getCount() {
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
        TextView words;

        if (v == null) {
            v = inflater.inflate(R.layout.horizontal_list_item, parent, false);
        }

        words = (TextView) v.findViewById(R.id.horizontalItem);


        words.setText(String.valueOf(listItems.get(position)));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("Adaptor", "Clicked " + position);
                Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
            }

//                Intent intent = new Intent(context.getApplicationContext(), InputActivity.class);
//
//                context.startActivity(intent);

        });

        return v;
    }
}

