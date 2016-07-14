package com.hfad.myrecipebook;

import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
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

public class EditItemActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1886, RESULT_DELETE=16;
    ImageView imageAdd;
    ArrayList<String> ingredients,savedIngredients;
    ImageView addIngredient, tickIcon, homeIcon, saveIcon, deleteIcon;
    LinearLayout addEdit, ratingBar;
    EditText addEditText, titleEdit;
    Recipe recipe;
    ImageView star1,star2,star3,star4,star5;
    Button confirm;
    int lastStarClicked,index;
    Spinner category;
    static String fileDirectory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        fileDirectory=Environment.getExternalStorageDirectory().getAbsolutePath(); //  getApplicationContext().getFilesDir().getAbsolutePath();
        Log.i("TAG","FILE DIRECTORY IS "+fileDirectory);

        ///****get input recipe based on the extra that was put in
        recipe = getIntent().getParcelableExtra("recipe");
        index=getIntent().getIntExtra("index",-1);

        //**********************************************************************************
        //****** INITIALIZE ALL ELEMENTS OF LAYOUT   ***************************************
        ///set ingredients in display
        ingredients=recipe.ingredients;
        ListView ingredientList = (ListView) findViewById(R.id.ingredientList);
        final IngredientListAdapter adapty=new IngredientListAdapter(this,ingredients);
        ingredientList.setAdapter(adapty);
        final ViewGroup.LayoutParams params = ingredientList.getLayoutParams();
        params.height = (101*ingredients.size());

        savedIngredients=new ArrayList<>();
        if (ingredients.size()>0) {
            for (int i=0;i<ingredients.size();i++) {
                savedIngredients.add(ingredients.get(i));
            }
        }


        ///set image in display
        imageAdd=(ImageView) findViewById(R.id.imageAdd);
        saveFileToImageView(recipe.getUri().getPath());


        ///set title in display
        titleEdit=(EditText) findViewById(R.id.titleEdit);
        Log.d("TITLE","********"+recipe.title);
        titleEdit.setText(recipe.title);

        ///set rating in display
        ratingBar=(LinearLayout) findViewById(R.id.rating);
        setRatingBarClickListener();
        initializeRatingBar(recipe.rating);


        ///set spinner to correct category
        category= (Spinner) findViewById(R.id.categories);
        category.setSelection(getCategoryIndex(recipe.category));
        //**********************************************************************************

        //get access to keyboard
        final InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);



        addEdit = (LinearLayout) findViewById(R.id.addEditBar);
        addEditText=(EditText) findViewById(R.id.addEditText);

