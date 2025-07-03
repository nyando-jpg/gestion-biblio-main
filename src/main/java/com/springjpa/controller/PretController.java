

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
                                 jakarta.servlet.http.HttpSession session,
                                 Model model) {
        // Récupérer l'admin connecté depuis la session
        Integer idAdmin = (Integer) session.getAttribute("adminId");
        if (idAdmin == null) {
            model.addAttribute("error", "Admin non connecté");
            return "faire-pret";
        }
        Admin admin = adminService.findById(idAdmin);
        Adherant adherant = adherantService.findById(idAdherant);
        Exemplaire exemplaire = exemplaireService.findById(idExemplaire);
        TypePret typePret = typePretService.findById(idTypePret);
        // Générer un nouvel id_pret (auto ou max+1)
        int newIdPret = pretService.findAll().stream().mapToInt(p -> p.getIdPret()).max().orElse(0) + 1;
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        Pret pret = new Pret(newIdPret, now, admin, typePret, exemplaire, adherant);
        pretService.save(pret);
        return "redirect:/";
    }

 
}