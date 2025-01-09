package com.controller.CRUD;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.production.vente.Vente;
import com.model.produit.Produit;
import com.service.connection.UtilDb;

@WebServlet("/CRUD/vente")
public class VenteCRUD extends HttpServlet {
    
    UtilDb utilDb ;

    @Override
    public void init() throws ServletException {
        super.init();
        utilDb = (UtilDb) getServletContext().getAttribute("utilDb");
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        try {
            Connection connection = utilDb.getConnection();
            String idProduit = req.getParameter("idProduit");
            String dateMin = req.getParameter("dateMin") ; 
            String dateMax = req.getParameter("dateMax") ; 
            
            req.setAttribute("ventes", Vente.getByCriteria(connection, idProduit, dateMin, dateMax));

        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
        }
        processRequest(req, res);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      
        String idProduit = req.getParameter("idProduit");
        String quantite = req.getParameter("quantite");
        String date = req.getParameter("date");

        Vente[] ventes = new Vente[0] ; 
        try {
            Connection connection = utilDb.getConnection();
            Vente vente = new Vente(null, quantite, date, idProduit);
            vente.insert(connection);
            ventes =  Vente.getByCriteria(connection, null, null, null) ;
            req.setAttribute("message", "Vente insérée");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());

        } finally{
            req.setAttribute("ventes",ventes);
            processRequest(req, res);
        }
    }

    void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/pages/CRUD/vente.jsp");
        try{
            Connection connection = utilDb.getConnection();
            req.setAttribute("produits", Produit.getAll(connection));

        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
            System.err.println(err);
        }
        rd.forward(req, res);
    }
}
