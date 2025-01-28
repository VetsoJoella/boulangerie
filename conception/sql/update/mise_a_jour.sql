ALTER TABLE produit
ADD COLUMN idProduit VARCHAR(50);

ALTER TABLE produit
ADD CONSTRAINT fgn FOREIGN KEY (idProduit)
REFERENCES type (id)
-- ON DELETE action
-- ON UPDATE action;

-- ALTER TABLE produit
-- RENAME COLUMN idProduit TO idType;


insert into type(nom) values('pain'),('pizza')

-- Jeudi 14 janvier

-- SELECT conname
-- FROM pg_constraint
-- WHERE conrelid = 'vente'::regclass AND contype = 'f';

-- ALTER TABLE vente DROP CONSTRAINT vente_idproduit_fkey;

ALTER TABLE vente
ADD CONSTRAINT vente_idproduit_fkey
FOREIGN KEY (idProduit) REFERENCES produitCaracteristique(id);


-- Jeudi 16 janvier

-- ALTER TABLE vente
-- ADD COLUMN idClient VARCHAR(50);

ALTER TABLE vente
ADD CONSTRAINT fgnClient FOREIGN KEY (idClient)
REFERENCES client (id)

-- Vendeur - Talata 21 janvier

-- ALTER TABLE vente
-- ADD COLUMN idVendeur VARCHAR(50);

-- ALTER TABLE vente
-- ADD COLUMN commission NUMERIC(10,2)  ; 

-- ALTER TABLE vente
-- ADD CONSTRAINT fgnVendeur FOREIGN KEY (idVendeur)
-- REFERENCES vendeur (id)


-- Jeudi 23 janvier

ALTER TABLE vendeur
ADD COLUMN idGenre VARCHAR(50);

-- ALTER TABLE vendeur
-- ADD CONSTRAINT fgnGenre FOREIGN KEY (idGenre)
-- REFERENCES genre (id)