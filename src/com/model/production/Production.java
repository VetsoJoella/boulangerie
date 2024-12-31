package com.model.production;

import com.exception.model.ValeurInvalideException;
import com.model.produit.Produit;

import java.sql.*;


public abstract class Production {

    private String id;
    private int quantite;
    private Date date;
    private Produit produit; 

    // Getters et setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) throws ValeurInvalideException{
        if(quantite<0) throw new ValeurInvalideException("Quantité de fabrication ne peut pa etre négative");
        else this.quantite = quantite;
    }

    public void setQuantite(String quantite) throws ValeurInvalideException{
        try {
            int qtt = Integer.valueOf(quantite);
            setQuantite(qtt);
        } catch(Exception err) {
            throw new ValeurInvalideException("La quantité insérée n'est pas une valeur numétique");
        }
      
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        if (date == null) this.date = new Date(System.currentTimeMillis());
        
        else this.date = date;
        
    }

    public void setDate(String date) {
        try {
            Date d = Date.valueOf(date);
            setDate(d);
        } catch(Exception err) {
            this.date = new Date(System.currentTimeMillis());
        }
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public void setProduit(String idProduit) {
        setProduit(new Produit(idProduit));
    }

    // Constructeurs
    public Production() {}

    public Production(String id) {
        setId(id);
    }

    public Production(int quantiteFabrication, Date dateFabrication, Produit produit) throws ValeurInvalideException{
        setQuantite(quantiteFabrication);
        setDate(dateFabrication);
        setProduit(produit);
    }

    public Production(String id, int quantiteFabrication, Date dateFabrication, Produit produit) throws ValeurInvalideException {
        setId(id);
        setQuantite(quantiteFabrication);
        setDate(dateFabrication);
        setProduit(produit);
    }

    public Production(String id, String quantiteFabrication, String dateFabrication, String idProduit) throws ValeurInvalideException {
        setId(id);
        setQuantite(quantiteFabrication);
        setDate(dateFabrication);
        setProduit(idProduit);
    }

    

    @Override
    public String toString() {
        return "Production{" +
                "id='" + id + '\'' +
                ", quantiteFabrication=" + getQuantite() +
                ", dateFabrication=" + getDate() +
                ", produit=" + produit +
                '}';
    }

    protected abstract void insertMere(Connection connection) throws Exception ;
    
    public abstract void insert(Connection connection) throws Exception;
}
