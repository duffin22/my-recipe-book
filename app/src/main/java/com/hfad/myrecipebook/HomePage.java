package com.hfad.myrecipebook;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    private static final int ADD_REQUEST = 1922;
    public boolean isBigButton, isSearchButton;
    ImageAdapter adapty;
    ArrayList<Recipe> recipes;
    ArrayList<String> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        verifyStoragePermissions(this);

        ingredients=new ArrayList<String>();
        recipes=new ArrayList<Recipe>();

        ingredients.add("stuff");

        recipes.add(new Recipe("To get started add your first recipe!",0,"cat",ingredients));

        GridView gridview = (GridView) findViewById(R.id.gridview);
        adapty=new ImageAdapter(this,recipes);


        if (gridview != null) {
            gridview.setAdapter(adapty);
        }

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
        //this.startActivity(intent);
        this.startActivityForResult(intent,ADD_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_REQUEST) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(HomePage.this, "Item successfully passed back to collection",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(HomePage.this, "Item not added to your collection",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }




    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
