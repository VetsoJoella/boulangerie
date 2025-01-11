package com.controller.CRUD.fabrication;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.ingredient.Ingredient;
import com.model.production.fabrication.Fabrication;
import com.model.produit.Produit;
import com.model.produit.variete.Variete;
import com.service.connection.UtilDb;

@WebServlet("/CRUD/fabrication")
public class FabricationCRUD extends HttpServlet{

    UtilDb utilDb ;

    @Override
    public void init() throws ServletException {
        super.init();
        utilDb = (UtilDb) getServletContext().getAttribute("utilDb");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        System.out.println("Appel de get dans FABRICATION");
        
        try {
            Connection connection = utilDb.getConnection();
            String idIngredient = req.getParameter("idIngredient") ; 
            String idVariete = req.getParameter("idVariet") ; 

            System.out.println(" idIngredient est "+idIngredient+" idVariete est "+idVariete);
            
            req.setAttribute("fabrications", Fabrication.getByCriteria(connection, null, null, idIngredient, idVariete, null,null));

        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
            err.printStackTrace();
        }
        processRequest(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        System.out.println("Appel de post dans FABRICATION");
        String produit = req.getParameter("idProduit");
        String quantite = req.getParameter("quantite"); 
        String date = req.getParameter("date"); 

        try{
           
            Fabrication fabrication = new Fabrication(null, quantite, date, "0", produit); 
            Connection connection = utilDb.getConnection();
            req.setAttribute("fabrications", Fabrication.getByCriteria(connection, null, null, null, null, null, null));
            
            fabrication.insert(connection);
            req.setAttribute("message", "Insertion de fabrication effectuée");


        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
            System.out.println("erreur est "+err.getMessage());
            // err.printStackTrace();

        } finally{
            // connection.setAutoCommit(true);
            processRequest(req, res);
        }

    }

    void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/pages/CRUD/fabrication.jsp");
        try{
            Connection connection = utilDb.getConnection();
            req.setAttribute("produits", Produit.getAll(connection));
            req.setAttribute("varietes", new Variete().getAll(connection));
            req.setAttribute("ingredients", Ingredient.getAll(connection));

        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
            System.out.println("Erreur remplacée "+err);
        } finally {
            rd.forward(req, res);

        }
    }
}
