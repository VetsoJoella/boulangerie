-- Création des séquences pour chaque table
CREATE DATABASE Boulangerie;
\c boulangerie;



CREATE SEQUENCE s_unite START 1 INCREMENT 1;
CREATE SEQUENCE s_ingredient START 1 INCREMENT 1;
CREATE SEQUENCE s_produit START 1 INCREMENT 1;
CREATE SEQUENCE s_recette START 1 INCREMENT 1;
CREATE SEQUENCE s_achatIngredient START 1 INCREMENT 1;
CREATE SEQUENCE s_fabrication START 1 INCREMENT 1;
CREATE SEQUENCE s_vente START 1 INCREMENT 1;
CREATE SEQUENCE s_historiqueproduit START 1 INCREMENT 1;
CREATE SEQUENCE s_detailFabrication START 1 INCREMENT 1;
CREATE SEQUENCE s_variete START 1 INCREMENT 1;
CREATE SEQUENCE s_saveur START 1 INCREMENT 1;
CREATE SEQUENCE s_produitBase START 1 INCREMENT 1;
CREATE SEQUENCE s_conseilDuMois START 1 INCREMENT 1;

-- Remarque : Les séquences seront utilisées par les colonnes par défaut dans vos tables grâce à nextval().
