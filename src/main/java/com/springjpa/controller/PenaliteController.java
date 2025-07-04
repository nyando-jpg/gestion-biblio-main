package com.springjpa.controller;

import com.springjpa.entity.Penalite;
import com.springjpa.entity.Adherant;
import com.springjpa.service.PenaliteService;
import com.springjpa.service.AdherantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class PenaliteController {
    @Autowired
    private PenaliteService penaliteService;

    @Autowired
    private AdherantService adherantService;

    @GetMapping("/penalites")
    public String listerPenalites(Model model) {
        model.addAttribute("penalites", penaliteService.findAll());
        return "penalites";
    }

    @GetMapping("/penalites/ajouter")
    public String afficherFormulaireAjout() {
        return "ajouter-penalite";
    }

    @PostMapping("/penalites/ajouter")
    public String ajouterPenalite(@RequestParam("idAdherant") int idAdherant,
                                  @RequestParam("duree") int duree,
                                  @RequestParam("datePenalite") String datePenaliteStr,
                                  Model model) {
        try {
            // Vérification que l'adhérent existe
            Adherant adherant;
            try {
                adherant = adherantService.findById(idAdherant);
            } catch (Exception ex) {
                model.addAttribute("error", "L'adhérent n'existe pas dans la base.");
                return "ajouter-penalite";
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime datePenalite = LocalDateTime.parse(datePenaliteStr, formatter);
            Penalite penalite = new Penalite();
            penalite.setAdherant(adherant);
            penalite.setDuree(duree);
            penalite.setDatePenalite(datePenalite);
            penaliteService.save(penalite);
            model.addAttribute("success", "La pénalité a bien été ajoutée.");
            return "ajouter-penalite";
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de l'ajout de la pénalité : " + e.getMessage());
            return "ajouter-penalite";
        }
    }
}
