package com.springjpa.service;

import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.entity.Pret;
import com.springjpa.repository.PretRepository;
import com.springjpa.service.DureePretService;
import com.springjpa.service.ReservationService;
import com.springjpa.service.AdherantService;

@Service
public class PretService {
    @Autowired
    private PretRepository pretRepository;
    
    @Autowired
    private DureePretService dureePretService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private AdherantService adherantService;

    public Pret findByAdherantAndExemplaire(Integer idAdherant, Integer idExemplaire) {
        return pretRepository.findTopByAdherantIdAdherantAndExemplaireIdExemplaireOrderByDateDebutDesc(idAdherant, idExemplaire);
    }

    public Pret findById(Integer id){
        return pretRepository.findById(id).get();
    }

    public List<Pret> findAll(){
        return pretRepository.findAll();
    }

    public void save(Pret pret){
        pretRepository.save(pret);
    }
    
    public long countActivePretsByAdherantAndType(Integer idAdherant, Integer idTypePret) {
        return pretRepository.countActivePretsByAdherantAndType(idAdherant, idTypePret);
    }
    
    public boolean hasRetards(Integer idAdherant, Integer idProfil, java.time.LocalDateTime datePret) {
        Integer duree = dureePretService.getDureeByProfil(idProfil);
        java.time.LocalDateTime dateLimite = datePret.minusDays(duree);
        return pretRepository.hasRetardsByAdherant(idAdherant, dateLimite);
    }
    
    public List<Pret> getPretsEnRetard(Integer idAdherant, Integer idProfil, java.time.LocalDateTime datePret) {
        Integer duree = dureePretService.getDureeByProfil(idProfil);
        java.time.LocalDateTime dateLimite = datePret.minusDays(duree);
        return pretRepository.findPretsEnRetardByAdherant(idAdherant, dateLimite);
    }

    public List<Pret> findActivePretsByExemplaire(Integer idExemplaire) {
        return pretRepository.findActivePretsByExemplaire(idExemplaire);
    }

    public List<Pret> findActivePretsByAdherant(Integer idAdherant) {
        return pretRepository.findActivePretsByAdherant(idAdherant);
    }

    public boolean isProlongeable(Pret pret) {
        // Vérifier si l'exemplaire est dispo
        boolean dispo = pret.getExemplaire().isDispo();
        // Durée de prolongement selon le profil
        Integer idProfil = pret.getAdherant().getProfil().getIdProfil();
        Integer duree = dureePretService.getDureeByProfil(idProfil);
        java.time.LocalDateTime nouvelleDateFin = pret.getDateDebut().plusDays(duree * 2); // double durée
        // Vérifier s'il y a une réservation active sur la période du prolongement
        boolean hasReservation = reservationService.hasActiveReservationsBeforeDate(
            pret.getExemplaire().getIdExemplaire(),
            nouvelleDateFin
        );
        // Vérifier si l'adhérent a une pénalité en cours
        boolean penalise = adherantService.isPenalise(pret.getAdherant().getIdAdherant());
        return dispo && !hasReservation && !penalise;
    }

    public void prolongerPret(Pret pret) {
        // Prolonger selon la durée du profil
        Integer idProfil = pret.getAdherant().getProfil().getIdProfil();
        Integer duree = dureePretService.getDureeByProfil(idProfil);
        // On suppose que la date de début ne change pas, on ajoute la durée à la date de début
        java.time.LocalDateTime nouvelleDateFin = pret.getDateDebut().plusDays(duree * 2); // double durée
        // Ici, il faudrait mettre à jour la date de fin dans FinPret, ou gérer la logique selon ton modèle
        // Pour l'instant, on ne fait que simuler (à adapter selon la structure de FinPret)
        // ...
        // Si FinPret existe déjà, on le met à jour, sinon on ne fait rien (à adapter)
        // Cette méthode doit être adaptée selon la logique métier exacte
    }
}
