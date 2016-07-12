package com.hfad.myrecipebook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddItemActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    ImageView imageAdd;
    Uri uri;
    ArrayList<String> ingredients;
    ImageView addIngredient, tickIcon;
    LinearLayout addEdit;
    EditText addEditText;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        ingredients=new ArrayList<>();
        recipe=new Recipe("Default-title",R.drawable.burger,0,"Default-category",ingredients);

        final InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        addIngredient=(ImageView) findViewById(R.id.addIngredientButton);

        addEdit = (LinearLayout) findViewById(R.id.addEditBar);
        addEditText=(EditText) findViewById(R.id.addEditText);

        ingredients.add("A spoonful of sugar");
        ingredients.add("1000ml of gravy");
        ingredients.add("A partridge in a pear tree");

        ListView ingredientList = (ListView) findViewById(R.id.ingredientList);

        final IngredientListAdapter adapty=new IngredientListAdapter(this,ingredients);

        ingredientList.setAdapter(adapty);
        final ViewGroup.LayoutParams params = ingredientList.getLayoutParams();
        params.height = (101*ingredients.size());

        imageAdd=(ImageView) findViewById(R.id.imageAdd);

        Log.d("Pointer","Entered onCreate method");

        final ImageView cameraButton= (ImageView) findViewById(R.id.cameraButton);


        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Pointer","Entered onClick creator for camera button");
                openCameraActivity(view);
            }
        });

        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEdit.setVisibility(View.VISIBLE);
                tickIcon = (ImageView) findViewById(R.id.tickIcon);
                addEditText.requestFocus();


                tickIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String s=addEditText.getText().toString();
                        addEdit.setVisibility(View.GONE);

                        if (s.length()>0) {
                            ingredients.add(s);
                            adapty.notifyDataSetChanged();
                            params.height = (101 * ingredients.size());
                            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            addEditText.setText("");
                        } else {
                            Toast.makeText(AddItemActivity.this, "Ingredients cannot have zero length",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });

            }



        });
    }



    public void openCameraActivity(View view) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
        Log.d("Pointer","Entered Camera Activity *******");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageAdd.setImageBitmap(photo);
        }
    }
}
