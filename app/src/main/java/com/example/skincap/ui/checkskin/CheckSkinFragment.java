package com.example.skincap.ui.checkskin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.skincap.R;
import com.example.skincap.databinding.FragmentCheckSkinBinding;
import com.example.skincap.ui.base.BaseFragment;

public class CheckSkinFragment extends BaseFragment<FragmentCheckSkinBinding> {

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

        Intent myIntent = new Intent(requireActivity(), ResultActivity.class);
        myIntent.putExtra("REQUEST_CODE", 1001);
        startActivity(myIntent);
    }

    private void onClickGalleryButton(View view) {

        Intent myIntent = new Intent(requireActivity(), ResultActivity.class);
        myIntent.putExtra("REQUEST_CODE", 1002);
        startActivity(myIntent);
    }

    @SuppressWarnings("unchecked")
    private static <T> T getDataOrNull(final Intent data) {
        return (T) (data != null ? data.getExtras().get("data") : null);
    }
}
