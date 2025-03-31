package com.model.generalise.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseNom {

    private String id;
    private String nom;

    // Méthode abstraite pour récupérer le nom de la table
    protected abstract String getTable();

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

    // Constructeurs
    public BaseNom() {}

    public BaseNom(String id) {
        setId(id);
    }

    public BaseNom(String id, String nom) {
        setId(id);
        setNom(nom);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nom='" + getNom() + "'" +
            "}";
    }

   
    // CRUD Operations

    // Insert
    public void insert(Connection connection) throws Exception {
        String sql = "INSERT INTO " + getTable() + " (nom) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, getNom());
            pstmt.executeUpdate();
        }
    }

    // Get All
    public <T extends BaseNom> T[] getAll(Connection connection) throws Exception {
        List<T> bases = new ArrayList<>();
        String sql = "SELECT id, nom FROM " + getTable();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String id = rs.getString("id");
                String nom = rs.getString("nom");
                Object base = getClass().getDeclaredConstructor(String.class, String.class).newInstance(id, nom);
                bases.add((T)base);
            }
        }
        @SuppressWarnings("unchecked")
        T[] array = (T[]) java.lang.reflect.Array.newInstance(getClass(), bases.size());
        return bases.toArray(array);
    }


    // Get By ID
    public <T extends BaseNom> T getById(Connection connection, String id) throws Exception {

        String sql = "SELECT id, nom FROM " + getTable() + " WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String nom = rs.getString("nom");
                    return (T)getClass().getDeclaredConstructor(String.class, String.class).newInstance(id, nom);
                }
            }
        }
        return null;
    }

    // Delete
    public void delete(Connection connection) throws Exception {
        String sql = "DELETE FROM " + getTable() + " WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, getId());
            pstmt.executeUpdate();
        }
    }
}