package com.controller.CRUD.client;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.production.vente.Vente;
import com.service.connection.UtilDb;

@WebServlet("/CRUD/client")
public class ClientController extends HttpServlet {
    
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

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/pages/CRUD/client.jsp");
        try {
            Connection connection = utilDb.getConnection();
            String dateDebut = req.getParameter("dateDebut");
            String dateFin = req.getParameter("dateFin");
            
            req.setAttribute("ventes", Vente.getByCriteria(connection, null, null, null, null, dateDebut, dateFin));

        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
        }
        rd.forward(req, res);
    }
}
