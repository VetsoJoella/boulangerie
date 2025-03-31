package com.service.config;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.Properties;

public class ConfigUtil {

    private static final Properties properties = new Properties();
    private static boolean isLoaded = false;  // Indicateur pour vérifier si les propriétés sont chargées correctement

    // Chemin vers le fichier de configuration (en utilisant un chemin relatif)
    private static final String CONFIG_FILE = "/WEB-INF/config.properties";

    // Chargement des propriétés au démarrage de la classe
    public static void loadProperties(ServletContext context) {
        try (InputStream input = context.getResourceAsStream(CONFIG_FILE)) {
            // Vérifier si le fichier de configuration est trouvé
            if (input == null) {
                System.err.println("Erreur : Le fichier de configuration '" + CONFIG_FILE + "' n'a pas été trouvé.");
                return;
            }
            // Charger les propriétés depuis le fichier
            properties.load(input);
            isLoaded = true;
            System.out.println("Le fichier de configuration '" + CONFIG_FILE + "' a été chargé avec succès.");
        } catch (IOException ex) {
            System.err.println("Erreur lors du chargement du fichier de configuration '" + CONFIG_FILE + "'.");
            ex.printStackTrace();
        }
    }

    // Méthode pour récupérer une propriété par clé
    public static String getProperty(String key) {
        if (!isLoaded) {
            System.err.println("Erreur : Les propriétés n'ont pas été chargées correctement.");
            return null;
        }

        String value = properties.getProperty(key);
        if (value == null) {
            System.err.println("Avertissement : La propriété '" + key + "' n'existe pas dans le fichier de configuration.");
        }
        return value;
    }

    // Méthode pour récupérer une propriété avec une valeur par défaut
    public static String getProperty(String key, String defaultValue) {
        if (!isLoaded) {
            System.err.println("Erreur : Les propriétés n'ont pas été chargées correctement.");
            return defaultValue;
        }

        return properties.getProperty(key, defaultValue);
    }

    // Méthode pour vérifier si une propriété existe
    public static boolean containsKey(String key) {
        return properties.containsKey(key);
    }
}
