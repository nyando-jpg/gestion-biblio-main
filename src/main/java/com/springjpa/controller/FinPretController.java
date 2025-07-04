package com.springjpa.controller;

import com.springjpa.entity.FinPret;
import com.springjpa.entity.Pret;
import com.springjpa.entity.Exemplaire;
import com.springjpa.service.FinPretService;
import com.springjpa.service.PretService;
import com.springjpa.service.ExemplaireService;

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

            model.addAttribute("success", "Retour de prêt enregistré avec succès.");
            return "ajouter-finpret";
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors du retour de prêt : " + e.getMessage());
            return "ajouter-finpret";
        }
    }
}
