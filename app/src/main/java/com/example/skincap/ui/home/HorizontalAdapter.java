package com.example.skincap.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skincap.R;
import com.example.skincap.util.GlideBinder;

import java.util.ArrayList;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

    public ArrayList<ChildModel> childModelArrayList;

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

    public HorizontalAdapter(ArrayList<ChildModel> arrayList, @LayoutRes int layoutId, Context context) {
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

        GlideBinder.bindImage(holder.skinImage, currentItem.getSkinImage());
    }

    @Override
    public int getItemCount() {
        return childModelArrayList.size();
    }
}