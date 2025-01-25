package com.model.produit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.exception.model.ValeurInvalideException;
import com.model.produit.base.ProduitBase;
import com.model.produit.saveur.Saveur;
import com.model.produit.variete.Variete;

public class Produit {
    
    String id ; 
    ProduitBase produitBase ;
    Saveur saveur ;
    double prixVente ;

    // Getter et Setter
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public ProduitBase getProduitBase() {
        return produitBase;
    }

    public void setProduitBase(ProduitBase produitBase) {
        this.produitBase = produitBase;
    }

    public void setProduitBase(String idProduitBase) {
        setProduitBase(new ProduitBase(idProduitBase));
    }

    public Saveur getSaveur() {
        return saveur;
    }

    public void setSaveur(Saveur saveur) {
        this.saveur = saveur;
    } 

    public void setSaveur(String idSaveur) {
        setSaveur(new Saveur(idSaveur));
    } 

    public double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(double prix) throws ValeurInvalideException {
        if(prix<0) throw new ValeurInvalideException("Le prix ne peut pas etre négative");
        else this.prixVente = prix;
    }

    public void setPrixVente(String prix) throws ValeurInvalideException {
        try {
            double d = Double.valueOf(prix);
            setPrixVente(d);
        } catch (Exception e) {
            throw new ValeurInvalideException("Valeur de prix invalide");
        }
    }

    public Produit() {    }

    public Produit(String id) {
        setId(id);
    }

    public Produit(String id, ProduitBase produit, Saveur saveur) {
        setId(id);
        setProduitBase(produit);
        setSaveur(saveur);
    }

    public Produit(String id, ProduitBase produit, Saveur saveur, double prix) throws ValeurInvalideException{
        setId(id);
        setProduitBase(produit);
        setSaveur(saveur);
        setPrixVente(prix);
    }

    public Produit(String idProduitBase, String idSaveur, String prix) throws ValeurInvalideException{
        setProduitBase(idProduitBase);
        setSaveur(idSaveur);
        setPrixVente(prix);
    }

    // CRUD
    public static Produit[] getAll(Connection connection) throws Exception {
        
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * from v_produit_saveur_variete_detail ";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            // System.out.println("La requete est "+sql);
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    String id = rs.getString("id");
                    String idProduitBase = rs.getString("idProduitBase");
                    double prix = rs.getDouble("d_prixvente");

                    Variete variete = new Variete(rs.getString("idVariete"), rs.getString("nomVariete"));
                    Saveur saveur = new Saveur(rs.getString("idSaveur"), rs.getString("nomSaveur"));
                    
                    ProduitBase produitBase = ProduitBase.getById(connection, idProduitBase) ;

                    produitBase.setVariete(variete);

                    // produitBase.setSaveur(saveur);
                    produits.add(new Produit(id, produitBase, saveur, prix));
                }
            }
        }
        return produits.toArray(new Produit[0]);
    }

    public static Produit getById(Connection connection, String idProduit) throws Exception {
        
        String sql = "SELECT * from v_produit_saveur_variete_detail WHERE 1=1 ";
        if(idProduit!=null) sql+= " and id = ? ";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            if(idProduit!=null) stmt.setString(1, idProduit);
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    String id = rs.getString("id");
                    String idProduitBase = rs.getString("idProduitBase");
                    double prix = rs.getDouble("d_prixvente");

                    Variete variete = new Variete(rs.getString("idVariete"), rs.getString("nomVariete"));
                    Saveur saveur = new Saveur(rs.getString("idSaveur"), rs.getString("nomSaveur"));
                    
                    ProduitBase produitBase = ProduitBase.getById(connection, idProduitBase) ;

                    produitBase.setVariete(variete);

                    // produitBase.setSaveur(saveur);
                    return (new Produit(id, produitBase, saveur, prix));
                }
            }
        }
        return null ;
    }


    public void insert(Connection connection) throws Exception{

        String sql = "INSERT INTO produit(id, idProduitBase, idSaveur, d_prixVente) VALUES (DEFAULT, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, getProduitBase().getId());
            pstmt.setString(2, getSaveur().getId());
            pstmt.setDouble(3, getPrixVente());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    setId(generatedKeys.getString(1)); // Récupération de l'ID généré
                   enregistrerHistorique(connection);
                } else throw new Exception("Aucune ligne de donnée inséree ");
            }
        } 
    }

    public static Produit[] getByCriteria(Connection connection, String idProduitBase, String idSaveur, String idVariete) throws Exception {

        ProduitBase produitBase = null ;
        Saveur saveur = null ;
        Variete variete = null ; 
        if(idProduitBase!=null && !idProduitBase.isEmpty()) produitBase = new ProduitBase(idProduitBase);
        if(idSaveur!=null && !idSaveur.isEmpty()) saveur = new Saveur(idSaveur);
        if(idVariete!=null && !idVariete.isEmpty()) variete = new Variete(idVariete);
        return getByCriteria(connection, produitBase, saveur, variete);
    }

    static Produit[] getByCriteria(Connection connection, ProduitBase produitBase, Saveur saveur, Variete variete) throws Exception {
        
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * from v_produit_saveur WHERE 1=1 ";
        if(produitBase!=null) sql+= "and idProduitBase = ? ";
        else sql+="and '1'= ? ";
        if(saveur!=null) sql+= "and idSaveur = ? ";
        else sql+="and '1'= ? ";
        if(variete!=null) sql+= "and idVariete = ? ";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            // System.out.println("Get y criteria de produit "+sql);
            if(produitBase!=null) stmt.setString(1, produitBase.getId());
            else stmt.setString(1, "1");
            if(saveur!=null) stmt.setString(2, saveur.getId());
            else stmt.setString(2, "1");
            if(variete!=null) stmt.setString(3, variete.getId());

            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    String id = rs.getString("id");
                    String idProduitBase = rs.getString("idProduitBase");
                    double prix = rs.getDouble("d_prixvente");

                    Variete v = new Variete(rs.getString("idVariete"), rs.getString("nomVariete"));
                    Saveur s = new Saveur(rs.getString("idSaveur"), rs.getString("nomSaveur"));
                    
                    ProduitBase p = ProduitBase.getById(connection, idProduitBase) ;
                    p.setVariete(v);

                    // produitBase.setSaveur(saveur);
                    produits.add(new Produit(id, p, s, prix));
                }
            }
        }
        return produits.toArray(new Produit[0]);
    }

    public void update(Connection connection) throws Exception {
     
        connection.setAutoCommit(false);
        String sql = "UPDATE produit SET d_prixVente = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, getPrixVente());
            pstmt.setString(2, getId());
            pstmt.executeUpdate();

           enregistrerHistorique(connection);
            connection.commit();

        } catch(Exception err) {
            connection.rollback();
            throw err ;
        }
    }

    private void enregistrerHistorique(Connection connection) throws Exception{
        HistoriqueProduit historiqueProduit = new HistoriqueProduit(this, null, getPrixVente());
        historiqueProduit.insert(connection);
    }

}
