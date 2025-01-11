package com.model.ingredient;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.exception.DonneesManquantesException;
import com.exception.model.ValeurInvalideException;
import com.model.ingredient.unite.Unite;
import com.model.rapport.Rapport;
import com.service.util.DateFomatter;

public class AchatIngredient {
    
    String id ; 
    Ingredient ingredient ; 
    Date date ; 
    double prixUnitaire;
    double quantite ; 
    double reste ; 


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public void setIngredient(String idIngredient) {
        setIngredient(new Ingredient(idIngredient));
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        if (date == null) this.date = new Date(System.currentTimeMillis());
        
        else this.date = date;
    }

    public void setDate(String date) throws ValeurInvalideException{
        try {
            Date d = Date.valueOf(date);
            setDate(d);
        } catch(Exception err) {
            throw new ValeurInvalideException("Valeur de date invalide");
        }
    }

    public double getPrixUnitaire() {
        return this.prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) throws ValeurInvalideException{
        if(prixUnitaire<0) throw new ValeurInvalideException("Valeur de prix unitaire invalide ");
        else this.prixUnitaire = prixUnitaire;
    }

    public void setPrixUnitaire(String prixUnitaire) throws ValeurInvalideException{
        try {
            double prix = Double.valueOf(prixUnitaire);
            setPrixUnitaire(prix);
        } catch (Exception e) {
            throw new ValeurInvalideException("La valeur de prix insérée n'est pas numérique") ;
        }
    }

    public double getQuantite() {
        return this.quantite;
    }

    public void setQuantite(double quantite) throws ValeurInvalideException{
        if(quantite<0) throw new ValeurInvalideException("Valeur de quantité invalide ");
        else this.quantite = quantite;
    }

    public void setQuantite(String quantite) throws ValeurInvalideException{
        try {
            double q = Double.valueOf(quantite);
            setQuantite(q);
        } catch (Exception e) {
            throw new ValeurInvalideException("La valeur de quantité insérée n'est pas numérique") ;
        }
    }

    public double getReste() {
        return this.reste;
    }

    public void setReste() throws ValeurInvalideException{
       setReste(getQuantite());
    }

    public void setReste(double reste) throws ValeurInvalideException{
        if(reste<0) throw new ValeurInvalideException("Valeur de reste invalide ");
        else this.reste = reste;
    }

    public void setReste(String reste) throws ValeurInvalideException{
        try {
            double r = Double.valueOf(reste);
            setReste(r);
        } catch (Exception e) {
            throw new ValeurInvalideException("La valeur de reste insérée n'est pas numérique") ;
        }
    }

    public AchatIngredient() { }

    public AchatIngredient(Ingredient ingredient, Date date, double prixUnitaire, double quantite) throws ValeurInvalideException {
        setIngredient(ingredient);
        setDate(date);
        setPrixUnitaire(prixUnitaire);
        setQuantite(quantite);
        setReste();
    }

    public AchatIngredient(String idIngredient, String date, String prixUnitaire, String quantite, String reste) throws ValeurInvalideException {
        setIngredient(idIngredient);
        setDate(date);
        setPrixUnitaire(prixUnitaire);
        setQuantite(quantite);
        setReste();
    }

    public AchatIngredient(String id, Ingredient ingredient, Date date, double prixUnitaire, double quantite) throws ValeurInvalideException {
        setId(id);
        setIngredient(ingredient);
        setDate(date);
        setPrixUnitaire(prixUnitaire);
        setQuantite(quantite);
        setReste(reste);
    }

    @Override
    public String toString() {
        return "{" +
            "id='" + getId() + "'" +
            ", ingredient = " + getIngredient() + "" +
            ", prix unitaire = " + getPrixUnitaire() + 
            ", date achat = " + getDate() + 
            ", quantité = "+ getQuantite()+
            ", reste = "+ getReste()+
            "}";
    }

    // Opération de la base 

