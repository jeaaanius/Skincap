package com.example.skincap.ui.checkskin;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
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
import com.example.skincap.databinding.FragmentCheckSkinBinding;
import com.example.skincap.ui.base.BaseFragment;

import java.util.Objects;

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
        //  For Starting Camera
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAMERA_REQUEST_CODE);
    }

    private void onClickGalleryButton(View view) {
        //  For Opening Gallery
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
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
    }

    private void getSelectedImage(@Nullable final Intent data) {
        Uri selectedImage = data.getData();
        binding.checkSkinImage.setImageURI(selectedImage);
    }

    @SuppressWarnings("unchecked")
    private static <T> T getDataOrNull(final Intent data) {
        return (T) (data != null ? data.getExtras().get("data") : null);
    }
}
