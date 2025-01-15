package com.controller.CRUD.achat;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.ingredient.AchatIngredient;
import com.model.ingredient.Ingredient;
import com.service.connection.UtilDb;

@WebServlet("/CRUD/achatIngredient")
public class AchatIngredientCRUD extends HttpServlet{
    
    UtilDb utilDb ;

    @Override
    public void init() throws ServletException {
        super.init();
        utilDb = (UtilDb) getServletContext().getAttribute("utilDb");
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        try {
            Connection connection = utilDb.getConnection();
            String idIngredient = req.getParameter("idIngredient");
            String dateMin = req.getParameter("dateMin") ; 
            String dateMax = req.getParameter("dateMax") ; 
            
            req.setAttribute("achats", AchatIngredient.getByCriteria(connection, idIngredient, dateMin, dateMax));


        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
        }
        processRequest(req, res);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        String ingredient = req.getParameter("idIngredient");
        String quantite = req.getParameter("quantite"); 
        String date = req.getParameter("date"); 
        String pU = req.getParameter("prix"); 


        try{
            Connection connection = utilDb.getConnection();
            AchatIngredient achatIngredient = new AchatIngredient(ingredient, date, pU, quantite, "0");
            connection.setAutoCommit(false);
            achatIngredient.insert(connection);
            connection.commit();
            connection.setAutoCommit(true);
            req.setAttribute("achats", AchatIngredient.getByCriteria(connection, null, null, null));
            req.setAttribute("message", "Achat effectué");


        } catch(Exception err) {
            // err.printStackTrace();
            req.setAttribute("message", err.getMessage());
        }
        processRequest(req, res);

    }

    void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/pages/CRUD/achat-ingredient.jsp");
        try{
            Connection connection = utilDb.getConnection();
            req.setAttribute("ingredients", Ingredient.getAll(connection));

        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
            System.err.println(err);
        }
        rd.forward(req, res);
    }
}
