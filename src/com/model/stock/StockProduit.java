package com.model.stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.model.production.fabrication.Fabrication;
import com.model.produit.Produit;

public class StockProduit extends Stock {
    
    Produit produit ;
    Fabrication[] fabrications ;

    // Getters et setters
    public Produit getProduit() {
        return this.produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Fabrication[] getFabrications() {
        return fabrications;
    }

    public void setFabrications(Fabrication[] fabrications) {
        this.fabrications = fabrications;
    }

    public void setFabrications(Connection connection) throws Exception {
        
        if(produit!=null) {
            setFabrications(Fabrication.getFabricationNonVendue(connection, getProduit()));
        }
    }

    // Contructeur
    public StockProduit() {
        super();
        setProduit(new Produit());
    }

    public StockProduit(Produit produit, double quantite) {
        super(quantite);
        setProduit(produit);
    }

    // Méthode pour récupérer le stock par ID de produit
    public static Stock[] getStock(Connection connection) throws Exception {

        List<Stock> stocks = new ArrayList<>();

        String sql = "SELECT * FROM v_stock_produit_details WHERE 1=1 ";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Produit p = new Produit(rs.getString("idproduit"), rs.getString("nomproduit"), rs.getDouble("d_prixVente"));
                    StockProduit stock = new StockProduit(p, rs.getDouble("reste"));
                    stock.setFabrications(connection);
                    stocks.add(stock);
                }
            }
        }
        return stocks.toArray(new Stock[0]);

    }

    public static Stock getStock(Connection connection, Produit produit) throws Exception {


        String sql = "SELECT * FROM v_stock_produit_details WHERE 1=1 ";
        if(produit!=null) sql+= "and idProduit = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            if(produit!=null) pstmt.setString(1, produit.getId());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                  
                    Produit p = new Produit(rs.getString("idproduit"), rs.getString("nomproduit"), rs.getDouble("d_prixVente"));
                    StockProduit stock = new StockProduit(p, rs.getDouble("reste"));
                    stock.setFabrications(connection);
                    return stock ;
                }
            }
        }
        return null ;

    }

    @Override
    public void utiliserStock(double quantite) throws Exception {

        int quantiteVendue = 0 ; 
        List<Fabrication> fabrications = new ArrayList<>();
        for (int i = 0; i < getFabrications().length; i++) {

            if(quantiteVendue==quantite) break ;

            if(quantiteVendue+getFabrications()[i].getReste()<=quantite){ 
                quantiteVendue+=getFabrications()[i].getReste();
                getFabrications()[i].setReste(0);
            }
            else {
                getFabrications()[i].setReste(quantiteVendue);
                quantiteVendue = (int)quantite ; 
            }
            fabrications.add(getFabrications()[i]);
        }
        setFabrications(fabrications.toArray(new Fabrication[0]));
    
    }

}
