package com.example.skincap.ui.peripherals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;

import com.example.skincap.R;

public class ForCheckSkin extends AppCompatActivity   {

    Button camera_button;
    Button gallery_button;
    ImageView view_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_check_skin);

        camera_button = findViewById(R.id.camera_button);
        view_image = findViewById(R.id.view_image);
        gallery_button = findViewById(R.id.gallery_button);


        //  For Camera Permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA },1);
        }

        //  Camera Button
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  For Starting Camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);
            }
        });

        //  Gallery Button
        gallery_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(2);
            }
        });

    }

    void openGallery(int requestCode) {
        Intent i = new Intent();
        i.setType("image/");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select an Image"), requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            //  Camera
            if (requestCode == 1) {
                Bitmap captureImage = (Bitmap) data.getExtras().get("data");

                view_image.setImageBitmap(captureImage);
            }

            //  Gallery
            else if (requestCode == 2) {
                Uri selected_image = data.getData();

                if (null != selected_image){
                    view_image.setImageURI(selected_image);
                }
            }
        }
    }
}