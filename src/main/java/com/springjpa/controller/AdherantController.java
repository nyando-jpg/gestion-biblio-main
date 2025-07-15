package com.springjpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.springjpa.entity.Adherant;
import com.springjpa.entity.Profil;
import com.springjpa.service.AdherantService;
import com.springjpa.service.PenaliteService;
import com.springjpa.service.PretService;
import com.springjpa.service.QuotaTypePretService;
import com.springjpa.service.DureePretService;
import com.springjpa.service.ProfilService;
import com.springjpa.service.TypePretService;
import org.springframework.web.bind.annotation.PathVariable;
import com.springjpa.service.FinPretService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AdherantController {
    @Autowired
    private AdherantService adherantService;
    @Autowired
    private PenaliteService penaliteService;
    @Autowired
    private PretService pretService;
    @Autowired
    private QuotaTypePretService quotaTypePretService;
    @Autowired
    private DureePretService dureePretService;
    @Autowired
    private ProfilService profilService;
    @Autowired
    private TypePretService typePretService;
    @Autowired
    private FinPretService finPretService;

    @GetMapping("/adherants")
    public String listAdherants(Model model) {
        model.addAttribute("adherants", adherantService.findAll());
        return "adherant";
    }

    @GetMapping("/ajouter-adherant")
    public String showAjoutAdherant(Model model) {
        // Charger la liste des profils pour le select
        model.addAttribute("profils", adherantService.getAllProfils());
        return "ajouter-adherant";
    }

    @PostMapping("/ajouter-adherant")
    public String saveAdherant(@RequestParam("nomAdherant") String nomAdherant,
                               @RequestParam("prenomAdherant") String prenomAdherant,
                               @RequestParam("password") String password,
                               @RequestParam("idProfil") Integer idProfil,
                               Model model) {
        // Générer un nouvel id_adherant (auto-incrément ou max+1)
        Integer newId = adherantService.getNextAdherantId();
        Profil profil = adherantService.getProfilById(idProfil);
        Adherant adherant = new Adherant(newId, nomAdherant, prenomAdherant, password, profil);
        adherantService.save(adherant);

        // Ajout automatique dans la table inscription
        Integer newInscriptionId = adherantService.getNextInscriptionId();
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        com.springjpa.entity.Inscription inscription = new com.springjpa.entity.Inscription(
            newInscriptionId, now, false, adherant
        );
        adherantService.saveInscription(inscription);

        return "redirect:/adherants";
    }
    
    // Afficher le formulaire d'abonnement
    @GetMapping("/adherants/abonnement")
    public String showAbonnementForm(@RequestParam("id") int idAdherant, Model model) {
        Adherant adherant = adherantService.findById(idAdherant);
        model.addAttribute("adherant", adherant);
        return "faire-abonnement";
    }
    
    // Traiter l'abonnement
    @PostMapping("/adherants/abonnement")
    public String traiterAbonnement(@RequestParam("idAdherant") int idAdherant,
                                   @RequestParam("dateInscription") String dateInscription,
                                   @RequestParam("dateFin") String dateFin,
                                   Model model) {
        try {
            Adherant adherant = adherantService.findById(idAdherant);
            // Convertir les dates string en LocalDateTime
            java.time.LocalDateTime dateInscriptionDateTime;
            java.time.LocalDateTime dateFinDateTime;
            try {
                if (dateInscription.length() == 16) dateInscription += ":00";
                if (dateFin.length() == 16) dateFin += ":00";
                dateInscriptionDateTime = java.time.LocalDateTime.parse(dateInscription);
                dateFinDateTime = java.time.LocalDateTime.parse(dateFin);
            } catch (Exception e) {
                model.addAttribute("error", "Format de date invalide. Utilisez le format: AAAA-MM-JJ HH:MM");
                return "faire-abonnement";
            }
            // Générer un nouvel id_inscription
            Integer newInscriptionId = adherantService.getNextInscriptionId();
            com.springjpa.entity.Inscription inscription = new com.springjpa.entity.Inscription(
                newInscriptionId, dateInscriptionDateTime, true, adherant
            );
            inscription.setDateFin(dateFinDateTime);
            adherantService.saveInscription(inscription);
            model.addAttribute("success", "Abonnement enregistré avec succès.");
            return "faire-abonnement";
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de l'enregistrement de l'abonnement : " + e.getMessage());
            return "faire-abonnement";
        }
    }

    @GetMapping("/adherants/{id}")
    public String detailAdherant(@PathVariable Integer id, Model model) {
        var adherant = adherantService.findById(id);
        Map<String, Object> simpleAdherant = new HashMap<>();
        simpleAdherant.put("idAdherant", adherant.getIdAdherant());
        simpleAdherant.put("nom", adherant.getNomAdherant());
        simpleAdherant.put("prenom", adherant.getPrenomAdherant());
        simpleAdherant.put("profil", adherant.getProfil() != null ? adherant.getProfil().getNomProfil() : "");
        // Abonnement : date début et durée (même si non actif)
        java.time.LocalDateTime dateDebutAbonnement = null;
        Integer dureeAbonnementJours = null;
        var lastInscriptionOpt = adherantService.getLastInscription(id);
        if (lastInscriptionOpt.isPresent()) {
            var inscription = lastInscriptionOpt.get();
            dateDebutAbonnement = inscription.getDateInscription();
            var inscriptionProfil = adherant.getProfil() != null ? profilService.getInscriptionProfilByProfil(adherant.getProfil()) : null;
            if (inscriptionProfil != null) {
                dureeAbonnementJours = inscriptionProfil.getDuree();
            }
        }
        simpleAdherant.put("dateDebutAbonnement", dateDebutAbonnement != null ? dateDebutAbonnement : "");
        simpleAdherant.put("dureeAbonnementJours", dureeAbonnementJours != null ? dureeAbonnementJours : "");
        // Pénalités en cours
        List<Map<String, Object>> penalitesEnCours = new java.util.ArrayList<>();
        var penalites = penaliteService.findByAdherantId(id);
        var now = java.time.LocalDateTime.now();
        for (var p : penalites) {
            var fin = p.getDatePenalite().plusDays(p.getDuree());
            if (now.isBefore(fin)) {
                Map<String, Object> pen = new HashMap<>();
                pen.put("dateDebut", p.getDatePenalite());
                pen.put("dureeJours", p.getDuree());
                pen.put("finPenalite", fin);
                penalitesEnCours.add(pen);
            }
        }
        simpleAdherant.put("penalitesEnCours", penalitesEnCours);
        // Abonnement actif ?
        boolean inscrit = adherantService.isInscri(id);
        simpleAdherant.put("abonnementActif", inscrit ? "Oui" : "Non");
        // Quotas
        var profil = adherant.getProfil();
        List<Map<String, Object>> quotas = new java.util.ArrayList<>();
        if (profil != null) {
            var typesPret = typePretService.findAll();
            for (var typePret : typesPret) {
                Integer quota = quotaTypePretService.getQuotaByProfilAndTypePret(profil.getIdProfil(), typePret.getIdTypePret());
                long enCours = pretService.findAll().stream()
                    .filter(p -> p.getAdherant().getIdAdherant().equals(id)
                        && p.getTypePret().getIdTypePret().equals(typePret.getIdTypePret())
                        && finPretService.findAll().stream().noneMatch(fp -> fp.getPret().getIdPret().equals(p.getIdPret())))
                    .count();
                Map<String, Object> q = new HashMap<>();
                q.put("typePret", typePret.getType());
                q.put("quota", enCours);
                q.put("max", quota);
                quotas.add(q);
            }
        }
        long nbPrets = pretService.findAll().stream()
            .filter(p -> p.getAdherant().getIdAdherant().equals(id)
                && finPretService.findAll().stream().noneMatch(fp -> fp.getPret().getIdPret().equals(p.getIdPret())))
            .count();
        simpleAdherant.put("nombrePretsActuels", nbPrets);
        // Toujours passer la Map simplifiée et les listes associées à la JSP
        model.addAttribute("adherantSimple", simpleAdherant);
        model.addAttribute("quotas", quotas);
        model.addAttribute("penalitesEnCours", penalitesEnCours);
        return "adherant-detail";
    }
}
