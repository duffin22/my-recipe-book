package com.hfad.myrecipebook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class AddItemActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    ImageView imageAdd;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        imageAdd=(ImageView) findViewById(R.id.imageAdd);

        Log.d("Pointer","Entered onCreat method");

        final ImageView cameraButton= (ImageView) findViewById(R.id.cameraButton);


        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Pointer","Entered onClick creator for camera button");
                openCameraActivity(view);
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
