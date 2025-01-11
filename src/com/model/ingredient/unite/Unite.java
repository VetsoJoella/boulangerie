package com.model.ingredient.unite;

import com.model.generalise.base.BaseNom;

public class Unite extends BaseNom{

    @Override
    protected String getTable() {
        return "unite";
    }

    public Unite() {
        super();
    }

    public Unite(String id) {
        super(id);
    }

    public Unite(String id, String nom) {
        super(id, nom);
    }
}
