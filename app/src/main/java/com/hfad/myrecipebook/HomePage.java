package com.hfad.myrecipebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(HomePage.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

        ArrayList<String> items=new ArrayList<>();
        items.add("All Categories");
        items.add("Breakfast");
        items.add("Lunch");
        items.add("Dinner");
        items.add("Lite Bites");
        items.add("Snacks");
        items.add("Other");

        ListView listview = (ListView) findViewById(R.id.horizonList);
        listview.setAdapter(new HorizontalListAdapter(this,items));
    }
}
