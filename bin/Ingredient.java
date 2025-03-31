package com.model.ingredient;

import com.exception.DonneesManquantesException;
import com.model.ingredient.unite.Unite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Ingredient {

    private String id;
    private String nom;
    private Unite unite; 

    // Getters et setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public void setUnite(String idUnite) {
        setUnite(new Unite(idUnite));
    }

    public Ingredient() {}

    public Ingredient(String id) {
        this.id = id;
    }

    public Ingredient(String id, String nom, Unite unite) {
        setId(id);
        setNom(nom);
        setUnite(unite);
    }

    public Ingredient(String id, String nom, String idUnite) {
        setId(id);
        setNom(nom);
        setUnite(idUnite);
    }


    @Override
    public String toString() {
        return "Ingredient{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", unite=" + unite +
                '}';
    }

    // Méthode pour insérer un ingrédient
    public void insert(Connection connection) throws Exception {

        if(getUnite()==null || getUnite().getId()==null || getUnite().getId().isEmpty()) { 
            throw new DonneesManquantesException("Valeur de unité manquante ", this);
        }
        String sql = "INSERT INTO ingredient (id, nom, idUnite) VALUES (DEFAULT, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, getNom());
            pstmt.setString(2, getUnite().getId()); // Utilisation de l'ID de l'objet Unite
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.id = generatedKeys.getString(1); // Récupération de l'ID généré
                }
            }
        }
    }

    // Méthode pour récupérer tous les ingrédients
    public static Ingredient[] getAll(Connection connection) throws Exception {

        List<Ingredient> ingredients = new ArrayList<>();
        String sql = "SELECT i.id, i.nom, u.id AS unite_id, u.nom AS unite_nom FROM ingredient i JOIN unite u ON i.idUnite = u.id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String id = rs.getString("id");
                String nom = rs.getString("nom");
                String uniteId = rs.getString("unite_id");
                String uniteNom = rs.getString("unite_nom");

                Unite unite = new Unite(uniteId, uniteNom);
                ingredients.add(new Ingredient(id, nom, unite));
            }
        }
        return ingredients.toArray(new Ingredient[0]);
    }

    // Méthode pour récupérer un ingrédient par ID
    public static Ingredient getById(Connection connection, String id) throws Exception {

        if(id==null || id.isEmpty()) throw new DonneesManquantesException("Valeur de id manquante");
        String sql = "SELECT i.id, i.nom, u.id AS unite_id, u.nom AS unite_nom FROM ingredient i JOIN unite u ON i.idUnite = u.id WHERE i.id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String nom = rs.getString("nom");
                    String uniteId = rs.getString("unite_id");
                    String uniteNom = rs.getString("unite_nom");

                    Unite unite = new Unite(uniteId, uniteNom);
                    return new Ingredient(id, nom, unite);
                }
            }
        }
        return null;
    }

    // Méthode pour supprimer un ingrédient par ID
    public void delete(Connection connection) throws Exception {

        String sql = "DELETE FROM ingredient WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, getId());
            pstmt.executeUpdate();
        }
    }
}
