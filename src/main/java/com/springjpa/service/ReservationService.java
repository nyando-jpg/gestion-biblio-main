package com.springjpa.service;

import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.entity.Reservation;
import com.springjpa.repository.ReservationRepository;
import java.time.LocalDateTime;
import com.springjpa.service.PretService;
import com.springjpa.entity.Pret;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PretService pretService;

    public Reservation findById(Integer id){
        return reservationRepository.findById(id).get();
    }

    public List<Reservation> findAll(){
        return reservationRepository.findAll();
    }

    public void save(Reservation reservation){
        Integer idExemplaire = reservation.getExemplaire().getIdExemplaire();
        LocalDateTime dateReservation = reservation.getDateDeReservation();
        // 1. Vérifier s'il existe une réservation active pour cet exemplaire à la même date
        boolean conflitReservation = reservationRepository.countActiveReservationsBeforeDate(idExemplaire, dateReservation) > 0;
        // 2. Vérifier s'il existe un prêt qui couvre la date de réservation
        boolean conflitPret = false;
        for (Pret pret : pretService.findActivePretsByExemplaire(idExemplaire)) {
            // Calculer la date de fin de prêt prévue
            Integer idProfil = pret.getAdherant().getProfil().getIdProfil();
            int dureePret = pretService.dureePretService.getDureeByProfil(idProfil);
            LocalDateTime dateFinPrevue = pret.getDateDebut().plusDays(dureePret);
            // Si la date de réservation est comprise entre la date de début et la date de fin prévue du prêt, il y a conflit
            if (!dateReservation.isAfter(dateFinPrevue)) {
                conflitPret = true;
                break;
            }
        }
        if (conflitReservation || conflitPret) {
            throw new IllegalStateException("Impossible d'insérer la réservation : le livre est déjà réservé ou prêté sur la période demandée.");
        }
        reservationRepository.save(reservation);
    }

    public void saveSansVerif(Reservation reservation) {
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
