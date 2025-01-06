package com.model.production.vente;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.exception.model.ValeurInvalideException;
import com.exception.stock.StockInsuffisantException;
import com.model.production.Production;
import com.model.produit.Produit;
import com.model.stock.Stock;
import com.model.stock.StockProduit;

public class Vente extends Production{

    // Constructeur 
    public Vente() {}

    public Vente(String id) {
        super(id);
    }

    public Vente(int quantiteVente, Date dateVente, Produit produit) throws ValeurInvalideException{
        super(quantiteVente, dateVente, produit);       
    }

    public Vente(String id, String quantiteVente, String dateVente, String idProduit) throws ValeurInvalideException{
        super(id, quantiteVente, dateVente, idProduit);       
    }

    public Vente(String id, int quantiteVente, Date dateVente, Produit produit) throws ValeurInvalideException {
        super(id, quantiteVente, dateVente, produit);       
        
    }

    @Override
    public String toString() {
        return "Vente{" +
                "id='" + getId() + '\'' +
                ", quantiteFabrication=" + getQuantite() +
                ", dateFabrication=" + getDate() +
                ", produit=" + getProduit() +
                '}';
    }

    // Acces dans la base de données 

    static Vente[] getByCriteria(Connection connection, Produit produit, Date dateMin, Date dateMax) throws Exception {
        
        List<Vente> ventes = new ArrayList<>();
        String sql = "SELECT v.id, v.quantiteVente, v.dateVente, v.d_prixUnitaire, idProduit, p.nom AS nomProduit "+
                    " FROM vente v JOIN produit p ON v.idProduit = p.id where 1=1 ";

        if(produit!=null) sql+= "and idProduit = ? ";
        else sql+= "and '1' = ? ";
        if (dateMin!=null) sql+= "and dateVente >= ? ";
        else sql+= "and '1' = ? ";
        if (dateMax!=null) sql+= "and dateVente <= ? ";
        else sql+= "and '1' = ? ";
        sql+= "order by dateVente asc";

          
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            if(produit!=null) pstmt.setString(1, produit.getId());
            else pstmt.setString(1, "1");
            if(dateMin!=null) pstmt.setDate(2, dateMin);
            else pstmt.setString(2, "1");
            if(dateMax!=null) pstmt.setDate(3, dateMax);
            else pstmt.setString(3, "1");
            // System.out.println("Requete de vente est "+sql);
            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    String id = rs.getString("id");
                    int quantiteVente = rs.getInt("quantitevente");
                    Date dateVente = rs.getDate("dateVente");
                      
                    Produit p = new Produit(rs.getString("idProduit"), rs.getString("nomProduit"), rs.getDouble("d_prixunitaire"));
    
                    ventes.add(new Vente(id, quantiteVente, dateVente, p));
                }
            }
        }
       
        return ventes.toArray(new Vente[0]);
    }

    public static Vente[] getByCriteria(Connection connection, String idProduit, String dateMin, String dateMax) throws Exception {
        
        Date min = null, max = null ;
        Produit produit = null;
        if(idProduit!=null) produit = new Produit(idProduit);
        try{
            min = Date.valueOf(dateMin);
        } catch(Exception err){}

        try{
            max = Date.valueOf(dateMax);
        } catch(Exception err){}

        return getByCriteria(connection, produit, min, max);
       
    }

    // Méthode pour récupérer une fabrication par ID
    public static Vente getById(Connection connection, String id) throws Exception {
        
        String sql = "SELECT v.id, v.quantiteVente, v.dateVente, v.d_prixUnitaire, idProduit, p.nom AS nomProduit "+
                    " FROM vente v JOIN produit p ON v.idProduit = p.id where 1=1 and id = ? ";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int quantiteVente = rs.getInt("quantitevente");
                    Date dateVente = rs.getDate("dateVente");
                      
                    Produit p = new Produit(rs.getString("idProduit"), rs.getString("nomProduit"), rs.getDouble("d_prixunitaire"));

                    new Vente(id, quantiteVente, dateVente, p);
                }
            }
        }
        return null; // Aucune fabrication trouvée
    }

    // Autres 
    void verifierStockProduit(Stock stock) throws StockInsuffisantException {

        if(getQuantite()>stock.getQuantite()) { 

            String message = "Stock produit "+getProduit().getNom()+" insuffisant ";
            throw new StockInsuffisantException(message, stock);
        }
    }

    
    protected void insertMere(Connection connection) throws Exception {

        if(getProduit().getPrixVente()==0) setProduit(Produit.getById(connection, getProduit().getId()));

        String sql = "INSERT INTO vente (id, quantiteVente, dateVente, d_prixUnitaire, idProduit) VALUES (DEFAULT, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, getQuantite());
            pstmt.setDate(2, getDate());
            pstmt.setDouble(3, getProduit().getPrixVente());
            pstmt.setString(4, getProduit().getId()); // Utilisation de l'ID de l'objet Produit
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    setId(generatedKeys.getString(1)); // Récupération de l'ID généré
                }
            }
        }
    }

    @Override
    public void insert(Connection connection) throws Exception{
        
        Stock stock = StockProduit.getStock(connection, getProduit());

        verifierStockProduit(stock) ;

        connection.setAutoCommit(false);
        try {

            insertMere(connection);
            stock.utiliserStock(getQuantite());
            connection.commit();
        } catch(Exception err) {
            connection.rollback();
            throw err ; 
        }

    }

    // Meilleur vente 

    public static Vente[] getMeilleurVente(Connection connection, Date dateDebut, Date dateFin) throws Exception {

        List<Vente> ventes = new ArrayList<>();
        String sql = "select p.id, nom, quantite from produit p join "+
                     "(select idProduit, sum(quantiteVente) as quantite from vente where 1=1 ";

        if (dateDebut!=null) sql+= " and dateVente >= ?";
        else sql+= "and '1' = ? ";
        if (dateFin!=null) sql+= "and dateVente <= ? ";
        else sql+= "and '1' = ? ";
        sql+= "group by idProduit )  as v on idProduit = p.id ";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

       
            if(dateDebut!=null) pstmt.setDate(1, dateDebut);
            else pstmt.setString(1, "1");
            if(dateFin!=null) pstmt.setDate(2, dateFin);
            else pstmt.setString(2, "1");

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    
                    Produit p = new Produit(rs.getString("id"), rs.getString("nom"), 0);

                    ventes.add(new Vente(null, rs.getInt("quantite"), null, p));
                }
            }
        }
        return ventes.toArray(new Vente[0]);
    }

}


