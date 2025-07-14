package com.springjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.springjpa.entity.Reservation;
import java.time.LocalDateTime;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    
    /**
     * Compte les réservations actives pour un exemplaire avant une date donnée
     * @param idExemplaire l'ID de l'exemplaire
     * @param dateLimite la date limite
     * @return le nombre de réservations actives
     */
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.exemplaire.idExemplaire = :idExemplaire " +
           "AND r.dateDeReservation < :dateLimite " +
           "AND r.statut.idStatut IN (1, 2)") // 1 = En attente, 2 = Validée
    long countActiveReservationsBeforeDate(@Param("idExemplaire") Integer idExemplaire, 
                                         @Param("dateLimite") LocalDateTime dateLimite);
}
