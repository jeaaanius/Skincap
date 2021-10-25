package com.example.skincap.ui.journal;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skincap.databinding.JournalItemBinding;
import com.example.skincap.model.Journal;

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