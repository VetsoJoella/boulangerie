package com.controller.CRUD;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.ingredient.Ingredient;
import com.model.produit.Produit;
import com.model.produit.type.Type;
import com.service.connection.UtilDb;

@WebServlet("/CRUD/produit")
public class ProduitCRUD extends HttpServlet{
    
    UtilDb utilDb ;

    @Override
    public void init() throws ServletException {
        super.init();
        utilDb = (UtilDb) getServletContext().getAttribute("utilDb");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        String idType = req.getParameter("idType");
        try {
            Connection connection = utilDb.getConnection();

            if(req.getParameter("action")!=null && req.getParameter("idProduit")!=null && req.getParameter("action").compareToIgnoreCase("u")==0){
           
                Produit produit = Produit.getById(connection, req.getParameter("idProduit"));
                req.setAttribute("id", produit.getId());
                req.setAttribute("nom", produit.getNom());
                req.setAttribute("prix", produit.getPrixVente());
            }
            
            else if(req.getParameter("idProduit")!=null) {
    
                Produit produit = new Produit(req.getParameter("idProduit"));
                produit.delete(connection);
            } 
            // Recette[] recettes = Recette.getByCriteriaType(connection, ingredient,type ) ; 
            // List<Produit> produits = new ArrayList<>();
            // for (Recette recette : recettes) {
            //     produits.add(recette.getProduit());
            // }
            // req.setAttribute("produits", produits.toArray(new Produit[0]));
            req.setAttribute("produits", Produit.getByCriteria(connection, idType));

        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
            err.printStackTrace();
        }
        
        processRequest(req, res);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      
        System.out.println("Appel de do post produit");
        String nom = req.getParameter("nom");
        String prix = req.getParameter("prix");
        String id = req.getParameter("id");
        String idType = req.getParameter("idType");

        try{
            Connection connection = utilDb.getConnection();
            Produit produit = new Produit(id, nom, prix);
            produit.setType(new Type(idType));
            connection.setAutoCommit(false);
            // if(id!=null) produit.update(connection);
            // else produit.insert(connection);
            produit.insert(connection);
            connection.commit();
            connection.setAutoCommit(true);
            req.setAttribute("message", "Insertion produit effectué");
            req.setAttribute("produits", Produit.getAll(connection));

        } catch(Exception err) {
            err.printStackTrace();
            req.setAttribute("message", err.getMessage());
        }
        
        processRequest(req, res);
    }

    void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/pages/CRUD/produit.jsp");
        try{
            Connection connection = utilDb.getConnection();
            req.setAttribute("types", Type.getAll(connection));
            req.setAttribute("ingredients", Ingredient.getAll(connection));

        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
            System.err.println(err);
        }
        rd.forward(req, res);    
    }
}
