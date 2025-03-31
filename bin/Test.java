package com.main;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import com.configuration.CommissionConfig;
import com.model.produit.Produit;
import com.service.connection.UtilDb;
import com.service.util.date.Intervalle;

// import java.sql.Connection;
// import com.model.ingredient.Ingredient;
// import com.model.production.fabrication.Fabrication;
// import com.service.connection.UtilDb;
// import com.model.produit.base.ProduitBase;
// import com.model.produit.recette.Recette;
// import com.model.produit.variete.Variete;

public class Test {
    
    public static void main(String[] args) {
     
        // Date debut = Date.valueOf("2025-01-14"), fin =Date.valueOf("2025-03-14");
        // try {
        //     Intervalle intervalle = new Intervalle(debut, fin);
        //     for (Intervalle i : intervalle.getIntervalleAssocie()) {
        //         System.out.println("Date début est "+intervalle.getDateDebut()+" Date fin est "+intervalle.getDateFin());

        //     }
        // } catch (Exception e) {
        //    e.printStackTrace();
        // }
       
         
        try{
            UtilDb utilDb = new UtilDb("boulangerie", "postgres", "postgres") ;
            try (Connection connection = utilDb.getConnection()) {
                Produit produit = Produit.getById(connection, "PRD00014", Date.valueOf("2025-01-12"));
                System.out.println(produit.getPrixVente());
            } catch (Exception e) {
                e.printStackTrace();
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // public static List<Intervalle> getIntervalleAssocie(Date dateDebut, Date dateFin) {
    //     List<Intervalle> intervalles = new ArrayList<>();

    //     // Convertir les dates en LocalDate pour faciliter la manipulation
    //     LocalDate debut = dateDebut.toLocalDate();
    //     LocalDate fin = dateFin.toLocalDate();

    //     // Boucler sur chaque mois entre dateDebut et dateFin
    //     LocalDate current = debut.withDayOfMonth(1); // Commence au début du mois de dateDebut
    //     while (!current.isAfter(fin)) { // Continue jusqu'à la fin du mois de dateFin
    //         LocalDate startOfMonth = current.withDayOfMonth(1);
    //         LocalDate endOfMonth = current.with(TemporalAdjusters.lastDayOfMonth());

    //         // Ajouter l'intervalle au résultat
    //         // intervalles.add(new Intervalle(Date.valueOf(startOfMonth), Date.valueOf(endOfMonth)));
    //         System.out.println("Date début est "+startOfMonth+" Date fin est "+endOfMonth);

    //         // Passer au mois suivant
    //         current = current.plusMonths(1);
    //     }

    //     return intervalles;
    // }



}


   

