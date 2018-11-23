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
import com.craving.restaurant.adapter.OrderHistoryReyclerAdapter;
import com.craving.restaurant.interfaces.OnClickItem;

import java.util.ArrayList;

public class OrderHistoryFragment extends BaseFragment {
    private RecyclerView rvOrderHistory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvOrderHistory = view.findViewById(R.id.rvOrderHistory);
        rvOrderHistory.setLayoutManager(new LinearLayoutManager(baseActivity, LinearLayoutManager.VERTICAL, false));
        rvOrderHistory.setAdapter(new OrderHistoryReyclerAdapter(baseActivity, new ArrayList<String>(), new OnClickItem() {
            @Override
            public void onListItemClick(Object o, int position) {

            }
        }));
    }
    @Override
    public void onClick(View view) {
        super.onClick(view);
    }
}
