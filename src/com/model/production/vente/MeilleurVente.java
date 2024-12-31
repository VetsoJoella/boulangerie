package com.model.production.vente;

import java.sql.Connection;
import java.sql.Date;

public class MeilleurVente {
    
    Date dateDebut ; 
    Date dateFin ; 
    Vente[] ventes ; 

    // Getter et setter

    public Date getDateDebut() {
        return this.dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        if(dateDebut!=null) {
            try {
                Date debut = Date.valueOf(dateDebut);
                setDateDebut(debut);
            } catch(Exception err) {
                this.dateDebut = null;
            }
        }
    }

    public Date getDateFin() {
        return this.dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public void setDateFin(String dateFin) {
        if(dateFin!=null) {
            try {
                Date debut = Date.valueOf(dateFin);
                setDateFin(debut);
            } catch(Exception err) {
                this.dateFin = null;
            }
        }
    }

    public Vente[] getVentes() {
        return this.ventes;
    }

    public void setVentes(Vente[] ventes) {
        this.ventes = ventes;
    }

    public void setVentes(Connection connection) throws Exception {

        setVentes(Vente.getMeilleurVente(connection, getDateDebut(), getDateFin()));
    }

    
}
