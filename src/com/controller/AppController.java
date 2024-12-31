package com.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.service.config.Configuration;
import com.service.connection.UtilDb;

@WebListener
public class AppController implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            UtilDb utilDb = new UtilDb(Configuration.BASE, Configuration.USER, Configuration.PWD);
            sce.getServletContext().setAttribute("utilDb", utilDb);
        } catch(Exception err) {
            err.printStackTrace();
        }
      
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
}

