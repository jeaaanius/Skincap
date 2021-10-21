package com.example.skincap.ui.checkskin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.skincap.R;
import com.example.skincap.classifier.ImageClassifier;
import com.example.skincap.databinding.ActivityCreateJournalBinding;
import com.example.skincap.ui.journal.CreateJournalViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResultActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1000;
    private static final int CAMERA_REQUST_CODE = 1001;
    //Initialize UI Elements
    private ImageView imageView;
    private ListView listView;
    private ImageClassifier imageClassifier;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_result);

        //deins ko magawa yung pag nagok sa camera lalabas yung fragment_result na layout

        initializeUIElements();

    }
    private void initializeUIElements() {
        imageView = findViewById(R.id.check_skin_image);
        listView = findViewById(R.id.lv_probabilities);
        Button takepicture = findViewById(R.id.camera_button);

        try {
            imageClassifier = new ImageClassifier(this);
        } catch (IOException e) {
            Log.e("Image Classifier Error", "ERROR: " + e);
        }

        takepicture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(hasPermission()){
                    openCamera();
                }else{
                    requestPermission();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CAMERA_REQUST_CODE){
            Bitmap photo = (Bitmap) Objects.requireNonNull(Objects.requireNonNull(data).getExtras()).get("data");
            imageView.setImageBitmap(photo);
            // pass this bitmap to classifier
            List<ImageClassifier.Recognition> predictions = imageClassifier.recognizeImage(
                    photo, 0);
            final List<String> predictionsList = new ArrayList<>();
            for(ImageClassifier.Recognition recog : predictions){
                predictionsList.add(recog.getName() + " : " + recog.getConfidence());
            }
            ArrayAdapter<String> predictionsAdapter = new ArrayAdapter<>(
                    this, R.layout.support_simple_spinner_dropdown_item, predictionsList);
            listView.setAdapter(predictionsAdapter);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //@Override
    public void onRequestPermissionResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERMISSION_REQUEST_CODE){
            if(hasAllPermission(grantResults)){
                openCamera();
            }else{
                requestPermission();
            }
        }
    }

    private boolean hasAllPermission(int[] grantResults) {
        for(int result: grantResults){
            if(result == PackageManager.PERMISSION_DENIED)
                return false;
        }
        return true;
    }

    // Add Permission Checks
    private void requestPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                Toast.makeText(this, "Camera Permission Required", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    // open Camera Intent to Take Picture
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUST_CODE);
    }

    private boolean hasPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }
}
