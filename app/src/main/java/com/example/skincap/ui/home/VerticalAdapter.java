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
                add(new ChildModel("https://img.republicworld.com/republic-prod/stories/promolarge/xhdpi/wbibf5kz0gdidq0b_1614234544.jpeg", "Oily"));
                add(new ChildModel("https://img.republicworld.com/republic-prod/stories/promolarge/xhdpi/wbibf5kz0gdidq0b_1614234544.jpeg", "Dry"));
                add(new ChildModel("https://img.republicworld.com/republic-prod/stories/promolarge/xhdpi/wbibf5kz0gdidq0b_1614234544.jpeg", "Balanced"));
            }
        };
    }

    private List<ChildModel> getSkinIssues() {
        return new ArrayList<ChildModel>() {
            {
                add(new ChildModel("https://img.republicworld.com/republic-prod/stories/promolarge/xhdpi/wbibf5kz0gdidq0b_1614234544.jpeg", "Acne Papule"));
                add(new ChildModel("https://img.republicworld.com/republic-prod/stories/promolarge/xhdpi/wbibf5kz0gdidq0b_1614234544.jpeg", "Sunspots"));
                add(new ChildModel("https://img.republicworld.com/republic-prod/stories/promolarge/xhdpi/wbibf5kz0gdidq0b_1614234544.jpeg", "Whiteheads"));
                add(new ChildModel("https://img.republicworld.com/republic-prod/stories/promolarge/xhdpi/wbibf5kz0gdidq0b_1614234544.jpeg", "Blackheads"));
                add(new ChildModel("https://img.republicworld.com/republic-prod/stories/promolarge/xhdpi/wbibf5kz0gdidq0b_1614234544.jpeg", "Fungal Acne"));
                add(new ChildModel("https://img.republicworld.com/republic-prod/stories/promolarge/xhdpi/wbibf5kz0gdidq0b_1614234544.jpeg", "Perioral Dermatitis"));
                add(new ChildModel("https://img.republicworld.com/republic-prod/stories/promolarge/xhdpi/wbibf5kz0gdidq0b_1614234544.jpeg", "Milia"));
            }
        };
    }

    private List<ChildModel> getIngredientsItems() {
        return new ArrayList<ChildModel>() {
            {
                add(new ChildModel("https://img.republicworld.com/republic-prod/stories/promolarge/xhdpi/wbibf5kz0gdidq0b_1614234544.jpeg", "Green Tea"));
                add(new ChildModel("https://img.republicworld.com/republic-prod/stories/promolarge/xhdpi/wbibf5kz0gdidq0b_1614234544.jpeg", "Salicylic acid"));
                add(new ChildModel("https://img.republicworld.com/republic-prod/stories/promolarge/xhdpi/wbibf5kz0gdidq0b_1614234544.jpeg", "Tea Tree"));
                add(new ChildModel("https://img.republicworld.com/republic-prod/stories/promolarge/xhdpi/wbibf5kz0gdidq0b_1614234544.jpeg", "Vitamin C"));
                add(new ChildModel("https://img.republicworld.com/republic-prod/stories/promolarge/xhdpi/wbibf5kz0gdidq0b_1614234544.jpeg", "Vitamin E"));
            }
        };

    }
}
