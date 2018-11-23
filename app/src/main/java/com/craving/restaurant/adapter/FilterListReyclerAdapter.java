package com.craving.restaurant.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.craving.restaurant.BaseActivity;
import com.craving.restaurant.R;
import com.craving.restaurant.interfaces.OnClickItem;

import java.util.List;

public class FilterListReyclerAdapter extends RecyclerView.Adapter<FilterListReyclerAdapter.ViewHolder>{

    private BaseActivity mContext;
    private OnClickItem mInterface;
   private  List<String > dataList;


    public FilterListReyclerAdapter(BaseActivity con, List<String> mainCat, OnClickItem _interface) {
        if(con!=null) {
            mContext = con;
            mInterface=_interface;
            dataList=mainCat;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_filter_items, parent, false);
        ViewHolder vh = new ViewHolder(v);


        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvItem.setText(dataList.get(position));
        holder.tvItem.setTag(position);
        holder.tvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mInterface.onListItemClick(view.getId(),position);
            }
        });

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvItem;

        public ViewHolder(View view) {
            super(view);

            tvItem = (TextView) view.findViewById(R.id.tvItem);


        }
    }
}

