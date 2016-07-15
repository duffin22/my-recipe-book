package com.hfad.myrecipebook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by matthewtduffin on 09/07/16.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private final LayoutInflater inflater;
    ArrayList<Recipe> recipe;
    ImageView imageView;

    public ImageAdapter(Context c, ArrayList<Recipe> recipes) {
        mContext = c;
        inflater = LayoutInflater.from(c);
        this.recipe=recipes;
    }

    public int getCount() {
        if (!this.recipe.isEmpty())
            return recipe.size();
        else {
            return 0;
        }
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View child, ViewGroup parent) {
        Log.d("Position: " , "" + position);

        FrameLayout frameLayout;
        ImageView currentStar;
        TextView textView;
        LinearLayout ratings;

        View v =child;

        if (v == null) {
            v = inflater.inflate(R.layout.grid_item, parent, false);
        }

        frameLayout = (FrameLayout) v.findViewById(R.id.frame);

        imageView= (ImageView) v.findViewById(R.id.image);
        textView= (TextView) v.findViewById(R.id.name);


        try {
            //imageView.setImageURI(recipe.get(position).getUri());
            saveFileToImageView(recipe.get(position).getUri().getPath(), position);
        } catch (Exception e) {
        }



        imageView.setAlpha(0.7f);
        textView.setText(recipe.get(position).title);

        ratings=(LinearLayout) v.findViewById(R.id.rating);
        for (int i=0;i<5;i++) {
            currentStar=(ImageView) ratings.getChildAt(i);
            if (recipe.get(position).rating > i) {
                currentStar.setImageResource(R.drawable.star_full);
            } else {
                currentStar.setImageResource(R.drawable.star_empty);
            }
        }


        for (int i=1; i<=5; i++) {
            if (i<=recipe.get(position).rating) {

            }
        }

        return frameLayout;
    }

//    public void saveFileToImageView(String filePath) {
//        File imgFile = new  File(filePath);
//
//        if(imgFile.exists()){
//
//            Bitmap myBitmap = rotateImage(BitmapFactory.decodeFile(imgFile.getAbsolutePath()),90);
//
//            imageView.setImageBitmap(myBitmap);
//
//        } else {
//            Log.i("STUFF","Image doesn't exist");
//        }
//    }

    public void saveFileToImageView(String filePath, int position) {
        File imgFile = new  File(filePath);

        if(imgFile.exists()){

            try {
                ExifInterface ei = new ExifInterface(recipe.get(position).getUri().getPath());
                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                Bitmap yourBitmap = BitmapFactory.decodeFile(imgFile.getPath());
                Bitmap myBitmap;
                switch(orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        myBitmap = rotateImage(yourBitmap, 90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        myBitmap = rotateImage(yourBitmap, 180);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        myBitmap = rotateImage(yourBitmap, 270);
                        break;
                    default:
                        myBitmap = yourBitmap;
                }
                imageView.setImageBitmap(myBitmap);

                //imageAdd.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
//                photoFrame.setVisibility(View.VISIBLE);
//                cameraButton.setImageResource(R.drawable.ic_add_a_photo_purple);
//                hasPhotoBeenTaken=true;
//
//                LinearLayout titleLayout=(LinearLayout) findViewById(R.id.titleLayout);
//                titleParams =  (LinearLayout.LayoutParams) titleLayout.getLayoutParams();
//                titleParams.setMargins(0,10,0,10);
//                titleLayout.setLayoutParams(titleParams);
            } catch (Exception e) {
                Log.d("TAG","FAILURE TO FIND IMAGE SOURCE");
            }


            //imageAdd.setImageBitmap(myBitmap);

            //imageAdd.setImageBitmap(myBitmap);
//            imageAdd.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
//            photoFrame.setVisibility(View.VISIBLE);
//            cameraButton.setImageResource(R.drawable.ic_add_a_photo_purple);
//            hasPhotoBeenTaken=true;
//
//            LinearLayout titleLayout=(LinearLayout) findViewById(R.id.titleLayout);
//            titleParams =  (LinearLayout.LayoutParams) titleLayout.getLayoutParams();
//            titleParams.setMargins(0,10,0,10);
//            titleLayout.setLayoutParams(titleParams);

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





}
