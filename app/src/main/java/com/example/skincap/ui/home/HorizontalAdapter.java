package com.example.skincap.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.skincap.R;

import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

    public List<ChildModel> childModelArrayList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView skinImage;
        public TextView skinName;

        public MyViewHolder(View itemView) {
            super(itemView);
            skinImage = itemView.findViewById(R.id.skin_image);
            skinName = itemView.findViewById(R.id.skin_name);
        }
    }

    @LayoutRes
    private final int layoutId;

    public HorizontalAdapter(List<ChildModel> arrayList, @LayoutRes int layoutId) {
        this.childModelArrayList = arrayList;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ChildModel currentItem = childModelArrayList.get(position);
        holder.skinName.setText(currentItem.getSkinName());

        Glide.with(holder.skinImage)
                .asBitmap()
                .override(
                        holder.skinImage.getWidth(),
                        holder.skinImage.getHeight()
                )
                .load(currentItem.getSkinImage())
                .into(holder.skinImage);
    }

    @Override
    public int getItemCount() {
        return childModelArrayList.size();
    }
}