package com.craving.restaurant.fragments;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.craving.restaurant.MainActivity;
import com.craving.restaurant.R;
import com.craving.restaurant.adapter.AllProductReyclerAdapter;
import com.craving.restaurant.adapter.FilterListReyclerAdapter;
import com.craving.restaurant.constants.Constants;
import com.craving.restaurant.interfaces.OnClickItem;
import com.craving.restaurant.model.Produse;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DashboardFragment extends BaseFragment {


    private RecyclerView rlProductList;
    private View ivFilter;
    private AllProductReyclerAdapter adapter;

   // public static List<Produse> allProduct=new ArrayList<>();
    public static ArrayList<Produse> filteredProduct=new ArrayList<>();
    public static ArrayList<Produse> tempProduct=new ArrayList<>();

    private LinearLayout  llFilter,llBody;

    private  EditText etFilter;





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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rlProductList = view.findViewById(R.id.rlProductList);
        filteredProduct = (ArrayList<Produse>) Constants.allProduct.clone();
        tempProduct = (ArrayList<Produse>) filteredProduct.clone();

        ivFilter = view.findViewById(R.id.ivFilter);
        llFilter = view.findViewById(R.id.llFilter);
        llBody = view.findViewById(R.id.llBody);
        ivFilter.setOnClickListener(this);
        rlProductList.setLayoutManager(new LinearLayoutManager(baseActivity, LinearLayoutManager.VERTICAL, false));
        /*if (getArguments()!=null){
            if (getArguments().getString("search")!=null)
            {
                adapter =   new AllProductReyclerAdapter(baseActivity, Constants.filteredProduct, new OnClickItem() {
                    @Override
                    public void onListItemClick(Object o, int position) {
                        Gson gson = new Gson();
                        String result = gson.toJson(Constants.filteredProduct.get(position));
                        Bundle bundle=new Bundle();
                        bundle.putString("details",result);
                        ProductdetailsFragment fragment=new ProductdetailsFragment();
                        fragment.setArguments(bundle);
                        displayView(fragment);
                        rlProductList.setAdapter(adapter);
                    }
                });
            }

        }else{*/
            adapter=   new AllProductReyclerAdapter(baseActivity,  tempProduct, new OnClickItem() {
                @Override
                public void onListItemClick(Object o, int position) {
                    Gson gson = new Gson();
                    String result = gson.toJson( tempProduct.get(position));
                    Bundle bundle=new Bundle();
                    bundle.putString("details",result);
                    ProductdetailsFragment fragment=new ProductdetailsFragment();
                    fragment.setArguments(bundle);
                    displayView(fragment);
                }
            });
            rlProductList.setAdapter(adapter);

       // }
        etFilter = view.findViewById(R.id.etFilter);
        etFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
                if (adapter!=null){
                    if (s.toString().length()>0){
                        doSearchFilter();
                       /* for(int i=0; i<filteredProduct.size(); i++){

                        }*/
                        // (row.getCod()!=null&&row.getCod().toLowerCase().contains(charString.toLowerCase())) || (row.getDenumire()!=null&&row.getDenumire().toLowerCase().contains(charString.toLowerCase()))
                    }else{
                        tempProduct.clear();
                        tempProduct = (ArrayList<Produse>) Constants.allProduct.clone();
                        adapter=   new AllProductReyclerAdapter(baseActivity,  tempProduct, new OnClickItem() {
                            @Override
                            public void onListItemClick(Object o, int position) {
                                Gson gson = new Gson();
                                String result = gson.toJson( tempProduct.get(position));
                                Bundle bundle=new Bundle();
                                bundle.putString("details",result);
                                ProductdetailsFragment fragment=new ProductdetailsFragment();
                                fragment.setArguments(bundle);
                                displayView(fragment);
                            }
                        });
                        rlProductList.setAdapter(adapter);
                    }
                      //  adapter.getFilter().filter(s.toString());
                    //
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub

            }
        });




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
            case R.id.ivFilter:
                //displayView(new ProductFilterFragment());
                etFilter.setText("");
                getFilter(Constants.allProduct);
                viewFilter();
                break;
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
               /* Bundle b=  new Bundle();
                b.putString("search","search");
                DashboardFragment fragment=new DashboardFragment();
                fragment.setArguments(b);
                displayView(fragment);*/
                viewBody();
                break;

        }
    }

    public void viewBody(){
        llBody.setVisibility(View.VISIBLE);
        llFilter.setVisibility(View.INVISIBLE);
    }

    public void viewFilter(){
        llFilter.setVisibility(View.VISIBLE);
        llBody.setVisibility(View.INVISIBLE);
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
    public void doSearchFilter(){

        String stSearch = etFilter.getText().toString().trim();
        tempProduct.clear();

        if(stSearch.length()>0){
            for(int i=0; i<filteredProduct.size();i++){
                Produse row = filteredProduct.get(i);
                if(row!=null){
                    if((row.getCod()!=null&&row.getCod().toLowerCase().contains(stSearch.toLowerCase())) || (row.getDenumire()!=null&&row.getDenumire().toLowerCase().contains(stSearch.toLowerCase()))){
                        tempProduct.add(row);
                    }
                }
            }
        }
        System.out.println("!!!!!!!!!!!!!!!!!! ");

        adapter=   new AllProductReyclerAdapter(baseActivity,  tempProduct, new OnClickItem() {
            @Override
            public void onListItemClick(Object o, int position) {
                Gson gson = new Gson();
                String result = gson.toJson( tempProduct.get(position));
                Bundle bundle=new Bundle();
                bundle.putString("details",result);
                ProductdetailsFragment fragment=new ProductdetailsFragment();
                fragment.setArguments(bundle);
                displayView(fragment);
            }
        });
        rlProductList.setAdapter(adapter);



    }

    public void filter(){
        // tvGroup,tvType,tvColor,tvParte,tvDin,tvRule,tvDepozit
        String stGroup = tvGroup.getText().toString();
        String stType = tvType.getText().toString();
        String stParte = tvParte.getText().toString();
        String stDin = tvDin.getText().toString();
        String stRule = tvRule.getText().toString();
        String stDepozit = tvDepozit.getText().toString();



       // tempProduct.clear();
       // tempProduct = (ArrayList<Produse>) Constants.allProduct.clone();
        ArrayList<Produse> all = (ArrayList<Produse>) Constants.allProduct.clone();


        ArrayList<Produse> p1 = new ArrayList<>();
        if(!stGroup.toLowerCase().contains("select")){
            for (int i=0;i<all.size();i++) {
                if (all.get(i).getGrupa()!=null&&all.get(i).getGrupa().equals(stGroup))
                    p1.add(all.get(i));
            }
        }else{
            p1 = (ArrayList<Produse>) all.clone();
        }




        ArrayList<Produse> p2 = new ArrayList<>();
        if(!stType.toLowerCase().contains("select")){
            for (int i=0;i<p1.size();i++) {
                if (p1.get(i).getTipul()!=null&&p1.get(i).getTipul().equals(stType))
                    p2.add(p1.get(i));
            }
        }else{
            p2 = (ArrayList<Produse>) p1.clone();
        }


        ArrayList<Produse> p3 = new ArrayList<>();
        if(!stParte.toLowerCase().contains("select")){
            for (int i=0;i<p2.size();i++) {
                if (p2.get(i).getParte()!=null&&p2.get(i).getParte().equals(stParte))
                    p3.add(p2.get(i));
            }
        }else{
            p3 = (ArrayList<Produse>) p2.clone();
        }


        ArrayList<Produse> p4 = new ArrayList<>();
        if(!stDin.toLowerCase().contains("select")){
            for (int i=0;i<p3.size();i++) {
                if (p3.get(i).getDim()!=null&&p3.get(i).getDim().equals(stDin))
                    p4.add(p3.get(i));
            }
        }else{
            p4 = (ArrayList<Produse>) p3.clone();
        }


        ArrayList<Produse> p5 = new ArrayList<>();
        if(!stRule.toLowerCase().contains("select")){
            for (int i=0;i<p4.size();i++) {
                if (p4.get(i).getNorma()!=null&&p4.get(i).getNorma().equals(stRule))
                    p5.add(p4.get(i));
            }
        }else{
            p5 = (ArrayList<Produse>) p4.clone();
        }

        ArrayList<Produse> p6 = new ArrayList<>();
        if(!stDepozit.toLowerCase().contains("select")){
            for (int i=0;i<p5.size();i++) {
                if (p5.get(i).getDepozit()!=null&&p5.get(i).getDepozit().equals(stDepozit))
                   p6.add(p5.get(i));
            }
        }else{
            p6= (ArrayList<Produse>) p5.clone();
        }


        tempProduct.clear();
        tempProduct = (ArrayList<Produse>) p6.clone();

        adapter=   new AllProductReyclerAdapter(baseActivity,  tempProduct, new OnClickItem() {
            @Override
            public void onListItemClick(Object o, int position) {
                Gson gson = new Gson();
                String result = gson.toJson( tempProduct.get(position));
                Bundle bundle=new Bundle();
                bundle.putString("details",result);
                ProductdetailsFragment fragment=new ProductdetailsFragment();
                fragment.setArguments(bundle);
                displayView(fragment);
            }
        });
        rlProductList.setAdapter(adapter);

    }
}
