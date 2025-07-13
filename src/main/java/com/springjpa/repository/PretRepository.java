package com.springjpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.springjpa.entity.Pret;

@Repository
public interface PretRepository extends JpaRepository<Pret, Integer> {
    Pret findTopByAdherantIdAdherantAndExemplaireIdExemplaireOrderByDateDebutDesc(Integer idAdherant, Integer idExemplaire);
    long countByAdherantIdAdherantAndTypePretIdTypePret(Integer idAdherant, Integer idTypePret);
    
    // Compter les prêts actifs (sans entrée dans fin_pret) par adhérent et type
    @Query("SELECT COUNT(p) FROM Pret p WHERE p.adherant.idAdherant = :idAdherant AND p.typePret.idTypePret = :idTypePret AND p.idPret NOT IN (SELECT fp.pret.idPret FROM FinPret fp)")
    long countActivePretsByAdherantAndType(@Param("idAdherant") Integer idAdherant, @Param("idTypePret") Integer idTypePret);
    
    // Vérifier si l'adhérent a des retards
    @Query("SELECT COUNT(p) > 0 FROM Pret p WHERE p.adherant.idAdherant = :idAdherant AND p.idPret NOT IN (SELECT fp.pret.idPret FROM FinPret fp) AND p.dateDebut < :dateLimite")
    boolean hasRetardsByAdherant(@Param("idAdherant") Integer idAdherant, @Param("dateLimite") java.time.LocalDateTime dateLimite);
    
    // Trouver les prêts en retard
    @Query("SELECT p FROM Pret p WHERE p.adherant.idAdherant = :idAdherant AND p.idPret NOT IN (SELECT fp.pret.idPret FROM FinPret fp) AND p.dateDebut < :dateLimite")
    List<Pret> findPretsEnRetardByAdherant(@Param("idAdherant") Integer idAdherant, @Param("dateLimite") java.time.LocalDateTime dateLimite);
}
