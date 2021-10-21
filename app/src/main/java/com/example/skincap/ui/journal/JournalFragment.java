package com.example.skincap.ui.journal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.skincap.R;
import com.example.skincap.databinding.FragmentJournalBinding;
import com.example.skincap.ui.base.BaseFragment;

public class JournalFragment extends BaseFragment<FragmentJournalBinding> {

    public JournalFragment() {
        super(R.layout.fragment_journal);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentJournalBinding.bind(view);

        binding.floatingButton.setOnClickListener(e -> createIntent());
    }

    private void createIntent() {
        startActivity(new Intent(requireActivity(), CreateJournalActivity.class));
    }
}
