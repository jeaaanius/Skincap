package com.example.skincap.ui.library;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skincap.R;
import com.example.skincap.databinding.LibraryRowBinding;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder> {

    @NonNull
    @Override
    public LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        LibraryRowBinding binding = LibraryRowBinding.inflate(inflater, parent, false);
        return new LibraryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryViewHolder holder, int position) {
        Library library = LibraryDataSource.skinIssueList.get(position);
        holder.bindData(library);
    }

    @Override
    public int getItemCount() {
        return LibraryDataSource.skinIssueList.size();
    }

    protected class LibraryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final LibraryRowBinding binding;

        public LibraryViewHolder(@NonNull final LibraryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.skinIssue.setOnClickListener(this);
            binding.expandableLayout.setOnClickListener(this);
        }

        public void bindData(final Library library) {
            binding.skinIssue.setText(library.getSkinIssue());
            binding.definitionDesc.setText(library.getDefinition());
            binding.causesDesc.setText(library.getCauses());
            binding.ingredDesc.setText(library.getIngredient());

            binding.expandableLayout.setVisibility(library.isExpanded() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.skinIssue) {
                Library library = LibraryDataSource.skinIssueList.get(getAdapterPosition());
                library.setExpanded(!library.isExpanded());
                notifyItemChanged(getAdapterPosition());
            }
        }
    }
}