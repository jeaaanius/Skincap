package com.example.skincap.ui.journal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skincap.R;
import com.example.skincap.databinding.FragmentJournalBinding;
import com.example.skincap.databinding.JournalItemBinding;
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


class JournalAdapter extends ListAdapter<Journal, JournalAdapter.JournalViewHolder> {

    public JournalAdapter() {
        super(new DiffUtil.ItemCallback<Journal>() {
            @Override
            public boolean areItemsTheSame(@NonNull Journal oldItem, @NonNull Journal newItem) {
                return oldItem.getJournalId().equals(newItem.getJournalId());
            }

            @SuppressLint("DiffUtilEquals")
            @Override
            public boolean areContentsTheSame(@NonNull Journal oldItem, @NonNull Journal newItem) {
                return oldItem == newItem;
            }
        });
    }

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final JournalItemBinding binding = JournalItemBinding.inflate(inflater, parent, false);
        return new JournalViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
        holder.bindJournal(getItem(position));
    }

    static class JournalViewHolder extends RecyclerView.ViewHolder {

        private final JournalItemBinding binding;

        public JournalViewHolder(JournalItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindJournal(final Journal journal) {
            binding.startDate.setText(journal.getStartDate());
            binding.title.setText(journal.getTitle());
            binding.expectedDate.setText(journal.getExpectedDate());

            // todo pa handle nalang ng states dito base sa use case nyo
            // binding.journalState.setText();
        }
    }
}

