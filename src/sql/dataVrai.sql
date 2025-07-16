-- TYPE_PRET (à insérer en premier car utilisé ailleurs)
INSERT INTO type_pret (id_type_pret, type) VALUES
  (1, 'Emprunt');

-- PROFILS
INSERT INTO profil (id_profil, nom_profil, quota_pret, quota_reservation) VALUES
  (1, 'Etudiant', 7, 1),
  (2, 'Enseignant', 9, 2),
  (3, 'Professionnel', 12, 3);

-- AUTEURS
INSERT INTO auteur (id_auteur, nom_auteur, prenom_auteur) VALUES
  (1, 'Hugo', 'Victor'),
  (2, 'Camus', 'Albert'),
  (3, 'Rowling', 'JK');

-- EDITEURS
INSERT INTO editeur (id_editeur, nom_editeur, localisation) VALUES
  (1, 'Diogophile', 'France'),
  (2, 'Diogophile', 'France'),
  (3, 'Diogophile', 'France');

-- CATEGORIES
INSERT INTO categorie (id_categorie, nom_categorie) VALUES
  (1, 'Litterature_classique'),
  (2, 'Philosophie'),
  (3, 'Jeunesse_Fantastique');

-- LIVRES
INSERT INTO livre (id_livre, titre, isbn, langue, annee_publication, synopsis, nb_page, id_editeur, id_auteur) VALUES
  (1, 'Les_Miserables', '9782070409189', 'Francais', 1862, 'Litterature_classique', 1200, 1, 1),
  (2, 'LEtranger', '9782070360022', 'Francais', 1942, 'Philosophie', 600, 2, 2),
  (3, 'Harry_Potter_a_l_Ecole_des_sorciers', '9782070643026', 'Francais', 1997, 'Jeunesse_Fantastique', 350, 3, 3);

-- EXEMPLAIRES
INSERT INTO exemplaire (id_exemplaire, dispo, id_livre) VALUES
  (1, TRUE, 1),
  (2, TRUE, 1),
  (3, TRUE, 1),
  (4, TRUE, 2),
  (5, TRUE, 2),
  (6, TRUE, 3);

-- ADHERANTS
INSERT INTO adherant (id_adherant, nom_adherant, prenom_adherant, password, id_profil) VALUES
  (1, 'Bensaidi', 'Amine', 'pass', 1),
  (2, 'El_Khatibati', 'Sarah', 'pass', 1),
  (3, 'Moujahid', 'Youssef', 'pass', 1),
  (4, 'Benalil', 'Nadia', 'pass', 2),
  (5, 'Hadadi', 'Karima', 'pass', 2),
  (6, 'Toudhani', 'Salima', 'pass', 2),
  (7, 'El_Mansouri', 'Rachid', 'pass', 3),
  (8, 'Zerouali', 'Amine', 'pass', 3);

-- INSCRIPTION (ABONNEMENTS)
INSERT INTO inscription (id_inscription, date_inscription, etat, id_adherant, date_fin) VALUES
  (1, '2025-02-01 00:00:00', TRUE, 1, '2025-07-24 00:00:00'),
  (2, '2025-02-01 00:00:00', FALSE, 2, '2025-07-01 00:00:00'),
  (3, '2025-04-01 00:00:00', TRUE, 3, '2025-12-01 00:00:00'),
  (4, '2025-07-01 00:00:00', TRUE, 4, '2026-07-01 00:00:00'),
  (5, '2025-08-01 00:00:00', FALSE, 5, '2026-05-01 00:00:00'),
  (6, '2025-07-01 00:00:00', TRUE, 6, '2026-06-01 00:00:00'),
  (7, '2025-06-01 00:00:00', TRUE, 7, '2025-12-01 00:00:00'),
  (8, '2024-10-01 00:00:00', FALSE, 8, '2025-06-01 00:00:00');

-- INSCRIPTION_PROFIL
INSERT INTO inscription_profil (id_inscri_profil, duree, id_profil) VALUES
  (1, 365, 1),
  (2, 365, 2),
  (3, 365, 3);

-- ADMIN (pour tests pret/reservation)
INSERT INTO admin (id_admin, nom_admin, prenom_admin, password) VALUES
  (1, 'Admin', 'Test', 'adminpass');

-- Table statut_reservation
INSERT INTO statut_reservation (id_statut, nom_statut) VALUES (1, 'En attente');
INSERT INTO statut_reservation (id_statut, nom_statut) VALUES (2, 'Validée');

-- QUOTA_TYPE_PRET (optionnel, tu peux l’enlever si ta logique ne l’utilise pas)
INSERT INTO quota_type_pret (id_profil, id_type_pret, quota) VALUES
  (1, 1, 2),
  (2, 1, 3),
  (3, 1, 4);

INSERT INTO duree_pret (id_duree_pret, duree, id_profil) VALUES (1, 7, 1);
INSERT INTO duree_pret (id_duree_pret, duree, id_profil) VALUES (2, 9, 2);
INSERT INTO duree_pret (id_duree_pret, duree, id_profil) VALUES (3, 12, 3);
