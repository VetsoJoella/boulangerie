INSERT INTO caracteristique (nom) VALUES ('Nature');
INSERT INTO caracteristique (nom) VALUES ('Bio');
INSERT INTO caracteristique (nom) VALUES ('Gourmand');



-- Insérer des données dans la table produitcaracteristique
INSERT INTO produitcaracteristique (idcaracteristique, idproduit)
VALUES 
    -- Nature
    ('CRT00001', 'PRD00001'),
    ('CRT00001', 'PRD00002'),

    -- Bio
    ('CRT00002', 'PRD00002'),
    ('CRT00002', 'PRD00005'),

    -- Gourmand
    ('CRT00003', 'PRD00009'),
    ('CRT00003', 'PRD00010');
