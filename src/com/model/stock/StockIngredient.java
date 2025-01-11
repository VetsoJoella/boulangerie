package com.model.stock;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.model.ingredient.AchatIngredient;
import com.model.ingredient.Ingredient;
import com.model.produit.Produit;
import com.model.produit.recette.Recette;

public class StockIngredient extends Stock{
    
    
    private Ingredient ingredient;
    private AchatIngredient[] achatIngredients ; 

    // Getter et Setter
    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public AchatIngredient[] getAchatIngredients() {
        return achatIngredients;
    }

    public void setAchatIngredients(AchatIngredient[] achatIngredients) {
        this.achatIngredients = achatIngredients;
    }

    void setAchatIngredients(Connection connection) throws Exception{

        if(ingredient!=null) {
            setAchatIngredients(AchatIngredient.getAchatAvecReste(connection, ingredient, null, null));
        }

    }
    // Constructeurs
    public StockIngredient() {
        super();
    }

    public StockIngredient(Ingredient ingredient, double quantite) {
        super(quantite);
        setIngredient(ingredient);
    }


    // Méthode pour récupérer le stock par ID de produit
    public static Stock[] getStock(Connection connection) throws Exception {

        List<Stock> stocks = new ArrayList<>();

        String sql = "SELECT * FROM v_stockIngredient WHERE 1=1 ";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Ingredient ingredient = Ingredient.getById(connection,rs.getString("idIngredient"));
                    Stock stock = new StockIngredient(ingredient, rs.getDouble("reste"));
                    stocks.add(stock);
                }
            }
        }
        return stocks.toArray(new Stock[0]);

    }

    public static Stock[] getStock(Connection connection, Produit produit) throws Exception {

        List<Stock> stocks = new ArrayList<>();

        String sql = "SELECT * FROM v_stockIngredient WHERE 1=1 ";
        if(produit!=null && !produit.getId().isEmpty()) sql+= "and idIngredient in (select idIngredient from v_recette_produit where idProduit =? )";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            if(produit!=null && !produit.getId().isEmpty()) pstmt.setString(1, produit.getId());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Ingredient ingredient = Ingredient.getById(connection,rs.getString("idIngredient"));
                    Stock stock = new StockIngredient(ingredient, rs.getDouble("reste"));
                    stocks.add(stock);
                }
            }
        }
        return stocks.toArray(new Stock[0]);

    }

    public static Stock[] getStock(Connection connection, Recette recettes[], Date date) throws Exception {

        List<Stock> stocks = new ArrayList<>() ;
        for (Recette recette : recettes) {
                stocks.add(getStock(connection, recette.getIngredient(), date));
        }
        return stocks.toArray(new Stock[0]);
    }

    public static Stock[] getStock(Connection connection, Ingredient ingredients[]) throws Exception {

        List<Stock> stocks = new ArrayList<>() ;
        for (Ingredient ingredient : ingredients) {
            stocks.add(getStock(connection, ingredient));
        }
        return stocks.toArray(new Stock[0]);
    }
    

    public static Stock getStock(Connection connection, Ingredient ingredient) throws Exception {


        String sql = "SELECT * FROM v_stockIngredient WHERE 1=1 ";
        if(ingredient!=null) sql+= "and idIngredient =? )";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            if(ingredient!=null) pstmt.setString(1, ingredient.getId());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Ingredient i = Ingredient.getById(connection,rs.getString("idIngredient"));
                    StockIngredient stock = new StockIngredient(i, rs.getDouble("reste"));
                    stock.setAchatIngredients(connection);
                    return stock ;
                }
            }
        }
        return null ;

    }

    static Stock getStock(Connection connection, Ingredient ingredient, Date date) throws Exception {


        String sql = "select * from "+
                    "(select idIngredient, sum(d_reste) as reste from achatIngredient where 1=1 ";
        
        if(date!=null) sql+= " and dateAchat <= ? group by idIngredient) a ";
        else sql+= " and '1' = ? group by idIngredient) a ";
        if(ingredient!=null) sql+= "where idIngredient = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            System.out.println("Requete des getStock est : "+sql);
            if(date!=null) pstmt.setDate(1, date);
            else pstmt.setString(1, "1");
            if(ingredient!=null) pstmt.setString(2, ingredient.getId());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Ingredient i = Ingredient.getById(connection,rs.getString("idIngredient"));
                    StockIngredient stock = new StockIngredient(i, rs.getDouble("reste"));
                    stock.setAchatIngredients(connection);
                    return stock ;
                }
            }
        }
        return new StockIngredient();

    }

    public static Stock getStock(Connection connection, String idIngredient, String date) throws Exception {

        Ingredient ingredient = null ; Date d = null ;
        
        if(idIngredient!=null) ingredient = new Ingredient(idIngredient);
        try{
            d = Date.valueOf(date);
        } catch(Exception err){}

        return getStock(connection, ingredient, d);

    }

    @Override
    public void utiliserStock(double quantite) throws Exception {

        double quantiteUtilise = 0 ; 
        List<AchatIngredient> achatIngredients = new ArrayList<>();
        for (int i = 0; i < getAchatIngredients().length; i++) {

            if(quantiteUtilise==quantite) break ;

            if(quantiteUtilise+getAchatIngredients()[i].getReste()<=quantite){ 
                quantiteUtilise+=getAchatIngredients()[i].getReste();
                getAchatIngredients()[i].setReste(0);
            }
            else {
                getAchatIngredients()[i].setReste(getAchatIngredients()[i].getReste()-quantite+quantiteUtilise);
                quantiteUtilise = quantite ; 
            }
            achatIngredients.add(getAchatIngredients()[i]);
            System.out.println("Quantité restante est "+getAchatIngredients()[i].getReste());
        }
        setAchatIngredients(achatIngredients.toArray(new AchatIngredient[0]));
    }
    
        // // Méthode pour récupérer le stock par tableau d'ingrédients
        // public static Stock[] getStockByProduit(Connection connection, Ingredient[] ingredients) throws SQLException {
        //     List<Stock> stocks = new ArrayList<>();
        //     for (Ingredient ingredient : ingredients) {
        //         stocks.addAll(List.of(getStockByProduit(connection, ingredient.getId())));
        //     }
        //     return stocks.toArray(new Stock[0]);
        // }
    
        // // Méthode pour récupérer le stock par tableau de recettes
        // public static Stock[] getStockByProduit(Connection connection, Recette[] recettes) throws SQLException {
        //     List<Stock> stocks = new ArrayList<>();
        //     for (Recette recette : recettes) {
        //         Ingredient[] ingredients = recette.getIngredients(); // Suppose que Recette a cette méthode
        //         stocks.addAll(List.of(getStockByProduit(connection, ingredients)));
        //     }
        //     return stocks.toArray(new Stock[0]);
        // }
    
   
    
}

