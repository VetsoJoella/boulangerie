package com.model.produit.type;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Type {

    String id ;
    String nom ; 

    // Getter et setter
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Type() {}

    public Type(String id) {
        setId(id);
    }

    public Type(String id, String nom ) {
        setNom(nom);
        setId(id);
    }

    @Override
    public String toString() {
        return "{" +
            " nom='" + getNom() + "'" +
            ", id='" + getId() + "'" +
            "}";
    }


    // Opération dans la base de données : 

    public void insert(Connection connection) throws Exception {
        String sql = "INSERT INTO type  (nom) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, getNom());
            pstmt.executeUpdate();
        }
    }

    public static Type[] getAll(Connection connection) throws Exception {
        List<Type> types = new ArrayList<>();
        String sql = "SELECT id, nom FROM type";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String id = rs.getString("id");
                String nom = rs.getString("nom");
                types.add(new Type(id, nom));
            }
        }
        return types.toArray(new Type[0]);
    }

    public void delete(Connection connection) throws Exception {
        
        String sql = "DELETE FROM type WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, getId());
            pstmt.executeUpdate();
        }
    }

    public static Type getById(Connection connection, String id) throws Exception {
       
        String sql = "SELECT id, nom FROM type WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String nom = rs.getString("nom");
                    return new Type(id, nom);
                }
            }
        }
        return null; // Retourne null si aucune unité n'est trouvée
    }
    
}

