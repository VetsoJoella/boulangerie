package com.model.production.vente;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.configuration.CommissionConfig;
import com.exception.model.ValeurInvalideException;
import com.exception.stock.StockInsuffisantException;
import com.model.client.Client;
import com.model.production.Production;
import com.model.production.fabrication.Fabrication;
import com.model.production.vente.vendeur.Vendeur;
import com.model.produit.Produit;
import com.model.produit.base.ProduitBase;
import com.model.produit.saveur.Saveur;
import com.model.produit.variete.Variete;
import com.model.stock.Stock;
import com.model.stock.StockProduit;

public class Vente extends Production{

    Client client;
    Vendeur vendeur ; 
    double commission ; 

    
    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public void setCommission() {
        setCommission(0);
        if(getTotal()>=CommissionConfig.MIN_COMMISSION) { 
            setCommission((getQuantite()*getProduit().getPrixVente()*CommissionConfig.COMMISSION)/100);
        }
        
    }

    public Vendeur getVendeur() {
        return vendeur;
    }

    public void setVendeur(Vendeur vendeur) {
        this.vendeur = vendeur;
    }

    public void setVendeur(String idVendeur) {
       setVendeur(new Vendeur(idVendeur));
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setClient(String idClient) {
        setClient(new Client(idClient));
    }

    public double getTotal(){
        return getQuantite()*getProduit().getPrixVente() ; 
    }

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

    public Vente(String id, int quantiteVente, Date dateVente, Produit produit, Client client) throws ValeurInvalideException {
        super(id, quantiteVente, dateVente, produit);    
        setClient(client);   
        
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


    // Autres 
    void verifierStockProduit(Stock stock) throws StockInsuffisantException {

        if(stock==null) throw new StockInsuffisantException("Aucune fabrication n'a été encore faite ave ce produit", stock);

        if(getQuantite()>stock.getQuantite()) { 

            String message = "Stock produit "+getProduit().getProduitBase().getNom()+" insuffisant ";
            throw new StockInsuffisantException(message, stock);
        }
    }

    
    protected void insertMere(Connection connection) throws Exception {

        // if(getProduit().getPrixVente()==0) setProduit(Produit.getById(connection, getProduit().getId()));
        setProduit(Produit.getById(connection, getProduit().getId()));
        setCommission();

        String sql = "INSERT INTO vente (id, quantiteVente, dateVente, d_prixUnitaire, idProduit, idClient, idVendeur, commission) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, getQuantite());
            pstmt.setDate(2, getDate());
            pstmt.setDouble(3, getProduit().getPrixVente());
            pstmt.setString(4, getProduit().getId()); // Utilisation de l'ID de l'objet Produit
            pstmt.setString(5, getClient().getId()); // Utilisation de l'ID de l'objet Produit
            pstmt.setString(6, getVendeur().getId()); // Utilisation de l'ID de l'objet Produit
            pstmt.setDouble(7, getCommission()); // Utilisation de l'ID de l'objet Produit
            pstmt.executeUpdate();
            System.out.println("Prix du produit est "+getProduit().getPrixVente());

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    setId(generatedKeys.getString(1)); // Récupération de l'ID généré
                }
            }
        }
    }

    @Override
    public void insert(Connection connection) throws Exception{
        
        // Stock stock = StockProduit.getStock(connection, getProduit());

        // verifierStockProduit(stock) ;

        connection.setAutoCommit(false);
        try {

            insertMere(connection);
            // stock.utiliserStock(getQuantite());
            // Fabrication.update(connection, ((StockProduit)stock).getFabrications());
            connection.commit();
        } catch(Exception err) {
            connection.rollback();
            throw err ; 
        }

    }

     // Méthode pour récupérer une fabrication par ID
    public static Vente getById(Connection connection, String id) throws Exception {
        
        String sql = "SELECT v.id, v.quantiteVente, v.dateVente, v.d_prixUnitaire, idProduit, p.nom AS nomProduit, idClient "+
                    " FROM vente v JOIN produit p ON v.idProduit = p.id where 1=1 and id = ? ";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int quantiteVente = rs.getInt("quantitevente");
                    Date dateVente = rs.getDate("dateVente");
                    Client client = new Client().getById(connection, rs.getString("idClient"));
                      
                    Produit p = Produit.getById(connection, rs.getString("idProduit"));

                    new Vente(id, quantiteVente, dateVente, p, client);
                }
            }
        }
        return null; // Aucune fabrication trouvée
    }

    // Meilleur vente 

    public static Vente[] getMeilleurVente(Connection connection, Date dateDebut, Date dateFin) throws Exception {

        List<Vente> ventes = new ArrayList<>();
        String sql = "select p.id, nom, quantite from produit p join "+
                     "(select idProduit, coalesce(sum(quantiteVente),0) as quantite from vente where 1=1 ";

        if (dateDebut!=null) sql+= " and dateVente >= ? ";
        else sql+= "and '1' = ? ";
        if (dateFin!=null) sql+= "and dateVente <= ? ";
        else sql+= "and '1' = ? ";
        sql+= "group by idProduit )  as v on idProduit = p.id order by quantite desc ";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            System.out.println("Requete est "+sql);
            if(dateDebut!=null) pstmt.setDate(1, dateDebut);
            else pstmt.setString(1, "1");
            if(dateFin!=null) pstmt.setDate(2, dateFin);
            else pstmt.setString(2, "1");

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    
                    Produit p = Produit.getById(connection, rs.getString("idProduit"));

                    ventes.add(new Vente(null, rs.getInt("quantite"), null, p));
                }
            }
        }
        return ventes.toArray(new Vente[0]);
    }

    static Vente[] getByCriteria(Connection connection, Produit produit, ProduitBase produitBase, Variete variete, Saveur saveur) throws Exception {

        List<Vente> ventes = new ArrayList<>();
        String sql = "SELECT * "+
                    " FROM v_vente_produit where 1=1 ";

        if(variete!=null && !variete.getId().isEmpty()) sql+= "and idVariete = ? ";
        else sql+= "and '1' = ? ";
        if(saveur!=null && !saveur.getId().isEmpty()) sql+= "and idSaveur = ? ";
        else sql+= "and '1' = ? ";
        if(produit!=null && !produit.getId().isEmpty()) sql+= "and idProduit = ? ";
        else sql+= "and '1' = ? ";
        if(produitBase!=null && !produitBase.getId().isEmpty()) sql+= "and idProduitBase = ? ";
        else sql+= "and '1' = ? ";

        sql+= " and dateVente = CURRENT_DATE";
       
          
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            System.out.println("Requete de getByCriteria est "+sql);
            if(variete!=null) pstmt.setString(1, variete.getId());
            else pstmt.setString(1, "1");
            if(saveur!=null) pstmt.setString(2, saveur.getId());
            else pstmt.setString(2, "1");
            if(produit!=null) pstmt.setString(3, produit.getId());
            else pstmt.setString(3, "1");
            if(produitBase!=null) pstmt.setString(4, produitBase.getId());
            else pstmt.setString(4, "1");
          


            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    String id = rs.getString("id");
                    int quantiteVente = rs.getInt("quantitevente");
                    Date dateVente = rs.getDate("dateVente");
                    Client client = new Client().getById(connection, rs.getString("idClient"));
                      
                    Produit p = Produit.getById(connection, rs.getString("idProduit"));
                    ventes.add(new Vente(id, quantiteVente, dateVente, p, client));
                }
            }
        }
       
        return ventes.toArray(new Vente[0]);

    }


    static Vente[] getByCriteria(Connection connection, Produit produit, ProduitBase produitBase, Variete variete, Saveur saveur, Date dateMin, Date dateMax) throws Exception {
        
        if(dateMin==null && dateMax==null) return getByCriteria(connection, produit, produitBase, variete, saveur);
        List<Vente> ventes = new ArrayList<>();
        String sql = "SELECT * "+
                    " FROM v_vente_produit where 1=1 ";

        if(variete!=null && !variete.getId().isEmpty()) sql+= "and idVariete = ? ";
        else sql+= "and '1' = ? ";
        if(saveur!=null && !saveur.getId().isEmpty()) sql+= "and idSaveur = ? ";
        else sql+= "and '1' = ? ";
        if(produit!=null && !produit.getId().isEmpty()) sql+= "and idProduit = ? ";
        else sql+= "and '1' = ? ";
        if(produitBase!=null && !produitBase.getId().isEmpty()) sql+= "and idProduitBase = ? ";
        else sql+= "and '1' = ? ";
        if(dateMin!=null) sql+= "and dateVente >= ? ";
        else sql+= "and '1' = ? ";
        if(dateMax!=null) sql+= "and dateVente<= ? ";
          
        System.out.println("Appel de getByCriteria avec date");
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            System.out.println(sql);
            if(variete!=null) pstmt.setString(1, variete.getId());
            else pstmt.setString(1, "1");
            if(saveur!=null) pstmt.setString(2, saveur.getId());
            else pstmt.setString(2, "1");
            if(produit!=null) pstmt.setString(3, produit.getId());
            else pstmt.setString(3, "1");
            if(produitBase!=null) pstmt.setString(4, produitBase.getId());
            else pstmt.setString(4, "1");
            if(dateMin!=null) pstmt.setDate(5, dateMin);
            else pstmt.setString(5, "1");
            if(dateMax!=null) pstmt.setDate(6, dateMax);


            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    String id = rs.getString("id");
                    int quantiteVente = rs.getInt("quantitevente");
                    Date dateVente = rs.getDate("dateVente");
                    Client client = new Client().getById(connection, rs.getString("idClient"));
                      
                    Produit p = Produit.getById(connection, rs.getString("idProduit"));
                    ventes.add(new Vente(id, quantiteVente, dateVente, p, client));
                }
            }
        }
       
        System.out.println("Longueur de réponse est "+ventes.size());
        return ventes.toArray(new Vente[0]);
    }

    public static Vente[] getByCriteria(Connection connection, String idProduit, String idProduitBase, String idVariete, String idSaveur, String dateDebut, String dateFin) throws Exception {
        
        Variete variete = null;
        Saveur saveur = null;
        Produit produit = null ;
        ProduitBase produitBase = null ;
        Date min = null , max = null ;

        if(idVariete!=null && !idVariete.isEmpty()) variete = new Variete(idVariete);
        if(idSaveur!=null && !idSaveur.isEmpty()) saveur = new Saveur(idSaveur);
        if(idProduit!=null && !idProduit.isEmpty()) produit = new Produit(idProduit);
        if(idProduitBase!=null && !idProduitBase.isEmpty()) produitBase = new ProduitBase(idProduitBase);
        if(dateDebut!=null && !dateDebut.isEmpty()) min = Date.valueOf(dateDebut);
        if(dateFin!=null && !dateFin.isEmpty()) max = Date.valueOf(dateFin);

        return getByCriteria(connection, produit, produitBase, variete, saveur, min, max);
       
    }

  
}


