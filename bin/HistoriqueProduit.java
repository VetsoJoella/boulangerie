package com.model.produit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import com.exception.DonneesManquantesException;
import com.model.produit.base.ProduitBase;
import com.model.produit.saveur.Saveur;

public class HistoriqueProduit {
    
    String id ;
    Produit produit ; 
    Date date ;
    double prix ;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Produit getProduit() {
        return this.produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        if (date == null)  this.date = new Date(System.currentTimeMillis());
        else  this.date = date;
        
    }

    public double getPrix() {
        return this.prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public HistoriqueProduit() {}

    public HistoriqueProduit(Produit produit, Date date, double prix) {
        setProduit(produit);
        setDate(date);
        setPrix(prix);
    }

    public HistoriqueProduit(String id, Produit produit, Date date, double prix) {
        setId(id);
        setProduit(produit);
        setDate(date);
        setPrix(prix);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", produit='" + getProduit() + "'" +
            ", date='" + getDate() + "'" +
            ", prix='" + getPrix() + "'" +
            "}";
    }

    public void insert(Connection connection) throws Exception {
        
        if(getProduit()==null || getProduit().getId().isEmpty()) throw new DonneesManquantesException("Valeur de produit manquant", this);
        
        String sql = "INSERT INTO historiqueprixproduit (id, idProduit, prixProduit, dateProduit) VALUES (DEFAULT, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, getProduit().getId());
            pstmt.setDouble(2, getPrix());
            pstmt.setDate(3, getDate());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.id = generatedKeys.getString(1); // Récupération de l'ID généré
                }
            }
        }
    }

    // Méthode pour récupérer tous les produits
    public static HistoriqueProduit[] getByCriteria(Connection connection, String idProduit) throws Exception {
        
        List<HistoriqueProduit> listeProduits = new ArrayList<>();
        String sql = "SELECT * FROM v_historique_produit_produitBase_detail v where 1=1 ";
        if(idProduit!=null) sql+= " and idProduit = ? ";
        sql+=" order by dateProduit desc";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            if(idProduit!=null) {
                pstmt.setString(1, idProduit);
            }
            System.out.println("La requete de select est "+sql+" Valeur de produit id est "+idProduit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    double prixVente = rs.getDouble("prixProduit");
                    Date date = rs.getDate("dateProduit");
                    Saveur saveur = new Saveur(rs.getString("idSaveur"), rs.getString("nomSaveur"));
                    ProduitBase produitBase = new ProduitBase(rs.getString("idProduitBase"), rs.getString("nomProduitBase"));
                    Produit produit = new Produit(rs.getString("idProduit"),produitBase, saveur);
                    listeProduits.add(new HistoriqueProduit(id, produit, date, prixVente));
                }
            }
        }
        return listeProduits.toArray(new HistoriqueProduit[0]);
    }

    
}
