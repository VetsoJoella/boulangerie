package com.controller.CRUD.produit;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.produit.Produit;
import com.model.produit.base.ProduitBase;
import com.model.produit.saveur.Saveur;
import com.model.produit.variete.Variete;
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
        
        String idVariete = req.getParameter("idVariete"), saveur = req.getParameter("idSaveur");
        String idProduitBase = req.getParameter("iProduitBase") ;
        try {
            Connection connection = utilDb.getConnection();
            req.setAttribute("produits", Produit.getByCriteria(connection, idProduitBase, saveur, idVariete));

        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
            err.printStackTrace();
        }
        
        processRequest(req, res);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      
        System.out.println("Appel de do post produit");
        String idSaveur = req.getParameter("idSaveur"), idProduitBase = req.getParameter("idProduitBase");
        String prix = req.getParameter("prix");

        try{
            Connection connection = utilDb.getConnection();
            Produit produit = new Produit(idProduitBase, idSaveur, prix);
            produit.insert(connection);

            req.setAttribute("message", "Produit bien insérée");
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
            req.setAttribute("varietes", new Variete().getAll(connection));
            req.setAttribute("saveurs", new Saveur().getAll(connection));
            req.setAttribute("produitBases", ProduitBase.getAll(connection));

        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
            System.err.println(err);
        }
        rd.forward(req, res);    
    }
}
