package com.configuration;

import java.rmi.server.ExportException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CommissionConfig {
    
    public static double commission;  
    public static double minCommission ;

    public CommissionConfig(Connection connection) throws Exception{
        set(connection);
    }

    void set(Connection connection) throws Exception{
        setCommission(connection); setMinCommission(connection);
    }

    void setCommission(Connection connection) throws Exception{

        String sql = "SELECT pourcentage FROM commission order by dateChangement";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {

               commission = rs.getDouble("pourcentage");
               break ; 
            }
        } 


    }

    void setMinCommission(Connection connection) throws Exception{

        String sql = "SELECT montant FROM mincommission order by dateChangement";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {

               minCommission = rs.getDouble("montant");
               break ; 
            }
        } 

    }

    public double COMMISSION() {
        return commission;
    }

    public double MIN_COMMISSION() {
        return minCommission;
    }
}
