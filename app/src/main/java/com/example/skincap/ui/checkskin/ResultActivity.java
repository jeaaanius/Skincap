package com.example.skincap.ui.checkskin;
import com.example.skincap.ui.library.Library;
import com.example.skincap.ui.library.LibraryAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import static android.app.Activity.RESULT_OK;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skincap.R;
import com.example.skincap.classifier.AnalyzerClassifier;
import com.example.skincap.classifier.ImageClassifier;
import com.example.skincap.databinding.ActivityResultBinding;
import com.example.skincap.ui.library.Library;
import com.example.skincap.ui.library.LibraryAdapter;
import com.example.skincap.ui.library.LibraryDataSource;
import com.example.skincap.util.GlideBinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ResultActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 1001;
    //private static final int GALLERY_REQUEST_CODE = 1002;

    private ActivityResultBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityResultBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        startCameraInstant();
        //startGalleryInstant();
    }

    @SuppressWarnings("deprecation")
    private void startCameraInstant() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }
   /*@SuppressWarnings("deprecation")
    private void startGalleryInstant() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == CAMERA_REQUEST_CODE) {
            if(resultCode == RESULT_CANCELED) {
                finish();
            }else{
                Bitmap bitmapPhoto = (Bitmap) Objects.requireNonNull(Objects.requireNonNull(data).getExtras()).get("data");
                bitmapPhoto = bitmapPhoto.copy(Bitmap.Config.ARGB_8888, true);
                binding.ivCapture.setImageBitmap(bitmapPhoto);

                GlideBinder.bindImage(binding.ivCapture, bitmapPhoto);
                initImageClassifier(bitmapPhoto);
                String result = binding.lvProbabilities.getItemAtPosition(0).toString() + " " +
                        binding.lvProbabilities2.getItemAtPosition(0).toString();

                ArrayList<String> scripts = new ArrayList<String>();

                String[] result_array = result.split(" : ",0);
                for(int i = 0; i< result_array.length; i++){
                    result_array[i].trim();
                }
                String temp_array[] = result_array[1].split(" ");
                scripts.add(result_array[0]);
                scripts.add(temp_array[0]);
                scripts.add(temp_array[1]);
                scripts.add(result_array[2]);

                float confidence1 = (Float.parseFloat(scripts.get(1)))*100;
                float confidence2 = (Float.parseFloat(scripts.get(3)))*100;

                binding.resultTv.setText(scripts.get(0));
                binding.conditionConfidenceTv.setText(String.format("%.2f", confidence1) + "%");
                binding.resultTv2.setText(scripts.get(2));
                binding.analyzerConfidenceTv.setText(String.format("%.2f", confidence2) + "%");

                ArrayList<String> skinIssue = new ArrayList<>();
                String[] otherList = new String[] {
                        "Definition","Causes","Skin Care Ingredients",
                        "","" //to be continued AHAAHAHA ang pangit tlaga pero gumagana
                };
                Collections.addAll(skinIssue, otherList);

                //di ko pa malagay yung ResultsDataSource makalat pa HAHHAHAH XD

                if(scripts.get(0).equals("Acne Papule")){
                    binding.definitionDesc.setText(skinIssue.get(0));
                    binding.causesDesc.setText(skinIssue.get(1));
                    binding.ingredDesc.setText(skinIssue.get(2));
                }
                if(scripts.get(0).equals("Sunspots")){
                    binding.definitionDesc.setText(skinIssue.get(0));
                    binding.causesDesc.setText(skinIssue.get(1));
                    binding.ingredDesc.setText(skinIssue.get(2));
                }
                if(scripts.get(0).equals("Whiteheads")){
                    binding.definitionDesc.setText(skinIssue.get(0));
                    binding.causesDesc.setText(skinIssue.get(1));
                    binding.ingredDesc.setText(skinIssue.get(2));
                }
                if(scripts.get(0).equals("Blackheads")){
                    binding.definitionDesc.setText(skinIssue.get(0));
                    binding.causesDesc.setText(skinIssue.get(1));
                    binding.ingredDesc.setText(skinIssue.get(2));
                }
                if(scripts.get(0).equals("Fungal Acne")){
                    binding.definitionDesc.setText(skinIssue.get(0));
                    binding.causesDesc.setText(skinIssue.get(1));
                    binding.ingredDesc.setText(skinIssue.get(2));
                }
                if(scripts.get(0).equals("Perioral Dermatitis")){
                    binding.definitionDesc.setText(skinIssue.get(0));
                    binding.causesDesc.setText(skinIssue.get(1));
                    binding.ingredDesc.setText(skinIssue.get(2));
                }
                if(scripts.get(0).equals("Milia")){
                    binding.definitionDesc.setText(skinIssue.get(0));
                    binding.causesDesc.setText(skinIssue.get(1));
                    binding.ingredDesc.setText(skinIssue.get(2));
                }
                if(scripts.get(0).equals("Normal")){
                    binding.definitionDesc.setText(skinIssue.get(0));
                    binding.causesDesc.setText(skinIssue.get(1));
                    binding.ingredDesc.setText(skinIssue.get(2));
                }

            }
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

        // SECOND CLASSIFIER FOR SKIN ANALYZER
        try {

            AnalyzerClassifier classifier = new AnalyzerClassifier(this);

            List<AnalyzerClassifier.Recognition> predictions = classifier.recognizeImage(bitmap, 0);

            final List<String> predictionsList = new ArrayList<>();

            for (AnalyzerClassifier.Recognition recognition : predictions) {
                predictionsList.add(recognition.getName() + " : " + recognition.getConfidence());
                Log.i("Probabilities: ", recognition.getName() + " : " + recognition.getConfidence());
            }
            setRecognizedProbabilities2(predictionsList);

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

    // SECOND CLASSIFIER METHODS
    private void setRecognizedProbabilities2(final List<String> predictionsList) {
        binding.lvProbabilities2.setAdapter(getProbabilityAdapter2(predictionsList));
    }

    private ArrayAdapter<String> getProbabilityAdapter2(final List<String> predictionsList) {
        return new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, predictionsList);
    }
    //
}