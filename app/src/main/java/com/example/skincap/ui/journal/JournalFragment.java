package com.example.skincap.ui.journal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.skincap.R;
import com.example.skincap.databinding.FragmentJournalBinding;
import com.example.skincap.model.Journal;
import com.example.skincap.ui.base.BaseFragment;

import java.util.List;

public class JournalFragment extends BaseFragment<FragmentJournalBinding> {

    public JournalFragment() {
        super(R.layout.fragment_journal);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentJournalBinding.bind(view);

        binding.floatingButton.setOnClickListener(e -> createIntent());

        new ViewModelProvider(this).get(JournalListViewModel.class)
                .getJournals()
                .observe(getViewLifecycleOwner(), this::setJournals);
    }

    private void setJournals(final List<Journal> journals) {
        final JournalAdapter adapter = new JournalAdapter();
        adapter.submitList(journals);

        binding.journalList.setAdapter(adapter);
        binding.emptyState.setVisibility(journals.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void createIntent() {
        startActivity(new Intent(requireActivity(), CreateJournalActivity.class));
    }
}