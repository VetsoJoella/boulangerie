package com.model.production.vente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.model.caracteristique.Caracteristique;
import com.model.produit.Produit;
import com.model.produit.type.Type;

public class ProduitCaracteristique {
 
    String id ; 
    Produit produit ;
    Caracteristique caracteristique ;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Produit getProduit() {
        return produit;
    }
    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Caracteristique getCaracteristique() {
        return caracteristique;
    }
    public void setCaracteristique(Caracteristique caracteristique) {
        this.caracteristique = caracteristique;
    } 

    public ProduitCaracteristique(String id, Produit produit, Caracteristique caracteristique) {
        setId(id);
        setProduit(produit);
        setCaracteristique(caracteristique);
    }
    public static ProduitCaracteristique[] getAll(Connection connection) throws Exception {
        
        List<ProduitCaracteristique> produits = new ArrayList<>();
        String sql = "SELECT * from v_produit_caracteristique ";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            System.out.println("La requete est "+sql);
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    String idProduit = rs.getString("idProduit");
                    double prixVente = rs.getDouble("d_prixVente");
                    String nom = rs.getString("nom");
                    Produit produit = new Produit(idProduit, nom, prixVente) ;
                    Caracteristique caracteristique = new Caracteristique(rs.getString("idCaracteristique"), rs.getString("nomCaracteristique"));
                    produit.setCaracteristique(caracteristique);
                    produit.setType(Type.getById(connection, rs.getString("idType")));
                    produits.add(new ProduitCaracteristique(id, produit, caracteristique));
                }
            }
        }
        return produits.toArray(new ProduitCaracteristique[0]);
    }
    
}
