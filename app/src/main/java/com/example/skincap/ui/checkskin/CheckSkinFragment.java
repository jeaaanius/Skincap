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

//      startActivity(new Intent(requireActivity(), ResultActivity.class));
        Intent myIntent = new Intent(requireActivity(), ResultActivity.class);
        myIntent.putExtra("REQUEST_CODE", 1001);
        startActivity(myIntent);
    }

    private void onClickGalleryButton(View view) {

//        startActivity(new Intent(requireActivity(), ResultActivity.class));
        Intent myIntent = new Intent(requireActivity(), ResultActivity.class);
        myIntent.putExtra("REQUEST_CODE", 1002);
        startActivity(myIntent);
    }

    @SuppressWarnings("unchecked")
    private static <T> T getDataOrNull(final Intent data) {
        return (T) (data != null ? data.getExtras().get("data") : null);
    }
}
