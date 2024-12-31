package com.controller.CRUD;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.ingredient.unite.Unite;
import com.service.connection.UtilDb;


@WebServlet("/CRUD/unite")
public class UniteCRUD extends HttpServlet{
    
    UtilDb utilDb ;

    @Override
    public void init() throws ServletException {
        super.init();
        utilDb = (UtilDb) getServletContext().getAttribute("utilDb");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
       
       if(req.getParameter("idUnite")!=null) {
            Unite unite = new Unite(req.getParameter("idUnite"));
            try{
                Connection connection = utilDb.getConnection();
                unite.delete(connection);

            } catch(Exception err) {
                req.setAttribute("message", err.getMessage());
            }
       }
       processRequest(req, res);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        String nom = req.getParameter("nom");

        Unite unite =new Unite();
        unite.setNom(nom);

        try{
            Connection connection = utilDb.getConnection();
            connection.setAutoCommit(false);
            unite.insert(connection);
            connection.commit();
            connection.setAutoCommit(true);

        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
        }
        processRequest(req, res);


    }

    void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/pages/CRUD/unite.jsp");

        try{
            Connection connection = utilDb.getConnection();
            req.setAttribute("unites", Unite.getAll(connection));

        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
            System.err.println(err);
        }
        rd.forward(req, res);
    }
}
