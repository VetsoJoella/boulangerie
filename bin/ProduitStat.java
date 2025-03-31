package com.controller.statistique;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.production.vente.MeilleurVente;
import com.service.connection.UtilDb;

@WebServlet("/stat/produit")
public class ProduitStat extends HttpServlet{
    
    UtilDb utilDb ;

    @Override
    public void init() throws ServletException {
        super.init();
        utilDb = (UtilDb) getServletContext().getAttribute("utilDb");
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
       
        processRequest(req,res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
       processRequest(req, res);
    }

    void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String dateMin = req.getParameter("dateMin"), dateMax = req.getParameter("dateMax") ; 
        try {
            Connection connection= utilDb.getConnection();
            MeilleurVente meilleurVente = new MeilleurVente(dateMin, dateMax);
            meilleurVente.setVentes(connection);
            req.setAttribute("ventes", meilleurVente.getVentes());
            
        } catch (Exception e) {
            req.setAttribute("message", e.getMessage());
            e.printStackTrace();
        }
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/pages/statistique/stat-produit.jsp");
        rd.forward(req, res);
    }
}