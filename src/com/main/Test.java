package com.main;

import java.sql.Connection;
import com.model.ingredient.Ingredient;
import com.model.production.fabrication.Fabrication;
import com.service.connection.UtilDb;

public class Test {
    
    public static void main(String[] args) {
     
        Ingredient ingredient = new Ingredient("ING00004");
        try {

            UtilDb utilDb = new UtilDb("boulangerie", "postgres", "postgres");
            Connection connection = utilDb.getConnection() ; 
            //   public Fabrication(String id, String quantiteFabrication, String dateFabrication, String reste, String produit) throws ValeurInvalideException {
    
            Fabrication fabrication = new Fabrication(null, "2", "2024-12-31", "0", "PRD00001");
            fabrication.insert(connection);
            // AchatIngredient[] achatIngredients= AchatIngredient.getByCriteria(connection, null, Date.valueOf("2024-12-02"), Date.valueOf("2024-12-21"));
            // for (AchatIngredient achatIngredient : achatIngredients) {
            //     System.out.println(achatIngredient.toString());
            // }
            connection.close();

        } catch(Exception err) {
            // System.err.println(err);
            err.printStackTrace();
        }

    }
}
