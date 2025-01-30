package com.controller.CRUD.produit;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.produit.HistoriqueProduit;
import com.model.produit.Produit;
import com.model.produit.base.ProduitBase;
import com.model.produit.saveur.Saveur;
import com.model.produit.variete.Variete;
import com.service.connection.UtilDb;

@WebServlet("/CRUD/historiqueProduit")

public class MiseAJourProduit extends HttpServlet {
    
    
    UtilDb utilDb ;

    @Override
    public void init() throws ServletException {
        super.init();
        utilDb = (UtilDb) getServletContext().getAttribute("utilDb");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        String idProduit = req.getParameter("idProduit") ; 
        try{
            Connection connection = utilDb.getConnection();

            req.setAttribute("historiqueProduits", HistoriqueProduit.getByCriteria(connection, idProduit));

        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
            // System.err.println(err);
            err.printStackTrace();
        }
        processRequest(req, res);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      
       
        String idProduit = req.getParameter("idProduit") ;
        String prix = req.getParameter("prix") ;  
        String dateDebut = req.getParameter("dateDebut") ; 
        String dateFin = req.getParameter("dateFin") ; 

        try{
            Connection connection = utilDb.getConnection();
            Produit produit = new Produit(idProduit);
            produit.setPrixVente(prix);
            produit.update(connection, dateDebut, dateFin);
            req.setAttribute("historiqueProduits", HistoriqueProduit.getByCriteria(connection, null));

            req.setAttribute("message", "Produit mis à jour");

        } catch(Exception err) {
            err.printStackTrace();
            req.setAttribute("message", err.getMessage());
        }
        
        processRequest(req, res);
    }

    void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/pages/CRUD/historiqueProduit.jsp");
        try{
            Connection connection = utilDb.getConnection();
            req.setAttribute("produits", Produit.getAll(connection));

        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
            // System.err.println(err);
            err.printStackTrace();
        }
        rd.forward(req, res);    
    }
}

