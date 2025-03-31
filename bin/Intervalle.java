package com.service.util.date;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import com.exception.model.ValeurInvalideException;

public class Intervalle {
    
    Date dateDebut ;
    Date dateFin ;


    public Date getDateDebut() {
        return this.dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return this.dateFin;
    }

    public void setDateFin(Date dateFin) {
        if(dateFin==null) this.dateFin = getDernierDuMois(dateDebut);
        this.dateFin = dateFin;
    }

    public void setDateDebut(String date) throws ValeurInvalideException{
        try {
            Date d = Date.valueOf(date);
            setDateDebut(d);
        } catch (Exception e) {
           throw new ValeurInvalideException("Valeur de date début invalide");
        }
    }

    public void setDateFin(String date) throws ValeurInvalideException{
        try {
            Date d = Date.valueOf(date);
            setDateFin(d);
        } catch (Exception e) {
           this.dateFin = getDernierDuMois(getDateDebut());
        }
    }

    public Intervalle(){}

    public Intervalle(String dateDebut, String dateFin) throws ValeurInvalideException{
        setDateDebut(dateDebut);
        setDateFin(dateFin);
    }

    public Intervalle(Date dateDebut, Date dateFin) throws ValeurInvalideException{
        setDateDebut(dateDebut);
        setDateFin(dateFin);
    }

    public static Date getDernierDuMois(Date sqlDate) {
        if (sqlDate == null) {
            throw new IllegalArgumentException("La date ne peut pas être nulle");
        }

        // Convertit java.sql.Date en java.time.LocalDate
        LocalDate localDate = sqlDate.toLocalDate();

        // Trouve le dernier jour du mois
        LocalDate lastDayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth());

        // Convertit LocalDate en java.sql.Date
        return Date.valueOf(lastDayOfMonth);
    }

    public static Date getPremieruMois(int year, int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Le mois doit être compris entre 1 et 12");
        }

        // Crée une date représentant le premier jour du mois donné
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);

        // Convertit LocalDate en java.sql.Date
        return Date.valueOf(firstDayOfMonth);
    }

    public static int extraireMois(Date sqlDate) {
        if (sqlDate == null) {
            throw new IllegalArgumentException("La date ne peut pas être nulle");
        }

        // Convertit java.sql.Date en java.time.LocalDate
        LocalDate localDate = sqlDate.toLocalDate();

        // Extrait le mois (1 = janvier, 12 = décembre)
        return localDate.getMonthValue();
    }

    public static int extraireAnnee(Date sqlDate) {
        if (sqlDate == null) {
            throw new IllegalArgumentException("La date ne peut pas être nulle");
        }

        // Convertit java.sql.Date en java.time.LocalDate
        LocalDate localDate = sqlDate.toLocalDate();

        // Extrait l'année
        return localDate.getYear();
    }

    // public Intervalle[] getIntervalleAssocie(){
    //     return null ;
    // }

   
    
    public Intervalle[] getIntervalleAssocie() throws Exception{

        // System.out.println("Date début args "+getDateDebut()+" Date fin est "+getDateFin());
        List<Intervalle> intervalles = new ArrayList<>();

        // Convertir les dates en LocalDate pour faciliter la manipulation
        LocalDate debut = dateDebut.toLocalDate();
        LocalDate fin = dateFin.toLocalDate();

        // Boucler sur chaque mois entre dateDebut et dateFin
        LocalDate current = debut.withDayOfMonth(1); // Commence au début du mois de dateDebut
        while (!current.isAfter(fin)) { // Continue jusqu'à la fin du mois de dateFin
            LocalDate startOfMonth = current.withDayOfMonth(1);
            LocalDate endOfMonth = current.with(TemporalAdjusters.lastDayOfMonth());


            // Ajouter l'intervalle au résultat
            intervalles.add(new Intervalle(startOfMonth.toString(), endOfMonth.toString()));
            // System.out.println("Date début ici est "+startOfMonth.toString()+" Date fin est "+endOfMonth.toString());


            // Passer au mois suivant
            current = current.plusMonths(1);
        }
        intervalles.get(0).setDateDebut(getDateDebut());
        intervalles.get(intervalles.size()-1).setDateFin(getDateFin());
        return intervalles.toArray(new Intervalle[0]);
        
    }
    
    
    

}
