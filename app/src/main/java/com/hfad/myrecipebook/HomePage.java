package com.hfad.myrecipebook;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Parcelable;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    private static final int ADD_REQUEST = 1922, EDIT_REQUEST=2044, RESULT_DELETE=16;
    public boolean isBigButton;
    ImageAdapter adapty;
    Recipe lastClickedRecipe;
    ArrayList<Recipe> recipes, saladRecipe, beefRecipe, tomatoesRecipe;
    ArrayList<String> ingredients, saladIngredients, beefIngredients, tomatoesIngredients;
    File saveFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        verifyStoragePermissions(this);

        ingredients=new ArrayList<String>();
        recipes=new ArrayList<Recipe>();

        ingredients.add("stuff");
        ingredients.add("other stuff");
        ingredients.add("gravy");
        ingredients.add("100ml root vegetables");
        ingredients.add("pineapple slaw");
        ingredients.add("celeriac");
        ingredients.add("cilantro");
        ingredients.add("bacon");
        ingredients.add("anchovies");

        saladIngredients = new ArrayList<>();
        beefIngredients = new ArrayList<>();
        tomatoesIngredients = new ArrayList<>();

        saladIngredients.add("25g Chopped Walnuts");
        saladIngredients.add("300g Washed Salad Leaves");
        saladIngredients.add("Pinch of Salt");
        saladIngredients.add("15ml Olive Oil");
        saladIngredients.add("100g Thinly Chopped Onion");

        beefIngredients.add("300g SLice Rump Steak");
        beefIngredients.add("25ml Cooking Oil");
        beefIngredients.add("1 Large Onion, Sliced");
        beefIngredients.add("2 Cloves Fresh Garlic");
        beefIngredients.add("25g Fresh Root Ginger");
        beefIngredients.add("1 Red Pepper");
        beefIngredients.add("2tbsp Salted Black Beans");
        beefIngredients.add("2 Large Red Chillies");
        beefIngredients.add("3 Stalks Green Spring Onion");
        beefIngredients.add("1/2tbsp Chinese Rice Wine");
        beefIngredients.add("1tbsp Caster Sugar");
        beefIngredients.add("1tsp Dark Soy Sauce");



        tomatoesIngredients.add("300g Washed Mixed Leaves");
        tomatoesIngredients.add("1/2 Red Bell Pepper, Thinly Sliced");
        tomatoesIngredients.add("100g Surimi");







        try {
            recipes.add(new Recipe("Beef with Black Bean Sauce", 4, "Lunch", beefIngredients, 532742568));
            recipes.add(new Recipe("Crushed Walnut Salad", 5, "Lunch", saladIngredients, 206700415));
            recipes.add(new Recipe("Surimi & Bell Pepper Salad", 2, "Lunch", tomatoesIngredients, 475095041));
        } catch (Exception e) {

        }

        GridView gridview = (GridView) findViewById(R.id.gridview);
        adapty=new ImageAdapter(this,recipes);


        if (gridview != null) {
            gridview.setAdapter(adapty);
        }

        final ImageView bigButton,addButton;
        bigButton=(ImageView) findViewById(R.id.bigButton);
        isBigButton=false;
        addButton=(ImageView) findViewById(R.id.addButton);

        final InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        final Context context;


//        bigButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (isBigButton) {
//                    isBigButton=false;
//                    bigButton.setAlpha(1.0f);
//                    addButton.setVisibility(View.INVISIBLE);
//                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
//                            InputMethodManager.HIDE_NOT_ALWAYS);
//
//                } else if (!isBigButton) {
//                    bigButton.setAlpha(0.3f);
//                    addButton.setVisibility(View.VISIBLE);
//                    isBigButton=true;
//
//                    addButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            openAddItemActivity(view);
//                        }
//                    });
//
//                } else {
//                    bigButton.setAlpha(1.0f);
//                    addButton.setVisibility(View.INVISIBLE);
//                    isBigButton=false;
//                }
//            }
//
//        });

        addButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openAddItemActivity(view);
                        }
                    });


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                try {
                    lastClickedRecipe = recipes.get(position);
                    openEditItemActivity(v, lastClickedRecipe, position);
                } catch (Exception e) {
                    Toast.makeText(HomePage.this, "Grid item is not clickable yet",
                            Toast.LENGTH_SHORT).show();
                }
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

    public void openEditItemActivity(View view, Recipe r, int position) {
        Intent intent = new Intent(this.getApplicationContext(), EditItemActivity.class);
        intent.putExtra("recipe",r);
        intent.putExtra("index",position);

        Recipe r2 = intent.getExtras().getParcelable("recipe");
        Log.d("parce", r2.title);

        this.startActivityForResult(intent,EDIT_REQUEST);
    }

    public void openAddItemActivity(View view) {
        Intent intent = new Intent(this.getApplicationContext(), AddItem2.class);
        //this.startActivity(intent);
        this.startActivityForResult(intent,ADD_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_REQUEST) {
            if (resultCode == RESULT_OK) {

                Recipe r = data.getExtras().getParcelable("recipe");
                addRecipeToList(r);
            } else {
            }
        } else if (requestCode == EDIT_REQUEST) {
            if (resultCode == RESULT_OK) {

                Recipe r = data.getExtras().getParcelable("recipe");
                replaceRecipeInList(r, recipes.indexOf(lastClickedRecipe));
            } else if (resultCode==RESULT_DELETE) {
                int i=data.getIntExtra("index",-1);
                if (i>=0) {
                    recipes.remove(i);
                    adapty.notifyDataSetChanged();
                }
            }
        }
    }

    public void addRecipeToList(Recipe r) {
        recipes.add(r);
        adapty.notifyDataSetChanged();
    }

    public void replaceRecipeInList(Recipe r, int i) {
        recipes.set(i,r);
        adapty.notifyDataSetChanged();
    }


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

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
