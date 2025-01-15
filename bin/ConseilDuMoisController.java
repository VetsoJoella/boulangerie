package com.controller.CRUD.conseil;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.ingredient.Ingredient;
import com.model.production.conseil.ConseilDuMois;
import com.model.produit.Produit;
import com.service.connection.UtilDb;

@WebServlet("/CRUD/conseilDuMois")
public class ConseilDuMoisController extends HttpServlet {
    
     
    UtilDb utilDb ;
    String[] mois = new String[]{"Janvier", "Février", "Mars", "Avril", "Mai", "Juin","Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};

    @Override
    public void init() throws ServletException {
        super.init();
        utilDb = (UtilDb) getServletContext().getAttribute("utilDb");
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        try {
            Connection connection = utilDb.getConnection();
            String annee = req.getParameter("annee") ; 
            String mois = req.getParameter("mois") ; 
            System.out.println("Valeur de mois est "+mois+" - Année est "+annee);
            
            req.setAttribute("conseilDuMois", ConseilDuMois.getByCriteria(connection, annee, mois));

        } catch(Exception err) {
            err.printStackTrace();
            req.setAttribute("message", err.getMessage());
        }
        processRequest(req, res);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
       String idProduit = req.getParameter("idProduit");
       String dateDebut = req.getParameter("dateDebut"), dateFin = req.getParameter("dateFin");

        System.out.println("Date début dans controller "+dateDebut+" Date fin "+dateFin);
        try{
            Connection connection = utilDb.getConnection();
            ConseilDuMois conseilDuMois = new ConseilDuMois(idProduit, dateDebut, dateFin);
            conseilDuMois.insert(connection);

            req.setAttribute("conseilDuMois", ConseilDuMois.getByCriteria(connection, 0, 0));
            req.setAttribute("message", "Conseil du mois inséré ");


        } catch(Exception err) {
            err.printStackTrace();
            req.setAttribute("message", err.getMessage());
        }
        processRequest(req, res);

    }

    void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/pages/conseil/conseil-mois.jsp");
        try{
            Connection connection = utilDb.getConnection();
            req.setAttribute("mois", mois);
            req.setAttribute("produits", Produit.getAll(connection));

        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
            System.err.println(err);
            err.printStackTrace();
        }
        rd.forward(req, res);
    }
}
