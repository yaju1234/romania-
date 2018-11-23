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

public class OrderHistoryReyclerAdapter extends RecyclerView.Adapter<OrderHistoryReyclerAdapter.ViewHolder>{

    private BaseActivity mContext;
    private OnClickItem mInterface;
   private  List<String > dataList;
   private  int lastCheckedPosition=0;


    public OrderHistoryReyclerAdapter(BaseActivity con, List<String> mainCat, OnClickItem _interface) {
        if(con!=null) {
            mContext = con;
            mInterface=_interface;
            dataList=mainCat;
        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order_history_items, parent, false);
        ViewHolder vh = new ViewHolder(v);


        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


      /*  if (position==lastCheckedPosition){
            holder.tvCatMain.setBackgroundResource(R.drawable.rounded_corner_txt_bg_selected);
            holder.tvCatMain.setTextColor(Color.parseColor("#55D172"));
        }else {
            holder.tvCatMain.setBackgroundResource(R.drawable.rounded_corner_txt_bg_not_selected);
            holder.tvCatMain.setTextColor(Color.parseColor("#505050"));
        }
        holder.tvCatMain.setTag(position);
        holder.tvCatMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onListItemClick(view.getId(),position);
                lastCheckedPosition=position;
            }
        });
*/
//        try {
//            holder.imageView.setImageBitmap(workoutList.get(position).getThumbbitmap());
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
        //ImageLoader.getInstance().displayImage(Constants.WORKOUTS_THUMB+dataList.get(position).getVideoThumbnail(), holder.imageView, BaseActivity.cacheOptions);

       // Glide.with(mContext).load(Constants.WORKOUTS_THUMB+dataList.get(position).getVideoThumbnail()).into(holder.imageView);

      //  mContext.new ViewLoad(holder.imageView).execute(dataList.get(position).getVideoFullPath());

      /*  holder.rvMain.setTag(position);
        holder.rvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mInterface.onListItemClick(view.getId(),position);
            }
        });*/

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout ll_bottom;
        public RelativeLayout rvMain;
        public TextView tvCatMain;
       // public RoundedImageView imageView;
        // public TextView tv_discussion;

        public ViewHolder(View view) {
            super(view);

            tvCatMain = (TextView) view.findViewById(R.id.tvCatMain);
           /* rvMain = (RelativeLayout) view.findViewById(R.id.rvMain);
            nameTextView = view.findViewById(R.id.nameTextView);
            imageView = view.findViewById(R.id.imageView);
*/

        }
    }
}

