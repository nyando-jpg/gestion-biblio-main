package com.springjpa.service;

import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.entity.Reservation;
import com.springjpa.repository.ReservationRepository;
import java.time.LocalDateTime;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation findById(Integer id){
        return reservationRepository.findById(id).get();
    }

    public List<Reservation> findAll(){
        return reservationRepository.findAll();
    }

    public void save(Reservation reservation){
        reservationRepository.save(reservation);
    }
    
    /**
     * Vérifie s'il y a des réservations actives pour un exemplaire avant une date donnée
     * @param idExemplaire l'ID de l'exemplaire
     * @param dateLimite la date limite (généralement la date de fin de prêt)
     * @return true s'il y a des réservations actives, false sinon
     */
    public boolean hasActiveReservationsBeforeDate(Integer idExemplaire, LocalDateTime dateLimite) {
        // Vérifier les réservations avec statut "En attente" (id_statut = 1) ou "Validée" (id_statut = 2)
        // qui ont une date de réservation antérieure à la date limite
        return reservationRepository.countActiveReservationsBeforeDate(idExemplaire, dateLimite) > 0;
    }
}
