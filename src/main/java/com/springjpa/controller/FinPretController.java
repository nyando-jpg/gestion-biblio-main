package com.springjpa.controller;

import com.springjpa.entity.FinPret;
import com.springjpa.entity.Pret;
import com.springjpa.entity.Exemplaire;
import com.springjpa.service.FinPretService;
import com.springjpa.service.PretService;
import com.springjpa.service.ExemplaireService;
import com.springjpa.service.PenaliteService;
import com.springjpa.service.DureePretService;
import com.springjpa.service.AdherantService;
import com.springjpa.entity.Penalite;
import com.springjpa.entity.Adherant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.time.LocalDateTime;

@Controller
public class FinPretController {

    @Autowired
    private FinPretService finPretService;
    @Autowired
    private PretService pretService;
    @Autowired
    private ExemplaireService exemplaireService;
    @Autowired
    private PenaliteService penaliteService;
    @Autowired
    private DureePretService dureePretService;
    @Autowired
    private AdherantService adherantService;

    @GetMapping("/finprets/ajouter")
    public String afficherFormulaireAjoutFinPret() {
        return "ajouter-finpret";
    }

    @PostMapping("/finprets/ajouter")
    public String traiterRetourPret(@RequestParam("idAdherant") Integer idAdherant,
                                    @RequestParam("idExemplaire") Integer idExemplaire,
                                    @RequestParam("dateFin") String dateFinStr,
                                    Model model) {
        try {
            // 1. Trouver le prêt actif correspondant
            Pret pret = pretService.findByAdherantAndExemplaire(idAdherant, idExemplaire);
            if (pret == null) {
                model.addAttribute("error", "Aucun prêt trouvé pour cet adhérent et cet exemplaire.");
                return "ajouter-finpret";
            }

            // 2. Générer un nouvel id_fin_pret
            Integer idFinPret = finPretService.getNextFinPretId();

            // 3. Convertir la date
            LocalDateTime dateFin = LocalDateTime.parse(dateFinStr.replace("T", "T"));

            // 4. Créer et enregistrer le retour de prêt
            FinPret finPret = new FinPret(idFinPret, dateFin, pret);
            finPretService.save(finPret);

            // 5. Passer l'exemplaire à disponible
            Exemplaire exemplaire = pret.getExemplaire();
            exemplaire.setDispo(true);
            exemplaireService.save(exemplaire);

            // 6. Vérifier le retard et appliquer une pénalité si besoin
            Integer idProfil = pret.getAdherant().getProfil().getIdProfil();
            String typePret = pret.getTypePret().getType();
            Integer dureeAutorisee = dureePretService.getDureeByProfilEtType(idProfil, typePret);
            LocalDateTime dateLimite = pret.getDateDebut().plusDays(dureeAutorisee);
            if (dateFin.isAfter(dateLimite)) {
                // Générer un nouvel id_penalite
                int newIdPenalite = penaliteService.findAll().stream()
                    .mapToInt(p -> p.getIdPenalite())
                    .max()
                    .orElse(0) + 1;
                Penalite penalite = new Penalite();
                penalite.setIdPenalite(newIdPenalite);
                // Déterminer la durée selon le profil
                int duree = 10; // Par défaut étudiant
                if (pret.getAdherant().getProfil() != null) {
                    String nomProfil = pret.getAdherant().getProfil().getNomProfil().toLowerCase();
                    if (nomProfil.contains("enseignant")) {
                        duree = 9;
                    } else if (nomProfil.contains("professionnel")) {
                        duree = 8;
                    }
                }
                penalite.setDuree(duree);
                penalite.setDatePenalite(dateFin);
                Adherant adherant = adherantService.findById(idAdherant);
                penalite.setAdherant(adherant);
                penaliteService.save(penalite);
                model.addAttribute("info", "Retour en retard : une pénalité de " + duree + " jours a été appliquée.");
            }

            model.addAttribute("success", "Retour de prêt enregistré avec succès.");
            return "ajouter-finpret";
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors du retour de prêt : " + e.getMessage());
            return "ajouter-finpret";
        }
    }
}
