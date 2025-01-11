package com.model.produit.recette;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.exception.DonneesManquantesException;
import com.exception.model.ValeurInvalideException;
import com.model.ingredient.Ingredient;
import com.model.produit.Produit;
import com.model.produit.base.ProduitBase;
import com.model.produit.variete.Variete;

public class Recette {
    
    String id ;
    Produit produit ; 
    Ingredient ingredient ; 
    double quantite ;

    public String getId() {
        return id;
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

    public void setProduit(String idProduit) {
        setProduit(new Produit(idProduit));
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public void setIngredient(String idIngredient) {
        setIngredient(new Ingredient(idIngredient));
    }

    public double getQuantite() {
        return this.quantite;
    }

    public void setQuantite(double quantite) throws ValeurInvalideException{
        if(quantite<0) throw new ValeurInvalideException("La valeur de quantité n'est pas valide") ;
        else this.quantite = quantite;
    }

    public void setQuantite(String quantite) throws ValeurInvalideException{
        try {
            double qtt = Double.valueOf(quantite);
            setQuantite(qtt);
        } catch(Exception err) {
            throw new ValeurInvalideException("Valeur de la quantité insérée n'est pas numérique");
        }
    }


    public Recette() {
    }

    public Recette(String id) {
        setId(id);
    }

    public Recette(Produit produit, Ingredient ingredient, double quantite) throws ValeurInvalideException{
        setProduit(produit);
        setIngredient(ingredient);
        setQuantite(quantite);
    }

    public Recette(String idProduit, String idIngredient, String quantite) throws ValeurInvalideException{
        setProduit(idProduit);
        setIngredient(idIngredient);
        setQuantite(quantite);
    }

    public Recette(String id, Produit produit, Ingredient ingredient, double quantite) throws ValeurInvalideException{
        setId(id);
        setProduit(produit);
        setIngredient(ingredient);
        setQuantite(quantite);
    }

    @Override
    public String toString() {
        return "{" +
            "id='" + getId() + "'" +
            ", produit =" + getProduit() + "" +
            ", ingredient =" + getIngredient() + 
            ", quantité = "+ getQuantite()+
            "}";
    }

    // Opération dans la base 

     public void insert(Connection connection) throws Exception {

        if(getProduit()==null || getIngredient()==null || getProduit().getId().isEmpty() || getIngredient().getId().isEmpty()) {
            throw new DonneesManquantesException("Valeur invalide : vérifiez vos données", this);
        }
        String sql = "INSERT INTO recette (id, idIngredient, idProduit, quantite) VALUES (DEFAULT, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, getIngredient().getId());
            pstmt.setString(2, getProduit().getId());
            pstmt.setDouble(3, getQuantite());
            pstmt.executeUpdate();
        }
    }

    public void delete(Connection connection) throws Exception {
        
        String sql = "DELETE FROM recette WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    public static Recette[] getByCriteriaString(Connection connection, String idProduit, String idProduitBase, String idIngredient, String idVariete) throws Exception {

        Produit produit = null ; 
        ProduitBase produitBase = null ;
        Ingredient ingredient = null ;
        Variete variete = null ;
        if(idProduit!=null) produit=new Produit(idProduit);
        if(idProduitBase!=null) produitBase=new ProduitBase(idProduitBase);
        if(idIngredient!=null) ingredient=new Ingredient(idIngredient);
        if(idVariete!=null) variete=new Variete(idVariete);
        return getByCriteria(connection, produit, produitBase, ingredient, variete);
    }
       

    public static Recette[] getByCriteria(Connection connection, Produit produit, ProduitBase produitBase, Ingredient ingredient, Variete variete) throws Exception {
       
        List<Recette> recettes = new ArrayList<>();

        String sql = "select * from  v_recette_produit where 1=1 " ;
        if(produit!=null && produit.getId()!=null && !produit.getId().isEmpty()) sql+= "and idProduit = ? ";
        else sql+= "and '1' = ? ";
        if(ingredient!=null && ingredient.getId()!=null && !ingredient.getId().isEmpty()) sql+= "and idIngredient = ? ";
        else sql+= "and '1' = ? ";
        if(produitBase!=null && produitBase.getId()!=null && !produitBase.getId().isEmpty()) sql+= "and idProduitBase = ? ";
        else sql+= "and '1' = ? ";
        if(variete!=null && variete.getId()!=null && !variete.getId().isEmpty()) sql+= "and idVariete = ? ";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            if(produit!=null && produit.getId()!=null && !produit.getId().isEmpty()) pstmt.setString(1, produit.getId());
            else pstmt.setString(1, "1");

            if(ingredient!=null && ingredient.getId()!=null && !ingredient.getId().isEmpty()) pstmt.setString(2, ingredient.getId());
            else pstmt.setString(2, "1");

            if(produitBase!=null && produitBase.getId()!=null && !produitBase.getId().isEmpty()) pstmt.setString(3, produitBase.getId());
            else pstmt.setString(3, "1");

            if(variete!=null && variete.getId()!=null && !variete.getId().isEmpty()) pstmt.setString(4, variete.getId());

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Produit p = Produit.getById(connection, rs.getString("idProduit")); 
                    Ingredient i = Ingredient.getById(connection,rs.getString("idIngredient"));
                        
                    Recette recette = new Recette(rs.getString("id"),p, i, rs.getDouble("quantite"));
                    recettes.add(recette);
                }
            }
        }
        return recettes.toArray(new Recette[0]); 
    }

}
