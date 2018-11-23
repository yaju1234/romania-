package com.craving.restaurant.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductMain {
    @SerializedName("produse")
    @Expose
    private List<Produse> produse = null;

    public List<Produse> getProduse() {
        return produse;
    }

    public void setProduse(List<Produse> produse) {
        this.produse = produse;
    }
}
