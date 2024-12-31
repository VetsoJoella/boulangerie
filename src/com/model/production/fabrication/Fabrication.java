package com.model.production.fabrication;

import com.exception.model.ValeurInvalideException;
import com.exception.stock.StockException;
import com.exception.stock.StockInsuffisantException;
import com.model.ingredient.AchatIngredient;
import com.model.production.Production;
import com.model.produit.Produit;
import com.model.recette.Recette;
import com.model.stock.Stock;
import com.model.stock.StockIngredient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Fabrication extends Production{

    private int reste;

    // Getters et setters
    public int getReste() {
        return reste;
    }

    public void setReste(int reste) {
        this.reste = reste;
    }

    public void setReste(String reste) throws ValeurInvalideException{
        try {
            int r = Integer.valueOf(reste);
            setReste(r);
        } catch (Exception e) {
            throw new ValeurInvalideException("Valeur de reste non numérique");
        }
    }

    // Constructeurs
    public Fabrication() {}

    public Fabrication(String id) {
        super(id);
    }

    public Fabrication(int quantiteFabrication, Date dateFabrication, int reste, Produit produit) throws ValeurInvalideException{
        super(quantiteFabrication, dateFabrication, produit);       
        setReste(reste);
    }

    public Fabrication(String id, int quantiteFabrication, Date dateFabrication, int reste, Produit produit) throws ValeurInvalideException {
        super(id, quantiteFabrication, dateFabrication, produit);       
        setReste(reste);
    }

    public Fabrication(String id, String quantiteFabrication, String dateFabrication, String reste, String produit) throws ValeurInvalideException {
        super(id, quantiteFabrication, dateFabrication, produit);       
        setReste(reste);
    }

    @Override
    public String toString() {
        return "Fabrication{" +
                "id='" + getId() + '\'' +
                ", quantiteFabrication=" + getQuantite() +
                ", dateFabrication=" + getDate() +
                ", reste=" + getReste() +
                ", produit=" + getProduit() +
                '}';
    }

    // Méthode pour insérer une fabrication
    protected void insertMere(Connection connection) throws Exception {
        String sql = "INSERT INTO fabrication (id, quantiteFabrication, dateFabrication, d_reste, idProduit) VALUES (DEFAULT, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, getQuantite());
            pstmt.setDate(2, getDate());
            pstmt.setInt(3, getReste());
            pstmt.setString(4, getProduit().getId()); // Utilisation de l'ID de l'objet Produit
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    setId(generatedKeys.getString(1)); // Récupération de l'ID généré
                }
            }
        }
    }

    // Méthode pour récupérer toutes les fabrications
    static Fabrication[] getByCriteria(Connection connection, Produit produit, Date dateMin, Date dateMax) throws Exception {
        
        List<Fabrication> fabrications = new ArrayList<>();
        String sql = "SELECT f.id, f.quantiteFabrication, f.dateFabrication, f.d_reste, idProduit, p.nom AS nomProduit, p.d_prixVente " +
                     "FROM fabrication f JOIN produit p ON f.idProduit = p.id where 1=1 ";

        if(produit!=null) sql+= "and idProduit = ? ";
        else sql+= "and '1' = ? ";
        if (dateMin!=null) sql+= "and dateFabrication >= ? ";
        else sql+= "and '1' = ? ";
        if (dateMax!=null) sql+= "and dateFabrication <= ? ";
        else sql+= "and '1' = ? ";
          
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            if(produit!=null) pstmt.setString(1, produit.getId());
            else pstmt.setString(1, "1");
            if(dateMin!=null) pstmt.setDate(2, dateMin);
            else pstmt.setString(2, "1");
            if(dateMax!=null) pstmt.setDate(3, dateMax);
            else pstmt.setString(3, "1");

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    String id = rs.getString("id");
                    int quantiteFabrication = rs.getInt("quantiteFabrication");
                    Date dateFabrication = rs.getDate("dateFabrication");
                    int reste = rs.getInt("d_reste");
                      
                    Produit p = new Produit(rs.getString("idProduit"), rs.getString("nomProduit"), rs.getDouble("prixVente"));
    
                    fabrications.add(new Fabrication(id, quantiteFabrication, dateFabrication, reste, p));
                }
            }
        }
       
        return fabrications.toArray(new Fabrication[0]);
    }

    public static Fabrication[] getByCriteria(Connection connection, String idProduit, String dateMin, String dateMax) throws Exception {
        
        Date min = null, max = null ;
        Produit produit = new Produit(idProduit);
        try{
            min = Date.valueOf(dateMin);
        } catch(Exception err){}

        try{
            max = Date.valueOf(dateMax);
        } catch(Exception err){}

        return getByCriteria(connection, produit, min, max);
       
    }

    // Méthode pour récupérer une fabrication par ID
    public static Fabrication getById(Connection connection, String id) throws Exception {
        String sql = "SELECT f.id, f.quantiteFabrication, f.dateFabrication, f.d_reste, p.id AS produit_id, p.nom AS produit_nom, p.d_prixVente " +
                     "FROM fabrication f " +
                     "JOIN produit p ON f.idProduit = p.id " +
                     "WHERE f.id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int quantiteFabrication = rs.getInt("quantiteFabrication");
                    Date dateFabrication = rs.getDate("dateFabrication");
                    int reste = rs.getInt("d_reste");

                    // Création de l'objet Produit associé
                    String produitId = rs.getString("produit_id");
                    String produitNom = rs.getString("produit_nom");
                    double produitPrixVente = rs.getDouble("prixVente");
                    Produit produit = new Produit(produitId, produitNom, produitPrixVente);

                    return new Fabrication(id, quantiteFabrication, dateFabrication, reste, produit);
                }
            }
        }
        return null; // Aucune fabrication trouvée
    }

     // Méthode pour récupérer une fabrication par ID
     public static Fabrication[] getFabricationNonVendue(Connection connection, Produit produit) throws Exception {
        
        List<Fabrication> fabrications = new ArrayList<>();

        String sql = "SELECT f.id, f.quantiteFabrication, f.dateFabrication, f.d_reste, p.id AS produit_id, p.nom AS produit_nom, p.d_prixVente " +
                     "FROM fabrication f " +
                     "JOIN produit p ON f.idProduit = p.id " +
                     "WHERE d_reste > 0 ";
        if(produit!=null) sql+= " and idproduit = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            if(produit!=null) pstmt.setString(1, produit.getId());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int quantiteFabrication = rs.getInt("quantiteFabrication");
                    Date dateFabrication = rs.getDate("dateFabrication");
                    int reste = rs.getInt("d_reste");

                    String produitId = rs.getString("produit_id");
                    String produitNom = rs.getString("produit_nom");
                    double produitPrixVente = rs.getDouble("prixVente");
                    Produit p = new Produit(produitId, produitNom, produitPrixVente);
                    fabrications.add(new Fabrication(rs.getString("id"), quantiteFabrication, dateFabrication, reste, p));
                }
            }
        }
        return fabrications.toArray(new Fabrication[0]); // Aucune fabrication trouvée
    }

    public void delete(Connection connection) throws SQLException {

        String sql = "DELETE FROM fabrication WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, getId());
            pstmt.executeUpdate();
        }
    }


    // Autres 

    void verifierStockIngredient(Recette recette, Stock stock) throws StockInsuffisantException {

        if(recette.getQuantite()*getQuantite()>stock.getQuantite()) { 
            String message = "Stock ingredient "+recette.getIngredient().getNom()+" insuffisant ";
            throw new StockInsuffisantException(message, stock);
        }
    }

    private void verifierStockIngredient(Connection connection, Recette[] recettes, Stock[] stocks) throws Exception {

        // Exception 
        List<StockInsuffisantException> exceptions = new ArrayList<>();
        String message = "";
        for(int i = 0; i<recettes.length ; i++) {
            try {
                verifierStockIngredient(recettes[i], stocks[i]);
            } catch (StockInsuffisantException e) {
                exceptions.add(e);
                message+= e.getMessage()+"\n";

            } catch (Exception e) {
                throw e ; 
            }
        }

        if(exceptions.size()>0) {
            throw new StockException("Stock insuffisant : "+message,exceptions.toArray(new StockInsuffisantException[0]));
        }
        
    }

    void insertDetail(Connection connection, Recette recette) throws Exception {
        String sql = "INSERT INTO detailFabrication (id, d_quantite, idIngredient, idFabrication) VALUES (DEFAULT, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setDouble(1, getQuantite()*recette.getQuantite());
            pstmt.setString(2, recette.getIngredient().getId());
            pstmt.setString(3, getId()); // Utilisation de l'ID de l'objet Produit
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    // this.id = generatedKeys.getString(1); // Récupération de l'ID généré
                    throw new SQLException("Les details de la farbication n'est pas insérée");
                }
            }
        }
    }

    @Override
    public void insert(Connection connection) throws Exception{
        
        Recette[] recettes = Recette.getByCriteria(connection, getProduit(), null);
        Stock[] stocks = StockIngredient.getStock(connection, recettes, getDate());

        connection.setAutoCommit(false);
        try {
            verifierStockIngredient(connection, recettes, stocks) ;

            insertMere(connection);
            for (int i = 0; i < stocks.length; i++) {
                insertDetail(connection, recettes[i]);
                ((StockIngredient)stocks[i]).utiliserStock(recettes[i].getQuantite()*getQuantite());
                AchatIngredient.update(connection, ((StockIngredient)stocks[i]).getAchatIngredients());
            }
            connection.commit();

            
        } catch(Exception err) {
            connection.rollback();
            throw err ; 

        } finally{
            connection.setAutoCommit(true);

        }

    }
}
