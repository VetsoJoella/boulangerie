package com.model.produit.saveur;

import com.model.generalise.base.BaseNom;

public class Saveur extends BaseNom{

    @Override
    protected String getTable() {
        return "saveur";
    }

    public Saveur() {}

    public Saveur(String id) {
        super(id);
    }

    public Saveur(String id, String nom ) {
      super(id, nom);
    }

    
}
