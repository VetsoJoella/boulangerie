package com.model.production.vente.vendeur;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.model.generalise.base.BaseNom;

public class Vendeur extends BaseNom{

    Genre genre ; 

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override 
    public Vendeur[] getAll(Connection connection) throws Exception {
        List<Vendeur> bases = new ArrayList<>();
        String sql = "SELECT * FROM v_vendeur_genre" ;
        try (Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String id = rs.getString("id");
                String nom = rs.getString("nom");
                Genre genre = new Genre(rs.getString("idgenre"), rs.getString("nomgenre"));

                Vendeur vendeur = new Vendeur(id, nom);
                vendeur.setGenre(genre) ;
                bases.add(vendeur);
            }
        }
        return bases.toArray(new Vendeur[0]);
    }

   
    
    
    @Override
    protected String getTable() {
        return "Vendeur";
    }

    public Vendeur() {
        super();
    }

    public Vendeur(String id) {
        super(id);
    }

    public Vendeur(String id, String nom) {
        super(id, nom);
    }
}
