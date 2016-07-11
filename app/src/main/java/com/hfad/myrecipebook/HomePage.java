package com.hfad.myrecipebook;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    public boolean isBigButton, isSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        final ImageView bigButton,addButton, searchButton;
        bigButton=(ImageView) findViewById(R.id.bigButton);
        isBigButton=false;
        addButton=(ImageView) findViewById(R.id.addButton);
        searchButton=(ImageView) findViewById(R.id.searchButton);

        final LinearLayout searchBar= (LinearLayout) findViewById(R.id.searchBar);
        final EditText searchText= (EditText) findViewById(R.id.searchText);
        final ImageView searchIcon=(ImageView) findViewById(R.id.searchIcon);
        isSearchButton=false;

        final InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        final Context context;


        bigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isBigButton && isSearchButton) {
                    searchBar.setVisibility(View.INVISIBLE);
                    isSearchButton = false;
                    isBigButton=false;
                    bigButton.setAlpha(1.0f);
                    addButton.setVisibility(View.INVISIBLE);
                    searchButton.setVisibility(View.INVISIBLE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                } else if (!isBigButton) {
                    bigButton.setAlpha(0.3f);
                    addButton.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.VISIBLE);
                    isBigButton=true;

                    searchButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!isSearchButton && isBigButton) {
                                searchBar.setVisibility(View.VISIBLE);
                                isSearchButton = true;
                                searchIcon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        searchBar.setVisibility(View.INVISIBLE);
                                        isSearchButton = false;
                                        isBigButton=false;
                                        bigButton.setAlpha(1.0f);
                                        addButton.setVisibility(View.INVISIBLE);
                                        searchButton.setVisibility(View.INVISIBLE);
                                        searchText.setText("");
                                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                                InputMethodManager.HIDE_NOT_ALWAYS);
                                    }
                                });

                            } else if (isSearchButton && isBigButton) {
                                searchBar.setVisibility(View.INVISIBLE);
                                isSearchButton = false;
                                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                            }
                        }
                    });

                    addButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openAddItemActivity(view);
                        }



                    });

                } else {
                    bigButton.setAlpha(1.0f);
                    addButton.setVisibility(View.INVISIBLE);
                    searchButton.setVisibility(View.INVISIBLE);
                    isBigButton=false;
                }
            }


        });


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(HomePage.this, "Grid item " + position,
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
    }

    public void openAddItemActivity(View view) {
        Intent intent = new Intent(this.getApplicationContext(), AddItemActivity.class);
        this.startActivity(intent);
    }
}
