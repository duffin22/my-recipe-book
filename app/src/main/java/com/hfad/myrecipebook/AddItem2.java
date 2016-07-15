package com.hfad.myrecipebook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class AddItem2 extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1886, RESULT_DELETE=16;
    ImageView imageAdd;
    ArrayList<String> ingredients,savedIngredients, emptyIngredients;
    ImageView addIngredient, removeIngredient, tickIcon, homeIcon, saveIcon, deleteIcon;
    ImageView cameraButton;
    TextView ingredientsText;
    LinearLayout addEdit, ratingBar, toolbar;
    ListView ingredientList;
    EditText addEditText, titleEdit;
    Recipe recipe;
    ImageView star1,star2,star3,star4,star5;
    Button confirm;
    int lastStarClicked,index, lastClickedIngredientIndex;
    String lastClickedIngredient;
    Spinner category;
    static String fileDirectory;
    FrameLayout itemsFrame, photoFrame;
    IngredientListAdapter adapty;
    ViewGroup.LayoutParams params;
    LinearLayout.LayoutParams titleParams;
    boolean addButtonClicked=false;
    InputMethodManager inputManager;
    boolean isPromptNeeded=false;
    boolean hasPhotoBeenTaken=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item2);
        toolbar=(LinearLayout) findViewById(R.id.toolbar);
        photoFrame=(FrameLayout) findViewById(R.id.photoFrame);

        inputManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


        fileDirectory=Environment.getExternalStorageDirectory().getAbsolutePath(); //  getApplicationContext().getFilesDir().getAbsolutePath();
        Log.i("TAG","FILE DIRECTORY IS "+fileDirectory);

        emptyIngredients=new ArrayList<>();
        emptyIngredients.add("Press '+' to add Ingredients");
        recipe=new Recipe("",0,"Breakfast",emptyIngredients);

        //**********************************************************************************
        //****** INITIALIZE ALL ELEMENTS OF LAYOUT   ***************************************
        ///set ingredients in display
        ingredients=recipe.ingredients;
        ingredientList = (ListView) findViewById(R.id.ingredientList);
        adapty=new IngredientListAdapter(this,ingredients);
        ingredientList.setAdapter(adapty);
        makeIngredientsUnclickable();
        params = ingredientList.getLayoutParams();

        if (ingredients.size()>10) {
            params.height = (101 * ingredients.size());
        } else {
            params.height = (1010);
        }

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
        titleEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i("TAG","Enter pressed");
                    toolbar.requestFocus();
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return true;
            }
        });

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

        //add in camera usage
        cameraButton= (ImageView) findViewById(R.id.addPhotoButton);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Pointer","Entered onClick creator for camera button");
                openCameraActivity();
            }
        });



        addEdit = (LinearLayout) findViewById(R.id.addEditBar);
        addEditText=(EditText) findViewById(R.id.addEditText);

        addEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)||(actionId == EditorInfo.IME_ACTION_NEXT) ) {
                    Log.i("TAG","Enter pressed");
                    String s = addEditText.getText().toString();

                    toolbar.requestFocus();
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    if (s.length() > 0) {
                        if (ingredients.get(0)!="Press '+' to add Ingredients") {
                            ingredients.add(s);
                            adapty.notifyDataSetChanged();
                            if (ingredients.size()>10) {
                                params.height = (101 * ingredients.size());
                            } else {
                                params.height = (1010);
                            }
                            addEditText.setText("");
                        } else {
                            ingredients.set(0,s);
                            adapty.notifyDataSetChanged();
                            if (ingredients.size()>10) {
                                params.height = (101 * ingredients.size());
                            } else {
                                params.height = (1010);
                            }
                            addEditText.setText("");
                        }
                    } else {
                        Toast.makeText(AddItem2.this, "Ingredients cannot have zero length",
                                Toast.LENGTH_SHORT).show();
                    }
                    addEdit.setVisibility(View.GONE);
                }
                return true;
            }
        });

        addIngredient=(ImageView) findViewById(R.id.addIngredientButton);

        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tickIcon = (ImageView) findViewById(R.id.tickIcon);
                if (!addButtonClicked) {
                    addEdit.setVisibility(View.VISIBLE);
                    tickIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            isPromptNeeded=true;
                            String s = addEditText.getText().toString();
                            addEdit.setVisibility(View.GONE);

                            if (s.length() > 0) {
                                if (ingredients.get(0)!="Press '+' to add Ingredients") {
                                    ingredients.add(s);
                                    adapty.notifyDataSetChanged();
                                    if (ingredients.size()>10) {
                                        params.height = (101 * ingredients.size());
                                    } else {
                                        params.height = (1010);
                                    }
                                    addEditText.setText("");
                                } else {
                                    ingredients.set(0,s);
                                    adapty.notifyDataSetChanged();
                                    if (ingredients.size()>10) {
                                        params.height = (101 * ingredients.size());
                                    } else {
                                        params.height = (1010);
                                    }
                                    addEditText.setText("");
                                }
                            } else {
                                Toast.makeText(AddItem2.this, "Ingredients cannot have zero length",
                                        Toast.LENGTH_SHORT).show();
                            }
                            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


                        }
                    });
                    addButtonClicked=true;
                } else {
                    addEdit.setVisibility(View.GONE);
                    addButtonClicked=false;
                }



            }



        });


        homeIcon=(ImageView) findViewById(R.id.homeButton);

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasPhotoBeenTaken) {
                    AlertDialog diaBox = AskOptionHome();
                    diaBox.show();
                } else {
                    Toast.makeText(AddItem2.this, "Please take a photo before saving your recipe.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        saveIcon=(ImageView) findViewById(R.id.saveButton);

        saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSave();
                Toast.makeText(AddItem2.this, "Recipe updated. Press the home button to return to your home page.",
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


        removeIngredient=(ImageView) findViewById(R.id.minusIngredientButton);
        ingredientsText=(TextView) findViewById(R.id.ingredientsText);
        itemsFrame=(FrameLayout) findViewById(R.id.itemsFrame);
        removeIngredient.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPromptNeeded=true;
                addEdit.setVisibility(View.GONE);
                ingredientList.setEnabled(true);
                ingredientsText.setText("DELETE INGREDIENT");
                ingredientsText.setTextColor(Color.parseColor("#fafafa"));
                addIngredient.setImageResource(R.drawable.ic_close);
                addIngredient.setEnabled(false);
                removeIngredient.setVisibility(View.INVISIBLE);
                itemsFrame.setBackgroundColor(Color.parseColor("#ff0000"));
                makeIngredientsClickable();
                makeTitleClickable();

            }
        });





    }

    public void onSave() {
        String selectedValue=String.valueOf(category.getSelectedItem());
        recipe.category=selectedValue;
        recipe.title=titleEdit.getText().toString();
        recipe.rating=lastStarClicked;
        savedIngredients=recipe.ingredients;
        isPromptNeeded=false;

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

                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {

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

            }
        }

    }

    private AlertDialog AskOptionIngredient(String s) {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete Ingredient")
                .setMessage("Are you sure you want to delete '"+s.toUpperCase()+"'")
                .setIcon(R.drawable.ic_delete)

                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }

                })

                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ingredients.remove(lastClickedIngredientIndex);
                        adapty.notifyDataSetChanged();
                        if (ingredients.size()>10) {
                            params.height = (101 * ingredients.size());
                        } else {
                            params.height = (1010);
                        }


                    }
                })
                .create();
        return myQuittingDialogBox;

    }

    public void makeIngredientsClickable() {
        ingredientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                try {
                    lastClickedIngredientIndex = position;
                    lastClickedIngredient=ingredients.get(position);
                    AlertDialog diaBox = AskOptionIngredient(lastClickedIngredient);
                    diaBox.show();
                } catch (Exception e) {
                    Toast.makeText(AddItem2.this, "Grid item is not clickable yet",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void makeIngredientsUnclickable() {
        ingredientList.setEnabled(false);
    }

    public void makeTitleClickable() {
        itemsFrame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                isPromptNeeded=true;
                ingredientsText.setText("INGREDIENTS");
                ingredientsText.setTextColor(Color.parseColor("#311B92"));
                addIngredient.setImageResource(R.drawable.ic_add);
                addIngredient.setEnabled(true);
                removeIngredient.setVisibility(View.VISIBLE);
                itemsFrame.setBackgroundColor(Color.parseColor("#B39DDB"));
                makeIngredientsUnclickable();
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (hasPhotoBeenTaken) {
            AlertDialog diaBox = AskOptionHome();
            diaBox.show();
        } else {
            finish();
        }
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

            //imageAdd.setImageBitmap(myBitmap);
            imageAdd.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
            photoFrame.setVisibility(View.VISIBLE);
            cameraButton.setImageResource(R.drawable.ic_add_a_photo_purple);
            hasPhotoBeenTaken=true;

            LinearLayout titleLayout=(LinearLayout) findViewById(R.id.titleLayout);
            titleParams =  (LinearLayout.LayoutParams) titleLayout.getLayoutParams();
            titleParams.setMargins(0,10,0,10);
            titleLayout.setLayoutParams(titleParams);

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
                isPromptNeeded=true;

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
                isPromptNeeded=true;
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
                isPromptNeeded=true;
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
                isPromptNeeded=true;
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
                isPromptNeeded=true;
            }
        });



    }

}
