package com.controller.CRUD.vente.commission;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.production.vente.commission.Commission;
import com.service.connection.UtilDb;

@WebServlet("/CRUD/commissionGenre")
public class CommissionGenreController  extends HttpServlet {
 
    UtilDb utilDb ;

    @Override
    public void init() throws ServletException {
        super.init();
        utilDb = (UtilDb) getServletContext().getAttribute("utilDb");
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        processRequest(req, res);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        processRequest(req, res);

    }

    void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/pages/CRUD/commission-genre.jsp");
        try {
            Connection connection = utilDb.getConnection();
            String dateDebut = req.getParameter("dateDebut");
            String dateFin = req.getParameter("dateFin");
            
            Map<String, Commission[]> commissions = Commission.getByGenre(connection, dateDebut, dateFin) ; 
            req.setAttribute("commissions", commissions);
            req.setAttribute("sommes", getSommeParGenre(commissions));

            // req.setAttribute("ventes", Vente.getByCriteria(connection, null, null, null, null, dateDebut, dateFin));

        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
        }
        rd.forward(req, res);
    }

    private Double[] getSommeParGenre(Map<String, Commission[]> commissions){

        List<Double> sommes = new ArrayList<>() ;
        
        for (Map.Entry<String, Commission[]> commission : commissions.entrySet()) { 
                String genre = commission.getKey();
                Commission[] listes = commission.getValue();
                sommes.add(Commission.sommeCommission(listes));

        }

        return sommes.toArray(new Double[0]) ;

    }
}
