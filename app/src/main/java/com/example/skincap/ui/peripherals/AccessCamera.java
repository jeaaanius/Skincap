package com.example.skincap.ui.peripherals;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.skincap.R;

public class AccessCamera extends AppCompatActivity {

    Button camera_button;
    ImageView view_image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_check_skin);

        camera_button = findViewById(R.id.camera_button);
        view_image = findViewById(R.id.view_image);

        //  For Camera Permissions
        if (ContextCompat.checkSelfPermission(AccessCamera.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AccessCamera.this, new String[]{
                    Manifest.permission.CAMERA },100);
        }

        //  Camera Button
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  For Starting Camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //  Getting and viewing captured image
        if (requestCode == 100) {
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            view_image.setImageBitmap(captureImage);
        }
    }
}
