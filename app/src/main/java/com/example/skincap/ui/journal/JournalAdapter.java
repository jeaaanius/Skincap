package com.example.skincap.ui.journal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skincap.R;
import com.example.skincap.databinding.JournalItemBinding;
import com.example.skincap.model.Journal;
import com.example.skincap.util.GlideBinder;

import java.io.File;

class JournalAdapter extends ListAdapter<Journal, JournalAdapter.JournalViewHolder> {

    private final OnJournalItemClickListener listener;

    interface OnJournalItemClickListener {
        void onEditJournal(Journal journal);

        void onDeleteJournal(String id);
    }

    public JournalAdapter(OnJournalItemClickListener listener) {
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
        this.listener = listener;
    }

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final JournalItemBinding binding = JournalItemBinding.inflate(inflater, parent, false);
        return new JournalViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
        holder.bindJournal(getItem(position));
    }

    static class JournalViewHolder extends RecyclerView.ViewHolder {

        private final JournalItemBinding binding;
        private final OnJournalItemClickListener listener;

        private Journal journal;

        public JournalViewHolder(JournalItemBinding binding, OnJournalItemClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;

            final PopupMenu menu = createPopup(binding.popupImage);
            binding.popupImage.setOnClickListener((v -> menu.show()));

        }

        private PopupMenu createPopup(final View view) {
            final Context context = new ContextThemeWrapper(view.getContext(), R.style.PopupMenu_Journal);

            final PopupMenu menu = new PopupMenu(context, binding.popupImage);
            menu.getMenuInflater().inflate(R.menu.menu_journal_popup, menu.getMenu());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                menu.setGravity(Gravity.END);
            }

            menu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.edit) {
                    listener.onEditJournal(journal);
                } else {
                    listener.onDeleteJournal(journal.getJournalId());
                }
                return true;
            });
            return menu;
        }

        public void bindJournal(final Journal journal) {
            this.journal = journal;

            binding.startDate.setText(journal.getStartDate());
            binding.title.setText(journal.getTitle());
            binding.expectedDate.setText(journal.getExpectedDate());
            binding.notes.setText(journal.getNote());

            Uri imagePath = Uri.fromFile(new File(journal.getImagePath()));
            GlideBinder.bindImage(binding.photoView, imagePath);

            // todo pa handle nalang ng states dito base sa use case nyo
            // binding.journalState.setText();
        }
    }
}