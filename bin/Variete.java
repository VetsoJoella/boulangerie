package com.model.produit.variete;


import com.model.generalise.base.BaseNom;

public class Variete extends BaseNom{

    @Override
    protected String getTable() {
        return "variete";
    }

    public Variete() {}

    public Variete(String id) {
        super(id);
    }

    public Variete(String id, String nom ) {
        super(id, nom);
    }

}