     public void insert(Connection connection) throws Exception {

        if(getIngredient()==null || getIngredient().getId().isEmpty()) {
            throw new DonneesManquantesException("Valeur ingredient invalide : vérifiez vos données", this);
        }
        String sql = "INSERT INTO achatIngredient (id, idIngredient, quantiteAchete, d_reste, prixUnitaire, dateAchat) VALUES (DEFAULT, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, getIngredient().getId());
            pstmt.setDouble(2, getQuantite());
            pstmt.setDouble(3, getReste());
            pstmt.setDouble(4, getPrixUnitaire());
            pstmt.setDate(5, getDate());
            pstmt.executeUpdate();
        }
    }

    static AchatIngredient[] getByCriteria(Connection connection, Ingredient ingredient, Date dateMin, Date dateMax) throws Exception {
       
        List<AchatIngredient> achatIngredients = new ArrayList<>();

        String sql = "select * from  v_achat_ingredient_unite where 1=1 " ;
        if(ingredient!=null) sql+= "and idIngredient = ? ";
        else sql+= "and '1' = ? ";
        if (dateMin!=null) sql+= "and dateAchat >= ? ";
        else sql+= "and '1' = ? ";
        if (dateMax!=null) sql+= "and dateAchat <= ? ";
        else sql+= "and '1' = ? ";
        sql+= "order by dateachat asc";

        System.out.println("requete de getByCriteria de achat est "+sql);
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            if(ingredient!=null) pstmt.setString(1, ingredient.getId());
            else pstmt.setString(1, "1");
            if(dateMin!=null) pstmt.setDate(2, dateMin);
            else pstmt.setString(2, "1");
            if(dateMax!=null) pstmt.setDate(3, dateMax);
            else pstmt.setString(3, "1");

            // System.out.println("Requete de achat est "+sql);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Ingredient i = new Ingredient(rs.getString("idIngredient"), rs.getString("nomIngredient"), new Unite());
                    Unite unite = new Unite(rs.getString("idunite"), rs.getString("nomUnite"));
                    
                    i.setUnite(unite);
    
                    AchatIngredient achatIngredient = new AchatIngredient(rs.getString("id"), i, rs.getDate("dateachat"), rs.getDouble("prixunitaire"), rs.getDouble("quantiteachete"));
                    achatIngredient.setReste(rs.getDouble("d_reste"));
                    achatIngredients.add(achatIngredient);
                    // System.out.println(achatIngredient.toString());
                }
            }
        }
        return achatIngredients.toArray(new AchatIngredient[0]); 
    }

    public static AchatIngredient[] getByCriteria(Connection connection, String idIngredient, String dateMin, String dateMax) throws Exception {
       
        Ingredient ingredient = null;
        if(idIngredient!=null && !idIngredient.isEmpty()) ingredient = new Ingredient(idIngredient) ;
        Date min = null, max = null ;
        try{
            min = Date.valueOf(dateMin);
        } catch(Exception err){}

        try{
            max = Date.valueOf(dateMax);
        } catch(Exception err){}

        return getByCriteria(connection, ingredient, min, max);
        
    }

    public static AchatIngredient[] getAchatAvecReste(Connection connection, Ingredient ingredient, Date dateMin, Date dateMax) throws Exception {
       
        List<AchatIngredient> achatIngredients = new ArrayList<>();

        String sql = "select * from  v_achat_ingredient_unite where d_reste > 0 " ;
        if(ingredient!=null) sql+= "and idIngredient = ? ";
        else sql+= "and '1' = ? ";
        if (dateMin!=null) sql+= "and dateAchat >= ? ";
        else sql+= "and '1' = ? ";
        if (dateMax!=null) sql+= "and dateAchat <= ? ";
        else sql+= "and '1' = ? ";
        sql+= "order by dateachat asc";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            if(ingredient!=null) pstmt.setString(1, ingredient.getId());
            else pstmt.setString(1, "1");
            if(dateMin!=null) pstmt.setDate(2, dateMin);
            else pstmt.setString(2, "1");
            if(dateMax!=null) pstmt.setDate(3, dateMax);
            else pstmt.setString(3, "1");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Ingredient i = new Ingredient(rs.getString("idIngredient"), rs.getString("nomIngredient"), new Unite());
                    Unite unite = new Unite(rs.getString("idunite"), rs.getString("nomUnite"));
                    
                    i.setUnite(unite);
    
                    AchatIngredient achatIngredient = new AchatIngredient(rs.getString("id"), i, rs.getDate("dateachat"), rs.getDouble("prixunitaire"), rs.getDouble("quantiteachete"));
                    achatIngredient.setReste(rs.getDouble("d_reste"));
                    achatIngredients.add(achatIngredient);
                }
            }
        }
        return achatIngredients.toArray(new AchatIngredient[0]); 
    }

    // public void update(Connection connection) throws Exception {

    //     if (getIngredient() == null || getIngredient().getId().isEmpty()) {
    //         throw new DonneesManquantesException("Valeur ingredient invalide : vérifiez vos données", this);
    //     }

    //     String sql = "UPDATE achatIngredient SET idIngredient = ?, quantiteAchete = ?, d_reste = ?, prixUnitaire = ?, dateAchat = ? WHERE id = ?";
    //     try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
    //         pstmt.setString(1, getIngredient().getId());
    //         pstmt.setDouble(2, getQuantite());
    //         pstmt.setDouble(3, getReste());
    //         pstmt.setDouble(4, getPrixUnitaire());
    //         pstmt.setDate(5, getDate());
    //         pstmt.setString(6, getId()); // Assure que l'ID est correctement défini pour cibler la ligne à mettre à jour
    //         int rowsAffected = pstmt.executeUpdate();

    //         if (rowsAffected == 0) {
    //             throw new SQLException("La mise à jour a échoué : aucun enregistrement trouvé pour l'ID " + getId());
    //         }
    //     }
    // }

    public static void update(Connection connection, AchatIngredient[] achatIngredients) throws Exception {
        
        if (achatIngredients != null) {
    
            String sql = "UPDATE achatIngredient SET idIngredient = ?, quantiteAchete = ?, d_reste = ?, prixUnitaire = ?, dateAchat = ? WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                for (AchatIngredient achat : achatIngredients) {
                    if (achat.getIngredient() == null || achat.getIngredient().getId().isEmpty()) {
                        throw new DonneesManquantesException("Valeur ingredient invalide : vérifiez vos données", achat);
                    }
                    // Remplir les paramètres pour chaque achat
                    pstmt.setString(1, achat.getIngredient().getId());
                    pstmt.setDouble(2, achat.getQuantite());
                    pstmt.setDouble(3, achat.getReste());
                    pstmt.setDouble(4, achat.getPrixUnitaire());
                    pstmt.setDate(5, achat.getDate());
                    pstmt.setString(6, achat.getId());
                    pstmt.addBatch(); // Ajouter au batch
                }
        
                // Exécuter le batch
                int[] results = pstmt.executeBatch();
        
                // Vérifier les résultats
                for (int i = 0; i < results.length; i++) {
                    if (results[i] == 0) {
                        throw new SQLException("La mise à jour a échoué pour l'élément avec l'ID " + achatIngredients[i].getId());
                    }
                }
            }
        }
    }

    public static Rapport getRapport(AchatIngredient[] achatIngredients){
        
        double sommeMontant = 0, sommeQuantite = 0 ;
        Map<String,Double> maps = new LinkedHashMap<>();
        for (AchatIngredient achatIngredient : achatIngredients) {
            String dateStr = DateFomatter.formatterDate(achatIngredient.getDate());
            sommeMontant+= achatIngredient.getPrixUnitaire()*achatIngredient.getQuantite();
            sommeQuantite+= achatIngredient.getQuantite();
            if(maps.get(dateStr)==null){
                maps.put(dateStr, 0.0);
            }
            double val = maps.get(dateStr.toString());
            maps.replace(dateStr, val+achatIngredient.getQuantite());
        } 
        Rapport rapport = new Rapport(sommeMontant, sommeQuantite);
        rapport.setValeurs(maps);
        return rapport ;
    }
}
