package com.craving.restaurant.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.craving.restaurant.R;
import com.craving.restaurant.adapter.AllProductReyclerAdapter;
import com.craving.restaurant.interfaces.OnClickItem;

import java.util.ArrayList;

public class AllMenueFragment extends BaseFragment {
    private RecyclerView rvMenue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_menue, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMenue = view.findViewById(R.id.rvMenue);
        rvMenue.setLayoutManager(new LinearLayoutManager(baseActivity, LinearLayoutManager.VERTICAL, false));

    }
    @Override
    public void onClick(View view) {
        super.onClick(view);
    }
}
