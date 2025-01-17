package com.model.client;

import com.model.generalise.base.BaseNom;

public class Client extends BaseNom{

    @Override
    protected String getTable() {
        return "client";
    }

    public Client() {
        super();
    }

    public Client(String id) {
        super(id);
    }

    public Client(String id, String nom) {
        super(id, nom);
    }
}
