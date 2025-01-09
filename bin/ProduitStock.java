package com.controller.stock;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.stock.Stock;
import com.model.stock.StockProduit;
import com.service.connection.UtilDb;

@WebServlet("/stock/produit")
public class ProduitStock extends HttpServlet{
  
    UtilDb utilDb ;

    @Override
    public void init() throws ServletException {
        super.init();
        utilDb = (UtilDb) getServletContext().getAttribute("utilDb");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
       try {
            Stock[] stockProduits = StockProduit.getStock(utilDb.getConnection());
            req.setAttribute("stocks", stockProduits);
            System.out.println("Longueurs des stocks est "+stockProduits.length);

       } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
            err.printStackTrace();
       }
        processRequest(req,res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      
    }

    void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/pages/stock/stock-produit.jsp");
        rd.forward(req, res);
    }
}