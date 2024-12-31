
-- Liaison recette ~ ingredient ~ produit 

create or replace view v_recette_ingredient_produit as 
    SELECT r.id, idProduit, p.nom as nomProduit, d_prixvente, 
           idIngredient, i.nom as nomIngredient, idunite, u.nom as nomUnite, quantite 
    FROM recette r join produit p on idProduit = p.id join ingredient i on i.id = idIngredient join  unite u on u.id = idUnite ; 


-- Liaison 

create or replace view v_achat_ingredient_unite as 
    select a.id, dateAchat, prixUnitaire, quantiteAchete, d_reste, idIngredient, 
            i.nom as nomIngredient, idunite, u.nom as nomUnite 
            from achatIngredient a join ingredient i on i.id = idIngredient join unite u on u.id = idUnite


-- Etat stock ingredient:

create or replace view v_stock_ingredient as 
    select idIngredient, sum(d_reste) as reste from achatIngredient group by idIngredient  

create or replace view v_stock_ingredient_details as 
    select a.*, i.nom as nomIngredient , idUnite, u.nom as nomUnite 
    from v_stock_ingredient a join ingredient i on idIngredient = i.id 
    join unite u on u.id = idUnite  

-- Stock  une date t :
 
select a.*, i.nom as nomIngredient , idUnite, u.nom as nomUnite 
    from (select idIngredient, sum(d_reste) as reste from achatIngredient group by idIngredient where dateAchat <= date) as a
    join ingredient i on idIngredient = i.id 
    join unite u on u.id = idUnite


-- Etat stock produit : 

create or replace view v_stock_produit as 
    select idProduit, sum(d_reste) as reste from fabrication group by idProduit  

create or replace view v_stock_produit_details as 
    select s.*, p.nom as nomProduit, d_prixVente 
    from v_stock_produit s join produit p on idProduit = p.id 


select p.id, nom, quantite from produit p join (select idProduit, sum(quantiteVente) as quantite from vente group by idProduit) as v on idProduit = p.id