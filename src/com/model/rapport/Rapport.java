package com.model.rapport;

import java.util.Map;

public class Rapport {
 
    String nom ;
    double sommeMontant ; 
    double sommeQuantite ;
    Map<String, Double> valeurs ; 


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getSommeMontant() {
        return this.sommeMontant;
    }

    public void setSommeMontant(double sommeMontant) {
        this.sommeMontant = sommeMontant;
    }

    public double getSommeQuantite() {
        return this.sommeQuantite;
    }

    public void setSommeQuantite(double sommeQuantite) {
        this.sommeQuantite = sommeQuantite;
    }

    public Map<String, Double> getValeurs() {
        return valeurs;
    }

    public void setValeurs(Map<String, Double> valeurs) {
        this.valeurs = valeurs;
    }

    public Rapport(){}

    public Rapport(double sommeMontant, double sommeQuantite){
        setSommeMontant(sommeMontant);
        setSommeQuantite(sommeQuantite);
    }

    public String mapsEnString(){

        StringBuilder string = new StringBuilder() ;
        for (Map.Entry<String, Double> entry : valeurs.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            string.append("{ x: new Date('").append(key).append("'), y: ").append(value).append(" }, ");
        }
        return string.toString();
    }
}
