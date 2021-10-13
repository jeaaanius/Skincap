package com.example.skincap.ui.library;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.skincap.R;
import com.example.skincap.databinding.FragmentLibraryBinding;
import com.example.skincap.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends BaseFragment<FragmentLibraryBinding> {

    public LibraryFragment() {
        super(R.layout.fragment_library);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentLibraryBinding.bind(view);

        final LibraryAdapter adapter = new LibraryAdapter();
        binding.recyclerView.setAdapter(adapter);
    }
}
