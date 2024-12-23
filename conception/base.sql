CREATE TABLE unite(
   id VARCHAR(50)  default('U') || LPAD(nextval('s_unite')::TEXT,3,'0'),
   nom VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE ingredient(
   id VARCHAR(50)  default('ING') || LPAD(nextval('s_ingredient')::TEXT,3,'0'),
   nom VARCHAR(50)  NOT NULL,
   idUnite VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idUnite) REFERENCES unite(id)
);

CREATE TABLE produit(
   id VARCHAR(50)  default('PRD') || LPAD(nextval('s_produit')::TEXT,3,'0'),
   d_prixVente NUMERIC(8,2)   default 0,
   nom VARCHAR(30)  NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE recette(
   id VARCHAR(50)  default('RCT') || LPAD(nextval('s_recette')::TEXT,3,'0'),
   quantite NUMERIC(5,2)   default 0.0,
   idProduit VARCHAR(50)  NOT NULL,
   idIngredient VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idProduit) REFERENCES produit(id),
   FOREIGN KEY(idIngredient) REFERENCES ingredient(id)
);

CREATE TABLE achatIngredient(
   id VARCHAR(50)  default('ACT_ING') || LPAD(nextval('s_achatIngredient')::TEXT,3,'0'),
   dateAchat TIMESTAMP default current_timestamp,
   prixUnitaire NUMERIC(10,2)   default 0.0,
   quantiteAchete NUMERIC(7,2)   default 0.0,
   d_reste NUMERIC(7,2)   default 0.0,
   idIngredient VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idIngredient) REFERENCES ingredient(id)
);

CREATE TABLE fabrication(
   id VARCHAR(50)  default('FBR') || LPAD(nextval('s_fabrication')::TEXT,3,'0'),
   quantiteFabrication INTEGER NOT NULL,
   dateFabrication TIMESTAMP default current_timestamp,
   idProduit VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idProduit) REFERENCES produit(id)
);

CREATE TABLE vente(
   id VARCHAR(50)  default('VNT') || LPAD(nextval('s_vente')::TEXT,3,'0'),
   dateVente TIMESTAMP default current_timestamp,
   quantiteVente INTEGER default 0,
   d_prixUnitaire NUMERIC(10,2)   default 0.0,
   idProduit VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idProduit) REFERENCES produit(id)
);

CREATE TABLE historiqueProduit(
   id VARCHAR(50)  default('HST_PRD') || LPAD(nextval('s_historiqueproduit')::TEXT,3,'0'),
   dateProduit TIMESTAMP default current_timestamp,
   prixProduit NUMERIC(8,2)   default 0.0,
   idProduit VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(idProduit) REFERENCES produit(id)
);
