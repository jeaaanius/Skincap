package com.example.skincap.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.example.skincap.R;
import com.example.skincap.databinding.FragmentHomeBinding;
import com.example.skincap.ui.base.BaseFragment;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {

    ArrayList<ParentModel> parentModelArrayList = new ArrayList<>();

    private RecyclerView.LayoutManager parentLayoutManager;

    public HomeFragment() {super(R.layout.fragment_home);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentHomeBinding binding = FragmentHomeBinding.bind(view);

        //set the Categories for each array list set in the `ParentViewHolder`
        parentModelArrayList.add(new ParentModel("Skin Types"));
        parentModelArrayList.add(new ParentModel("Skin Issues"));
        parentModelArrayList.add(new ParentModel("Skin Ingredients"));

        VerticalAdapter verticalAdapter = setVerticalAdapter();
        binding.parentListView.setAdapter(verticalAdapter);
    }

    private VerticalAdapter setVerticalAdapter() {
        return new VerticalAdapter(parentModelArrayList);
    }
}