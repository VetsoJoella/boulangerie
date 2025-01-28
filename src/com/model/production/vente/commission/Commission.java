package com.model.production.vente.commission;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.production.vente.vendeur.Genre;
import com.model.production.vente.vendeur.Vendeur;

public class Commission {
    
    String id ; 
    double commission ; 
    Vendeur vendeur ;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public double getCommission() {
        return commission;
    }
    public void setCommission(double commission) {
        this.commission = commission;
    }

    public Vendeur getVendeur() {
        return vendeur;
    }
    public void setVendeur(Vendeur vendeur) {
        this.vendeur = vendeur;
    } 

    public Commission(){}

    public Commission(Vendeur vendeur, double commission) {
        setCommission(commission);
        setVendeur(vendeur);
    }

    static Commission[] getByCriteria(Connection connection, Date dateMin, Date dateMax, Genre genre) throws Exception {

        List<Commission> commissions = new ArrayList<>();

        String sql = "select v.*, commission from vendeur v " +
                    "left join (select idVendeur, coalesce(sum(commission),0) as commission from vente v where 1 = 1  " ;
        if (dateMin!=null) sql+= " and dateVente >= ? ";
        else sql+= " and '1' = ? ";
        if (dateMax!=null) sql+= " and dateVente <= ? ";
        else sql+= " and '1' = ? ";
        sql+= " group by idVendeur) f on v.id = idVendeur where 1 = 1 " ;
        if (genre!=null) sql+= " and idGenre = ? ";


        // System.out.println("requete de getByCriteria de commission est "+sql);
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            if(dateMin!=null) pstmt.setDate(1, dateMin);
            else pstmt.setString(1, "1");
            if(dateMax!=null) pstmt.setDate(2, dateMax);
            else pstmt.setString(2, "1");
            if(genre!=null) pstmt.setString(3, genre.getId());

            // System.out.println("Requete de achat est "+sql);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                   
                    String id = "" ; 
                    Vendeur v = new Vendeur(rs.getString("id"), rs.getString("nom"));
                    double c = rs.getDouble("commission");
                    // Genre g = new Genre().getById(connection, rs.getString("idGenre"));
                    v.setGenre(genre);
                    Commission cms = new Commission(v, c) ;
                    cms.setId(id); 
                    commissions.add(cms);
                    // System.out.println(achatIngredient.toString());
                }
            }
        }
        return commissions.toArray(new Commission[0]); 
    }

    static Map<String, Commission[]> getByGenre(Connection connection, Date dateMin, Date dateMax) throws Exception {

        Genre[] genres = new Genre().getAll(connection) ;

        Map<String, Commission[]> maps = new HashMap<>();
        
        for (Genre genre : genres) {
            maps.put(genre.getNom(), getByCriteria(connection, dateMin, dateMax, genre)) ;
        }

        return maps ; 
    }

    public static Map<String, Commission[]> getByGenre(Connection connection, String dateMin, String dateMax) throws Exception {

        Date min = null , max = null ; 

        try {
            min = Date.valueOf(dateMin);
        } catch (Exception e) {
            // TODO: handle exception
        } try {
            max = Date.valueOf(dateMax);

        } catch (Exception e) {
            // TODO: handle exception
        }

        return getByGenre(connection, min, max) ; 
    }

    public static Commission[] getByCriteria(Connection connection, String dateMin, String dateMax, String genre) throws Exception {

        Date min = null, max = null ; 
        Genre genre2 = null ; 
        try {
            min = Date.valueOf(dateMin) ;
        } catch (Exception e) {
            // TODO: handle exception
        }
        try {
            max = Date.valueOf(dateMax) ;
        } catch (Exception e) {
            // TODO: handle exception
        }
        if(genre!=null && !genre.isEmpty()) genre2 = new Genre(genre);

        return getByCriteria(connection, min, max, genre2);

    }

    public static double sommeCommission(Commission[] commissions) {
        double somme = 0 ;
        for (Commission commission : commissions) {
            somme += commission.getCommission();
        }
        return somme ;
    }
}
