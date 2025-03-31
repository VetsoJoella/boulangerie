package com.controller.CRUD.ingredient;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.ingredient.Ingredient;
import com.model.ingredient.unite.Unite;
import com.service.connection.UtilDb;

@WebServlet("/CRUD/ingredient")
public class IngredientCRUD extends HttpServlet{
    
    UtilDb utilDb ;

    @Override
    public void init() throws ServletException {
        super.init();
        utilDb = (UtilDb) getServletContext().getAttribute("utilDb");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        if(req.getParameter("idIngredient")!=null) {

            Ingredient ingredient = new Ingredient(req.getParameter("idIngredient"));
            try{
                Connection connection = utilDb.getConnection();
                ingredient.delete(connection);

            } catch(Exception err) {
                req.setAttribute("message", err.getMessage());
            }
       }
       processRequest(req, res);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        String nom = req.getParameter("nom");
        String idUnite = req.getParameter("idUnite");
        Ingredient ingredient = new Ingredient(null, nom, idUnite);

        try {
            Connection connection = utilDb.getConnection();
            connection.setAutoCommit(false);
            ingredient.insert(connection);
            connection.commit();
            connection.setAutoCommit(true);
            req.setAttribute("message", "Insertion ingredient effectué");

        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
        }
        processRequest(req, res);
    }

    void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/pages/CRUD/ingredient.jsp");
        try{
            Connection connection = utilDb.getConnection();
            req.setAttribute("unites", new Unite().getAll(connection));
            req.setAttribute("ingredients", Ingredient.getAll(connection));

        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
            System.err.println(err);
        }
        rd.forward(req, res);    
    }
}

