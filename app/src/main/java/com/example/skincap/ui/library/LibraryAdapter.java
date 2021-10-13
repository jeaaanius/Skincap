package com.example.skincap.ui.library;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skincap.R;

import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryVH> {

    private static final String TAG = "LibraryAdapter";
    List<Library> libraryList;

    public LibraryAdapter(List<Library> libraryList) {
        this.libraryList = libraryList;
    }

    @NonNull
    @Override
    public LibraryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_row, parent, false);
        return new LibraryVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryVH holder, int position) {

        Library library = libraryList.get(position);
        holder.skinIssue.setText(library.getSkinIssue());
        holder.definition_desc.setText(library.getDefinition());
        holder.causes_desc.setText(library.getCauses());
        holder.ingred_desc.setText(library.getIngredient());

        boolean isExpanded = libraryList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return libraryList.size();
    }

    class LibraryVH extends RecyclerView.ViewHolder {

        private static final String TAG = "LibraryVH";

        ConstraintLayout expandableLayout;
        TextView skinIssue, definition_desc, causes_desc, ingred_desc;

        public LibraryVH(@NonNull final View itemView) {
            super(itemView);

            skinIssue = itemView.findViewById(R.id.skinIssue);
            definition_desc = itemView.findViewById(R.id.definition_desc);
            causes_desc = itemView.findViewById(R.id.causes_desc);
            ingred_desc = itemView.findViewById(R.id.ingred_desc);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);

            skinIssue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Library library = libraryList.get(getAdapterPosition());
                    library.setExpanded(!library.isExpanded());
                    notifyItemChanged(getAdapterPosition());

                }
            });
        }
    }
}