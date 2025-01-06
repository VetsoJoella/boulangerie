package com.controller.statistique;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.ingredient.AchatIngredient;
import com.model.production.vente.Vente;
import com.model.rapport.Rapport;
import com.service.connection.UtilDb;

@WebServlet("/stat/achat")
public class AchatStat extends HttpServlet{
    
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
        processRequest(req,res);

    }

    void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        System.out.println("Appel de process request dans achatStat");
        String dateMin = req.getParameter("dateMin"), dateMax = req.getParameter("dateMax") ;
        try {
            Connection connection = utilDb.getConnection();

            Vente[] ventes = Vente.getByCriteria(connection, null, dateMin, dateMax);
            AchatIngredient[] achatIngredients = AchatIngredient.getByCriteria(connection, null, dateMin, dateMax);

            Rapport rapportVente = Vente.getRapport(ventes);
            Rapport rapportAchat = AchatIngredient.getRapport(achatIngredients);
            req.setAttribute("rapportVente", rapportVente);
            req.setAttribute("rapportAchat", rapportAchat);
            req.setAttribute("achats", rapportAchat.mapsEnString());
            req.setAttribute("ventes", rapportVente.mapsEnString());
            System.out.println("Ventes en string est "+ rapportVente.mapsEnString() +" Achats : "+rapportAchat.mapsEnString());
        } catch (Exception e) {
            req.setAttribute("message", e.getMessage());
            e.printStackTrace();
        } finally {
            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/pages/statistique/stat-achat-vente.jsp");
            rd.forward(req, res);
        }

    }
}