//        final ImageView cameraButton= (ImageView) findViewById(R.id.cameraButton);
//
//        cameraButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("Pointer","Entered onClick creator for camera button");
//                openCameraActivity(recipe);
//            }
//        });

        addIngredient=(ImageView) findViewById(R.id.addIngredientButton);

        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEdit.setVisibility(View.VISIBLE);

                tickIcon = (ImageView) findViewById(R.id.tickIcon);

                tickIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String s=addEditText.getText().toString();
                        addEdit.setVisibility(View.GONE);

                        if (s.length()>0) {
                            ingredients.add(s);
                            adapty.notifyDataSetChanged();
                            params.height = (101 * ingredients.size());
                            addEditText.setText("");
                        } else {
                            Toast.makeText(EditItemActivity.this, "Ingredients cannot have zero length",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });




            }



        });


        homeIcon=(ImageView) findViewById(R.id.homeButton);

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog diaBox = AskOptionHome();
                diaBox.show();

            }
        });

        saveIcon=(ImageView) findViewById(R.id.saveButton);

        saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSave();
                Toast.makeText(EditItemActivity.this, "Recipe updated. Press the home button to return to your home page.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        deleteIcon=(ImageView) findViewById(R.id.deleteButton);

        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog diaBox = AskOptionDelete();
                diaBox.show();

            }
        });



    }

    public void onSave() {
        String selectedValue=String.valueOf(category.getSelectedItem());
        recipe.category=selectedValue;
        recipe.title=titleEdit.getText().toString();
        recipe.rating=lastStarClicked;
        savedIngredients=recipe.ingredients;

    }

    private AlertDialog AskOptionHome() {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Exit Recipe Page")
                .setMessage("Any unsaved changes will be lost. Would you like to continue?")
                .setIcon(R.drawable.ic_home)

                .setPositiveButton("No", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        dialog.dismiss();
                    }

                })

                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        recipe.ingredients=savedIngredients;
                        Intent intent = new Intent();
                        intent.putExtra("recipe",recipe);
                        setResult(RESULT_OK, intent);
                        finish();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }

    private AlertDialog AskOptionDelete() {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete this recipe? This action cannot be undone.")
                .setIcon(R.drawable.ic_delete)

                .setPositiveButton("Canel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }

                })

                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        Intent intent = new Intent();
                        intent.putExtra("index",index);
                        setResult(RESULT_DELETE, intent);
                        finish();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }

    @Override
    public void onBackPressed() {
        AlertDialog diaBox = AskOptionHome();
        diaBox.show();
    }

    public int getCategoryIndex(String s) {
        if (s.equals("Breakfast")) {
            return 1;
        } else if (s.equals("Lunch")) {
            return 2;
        } else if (s.equals("Dinner")) {
            return 3;
        } else if (s.equals("Snacks")) {
            return 4;
        } else if (s.equals("Other")) {
            return 5;
        } else {
            return 0;
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

    public void initializeRatingBar (int i) {
        switch (i) {
            case 0:
                star1.setImageResource(R.drawable.star_empty);
                star2.setImageResource(R.drawable.star_empty);
                star3.setImageResource(R.drawable.star_empty);
                star4.setImageResource(R.drawable.star_empty);
                star5.setImageResource(R.drawable.star_empty);
                break;
            case 1:
                star1.setImageResource(R.drawable.star_full);
                star2.setImageResource(R.drawable.star_empty);
                star3.setImageResource(R.drawable.star_empty);
                star4.setImageResource(R.drawable.star_empty);
                star5.setImageResource(R.drawable.star_empty);
                break;
            case 2:
                star1.setImageResource(R.drawable.star_full);
                star2.setImageResource(R.drawable.star_full);
                star3.setImageResource(R.drawable.star_empty);
                star4.setImageResource(R.drawable.star_empty);
                star5.setImageResource(R.drawable.star_empty);
                break;
            case 3:
                star1.setImageResource(R.drawable.star_full);
                star2.setImageResource(R.drawable.star_full);
                star3.setImageResource(R.drawable.star_full);
                star4.setImageResource(R.drawable.star_empty);
                star5.setImageResource(R.drawable.star_empty);
                break;
            case 4:
                star1.setImageResource(R.drawable.star_full);
                star2.setImageResource(R.drawable.star_full);
                star3.setImageResource(R.drawable.star_full);
                star4.setImageResource(R.drawable.star_full);
                star5.setImageResource(R.drawable.star_empty);
                break;
            case 5:
                star1.setImageResource(R.drawable.star_full);
                star2.setImageResource(R.drawable.star_full);
                star3.setImageResource(R.drawable.star_full);
                star4.setImageResource(R.drawable.star_full);
                star5.setImageResource(R.drawable.star_full);

        }
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
                Toast.makeText(EditItemActivity.this, "Star 1 has been pressed",
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
                Toast.makeText(EditItemActivity.this, "Star 2 has been pressed",
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
                Toast.makeText(EditItemActivity.this, "Star 3 has been pressed",
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
                Toast.makeText(EditItemActivity.this, "Star 4 has been pressed",
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
                Toast.makeText(EditItemActivity.this, "Star 5 has been pressed",
                        Toast.LENGTH_SHORT).show();
            }
        });



    }



}
