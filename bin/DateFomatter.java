package com.service.util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFomatter {
    
    public static String formatterDate(Date date) {
        LocalDate localDate = date.toLocalDate();
        
        // Définir le formatteur pour le format yyyy-MM-dd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Formater la date en string
        String dateFormatee = localDate.format(formatter);
        return dateFormatee;
    }
}
