CREATE TABLE unite(
   id VARCHAR(50)  DEFAULT ('U') || LPAD(nextval('s_unite')::TEXT, 5, '0'),
   nom VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE ingredient(
   id VARCHAR(50)  DEFAULT ('ING') || LPAD(nextval('s_ingredient')::TEXT, 5, '0'),
   nom VARCHAR(50)  NOT NULL,
   idUnite VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idUnite) REFERENCES unite(id)
);

CREATE TABLE achatIngredient(
   id VARCHAR(50)  DEFAULT ('ACT_ING') || LPAD(nextval('s_achatIngredient')::TEXT, 5, '0'),
   dateAchat TIMESTAMP default current_timestamp,
   prixUnitaire NUMERIC(10,2)   default 0.0,
   quantiteAchete NUMERIC(7,2)   default 0.0,
   d_reste NUMERIC(7,2)   default 0.0,
   idIngredient VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idIngredient) REFERENCES ingredient(id)
);

CREATE TABLE variete(
   id VARCHAR(50)  DEFAULT ('VRT') || LPAD(nextval('s_variete')::TEXT, 5, '0'),
   nom VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE saveur(
   id VARCHAR(50)   DEFAULT ('SVR') || LPAD(nextval('s_saveur')::TEXT, 5, '0'),
   nom VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE client(
   id VARCHAR(50)  DEFAULT ('CLT') || LPAD(nextval('s_client')::TEXT, 5, '0'),
   nom VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE produitBase(
   id VARCHAR(50)  DEFAULT ('PRD_BS') || LPAD(nextval('s_produitBase')::TEXT, 5, '0'),
   nom VARCHAR(30)  NOT NULL,
   idVariete VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idVariete) REFERENCES variete(id)
);

CREATE TABLE produit(
   id VARCHAR(50)  DEFAULT ('PRD') || LPAD(nextval('s_produit')::TEXT, 5, '0'),
   d_prixVente NUMERIC(15,2)   default 0,
   idSaveur VARCHAR(50)  NOT NULL,
   idProduitBase VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idSaveur) REFERENCES saveur(id),
   FOREIGN KEY(idProduitBase) REFERENCES produitBase(id)
);

CREATE TABLE recette(
   id VARCHAR(50)  DEFAULT ('RCT') || LPAD(nextval('s_recette')::TEXT, 5, '0'),
   quantite NUMERIC(5,2)   default 0.0,
   idProduit VARCHAR(50)  NOT NULL,
   idIngredient VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idProduit) REFERENCES produit(id),
   FOREIGN KEY(idIngredient) REFERENCES ingredient(id)
);

CREATE TABLE fabrication(
   id VARCHAR(50)  DEFAULT ('FBR') || LPAD(nextval('s_fabrication')::TEXT, 5, '0'),
   quantiteFabrication INTEGER NOT NULL,
   dateFabrication TIMESTAMP default current_timestamp,
   d_reste INTEGER default 0,
   idProduit VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idProduit) REFERENCES produit(id)
);

CREATE TABLE vente(
   id VARCHAR(50)  DEFAULT ('VNT') || LPAD(nextval('s_vente')::TEXT, 5, '0'),
   dateVente TIMESTAMP default current_timestamp,
   quantiteVente INTEGER default 0,
   d_prixUnitaire NUMERIC(10,2)   default 0.0,
   idClient VARCHAR(50)  NOT NULL,
   idProduit VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idClient) REFERENCES client(id),
   FOREIGN KEY(idProduit) REFERENCES produit(id)
);

CREATE TABLE historiquePrixProduit(
   id VARCHAR(50)  DEFAULT ('HST_PRD') || LPAD(nextval('s_historiqueproduit')::TEXT, 5, '0'),
   dateProduit TIMESTAMP default current_timestamp,
   prixProduit NUMERIC(8,2)   default 0.0,
   idProduit VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idProduit) REFERENCES produit(id)
);

CREATE TABLE detailfabrication(
   id VARCHAR(50)  DEFAULT ('DTL_FBR') || LPAD(nextval('s_detailFabrication')::TEXT, 5, '0'),
   d_quantite VARCHAR(50) ,
   idIngredient VARCHAR(50)  NOT NULL,
   idFabrication VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idIngredient) REFERENCES ingredient(id),
   FOREIGN KEY(idFabrication) REFERENCES fabrication(id)
);
