

package com.springjpa.controller;

import com.springjpa.entity.Adherant;
import com.springjpa.entity.Exemplaire;
import com.springjpa.entity.TypePret;
import com.springjpa.entity.Pret;
import com.springjpa.entity.Admin;
import com.springjpa.service.AdherantService;
import com.springjpa.service.ExemplaireService;
import com.springjpa.service.TypePretService;
import com.springjpa.service.PretService;
import com.springjpa.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PretController {

    @Autowired
    private ExemplaireService exemplaireService;

    @Autowired
    private AdherantService adherantService;

    @Autowired
    private TypePretService typePretService;


    @GetMapping("/")
    public String index() {
        return "index"; // Redirection vers la page d'accueil
    }


    // Affichage du formulaire simple pour faire un prêt (id adhérant et id exemplaire)
    @GetMapping("/prets/faire")
    public String fairePretForm(Model model) {
        java.util.List<TypePret> types = typePretService.findAll();
        System.out.println("Types trouvés : " + types.size());
        model.addAttribute("typesPret", types);
        return "faire-pret";
    }

    // Traitement du formulaire simple
    @Autowired
    private PretService pretService;
    @Autowired
    private AdminService adminService;

    @PostMapping("/prets/creer")
    public String creerPretSimple(@RequestParam("idAdherant") int idAdherant,
                                 @RequestParam("idExemplaire") int idExemplaire,
                                 @RequestParam("idTypePret") int idTypePret,
                                 @RequestParam("datePret") String datePret,
                                 jakarta.servlet.http.HttpSession session,
                                 Model model) {
        // Récupérer l'admin connecté depuis la session
        Integer idAdmin = (Integer) session.getAttribute("adminId");
        if (idAdmin == null) {
            model.addAttribute("error", "Admin non connecté");
            return "faire-pret";
        }
        Admin admin = adminService.findById(idAdmin);
        Adherant adherant = null;
        try {
            adherant = adherantService.findById(idAdherant);
        } catch (Exception e) {
            model.addAttribute("error", "L'adhérent n'existe pas.");
            java.util.List<TypePret> types = typePretService.findAll();
            model.addAttribute("typesPret", types);
            return "faire-pret";
        }
        Exemplaire exemplaire = null;
        try {
            exemplaire = exemplaireService.findById(idExemplaire);
        } catch (Exception e) {
            model.addAttribute("error", "L'exemplaire n'existe pas.");
            java.util.List<TypePret> types = typePretService.findAll();
            model.addAttribute("typesPret", types);
            return "faire-pret";
        }
        if (!exemplaire.isDispo()) {
            model.addAttribute("error", "L'exemplaire n'est pas disponible.");
            java.util.List<TypePret> types = typePretService.findAll();
            model.addAttribute("typesPret", types);
            return "faire-pret";
        }
        TypePret typePret = typePretService.findById(idTypePret);
        // Vérifier que l'adhérent est actif (inscription.etat = 1)
        if (!adherantService.isInscri(idAdherant)) {
            model.addAttribute("error", "L'adhérent n'est pas actif ou son inscription n'est pas valide.");
            java.util.List<TypePret> types = typePretService.findAll();
            model.addAttribute("typesPret", types);
            return "faire-pret";
        }

        // Générer un nouvel id_pret (auto ou max+1)
        int newIdPret = pretService.findAll().stream().mapToInt(p -> p.getIdPret()).max().orElse(0) + 1;
        
        // Convertir la date string en LocalDateTime
        java.time.LocalDateTime datePretDateTime;
        try {
            if (datePret.length() == 16) { // Format "2025-07-25T12:13"
                datePret += ":00"; // Ajouter les secondes
            }
            datePretDateTime = java.time.LocalDateTime.parse(datePret);
        } catch (Exception e) {
            model.addAttribute("error", "Format de date invalide. Utilisez le format: AAAA-MM-JJ HH:MM");
            java.util.List<TypePret> types = typePretService.findAll();
            model.addAttribute("typesPret", types);
            return "faire-pret";
        }
        
        // Vérifier que l'adhérent n'a pas de pénalité en cours à la date de prêt
        if (adherantService.isPenaliseAtDate(idAdherant, datePretDateTime)) {
            model.addAttribute("error", "L'adhérent a une pénalité active à la date de prêt choisie et ne peut pas emprunter.");
            java.util.List<TypePret> types = typePretService.findAll();
            model.addAttribute("typesPret", types);
            return "faire-pret";
        }
        
        Pret pret = new Pret(newIdPret, datePretDateTime, admin, typePret, exemplaire, adherant);
        pretService.save(pret);
        // Mettre l'exemplaire comme non disponible
        exemplaire.setDispo(false);
        exemplaireService.save(exemplaire);
        // Ajout d'un message de validation
        model.addAttribute("success", "Le prêt a bien été enregistré.");
        java.util.List<TypePret> types = typePretService.findAll();
        model.addAttribute("typesPret", types);
        return "faire-pret";
    }

 
}