
package com.springjpa.service;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.springjpa.entity.Adherant;
import com.springjpa.entity.Profil;
import com.springjpa.repository.AdherantRepository;
import com.springjpa.repository.InscriptionRepository;

@Service
public class AdherantService {
    @Autowired
    private AdherantRepository adherantRepository;

    @Autowired 
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private ProfilService profilService;

    @Autowired
    private PenaliteService penaliteService;

    public List<Profil> getAllProfils() {
        return profilService.findAll();
    }


    public Adherant findById(Integer id){
        return adherantRepository.findById(id).get();
    }

    public List<Adherant> findAll(){
        List<Adherant> adherants = adherantRepository.findAll();
        for (Adherant a : adherants) {
            // Cherche inscription active pour chaque adhérant
            boolean actif = inscriptionRepository.findTopByAdherantIdAdherantAndEtatOrderByDateInscriptionDesc(a.getIdAdherant(), true).isPresent();
            a.setStatusAdherant(actif ? "Actif" : "Non actif");
        }
        return adherants;
    }

    public void save(Adherant adherant){
        adherantRepository.save(adherant);
    }

    public boolean isInscri(Integer adherantId) {
        var adherantOpt = adherantRepository.findById(adherantId);
        if (adherantOpt.isEmpty()) return false;

        var adherant = adherantOpt.get();
        // Récupérer la dernière inscription active AVEC date_fin non nulle
        var inscriptionOpt = inscriptionRepository.findAll().stream()
            .filter(i -> i.getAdherant().getIdAdherant().equals(adherantId))
            .filter(i -> Boolean.TRUE.equals(i.getEtat()))
            .filter(i -> i.getDateFin() != null)
            .sorted((a, b) -> b.getDateInscription().compareTo(a.getDateInscription()))
            .findFirst();
        if (inscriptionOpt.isEmpty()) return false;
        var inscription = inscriptionOpt.get();

        var now = java.time.LocalDateTime.now();
        return (now.isEqual(inscription.getDateInscription()) || now.isAfter(inscription.getDateInscription()))
            && now.isBefore(inscription.getDateFin());
    }

    public boolean isInscri(Integer adherantId, java.time.LocalDateTime referenceDate) {
        var adherantOpt = adherantRepository.findById(adherantId);
        if (adherantOpt.isEmpty()) return false;

        var adherant = adherantOpt.get();
        var inscriptionOpt = inscriptionRepository.findAll().stream()
            .filter(i -> i.getAdherant().getIdAdherant().equals(adherantId))
            .filter(i -> Boolean.TRUE.equals(i.getEtat()))
            .filter(i -> i.getDateFin() != null)
            .sorted((a, b) -> b.getDateInscription().compareTo(a.getDateInscription()))
            .findFirst();
        if (inscriptionOpt.isEmpty()) return false;
        var inscription = inscriptionOpt.get();

        var now = referenceDate;
        return (now.isEqual(inscription.getDateInscription()) || now.isAfter(inscription.getDateInscription()))
            && now.isBefore(inscription.getDateFin());
    }

    public boolean isPenalise(Integer adherantId) {
        return penaliteService.isPenalise(adherantId);
    }
    
    public boolean isPenaliseAtDate(Integer adherantId, java.time.LocalDateTime datePret) {
        return penaliteService.isPenaliseAtDate(adherantId, datePret);
    }

    public Integer getNextAdherantId() {
        // Si la table est vide, retourne 1, sinon max+1
        return adherantRepository.findAll().stream()
            .map(Adherant::getIdAdherant)
            .max(Integer::compareTo)
            .map(id -> id + 1)
            .orElse(1);
    }

    public Profil getProfilById(Integer idProfil) {
        return profilService.findById(idProfil);
    }

    public Integer getNextInscriptionId() {
        return inscriptionRepository.findAll().stream()
            .map(i -> i.getIdInscription())
            .max(Integer::compareTo)
            .map(id -> id + 1)
            .orElse(1);
    }

    public void saveInscription(com.springjpa.entity.Inscription inscription) {
        inscriptionRepository.save(inscription);
    }
    
    public boolean isDatePretAfterInscription(Integer adherantId, java.time.LocalDateTime datePret) {
        var adherantOpt = adherantRepository.findById(adherantId);
        if (adherantOpt.isEmpty()) return false;

        var adherant = adherantOpt.get();
        // Récupérer la dernière inscription active
        var inscriptionOpt = inscriptionRepository.findTopByAdherantIdAdherantAndEtatOrderByDateInscriptionDesc(adherantId, true);
        if (inscriptionOpt.isEmpty()) return false;
        var inscription = inscriptionOpt.get();

        // Vérifier que la date de prêt est après la date d'inscription
        return datePret.isAfter(inscription.getDateInscription());
    }

    public java.util.Optional<com.springjpa.entity.Inscription> getLastActiveInscription(Integer adherantId) {
        return inscriptionRepository.findTopByAdherantIdAdherantAndEtatOrderByDateInscriptionDesc(adherantId, true);
    }

    public java.util.Optional<com.springjpa.entity.Inscription> getLastInscription(Integer adherantId) {
        return inscriptionRepository.findTopByAdherantIdAdherantOrderByIdInscriptionDesc(adherantId);
    }
}
