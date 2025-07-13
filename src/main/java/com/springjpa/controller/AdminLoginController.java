package com.springjpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import com.springjpa.service.*;
import com.springjpa.entity.Admin;

@Controller
public class AdminLoginController {
    
    @Autowired
    private AdherantService adherantService;
    
    @Autowired
    private LivreService livreService;
    
    @Autowired
    private PretService pretService;
    
    @Autowired
    private ReservationService reservationService;
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private AuteurService auteurService;
    
    @Autowired
    private EditeurService editeurService;
    
    @Autowired
    private CategorieService categorieService;
    
    @Autowired
    private ExemplaireService exemplaireService;
    
    @Autowired
    private PenaliteService penaliteService;
    
    @Autowired
    private ProfilService profilService;
    
    @Autowired
    private TypePretService typePretService;
    
    @Autowired
    private StatutReservationService statutReservationService;

    @GetMapping("/admin/home")
    public String adminHome() {
        return "admin-home";
    }
    
    @PostMapping("/admin-login")
    public ModelAndView loginAdmin(@RequestParam("nomAdmin") String nom,
                                   @RequestParam("prenomAdmin") String prenom,
                                   @RequestParam("password") String password,
                                   HttpServletRequest request) {
        Admin admin = adminService.findAll().stream()
            .filter(a -> a.getNomAdmin().equals(nom)
                && a.getPrenomAdmin().equals(prenom)
                && a.getPassword().equals(password))
            .findFirst().orElse(null);
        if (admin != null) {
            request.getSession().setAttribute("adminId", admin.getIdAdmin());
            return new ModelAndView("admin-home");
        } else {
            ModelAndView mv = new ModelAndView("index");
            mv.addObject("loginError", true);
            return mv;
        }
    }

    // Nouvelle méthode pour afficher toutes les tables
    @GetMapping("/admin/tables")
    public String showAllTables(Model model) {
        // Récupérer toutes les données de chaque table
        model.addAttribute("adherants", adherantService.findAll());
        model.addAttribute("livres", livreService.findAll());
        model.addAttribute("prets", pretService.findAll());
        model.addAttribute("reservations", reservationService.findAll());
        model.addAttribute("admins", adminService.findAll());
        model.addAttribute("auteurs", auteurService.findAll());
        model.addAttribute("editeurs", editeurService.findAll());
        model.addAttribute("categories", categorieService.findAll());
        model.addAttribute("exemplaires", exemplaireService.findAll());
        model.addAttribute("penalites", penaliteService.findAll());
        model.addAttribute("profils", profilService.findAll());
        model.addAttribute("typesPret", typePretService.findAll());
        model.addAttribute("statutsReservation", statutReservationService.findAll());
        
        return "tables-view";
    }
}
