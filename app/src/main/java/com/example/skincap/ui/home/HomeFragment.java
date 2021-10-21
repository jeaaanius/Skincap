package com.example.skincap.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skincap.R;
import com.example.skincap.databinding.FragmentHomeBinding;
import com.example.skincap.ui.base.BaseFragment;
import com.example.skincap.ui.library.LibraryAdapter;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {

    private RecyclerView parentRecyclerView;
    private RecyclerView.Adapter ParentAdapter;
    ArrayList<ParentModel> parentModelArrayList = new ArrayList<>();
    private RecyclerView.LayoutManager parentLayoutManager;

    public HomeFragment() {super(R.layout.fragment_home);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        @NonNull FragmentHomeBinding binding = FragmentHomeBinding.bind(view);

        //set the Categories for each array list set in the `ParentViewHolder`
        parentModelArrayList.add(new ParentModel("Skin Types"));
        parentModelArrayList.add(new ParentModel("Skin Issues"));
        parentModelArrayList.add(new ParentModel("Skin Ingredients"));
        parentRecyclerView = parentRecyclerView.findViewById(R.id.Parent_recyclerView);
        parentRecyclerView.setHasFixedSize(true);
        //parentLayoutManager = new LinearLayoutManager(this);
        //ParentAdapter = new ParentRecyclerViewAdapter(parentModelArrayList, binding.);
        parentRecyclerView.setLayoutManager(parentLayoutManager);
        parentRecyclerView.setAdapter(ParentAdapter);
        ParentAdapter.notifyDataSetChanged();
    }
}