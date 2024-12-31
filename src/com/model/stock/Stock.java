package com.model.stock ;

public abstract class Stock {

    double quantite ; 

    // Getter et Setter
    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    // Constructeurs
    public Stock() {}

    public Stock( double quantite) {
        setQuantite(quantite);
    }

    public abstract void utiliserStock(double quantite) throws Exception;

}

