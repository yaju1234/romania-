package com.craving.restaurant.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.craving.restaurant.BaseActivity;
import com.craving.restaurant.R;
import com.craving.restaurant.interfaces.OnClickItem;
import com.craving.restaurant.model.Produse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AllProductReyclerAdapter extends RecyclerView.Adapter<AllProductReyclerAdapter.ViewHolder> implements Filterable {

    private BaseActivity mContext;
    private OnClickItem mInterface;
    private  List<Produse > dataList;
    private  int lastCheckedPosition=0;
    private List<Produse> contactListFiltered;


    public AllProductReyclerAdapter(BaseActivity con, List<Produse> mainCat, OnClickItem _interface) {
        if(con!=null) {
            mContext = con;
            mInterface=_interface;
            dataList=mainCat;
            contactListFiltered = mainCat;
        }
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category_items, parent, false);
        ViewHolder vh = new ViewHolder(v);


        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.tvCatMain.setText(""+dataList.get(position).getDenumire());
        holder.tvCode.setText(""+dataList.get(position).getCod());
        holder.tvDeposit.setText("Depozit :"+dataList.get(position).getDepozit());
        holder.tvCant.setText("Cant :"+dataList.get(position).getCant());
        holder.tvDate.setText(""+dataList.get(position).getDatainventar());
        if (dataList.get(position).getPicture()!=null)
            Picasso.get()
                    .load(dataList.get(position).getPicture())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(holder.ivItem);
        holder.linMain.setTag(position);
        holder.linMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mInterface.onListItemClick(view.getId(),position);
            }
        });

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = dataList;
                } else {
                    List<Produse> filteredList = new ArrayList<>();
                    for (Produse row : dataList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if ( (row.getCod()!=null&&row.getCod().toLowerCase().contains(charString.toLowerCase())) || (row.getDenumire()!=null&&row.getDenumire().toLowerCase().contains(charString.toLowerCase()))) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<Produse>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linMain;
        public ImageView ivItem;
        public TextView tvCatMain,tvCode,tvDeposit,tvCant,tvDate;
        // public RoundedImageView tvDeposit;
        // public TextView tv_discussion;

        public ViewHolder(View view) {
            super(view);

            tvCatMain = (TextView) view.findViewById(R.id.tvCatMain);
            ivItem = (ImageView) view.findViewById(R.id.ivItem);
            tvCode = view.findViewById(R.id.tvCode);
            tvDeposit = view.findViewById(R.id.tvDeposit);
            tvCant = view.findViewById(R.id.tvCant);
            tvDate = view.findViewById(R.id.tvDate);
            linMain = view.findViewById(R.id.linMain);


        }
    }
}

