package com.model.production.conseil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.exception.DonneesManquantesException;
import com.exception.model.ValeurInvalideException;
import com.model.produit.Produit;
import com.service.util.date.Intervalle;

public class ConseilDuMois {
    
    String id ;
    Produit produit ; 
    Intervalle intervalle ;

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

    public Intervalle getIntervalle() {
        return this.intervalle;
    }

    public void setIntervalle(Intervalle intervalle) {
        this.intervalle = intervalle;
    }

    public void setIntervalle(String dateDebut, String dateFin) throws ValeurInvalideException{
        setIntervalle(new Intervalle(dateDebut, dateFin));
    }

    public void setIntervalle(Date dateDebut, Date dateFin) throws ValeurInvalideException{
        setIntervalle(new Intervalle(dateDebut, dateFin));
    }

    public ConseilDuMois(){}

    public ConseilDuMois(String idProduit, String dateDebut, String dateFin) throws ValeurInvalideException{
        setProduit(idProduit);
        setIntervalle(dateDebut, dateFin);
    }

    public ConseilDuMois(Produit produit, Date dateDebut, Date dateFin) throws ValeurInvalideException{
        setProduit(produit);
        setIntervalle(dateDebut, dateFin);
    }


    // Méthode dans la base de données

    private void insertLigne(Connection connection,Date dateDebut, Date dateFin) throws Exception {
        
        String sql = "INSERT INTO conseilDuMois (id, idProduit, dateDebut, dateFin) VALUES (DEFAULT, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, getProduit().getId());
            pstmt.setDate(2, dateDebut); // Utilisation de l'ID de l'objet Unite
            pstmt.setDate(3, dateFin); // Utilisation de l'ID de l'objet Unite
            pstmt.executeUpdate();

        }
    } 

    public void insert(Connection connection) throws Exception {

        if(getProduit()==null || getIntervalle()==null) { 
            throw new DonneesManquantesException("Valeur de unité manquante ", this);
        }

        connection.setAutoCommit(false);  

        try { 
            for (Intervalle i : getIntervalle().getIntervalleAssocie()) {
                System.out.println("Intervalle est "+i.getDateDebut()+" Date fin "+i.getDateFin());
                insertLigne(connection, i.getDateDebut(), i.getDateFin());    
            }
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
            throw e ;
        } finally{
            connection.setAutoCommit(true);
        }
        
    }

    public static ConseilDuMois[] getByCriteria(Connection connection, String annee, String mois) throws Exception {
        int a = 0 , m = 0 ;
        try {
            int annneStr = Integer.valueOf(annee);
                a=  annneStr ;
        } catch (Exception e) {
            // TODO: handle exception
        }
        try {
            int moisStr = Integer.valueOf(mois);
                m=  moisStr ;
                 } catch (Exception e) {
            // TODO: handle exception
        }
        return getByCriteria(connection, a, m);

    }
    public static ConseilDuMois[] getByCriteria(Connection connection, int annee, int mois) throws Exception{

        List<ConseilDuMois> conseils = new ArrayList<>();
        String sql = "SELECT * from conseilDuMois where 1=1 " ;
        if(mois!=0) sql+= " and EXTRACT(MONTH FROM dateDebut) = ? " ;
        else sql+= " and '1' = ? " ;
        if(annee!=0) sql+= " and EXTRACT(YEAR FROM dateDebut) = ? ";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            System.out.println("Requete est "+sql);
            if(mois!=0) stmt.setInt(1, mois);
            else stmt.setString(1, "1");
            if(annee!=0) stmt.setInt(2, annee);

            System.out.println("La requete est "+sql);
            try(ResultSet rs = stmt.executeQuery()) {
            
                while (rs.next()) {
                    String id = rs.getString("id");
                    String idProduit = rs.getString("idProduit");
                    Date dateD = rs.getDate("dateDebut") , dateF = rs.getDate("dateFin");
                

                ConseilDuMois conseilDuMois = new ConseilDuMois();
                conseilDuMois.setId(idProduit);
                conseilDuMois.setProduit(Produit.getById(connection, idProduit));
                conseilDuMois.setIntervalle(dateD, dateF);
                conseilDuMois.setId(id);

                conseils.add(conseilDuMois);
                }
            }
        }
        return conseils.toArray(new ConseilDuMois[0]);
    }
}
