
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
        // Récupérer la dernière inscription active
        var inscriptionOpt = inscriptionRepository.findTopByAdherantIdAdherantAndEtatOrderByDateInscriptionDesc(adherantId, true);
        if (inscriptionOpt.isEmpty()) return false;
        var inscription = inscriptionOpt.get();

        // Verifier la duree de l'inscription pour le profil
        Profil profil = adherant.getProfil();
        var inscriptionProfil = profilService.getInscriptionProfilByProfil(profil);
        if (inscriptionProfil == null) return false;
        int duree = inscriptionProfil.getDuree();

        // Calcul de la date limite
        var dateLimite = inscription.getDateInscription().plusDays(duree);
        return dateLimite.isAfter(java.time.LocalDateTime.now());
    }

    public boolean isPenalise(Integer adherantId) {
        return penaliteService.isPenalise(adherantId);
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
}
