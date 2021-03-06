package com.hfad.myrecipebook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
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
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class AddItemActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    ImageView imageAdd;
    ArrayList<String> ingredients;
    ImageView addIngredient, tickIcon;
    LinearLayout addEdit, ratingBar;
    EditText addEditText, titleEdit;
    Recipe recipe;
    ImageView star1,star2,star3,star4,star5;
    Button confirm;
    int lastStarClicked;
    Spinner category;
    static String fileDirectory;
    boolean hasPhotoBeenTaken=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        fileDirectory=Environment.getExternalStorageDirectory().getAbsolutePath(); //  getApplicationContext().getFilesDir().getAbsolutePath();
        Log.i("TAG","FILE DIRECTORY IS "+fileDirectory);

        ingredients=new ArrayList<>();
        recipe=new Recipe("Default-title",0,"Default-category",ingredients);

        final InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        addIngredient=(ImageView) findViewById(R.id.addIngredientButton);

        addEdit = (LinearLayout) findViewById(R.id.addEditBar);
        addEditText=(EditText) findViewById(R.id.addEditText);

        ListView ingredientList = (ListView) findViewById(R.id.ingredientList);

        final IngredientListAdapter adapty=new IngredientListAdapter(this,ingredients);

        ingredientList.setAdapter(adapty);
        final ViewGroup.LayoutParams params = ingredientList.getLayoutParams();
        params.height = (101*ingredients.size());

        imageAdd=(ImageView) findViewById(R.id.imageAdd);

        Log.d("Pointer","Entered onCreate method");

        final ImageView cameraButton= (ImageView) findViewById(R.id.cameraButton);

        ratingBar=(LinearLayout) findViewById(R.id.rating);
        setRatingBarClickListener();


        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Pointer","Entered onClick creator for camera button");
                openCameraActivity();
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

        confirm=(Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //get a reference to the spinner
                category = (Spinner) findViewById(R.id.categories);
                String selectedValue=String.valueOf(category.getSelectedItem());

                ////add final values to the recipe item
                recipe.category=selectedValue;
                titleEdit=(EditText) findViewById(R.id.titleEdit);
                recipe.title=titleEdit.getText().toString();

                recipe.rating=lastStarClicked;

                Intent intent = new Intent();
                intent.putExtra("recipe",recipe);
                setResult(RESULT_OK, intent);
                finish();

            }
        });


    }

    public void openCameraActivity() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, recipe.getUri());
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
        Log.d("Pointer","Entered Camera Activity *******");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            if (data==null) {
                Log.i("LOG","returned null data ******************");
                saveFileToImageView(recipe.getUri().getPath());
                hasPhotoBeenTaken=true;
            }
        }

    }

    public void saveFileToImageView(String filePath) {
        File imgFile = new  File(filePath);

        if(imgFile.exists()){

            Bitmap myBitmap = rotateImage(BitmapFactory.decodeFile(imgFile.getAbsolutePath()),90);

            imageAdd.setImageBitmap(myBitmap);

        } else {
            Log.i("STUFF","Image doesn't exist");
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return retVal;
    }

    public void  setRatingBarClickListener () {
        star1=(ImageView) findViewById(R.id.star1) ;
        star2=(ImageView) findViewById(R.id.star2) ;
        star3=(ImageView) findViewById(R.id.star3) ;
        star4=(ImageView) findViewById(R.id.star4) ;
        star5=(ImageView) findViewById(R.id.star5) ;


        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastStarClicked!=1) {
                    star1.setImageResource(R.drawable.star_full);
                    star2.setImageResource(R.drawable.star_empty);
                    star3.setImageResource(R.drawable.star_empty);
                    star4.setImageResource(R.drawable.star_empty);
                    star5.setImageResource(R.drawable.star_empty);
                    lastStarClicked = 1;
                } else {
                    star1.setImageResource(R.drawable.star_empty);
                    star2.setImageResource(R.drawable.star_empty);
                    star3.setImageResource(R.drawable.star_empty);
                    star4.setImageResource(R.drawable.star_empty);
                    star5.setImageResource(R.drawable.star_empty);
                    lastStarClicked=0;
                }
                Toast.makeText(AddItemActivity.this, "Star 1 has been pressed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastStarClicked!=2) {
                    star1.setImageResource(R.drawable.star_full);
                    star2.setImageResource(R.drawable.star_full);
                    star3.setImageResource(R.drawable.star_empty);
                    star4.setImageResource(R.drawable.star_empty);
                    star5.setImageResource(R.drawable.star_empty);
                    lastStarClicked = 2;
                } else {
                    star1.setImageResource(R.drawable.star_empty);
                    star2.setImageResource(R.drawable.star_empty);
                    star3.setImageResource(R.drawable.star_empty);
                    star4.setImageResource(R.drawable.star_empty);
                    star5.setImageResource(R.drawable.star_empty);
                    lastStarClicked=0;
                }
                Toast.makeText(AddItemActivity.this, "Star 2 has been pressed",
                        Toast.LENGTH_SHORT).show();
            }
        });

        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastStarClicked!=3) {
                    star1.setImageResource(R.drawable.star_full);
                    star2.setImageResource(R.drawable.star_full);
                    star3.setImageResource(R.drawable.star_full);
                    star4.setImageResource(R.drawable.star_empty);
                    star5.setImageResource(R.drawable.star_empty);
                    lastStarClicked = 3;
                } else {
                    star1.setImageResource(R.drawable.star_empty);
                    star2.setImageResource(R.drawable.star_empty);
                    star3.setImageResource(R.drawable.star_empty);
                    star4.setImageResource(R.drawable.star_empty);
                    star5.setImageResource(R.drawable.star_empty);
                    lastStarClicked=0;
                }
                Toast.makeText(AddItemActivity.this, "Star 3 has been pressed",
                        Toast.LENGTH_SHORT).show();
            }
        });

        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastStarClicked!=4) {
                    star1.setImageResource(R.drawable.star_full);
                    star2.setImageResource(R.drawable.star_full);
                    star3.setImageResource(R.drawable.star_full);
                    star4.setImageResource(R.drawable.star_full);
                    star5.setImageResource(R.drawable.star_empty);
                    lastStarClicked = 4;
                } else {
                    star1.setImageResource(R.drawable.star_empty);
                    star2.setImageResource(R.drawable.star_empty);
                    star3.setImageResource(R.drawable.star_empty);
                    star4.setImageResource(R.drawable.star_empty);
                    star5.setImageResource(R.drawable.star_empty);
                    lastStarClicked=0;
                }
                Toast.makeText(AddItemActivity.this, "Star 4 has been pressed",
                        Toast.LENGTH_SHORT).show();
            }
        });

        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastStarClicked!=5) {
                    star1.setImageResource(R.drawable.star_full);
                    star2.setImageResource(R.drawable.star_full);
                    star3.setImageResource(R.drawable.star_full);
                    star4.setImageResource(R.drawable.star_full);
                    star5.setImageResource(R.drawable.star_full);
                    lastStarClicked = 5;
                } else {
                    star1.setImageResource(R.drawable.star_empty);
                    star2.setImageResource(R.drawable.star_empty);
                    star3.setImageResource(R.drawable.star_empty);
                    star4.setImageResource(R.drawable.star_empty);
                    star5.setImageResource(R.drawable.star_empty);
                    lastStarClicked=0;
                }
                Toast.makeText(AddItemActivity.this, "Star 5 has been pressed",
                        Toast.LENGTH_SHORT).show();
            }
        });



    }



}
