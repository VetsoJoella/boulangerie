package com.model.production.vente.vendeur;

import com.model.generalise.base.BaseNom;

public class Genre extends BaseNom{

    @Override
    protected String getTable() {
        return "Genre";
    }

    public Genre() {
        super();
    }

    public Genre(String id) {
        super(id);
    }

    public Genre(String id, String nom) {
        super(id, nom);
    }
}