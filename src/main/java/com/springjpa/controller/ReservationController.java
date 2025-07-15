package com.springjpa.controller;

import com.springjpa.entity.*;
import com.springjpa.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    
    @Autowired
    private AdherantService adherantService;
    
    @Autowired
    private ExemplaireService exemplaireService;
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private StatutReservationService statutReservationService;

    @Autowired
    private TypePretService typePretService;

    @GetMapping("/reservations")
    public String listerReservations(Model model) {
        model.addAttribute("reservations", reservationService.findAll());
        return "reservations";
    }
    
    // Afficher le formulaire de création de réservation
    @GetMapping("/reservations/ajouter")
    public String afficherFormulaireReservation(Model model) {
        // Récupérer les listes pour les dropdowns
        List<Adherant> adherants = adherantService.findAll();
        List<Exemplaire> exemplaires = exemplaireService.findAll();
        List<TypePret> typesPret = typePretService.findAll();
        
        model.addAttribute("adherants", adherants);
        model.addAttribute("exemplaires", exemplaires);
        model.addAttribute("typesPret", typesPret);
        
        return "ajouter-reservation";
    }
    
    // Traiter la création de réservation
    @PostMapping("/reservations/creer")
    public String creerReservation(@RequestParam("idAdherant") int idAdherant,
                                   @RequestParam("idExemplaire") int idExemplaire,
                                   @RequestParam("idTypePret") int idTypePret,
                                   @RequestParam("dateReservation") String dateReservation,
                                   HttpSession session,
                                   Model model) {
        try {
            // Récupérer l'admin connecté depuis la session
            Integer idAdmin = (Integer) session.getAttribute("adminId");
            if (idAdmin == null) {
                model.addAttribute("error", "Admin non connecté");
                return "ajouter-reservation";
            }
            
            Admin admin = adminService.findById(idAdmin);
            Adherant adherant = adherantService.findById(idAdherant);
            Exemplaire exemplaire = exemplaireService.findById(idExemplaire);
            TypePret typePret = typePretService.findById(idTypePret);
            // Statut par défaut "En attente" (id = 1)
            StatutReservation statut = statutReservationService.findById(1);
            
            // Vérifications
            if (!adherantService.isInscri(idAdherant)) {
                model.addAttribute("error", "L'adhérent n'est pas actif ou son inscription n'est pas valide.");
                return rechargerFormulaire(model);
            }
            
            if (adherantService.isPenalise(idAdherant)) {
                model.addAttribute("error", "L'adhérent a une pénalité en cours et ne peut pas faire de réservation.");
                return rechargerFormulaire(model);
            }
            
            // Générer un nouvel ID pour la réservation
            int newIdReservation = reservationService.findAll().stream()
                .mapToInt(r -> r.getIdReservation())
                .max()
                .orElse(0) + 1;
            
            // Convertir la date string en LocalDateTime
            LocalDateTime dateReservationDateTime;
            try {
                // Le format HTML datetime-local envoie "2025-07-25T12:13" 
                // mais LocalDateTime.parse() attend "2025-07-25T12:13:00"
                if (dateReservation.length() == 16) { // Format "2025-07-25T12:13"
                    dateReservation += ":00"; // Ajouter les secondes
                }
                dateReservationDateTime = LocalDateTime.parse(dateReservation);
            } catch (Exception e) {
                model.addAttribute("error", "Format de date invalide. Utilisez le format: AAAA-MM-JJ HH:MM");
                return rechargerFormulaire(model);
            }
            
            // Créer la réservation
            Reservation reservation = new Reservation();
            reservation.setIdReservation(newIdReservation);
            reservation.setDateDeReservation(dateReservationDateTime);
            reservation.setAdmin(admin);
            reservation.setStatut(statut);
            reservation.setExemplaire(exemplaire);
            reservation.setAdherant(adherant);
            reservation.setTypePret(typePret);
            
            reservationService.save(reservation);
            
            model.addAttribute("success", "La réservation a été créée avec succès !");
            return "ajouter-reservation";
            
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la création de la réservation : " + e.getMessage());
            return rechargerFormulaire(model);
        }
    }
    
    // Valider une réservation (changer le statut de "En attente" à "Validée")
    @PostMapping("/reservations/valider")
    public String validerReservation(@RequestParam("idReservation") int idReservation, Model model) {
        try {
            Reservation reservation = reservationService.findById(idReservation);
            
            if (reservation == null) {
                model.addAttribute("error", "Réservation introuvable.");
                return "redirect:/reservations";
            }
            
            if (reservation.getStatut().getIdStatut() != 1) {
                model.addAttribute("error", "Cette réservation ne peut pas être validée (statut actuel : " + reservation.getStatut().getNomStatut() + ").");
                return "redirect:/reservations";
            }
            
            // Changer le statut à "Validée" (id = 2)
            StatutReservation statutValidee = statutReservationService.findById(2);
            reservation.setStatut(statutValidee);
            
            reservationService.save(reservation);
            
            model.addAttribute("success", "La réservation a été validée avec succès !");
            
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la validation : " + e.getMessage());
        }
        
        return "redirect:/reservations";
    }
    
    // Méthode utilitaire pour recharger le formulaire avec les données
    private String rechargerFormulaire(Model model) {
        List<Adherant> adherants = adherantService.findAll();
        List<Exemplaire> exemplaires = exemplaireService.findAll();
        List<TypePret> typesPret = typePretService.findAll();
        
        model.addAttribute("adherants", adherants);
        model.addAttribute("exemplaires", exemplaires);
        model.addAttribute("typesPret", typesPret);
        
        return "ajouter-reservation";
    }
}
