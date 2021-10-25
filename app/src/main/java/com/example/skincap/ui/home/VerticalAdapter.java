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
                add(new ChildModel("https://photojoy.jp/magazine/wp-content/uploads/2021/05/shutterstock_1454058797-min.jpg", "Oily"));
                add(new ChildModel("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/woman-with-atopic-dermatitis-royalty-free-image-900018018-1562167997.jpg", "Dry"));
                add(new ChildModel("https://luxurykitchenspace.com/wp-content/uploads/2021/02/qZX41m7CGNf7aUecy667PjzAVwoLpP-600x410.jpeg", "Balanced"));
            }
        };
    }

    private List<ChildModel> getSkinIssues() {
        return new ArrayList<ChildModel>() {
            {
                add(new ChildModel("https://www.parvisdesgentils.fr/wp-content/uploads/2019/06/turmeric-turmeric-which-660x400.jpg", "Acne Papule"));
                add(new ChildModel("https://www.caraprofesor.com/wp-content/uploads/2018/01/penyebab-flek-hitam-di-wajah.jpg", "Sunspots"));
                add(new ChildModel("https://ovalin.vn/wp-content/uploads/2020/03/mun-cam.jpg", "Whiteheads"));
                add(new ChildModel("https://4.bp.blogspot.com/-QxdZBKd9G5c/V17zttcR4NI/AAAAAAAABy4/zuJ5cwgym7cM5dLKhH_VwZmtz2uqp3ApQCK4B/s320/black.jpg", "Blackheads"));
                add(new ChildModel("https://envisionacnecenter.com/wp-content/uploads/2015/06/Folliculitis-300x197.jpg", "Fungal Acne"));
                add(new ChildModel("https://www.newtonwellesleyderm.com/sbtemplates/sbcommon/images/aad/pamphlets/45-1.jpg", "Perioral Dermatitis"));
                add(new ChildModel("https://mrdoctor.org/wp-content/uploads/2011/11/white-bump-on-eyelid-pictures.jpg", "Milia"));
            }
        };
    }

    private List<ChildModel> getIngredientsItems() {
        return new ArrayList<ChildModel>() {
            {
                add(new ChildModel("https://5.imimg.com/data5/YT/HA/MY-44441454/green-tea-extract-300-1000x1000.jpg", "Green Tea"));
                add(new ChildModel("https://ryalix.com/wp-content/uploads/2021/06/9-Best-Skin-Masks-For-Your-Skin-Type-5-1024x683.jpg", "Salicylic acid"));
                add(new ChildModel("https://www.farmasicolourcosmetics.com/wp-content/uploads/2021/10/tea-tree-essential-oil-500x500-1.jpeg", "Tea Tree"));
                add(new ChildModel("https://i0.wp.com/post.healthline.com/wp-content/uploads/2020/04/Vitamin_C_1296x728-header.jpg", "Vitamin C"));
                add(new ChildModel("https://cdn.shopify.com/s/files/1/1944/0163/articles/vitamin-e-rich-food_1140x.jpg?v=1546473551", "Vitamin E"));
            }
        };

    }
}
