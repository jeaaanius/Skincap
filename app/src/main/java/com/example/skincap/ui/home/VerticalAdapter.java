package com.example.skincap.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skincap.R;

import java.util.ArrayList;
import java.util.List;

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.MyViewHolder> {

    private final ArrayList<ParentModel> parentModelArrayList;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView category;
        public RecyclerView childRecyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.Skin_category);
            childRecyclerView = itemView.findViewById(R.id.horizontal_list_view);
        }
    }

    public VerticalAdapter(ArrayList<ParentModel> exampleList) {
        this.parentModelArrayList = exampleList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.parent_recyclerview_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return parentModelArrayList.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ParentModel currentItem = parentModelArrayList.get(position);

        holder.childRecyclerView.setLayoutManager(linearLayoutManager());
        holder.childRecyclerView.setHasFixedSize(true);
        holder.category.setText(currentItem.skinCategory());


        @LayoutRes int horizontalItem = position == 0
                ? R.layout.horizontal_rectangle_item
                : R.layout.horizontal_square_item;

        holder.childRecyclerView.setAdapter(new HorizontalAdapter(getDelegateList(position), horizontalItem));
    }

    private List<ChildModel> getDelegateList(int position) {
        List<ChildModel> modelList;

        switch (position) {
            case 0: {
                modelList = getSkinTypesItems();
                break;
            }
            case 1: {
                modelList = getSkinIssues();
                break;
            }
            case 2: {
                modelList = getIngredientsItems();
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }

        return modelList;
    }

    private LinearLayoutManager linearLayoutManager() {
        return new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
    }

    private List<ChildModel> getSkinTypesItems() {
        return new ArrayList<ChildModel>() {
            {
                add(new ChildModel(R.drawable.n_oily, "Oily"));
                add(new ChildModel(R.drawable.n_dry, "Dry"));
                add(new ChildModel(R.drawable.n_normal, "Balanced"));
            }
        };
    }

    private List<ChildModel> getSkinIssues() {
        return new ArrayList<ChildModel>() {
            {
                add(new ChildModel(R.drawable.m_acne_papule, "Acne Papule"));
                add(new ChildModel(R.drawable.m_sun_spot, "Sunspots"));
                add(new ChildModel(R.drawable.m_whiteheads, "Whiteheads"));
                add(new ChildModel(R.drawable.m_blackheads, "Blackheads"));
                add(new ChildModel(R.drawable.m_fungal_acne, "Fungal Acne"));
                add(new ChildModel(R.drawable.m_perioral_dermatitis, "Perioral Dermatitis"));
                add(new ChildModel(R.drawable.m_milia, "Milia"));
            }
        };
    }

    private List<ChildModel> getIngredientsItems() {
        return new ArrayList<ChildModel>() {
            {
                add(new ChildModel(R.drawable.o_green_tea, "Green Tea"));
                add(new ChildModel(R.drawable.o_salicylic_acid, "Salicylic acid"));
                add(new ChildModel(R.drawable.o_tea_tree, "Tea Tree"));
                add(new ChildModel(R.drawable.o_vitamin_c, "Vitamin C"));
                add(new ChildModel(R.drawable.o_vitamin_e, "Vitamin E"));
            }
        };

    }
}
