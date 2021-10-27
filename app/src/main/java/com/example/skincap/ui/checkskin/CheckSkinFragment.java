package com.example.skincap.ui.checkskin;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.skincap.R;
import com.example.skincap.classifier.AnalyzerClassifier;
import com.example.skincap.classifier.ImageClassifier;
import com.example.skincap.databinding.FragmentCheckSkinBinding;
import com.example.skincap.ui.base.BaseFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.Result;

public class CheckSkinFragment extends BaseFragment<FragmentCheckSkinBinding> {

    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int GALLERY_REQUEST_CODE = 2;

    public CheckSkinFragment() {
        super(R.layout.fragment_check_skin);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentCheckSkinBinding.bind(view);

        binding.cameraButton.setOnClickListener(this::onClickCameraButton);
        binding.galleryButton.setOnClickListener(this::onClickGalleryButton);
    }

    private void onClickCameraButton(View view) {

      startActivity(new Intent(requireActivity(), ResultActivity.class));
    }

    private void onClickGalleryButton(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);

        //startActivity(new Intent(requireActivity(), ResultActivity.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == CAMERA_REQUEST_CODE) {
            //  Camera
            getCapturedImage(data);
        }

        if (requestCode == GALLERY_REQUEST_CODE) {
            //  Gallery
            getSelectedImage(data);
        }

    }

    private void getCapturedImage(@Nullable final Intent data) {
        Bitmap captureImageBitmap = getDataOrNull(data);
        binding.checkSkinImage.setImageBitmap(captureImageBitmap);

        try {
            ImageClassifier imageClassifier = new ImageClassifier(requireActivity());

            List<ImageClassifier.Recognition> predictions = imageClassifier.recognizeImage(
                    captureImageBitmap, 0);

            final List<String> predictionsList = new ArrayList<>();

            for(ImageClassifier.Recognition recog : predictions){
                predictionsList.add(recog.getName() + " : " + recog.getConfidence());
            }

            for (String string : predictionsList) {
                Log.e(null, string);
            }

            //  ArrayAdapter<String> predictionsAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, predictionsList);

        } catch (IOException e) {
            Log.e("Image Classifier Error", "ERROR: " + e);
        }
        // FOR SKIN ANALYZER
        try {
            AnalyzerClassifier imageClassifier = new AnalyzerClassifier(requireActivity());
            List<AnalyzerClassifier.Recognition> predictions = imageClassifier.recognizeImage(
                    captureImageBitmap, 0);
            final List<String> predictionsList = new ArrayList<>();
            for(AnalyzerClassifier.Recognition recog : predictions){
                predictionsList.add(recog.getName() + " : " + recog.getConfidence());
            }
            for (String string : predictionsList) {
                Log.e(null, string);
            }

        } catch (IOException e) {
            Log.e("Image Classifier Error", "ERROR: " + e);
        }

    }

    private void getSelectedImage(@Nullable final Intent data) {

        Uri selectedImage = data.getData();
        try {
            Bitmap selectedImageBitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(),selectedImage);
            binding.checkSkinImage.setImageBitmap(selectedImageBitmap);

           /* ImageClassifier imageClassifier = new ImageClassifier(requireActivity());

            List<ImageClassifier.Recognition> predictions = imageClassifier.recognizeImage(
                    selectedImageBitmap, 0);

            final List<String> predictionsList = new ArrayList<>();

            for(ImageClassifier.Recognition recog : predictions){
                predictionsList.add(recog.getName() + " : " + recog.getConfidence());
            }

            for (String string : predictionsList) {
                Log.e(null, string);
            }*/

        } catch(IOException e){
            e.printStackTrace();
            //Log.e("Image Classifier Error", "ERROR: " + e);
        }

        /*// FOR SKIN ANALYZER
        try {
            Bitmap selectedImageBitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(),selectedImage);
            binding.checkSkinImage.setImageBitmap(selectedImageBitmap);

            AnalyzerClassifier imageClassifier = new AnalyzerClassifier(requireActivity());
            List<AnalyzerClassifier.Recognition> predictions = imageClassifier.recognizeImage(
                    selectedImageBitmap, 0);
            final List<String> predictionsList = new ArrayList<>();
            for(AnalyzerClassifier.Recognition recog : predictions){
                predictionsList.add(recog.getName() + " : " + recog.getConfidence());
            }
            for (String string : predictionsList) {
                Log.e(null, string);
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Image Classifier Error", "ERROR: " + e);
        }*/

        // startActivity(new Intent(getActivity(), ResultActivity.class));
    }

    @SuppressWarnings("unchecked")
    private static <T> T getDataOrNull(final Intent data) {
        return (T) (data != null ? data.getExtras().get("data") : null);
    }
}