package com.service.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UtilDb {

    String base ; 
    String user ;
    String pwd ;
    Connection connection ;
    
    public UtilDb(String base, String user, String pwd) throws Exception {
        try {
            String jdbcUrl = "jdbc:postgresql://localhost:5432/";  // Modifiez si nécessaire
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(jdbcUrl + base, user, pwd);
        }
        catch (ClassNotFoundException e) {
            System.err.println("Pilote PostgreSQL JDBC introuvable !");
            throw e;
        } 
        catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
            throw e;
        }
    }
    
    public Connection getConnection() throws Exception{
        return connection;
       
    }

    public String insertDate(String date){
        return "'"+date+"'";
    }
    
}