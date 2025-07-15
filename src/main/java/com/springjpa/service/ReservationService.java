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
        // 1. Vérifier s'il existe une réservation active qui chevauche la période demandée
        boolean conflitReservation = false;
        String typePretReservation = reservation.getTypePret().getType();
        int dureeReservation = pretService.dureePretService.getDureeByProfilEtType(reservation.getAdherant().getProfil().getIdProfil(), typePretReservation);
        LocalDateTime debutReservation = reservation.getDateDeReservation();
        LocalDateTime finReservation = debutReservation.plusDays(dureeReservation);
        for (Reservation r : reservationRepository.findAll()) {
            if (!r.getExemplaire().getIdExemplaire().equals(idExemplaire)) continue;
            if (r.getStatut().getIdStatut() != 1 && r.getStatut().getIdStatut() != 2) continue;
            if (r.getIdReservation().equals(reservation.getIdReservation())) continue; // ignorer soi-même
            String typePretR = r.getTypePret().getType();
            int dureeR = pretService.dureePretService.getDureeByProfilEtType(r.getAdherant().getProfil().getIdProfil(), typePretR);
            LocalDateTime debutR = r.getDateDeReservation();
            LocalDateTime finR = debutR.plusDays(dureeR);
            boolean chevauchement = !(finReservation.isBefore(debutR) || debutReservation.isAfter(finR));
            if (chevauchement) {
                conflitReservation = true;
                break;
            }
        }
        // 2. Vérifier s'il existe un prêt qui couvre la date de réservation
        boolean conflitPret = false;
        for (Pret pret : pretService.findActivePretsByExemplaire(idExemplaire)) {
            // Période du prêt actif
            Integer idProfilPret = pret.getAdherant().getProfil().getIdProfil();
            String typePretActif = pret.getTypePret().getType();
            int dureePretActif = pretService.dureePretService.getDureeByProfilEtType(idProfilPret, typePretActif);
            LocalDateTime debutPret = pret.getDateDebut();
            LocalDateTime finPret = debutPret.plusDays(dureePretActif);

            // Période de la réservation demandée (utiliser des noms différents)
            String typePretReservation2 = reservation.getTypePret().getType();
            int dureeReservation2 = pretService.dureePretService.getDureeByProfilEtType(reservation.getAdherant().getProfil().getIdProfil(), typePretReservation2);
            LocalDateTime debutReservation2 = reservation.getDateDeReservation();
            LocalDateTime finReservation2 = debutReservation2.plusDays(dureeReservation2);

            // Vérifier le chevauchement
            boolean chevauchement = !(finReservation2.isBefore(debutPret) || debutReservation2.isAfter(finPret));
            if (chevauchement) {
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

    public Reservation findActiveReservationByExemplaireAndAdherant(Integer idExemplaire, Integer idAdherant) {
        return reservationRepository.findAll().stream()
            .filter(r -> r.getExemplaire().getIdExemplaire().equals(idExemplaire))
            .filter(r -> r.getAdherant().getIdAdherant().equals(idAdherant))
            .filter(r -> r.getStatut().getIdStatut() == 1 || r.getStatut().getIdStatut() == 2)
            .findFirst().orElse(null);
    }
}
