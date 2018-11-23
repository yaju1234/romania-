package com.craving.restaurant.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.craving.restaurant.R;
import com.craving.restaurant.model.Produse;
import com.google.gson.Gson;

public class ProductdetailsFragment extends BaseFragment {
    private RecyclerView rvAddress,rvBankDetails;
    private TextView tvCod,tvDescriere,tvCant,tvTipul,tvNorma,tvPartea,tvDimensiune,tvCuloare,tvDepozit,tvDataInventar;
private  Produse detailsRresult;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_details, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments()!=null){
            String details = getArguments().getString("details");
            Gson gson = new Gson();
             detailsRresult = gson.fromJson(details, Produse.class);
        }

        tvCod=view.findViewById(R.id.tvCod);
        tvDescriere=view.findViewById(R.id.tvDescriere);
        tvCant=view.findViewById(R.id.tvCant);
        tvTipul=view.findViewById(R.id.tvTipul);
        tvNorma=view.findViewById(R.id.tvNorma);
        tvPartea=view.findViewById(R.id.tvPartea);
        tvDimensiune=view.findViewById(R.id.tvDimensiune);
        tvCuloare=view.findViewById(R.id.tvCuloare);
        tvDepozit=view.findViewById(R.id.tvDepozit);
        tvDataInventar=view.findViewById(R.id.tvDataInventar);
        if (detailsRresult.getCod()!=null)
            tvCod.setText(detailsRresult.getCod());
        if (detailsRresult.getDenumire()!=null)
            tvDescriere.setText(detailsRresult.getDenumire());
        if (detailsRresult.getCant()!=null)
            tvCant.setText(detailsRresult.getCant());
        if (detailsRresult.getTipul()!=null)
            tvTipul.setText(detailsRresult.getTipul());
        if (detailsRresult.getNorma()!=null)
            tvNorma.setText(detailsRresult.getNorma());
        if (detailsRresult.getPartea()!=null)
            tvPartea.setText(detailsRresult.getPartea());
        if (detailsRresult.getDim()!=null)
            tvDimensiune.setText(detailsRresult.getDim());
        if (detailsRresult.getColor()!=null)
            tvCuloare.setText(detailsRresult.getColor());
      if (detailsRresult.getDepozit()!=null)
          tvDepozit.setText(detailsRresult.getDepozit());
      if (detailsRresult.getDatainventar()!=null)
          tvDataInventar.setText(detailsRresult.getDatainventar());


    }
}
