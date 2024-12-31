package com.exception;

public class DonneesManquantesException extends Exception {

    private Object objet; // Attribut pour stocker l'objet associé à l'exception

    // Getter pour l'objet
    public Object getObjet() {
        return objet;
    }

    // Setter pour l'objet
    public void setObjet(Object objet) {
        this.objet = objet;
    }
    public DonneesManquantesException() {
        super();
    }

    public DonneesManquantesException(String message) {
        super(message);
    }

    public DonneesManquantesException(String message, Object objet) {
        super(message);
        setObjet(objet);
    }


    @Override
    public String toString() {
        return "DonneesManquantesException{" +
               "message='" + getMessage() + '\'' +
               ", objet=" + objet +
               '}';
    }
}
