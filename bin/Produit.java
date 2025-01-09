package com.model.produit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.exception.model.ValeurInvalideException;
import com.model.produit.type.Type;

public class Produit {

    private String id;
    private double prixVente;
    private String nom;
    private Type type ;

    

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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

    public double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(double prixVente) throws ValeurInvalideException{
        if(prixVente<0) throw new ValeurInvalideException("Valeur de prix de vente ne peut pas etre négative");
        else this.prixVente = prixVente;
    }

    public void setPrixVente(String prixVente) throws ValeurInvalideException{
        try {
            double prix = Double.valueOf(prixVente) ;
            setPrixVente(prix);
        } catch(Exception err) {
            throw new ValeurInvalideException("Valeur de prix de vente non numérique");
        }
       
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Constructeurs
    public Produit() {}

    public Produit(String id) {
        setId(id);
    }

    public Produit(String nom, double prixVente) throws ValeurInvalideException {
        setPrixVente(prixVente);
        setNom(nom);
    }

    public Produit(String id, String nom, double prixVente) throws ValeurInvalideException {
        setId(id);
        setPrixVente(prixVente);
        setNom(nom);
    }

    public Produit(String id, String nom, String prixVente) throws ValeurInvalideException {
        setId(id);
        setPrixVente(prixVente);
        setNom(nom);
    }  

    @Override
    public String toString() {
        return "Produit{" +
               "id='" + id + '\'' +
               ", prixVente=" + prixVente +
               ", nom='" + nom + '\'' +
               '}';
    }

    // Méthode pour insérer un produit
    public void insert(Connection connection) throws Exception {
        connection.setAutoCommit(false);

        String sql = "INSERT INTO produit (id, d_prixVente, nom, idType) VALUES (DEFAULT, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setDouble(1, getPrixVente());
            pstmt.setString(2, getNom());
            pstmt.setString(3, getType().getId());
            pstmt.executeUpdate();


            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    setId(generatedKeys.getString(1)); // Récupération de l'ID généré
                    HistoriqueProduit historiqueProduit = new HistoriqueProduit(this, null, getPrixVente());
                    historiqueProduit.insert(connection);
                } else throw new Exception("Aucune ligne de donnée inséree ");
            }
            connection.commit();
        } catch(Exception err) {
            connection.rollback();
            throw err ;

        }
    }

    // Méthode pour récupérer tous les produits
    public static Produit[] getAll(Connection connection) throws Exception {
        
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT id, d_prixVente, nom, idType FROM produit";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String id = rs.getString("id");
                double prixVente = rs.getDouble("d_prixVente");
                String nom = rs.getString("nom");
                Produit produit = new Produit(id, nom, prixVente) ;
                produit.setType(Type.getById(connection, rs.getString("idType")));
                produits.add(produit);
            }
        }
        return produits.toArray(new Produit[0]);
    }

    static Produit[] getByCriteria(Connection connection, Type type) throws Exception {
        
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT id, d_prixVente, nom, idType FROM produit where 1 = 1 ";
        if(type!=null) sql+= "and idType = ? ";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            if(type!=null) stmt.setString(1, type.getId());
            System.out.println("La requete est "+sql);
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    double prixVente = rs.getDouble("d_prixVente");
                    String nom = rs.getString("nom");
                    Produit produit = new Produit(id, nom, prixVente) ;
                    produit.setType(Type.getById(connection, rs.getString("idType")));
                    produits.add(produit);
                }
            }
        }
        return produits.toArray(new Produit[0]);
    }

    public static Produit[] getByCriteria(Connection connection, String idType) throws Exception{
        Type type = null ;
        if(idType!=null) type = new Type(idType);
        return getByCriteria(connection, type);
    }

    public static Produit getById(Connection connection, String id) throws Exception {

        String sql = "SELECT id, d_prixVente, nom FROM produit WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    double prixVente = rs.getDouble("d_prixVente");
                    String nom = rs.getString("nom");
                    Produit produit = new Produit(id, nom, prixVente) ;
                    produit.setType(Type.getById(connection, rs.getString("idType")));
                    return produit ;
                }
            }
        }
        return null; 
    }

    public void update(Connection connection) throws Exception {
        connection.setAutoCommit(false);
        String sql = "UPDATE produit SET nom = ?, d_prixVente = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, getNom());
            pstmt.setDouble(2, getPrixVente());
            pstmt.setString(3, getId());
            pstmt.executeUpdate();

            HistoriqueProduit historiqueProduit = new HistoriqueProduit(this, null, getPrixVente());
            historiqueProduit.insert(connection);
            connection.commit();

        } catch(Exception err) {
            connection.rollback();
            throw err ;
        }
    }

    public void delete(Connection connection) throws Exception {
        String sql = "DELETE FROM produit WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, getId());
            pstmt.executeUpdate();
        }
    }
}
