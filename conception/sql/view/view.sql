
-- Liaison historique ~ Produit 
create or replace view v_historique_produit_produitBase as
    select h.*, idProduitBase, idSaveur, idVariete, d_prixVente from historiquePrixProduit h 
    join produit p on h.idProduit = p.id 
    join produitBase pB on p.idProduitBase = pB.id  ;

create or replace view v_historique_produit_produitBase_detail as
    select v.*, s.nom as nomSaveur, vrt.nom as nomVariete, pB.nom as nomProduitBase from v_historique_produit_produitBase v 
    join saveur s on s.id = idSaveur 
    join variete vrt on vrt.id = idVariete join produitBase pB on pB.id = idProduitBase;


-- Liaision produitBase ~ variété 

create or replace view v_produitBase_variete as
    select pB.*, vrt.nom as nomVariete from produitBase pB 
    join variete vrt on pB.idVariete = vrt.id ;


-- Liaison produit ~ saveur
create or replace view v_produit_saveur as
    select p.*, s.nom as nomSaveur from produit p 
    join saveur s on idSaveur = s.id ;


-- Liaison produit ~ saveur ~ produitBase

create or replace view v_produit_saveur_variete as 
    select p.*, idVariete from produit p 
    join produitBase pB on pB.id = idProduitBase ; 

create or replace view v_produit_saveur_variete_detail as 
    select vpsv.*, vpv.nom as nomProduitBase, nomVariete, s.nom as nomSaveur  from v_produit_saveur_variete vpsv
    join v_produitBase_variete vpv on vpv.id = vpsv.idProduitBase join saveur s on s.id = vpsv.idSaveur;
    

-- Liaison recette ~ produit ~ produitBase 
create or replace view v_recette_produit as
    select r.*, idProduitBase, idSaveur, idVariete from recette r join produit p on p.id = idProduit 
    join produitBase pB on pB.id = p.idProduitBase;
       

-- Liaison fabrication ~ produit 

create or replace view v_fabrication_produit as 
    select f.*, idSaveur, idVariete, idProduitBase from fabrication f 
    join v_produit_saveur_variete vp on idProduit = vp.id  ;


-- Liaison vente ~ produit 
create or replace view v_vente_produit as 
    select v.*, d_prixVente, idProduitBase, idSaveur, idVariete from vente v 
    join v_produit_saveur_variete p on p.id = idProduit;


-- Stock Produit 
create or replace view v_stockProduit as
    select idProduit, sum(d_reste) as reste from fabrication group by idProduit;


-- Stock Ingredient 
create or replace view v_stockIngredient as
    select idIngredient, sum(d_reste) as reste from achatIngredient group by idIngredient;
    

-- Liaison 

create or replace view v_achat_ingredient_unite as 
    select a.id, dateAchat, prixUnitaire, quantiteAchete, d_reste, idIngredient, 
            i.nom as nomIngredient, idunite, u.nom as nomUnite 
            from achatIngredient a join ingredient i on i.id = idIngredient join unite u on u.id = idUnite;
----------------------------------------------


-- Liaison Client vente : 
create or replace view v_client_vente as 
    select v.*, c.nom as nomclient from vente v join client c on c.id= v.idClient;