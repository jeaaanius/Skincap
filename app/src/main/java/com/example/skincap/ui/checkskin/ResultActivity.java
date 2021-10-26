package com.example.skincap.ui.checkskin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skincap.R;
import com.example.skincap.classifier.ImageClassifier;
import com.example.skincap.databinding.ActivityResultBinding;
import com.example.skincap.util.GlideBinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResultActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 1001;

    private ActivityResultBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityResultBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        startCameraInstant();
    }

    @SuppressWarnings("deprecation")
    private void startCameraInstant() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            Bitmap bitmapPhoto = (Bitmap) Objects.requireNonNull(Objects.requireNonNull(data).getExtras()).get("data");
            bitmapPhoto = bitmapPhoto.copy(Bitmap.Config.ARGB_8888, true);
            binding.ivCapture.setImageBitmap(bitmapPhoto);

            GlideBinder.bindImage(binding.ivCapture, bitmapPhoto);
            initImageClassifier(bitmapPhoto);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initImageClassifier(final Bitmap bitmap) {
        try {

            ImageClassifier classifier = new ImageClassifier(this);

            List<ImageClassifier.Recognition> predictions = classifier.recognizeImage(bitmap, 0);

            final List<String> predictionsList = new ArrayList<>();

            for (ImageClassifier.Recognition recognition : predictions) {
                predictionsList.add(recognition.getName() + " : " + recognition.getConfidence());
                Log.i("Probabilities: ", recognition.getName() + " : " + recognition.getConfidence());
            }

            setRecognizedProbabilities(predictionsList);

        } catch (IOException e) {
            Log.e("Image Classifier Error", "ERROR: " + e);
        }
    }

    private void setRecognizedProbabilities(final List<String> predictionsList) {
        binding.lvProbabilities.setAdapter(getProbabilityAdapter(predictionsList));
    }

    private ArrayAdapter<String> getProbabilityAdapter(final List<String> predictionsList) {
        return new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, predictionsList);
    }
}
