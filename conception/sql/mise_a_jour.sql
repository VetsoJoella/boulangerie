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