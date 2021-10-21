package com.example.skincap.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.example.skincap.R;

public class ParentRecyclerViewAdapter extends RecyclerView.Adapter<ParentRecyclerViewAdapter.MyViewHolder> {
    private ArrayList<ParentModel> parentModelArrayList;
    public Context cxt;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView category;
        public RecyclerView childRecyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.Skin_category);
            childRecyclerView = itemView.findViewById(R.id.Child_RV);
        }
    }

    public ParentRecyclerViewAdapter(ArrayList<ParentModel> exampleList, Context context) {
        this.parentModelArrayList = exampleList;
        this.cxt = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_recyclerview_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return parentModelArrayList.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ParentModel currentItem = parentModelArrayList.get(position);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(cxt, LinearLayoutManager.HORIZONTAL, false);
        holder.childRecyclerView.setLayoutManager(layoutManager);
        holder.childRecyclerView.setHasFixedSize(true);

        holder.category.setText(currentItem.skinCategory());
        ArrayList<ChildModel> arrayList = new ArrayList<>();

        // added the first child row
        if (parentModelArrayList.get(position).skinCategory().equals("Skin Types")) {
            arrayList.add(new ChildModel(R.drawable.dev_1,"Oily"));
            arrayList.add(new ChildModel(R.drawable.dev_1,"Dry"));
            arrayList.add(new ChildModel(R.drawable.dev_1,"Balanced"));
            arrayList.add(new ChildModel(R.drawable.dev_1,"Normal"));
        }

        // added in second child row
        if (parentModelArrayList.get(position).skinCategory().equals("Skin Issues")) {
            arrayList.add(new ChildModel(R.drawable.dev_1,"Acne Papule"));
            arrayList.add(new ChildModel(R.drawable.dev_1,"Sunspots"));
            arrayList.add(new ChildModel( R.drawable.dev_1,"Whiteheads"));
            arrayList.add(new ChildModel( R.drawable.dev_1,"Blackheads"));
            arrayList.add(new ChildModel( R.drawable.dev_1,"Fungal Acne"));
            arrayList.add(new ChildModel( R.drawable.dev_1,"Folliculitis"));
            arrayList.add(new ChildModel( R.drawable.dev_1,"Perioral Dermatitis"));
            arrayList.add(new ChildModel( R.drawable.dev_1,"Milia"));
        }

        // added in third child row
        if (parentModelArrayList.get(position).skinCategory().equals("Skin Ingredients")) {
            arrayList.add(new ChildModel(R.drawable.dev_1,"Ingredient#1"));
            arrayList.add(new ChildModel(R.drawable.dev_1,"Ingredient#2"));
            arrayList.add(new ChildModel( R.drawable.dev_1,"Ingredient#3"));
            arrayList.add(new ChildModel( R.drawable.dev_1,"Ingredient#4"));
            arrayList.add(new ChildModel( R.drawable.dev_1,"Ingredient#5"));
            arrayList.add(new ChildModel( R.drawable.dev_1,"Ingredient#6"));
        }

        ChildRecyclerViewAdapter childRecyclerViewAdapter = new ChildRecyclerViewAdapter(arrayList,holder.childRecyclerView.getContext());
            holder.childRecyclerView.setAdapter(childRecyclerViewAdapter);
        }

}