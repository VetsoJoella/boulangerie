package com.controller.CRUD;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.connection.UtilDb;
import com.model.produit.type.Type;


@WebServlet("/CRUD/type")
public class TypeCRUD extends HttpServlet{
    
    UtilDb utilDb ;

    @Override
    public void init() throws ServletException {
        super.init();
        utilDb = (UtilDb) getServletContext().getAttribute("utilDb");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
       
       if(req.getParameter("idType")!=null) {
            // Unite type = new Unite(req.getParameter("idUnite"));
            Type type = new Type(req.getParameter("idUnite"));
            try{
                Connection connection = utilDb.getConnection();
                // type.delete(connection);

            } catch(Exception err) {
                req.setAttribute("message", err.getMessage());
            }
       }
       processRequest(req, res);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        String nom = req.getParameter("nom");

        Type type =new Type();
        type.setNom(nom);

        try{
            Connection connection = utilDb.getConnection();
            connection.setAutoCommit(false);
            type.insert(connection);
            connection.commit();
            connection.setAutoCommit(true);
            req.setAttribute("message", "Insertion unité effectué");

        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
        }
        processRequest(req, res);


    }

    void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/pages/CRUD/type.jsp");

        try{
            Connection connection = utilDb.getConnection();
            req.setAttribute("types", Type.getAll(connection));

        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
            System.err.println(err);
        }
        rd.forward(req, res);
    }
}
