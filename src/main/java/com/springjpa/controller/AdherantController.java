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

@Controller
public class AdherantController {
    @Autowired
    private AdherantService adherantService;

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
            newInscriptionId, now, true, adherant
        );
        adherantService.saveInscription(inscription);

        return "redirect:/adherants";
    }
}
