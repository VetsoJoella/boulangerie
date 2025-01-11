package com.controller.CRUD.fabrication;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.ingredient.Ingredient;
import com.model.produit.Produit;
import com.model.produit.base.ProduitBase;
import com.model.produit.recette.Recette;
import com.model.produit.variete.Variete;
import com.service.connection.UtilDb;

@WebServlet("/CRUD/recette")
public class RecetteCRUD extends HttpServlet{
    
    UtilDb utilDb ;

    @Override
    public void init() throws ServletException {
        super.init();
        utilDb = (UtilDb) getServletContext().getAttribute("utilDb");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String idProduit = req.getParameter("idProduit"), idProduitBase = req.getParameter("idProduitBase");
        String idIngredient = req.getParameter("idIngredient"), idVariete = req.getParameter("idVariete");
        try {
            Connection connection = utilDb.getConnection();
            req.setAttribute("recettes", Recette.getByCriteriaString(connection, idProduit, idProduitBase, idIngredient, idVariete));


        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
        }



       processRequest(req, res);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      
        String ingredient = req.getParameter("idIngredient");
        String produit = req.getParameter("idProduit");
        String quantite = req.getParameter("quantite"); 

        try{
            Recette recette = new Recette(produit, ingredient, quantite);

            Connection connection = utilDb.getConnection();
            req.setAttribute("recettes", Recette.getByCriteria(connection, null, null, null, null));

            connection.setAutoCommit(false);
            recette.insert(connection);
            connection.commit();
            connection.setAutoCommit(true);
            req.setAttribute("message", "Insertion recette effectuée");

        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
        }

        processRequest(req, res);

    }

    void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/pages/CRUD/recette.jsp");
        
        try{
            Connection connection = utilDb.getConnection();
            req.setAttribute("produitBases", ProduitBase.getAll(connection));
            req.setAttribute("varietes", new Variete().getAll(connection));
            req.setAttribute("produits", Produit.getAll(connection));
            req.setAttribute("ingredients", Ingredient.getAll(connection));

        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
            System.err.println(err);
        }
        rd.forward(req, res);
    }
}
