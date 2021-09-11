package com.example.skincap.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skincap.R;
import com.example.skincap.databinding.FragmentHomeBinding;
import com.example.skincap.ui.base.BaseFragment;


public class HomeFragment extends BaseFragment<FragmentHomeBinding> {

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentHomeBinding.bind(view);
    }
}