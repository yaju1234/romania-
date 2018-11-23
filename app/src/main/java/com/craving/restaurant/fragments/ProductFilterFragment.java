package com.craving.restaurant.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.craving.restaurant.MainActivity;
import com.craving.restaurant.R;
import com.craving.restaurant.adapter.FilterListReyclerAdapter;
import com.craving.restaurant.constants.Constants;
import com.craving.restaurant.interfaces.OnClickItem;
import com.craving.restaurant.model.Produse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductFilterFragment extends BaseFragment {
    private RecyclerView rvRecomended;
    private RecyclerView rv_last_search_cat;
    Set grupa = new HashSet();
    Set Tipul = new HashSet();
    Set depozit = new HashSet();
    Set norma = new HashSet();
    Set partea = new HashSet();
    Set dim = new HashSet();
    Set culoare = new HashSet();
    private TextView tvGroup,tvType,tvColor,tvParte,tvDin,tvRule,tvDepozit;
    private View tvFind;

    //Set setC = new LinkedHashSet();
    // Set setD = new TreeSet();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Constants.filteredProduct.clear();
        Constants.filteredProduct.addAll(Constants.allProduct);
        ((MainActivity)getActivity()).setPageTitle("Filter your Search");
        getFilter(Constants.allProduct);
        tvGroup=view.findViewById(R.id.tvGroup);
        tvType=view.findViewById(R.id.tvType);
        tvDepozit=view.findViewById(R.id.tvDepozit);
        tvRule=view.findViewById(R.id.tvRule);
        tvColor=view.findViewById(R.id.tvColor);
        tvParte=view.findViewById(R.id.tvParte);
        tvDin=view.findViewById(R.id.tvDin);
        tvFind=view.findViewById(R.id.tvFind);
        tvGroup.setOnClickListener(this);
        tvType.setOnClickListener(this);
        tvDepozit.setOnClickListener(this);
        tvRule.setOnClickListener(this);
        tvColor.setOnClickListener(this);
        tvParte.setOnClickListener(this);
        tvDin.setOnClickListener(this);
        tvFind.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.tvGroup:
                if (grupa!=null&&grupa.size()>0){
                    openFilterDialog(new ArrayList<String>(grupa),"grupa",tvGroup);
                }else
                    Toast.makeText(baseActivity,"Item not found",Toast.LENGTH_LONG).show();

                break;
            case R.id.tvType:
                if (Tipul!=null&&Tipul.size()>0){
                    openFilterDialog(new ArrayList<String>(Tipul),"tipul",tvType);
                }else
                    Toast.makeText(baseActivity,"Item not found",Toast.LENGTH_LONG).show();
                break;
            case R.id.tvDepozit:
                if (depozit!=null&&depozit.size()>0){
                    openFilterDialog(new ArrayList<String>(depozit),"depozit",tvDepozit);
                }else
                    Toast.makeText(baseActivity,"Item not found",Toast.LENGTH_LONG).show();
                break;
            case R.id.tvRule:
                if (norma!=null&&norma.size()>0){
                    openFilterDialog(new ArrayList<String>(norma),"norma",tvRule);
                }else
                    Toast.makeText(baseActivity,"Item not found",Toast.LENGTH_LONG).show();
                break;
            case R.id.tvColor:
                if (culoare!=null&&culoare.size()>0){
                    openFilterDialog(new ArrayList<String>(culoare),"color",tvColor);
                }else
                    Toast.makeText(baseActivity,"Item not found",Toast.LENGTH_LONG).show();
                break;
            case R.id.tvParte:
                if (partea!=null&&partea.size()>0){
                    openFilterDialog(new ArrayList<String>(partea),"parte",tvParte);
                }else
                    Toast.makeText(baseActivity,"Item not found",Toast.LENGTH_LONG).show();
                break;
            case R.id.tvDin:
                if (dim!=null&&dim.size()>0){
                    openFilterDialog(new ArrayList<String>(dim),"dim",tvDin);
                }else
                    Toast.makeText(baseActivity,"Item not found",Toast.LENGTH_LONG).show();
                break;

            case R.id.tvFind:
              Bundle b=  new Bundle();
              b.putString("search","search");
              DashboardFragment fragment=new DashboardFragment();
                fragment.setArguments(b);
               displayView(fragment);
                break;



        }
    }

    private void getFilter(List<Produse> allProduct){
        if (allProduct.size()>0){
            grupa.clear();
            Tipul.clear();
            depozit.clear();
            norma.clear();
            partea.clear();
            dim.clear();
            culoare.clear();

            for (int i=0;i<allProduct.size();i++){
                grupa.add(allProduct.get(i).getGrupa());
                Tipul.add(allProduct.get(i).getTipul());
                depozit.add(allProduct.get(i).getDepozit());
                norma.add(allProduct.get(i).getNorma());
                partea.add(allProduct.get(i).getPartea());
                dim.add(allProduct.get(i).getDim());
                culoare.add(allProduct.get(i).getColor());
            }

        }
    }

    private void openFilterDialog(final ArrayList<String> list, final String filterText, final TextView tv){
        final Dialog dialog=new Dialog(baseActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_filter);
        RecyclerView rvList= (RecyclerView)dialog.findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(baseActivity, LinearLayoutManager.VERTICAL, false));
        rvList.setAdapter(new FilterListReyclerAdapter(baseActivity, list, new OnClickItem() {
            @Override
            public void onListItemClick(Object o, int position) {
                tv.setText(list.get(position));
                dialog.dismiss();
                resetList(filterText,list.get(position));
            }
        }));
        dialog.show();

    }

    private void resetList(String filterText,String value) {
        List<Produse> allProduct=new ArrayList<>();
        if (Constants.filteredProduct.size()>0){
                switch (filterText){
                    case "grupa":
                        for (int i=0;i<Constants.filteredProduct.size();i++) {
                            if (Constants.filteredProduct.get(i).getGrupa()!=null&&Constants.filteredProduct.get(i).getGrupa().equals(value))
                             allProduct.add(Constants.filteredProduct.get(i));
                        }
                        Constants.filteredProduct.clear();
                        Constants.filteredProduct.addAll(allProduct);
                        getFilter(allProduct);
                        break;
                    case "tipul":
                        for (int i=0;i<Constants.filteredProduct.size();i++) {
                            if (Constants.filteredProduct.get(i).getTipul()!=null&&Constants.filteredProduct.get(i).getTipul().equals(value))
                                allProduct.add(Constants.filteredProduct.get(i));
                        }
                        Constants.filteredProduct.clear();
                        Constants.filteredProduct.addAll(allProduct);
                        getFilter(allProduct);
                        break;
                    case "depozit":
                        for (int i=0;i<Constants.filteredProduct.size();i++) {
                            if (Constants.filteredProduct.get(i).getDepozit()!=null&&Constants.filteredProduct.get(i).getDepozit().equals(value))
                                allProduct.add(Constants.filteredProduct.get(i));
                        }
                        Constants.filteredProduct.clear();
                        Constants.filteredProduct.addAll(allProduct);
                        getFilter(allProduct);
                        break;
                    case "norma":
                        for (int i=0;i<Constants.filteredProduct.size();i++) {
                            if (Constants.filteredProduct.get(i).getNorma()!=null&&Constants.filteredProduct.get(i).getNorma().equals(value))
                                allProduct.add(Constants.filteredProduct.get(i));
                        }
                        Constants.filteredProduct.clear();
                        Constants.filteredProduct.addAll(allProduct);
                        getFilter(allProduct);
                        break;
                    case "color":
                        for (int i=0;i<Constants.filteredProduct.size();i++) {
                            if (Constants.filteredProduct.get(i).getColor()!=null&&Constants.filteredProduct.get(i).getColor().equals(value))
                                allProduct.add(Constants.filteredProduct.get(i));
                        }
                        Constants.filteredProduct.clear();
                        Constants.filteredProduct.addAll(allProduct);
                        getFilter(allProduct);
                        break;
                    case "parte":
                        for (int i=0;i<Constants.filteredProduct.size();i++) {
                            if (Constants.filteredProduct.get(i).getParte()!=null&&Constants.filteredProduct.get(i).getParte().equals(value))
                                allProduct.add(Constants.filteredProduct.get(i));
                        }
                        Constants.filteredProduct.clear();
                        Constants.filteredProduct.addAll(allProduct);
                        getFilter(allProduct);
                        break;
                    case "dim":
                        for (int i=0;i<Constants.filteredProduct.size();i++) {
                            if (Constants.filteredProduct.get(i).getDim()!=null&&Constants.filteredProduct.get(i).getDim().equals(value))
                                allProduct.add(Constants.filteredProduct.get(i));
                        }
                        Constants.filteredProduct.clear();
                        Constants.filteredProduct.addAll(allProduct);
                        getFilter(allProduct);
                        break;
                }

        }


    }
}
