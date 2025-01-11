package com.controller.CRUD.produit;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.produit.base.ProduitBase;
import com.model.produit.variete.Variete;
import com.service.connection.UtilDb;

@WebServlet("/CRUD/produitBase")
public class ProduitBaseCRUD extends HttpServlet{
    
    UtilDb utilDb ;

    @Override
    public void init() throws ServletException {
        super.init();
        utilDb = (UtilDb) getServletContext().getAttribute("utilDb");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
       String idVariete = req.getParameter("idVariete") ; 
       try {
            ProduitBase[] produitBases = ProduitBase.getByCriteria(utilDb.getConnection(), idVariete) ; 
            req.setAttribute("produits", produitBases);

       } catch (Exception e) {
            req.setAttribute("message", e.getMessage());

       }
        
        processRequest(req, res);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      
        System.out.println("Appel de do post produit base");
     
        String nom = req.getParameter("nom"), idVariete = req.getParameter("idVariete");
        try {
            ProduitBase produitBase = new ProduitBase("", nom);
            produitBase.setVariete(idVariete);

            produitBase.insert(utilDb.getConnection());
            req.setAttribute("message", "Produit de base insérée");
            req.setAttribute("produits",ProduitBase.getAll(utilDb.getConnection()));


        } catch (Exception e) {
            req.setAttribute("message", e.getMessage());

        }
        processRequest(req, res);
    }

    void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/pages/CRUD/produit-base.jsp");
        try{
            Variete[] varietes= new Variete().getAll(utilDb.getConnection()) ;
            req.setAttribute("varietes", varietes);


        } catch(Exception err) {
            req.setAttribute("message", err.getMessage());
            System.err.println(err);
        }
        rd.forward(req, res);    
    }
}
