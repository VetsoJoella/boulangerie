package com.model.produit.base;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.exception.model.ValeurInvalideException;
import com.model.produit.variete.Variete;

public class ProduitBase {

    private String id;
    private String nom;
    private Variete variete ;    
    

    public Variete getVariete() {
        return variete;
    }

    public void setVariete(Variete variete) {
        this.variete = variete;
    }

    public void setVariete(String idVvariete) {
       setVariete(new Variete(idVvariete));
    }

    // Getters et setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(id!=null) {
            this.id = id;
        }
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Constructeurs
    public ProduitBase() {}

    public ProduitBase(String id) {
        setId(id);
    }

    public ProduitBase(String id, String nom) throws ValeurInvalideException {
        setId(id);
        setNom(nom);
    }


    @Override
    public String toString() {
        return "Produit{" +
               "id='" + id + '\'' +
               ", nom='" + nom + '\'' +
               '}';
    }

    // Méthode pour insérer un produit
    public void insert(Connection connection) throws Exception {
        connection.setAutoCommit(false);

        String sql = "INSERT INTO produitBase (id, nom, idVariete) VALUES (DEFAULT, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, getNom());
            pstmt.setString(2, getVariete().getId());
            pstmt.executeUpdate();


            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    setId(generatedKeys.getString(1)); // Récupération de l'ID généré
                    // HistoriqueProduit historiqueProduit = new HistoriqueProduit(this, null);
                    // historiqueProduit.insert(connection);
                } else throw new Exception("Aucune ligne de donnée inséree ");
            }
            connection.commit();
        } catch(Exception err) {
            connection.rollback();
            throw err ;

        }
    }

    // Méthode pour récupérer tous les produits
    public static ProduitBase[] getAll(Connection connection) throws Exception {
        
        List<ProduitBase> produits = new ArrayList<>();
        String sql = "SELECT * FROM produitBase";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String id = rs.getString("id");
                String nom = rs.getString("nom");
                ProduitBase produit = new ProduitBase(id, nom) ;
                produit.setVariete(new Variete().getById(connection, rs.getString("idVariete")));
                produits.add(produit);
            }
        }
        return produits.toArray(new ProduitBase[0]);
    }

    static ProduitBase[] getByCriteria(Connection connection, Variete variete) throws Exception {
        
        List<ProduitBase> produits = new ArrayList<>();
        String sql = "SELECT* from v_produitBase_variete where 1 = 1 ";
        if(variete!=null) sql+= "and idVariete = ? ";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            if(variete!=null) stmt.setString(1, variete.getId());
            // System.out.println("La requete est "+sql);
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    String nom = rs.getString("nom");
                    ProduitBase produit = new ProduitBase(id, nom) ;
                    produit.setVariete(new Variete(rs.getString("idVariete"), rs.getString("nomVariete")));
                    produits.add(produit);
                }
            }
        }
        return produits.toArray(new ProduitBase[0]);
    }

    public static ProduitBase[] getByCriteria(Connection connection, String idVariete) throws Exception{
        Variete variete = null ;
        if(idVariete!=null) variete = new Variete(idVariete);
        return getByCriteria(connection, variete);
    }

    public static ProduitBase getById(Connection connection, String id) throws Exception {

        String sql = "SELECT* FROM v_produitBase_variete WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String nom = rs.getString("nom");
                    ProduitBase produit = new ProduitBase(id, nom) ;
                    produit.setVariete(new Variete(rs.getString("idVariete"), rs.getString("nomVariete")));
                    return produit ;
                }
            }
        }
        return null; 
    }

    public void update(Connection connection) throws Exception {
        connection.setAutoCommit(false);
        String sql = "UPDATE produitBase SET nom = ?, d_prixVente = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, getNom());
            pstmt.setString(2, getId());
            pstmt.executeUpdate();

            connection.commit();

        } catch(Exception err) {
            connection.rollback();
            throw err ;
        }
    }

    public void delete(Connection connection) throws Exception {
        String sql = "DELETE FROM produitBase WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, getId());
            pstmt.executeUpdate();
        }
    }
}
