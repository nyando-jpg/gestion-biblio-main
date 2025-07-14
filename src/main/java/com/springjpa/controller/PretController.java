

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
import com.springjpa.service.QuotaTypePretService;
import com.springjpa.service.ReservationService;
import com.springjpa.service.DureePretService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
    
    @Autowired
    private QuotaTypePretService quotaTypePretService;
    
    @Autowired
    private ReservationService reservationService;
    
    @Autowired
    private DureePretService dureePretService;

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
        
        // Vérifier que la date de prêt est après la date d'inscription
        if (!adherantService.isDatePretAfterInscription(idAdherant, datePretDateTime)) {
            model.addAttribute("error", "La date de prêt ne peut pas être antérieure à la date d'inscription de l'adhérent.");
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
        
        // Vérifier les quotas de prêt
        Integer idProfil = adherant.getProfil().getIdProfil();
        Integer quotaMax = quotaTypePretService.getQuotaByProfilAndTypePret(idProfil, idTypePret);
        long prêtsActuels = pretService.countActivePretsByAdherantAndType(idAdherant, idTypePret);
        
        if (prêtsActuels >= quotaMax) {
            model.addAttribute("error", "L'adhérent a atteint son quota de livres pour ce type de prêt. Quota maximum : " + quotaMax + " livres. Livres actuellement empruntés : " + prêtsActuels + ". Veuillez d'abord rendre des livres.");
            java.util.List<TypePret> types = typePretService.findAll();
            model.addAttribute("typesPret", types);
            return "faire-pret";
        }
        
        // Vérifier les retards en cours par rapport à la date de prêt choisie
        if (pretService.hasRetards(idAdherant, idProfil, datePretDateTime)) {
            List<Pret> pretsEnRetard = pretService.getPretsEnRetard(idAdherant, idProfil, datePretDateTime);
            model.addAttribute("error", "L'adhérent a des prêts en retard par rapport à la date de prêt choisie (" + pretsEnRetard.size() + " livre(s)). Veuillez d'abord rendre les livres en retard avant d'emprunter.");
            java.util.List<TypePret> types = typePretService.findAll();
            model.addAttribute("typesPret", types);
            return "faire-pret";
        }
        
        // Calculer la date de fin de prêt
        Integer dureePret = dureePretService.getDureeByProfil(idProfil);
        LocalDateTime dateFinPret = datePretDateTime.plus(dureePret, ChronoUnit.DAYS);
        
        // Vérifier s'il y a des réservations actives pour cet exemplaire avant la date de fin de prêt
        if (reservationService.hasActiveReservationsBeforeDate(idExemplaire, dateFinPret)) {
            model.addAttribute("error", "Ce livre a des réservations actives avant la date de fin de prêt (" + dateFinPret.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "). Le prêt ne peut pas être effectué.");
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

    @GetMapping("/prets/prolonger")
    public String afficherFormulaireProlongement(@RequestParam(value = "idAdherant", required = false) Integer idAdherant, Model model) {
        if (idAdherant == null) {
            model.addAttribute("error", "Aucun adhérent sélectionné. Veuillez choisir un adhérent.");
            model.addAttribute("adherants", adherantService.findAll());
            return "saisir-adherant-prolongement";
        }
        try {
            List<Pret> pretsActifs = pretService.findActivePretsByAdherant(idAdherant);
            model.addAttribute("pretsActifs", pretsActifs);
            model.addAttribute("idAdherant", idAdherant);
            if (pretsActifs == null || pretsActifs.isEmpty()) {
                model.addAttribute("info", "Cet adhérent n'a aucun prêt actif à prolonger.");
            }
            return "prolonger-pret";
        } catch (Exception e) {
            model.addAttribute("error", "Adhérent invalide ou erreur interne. Veuillez réessayer.");
            model.addAttribute("adherants", adherantService.findAll());
            return "saisir-adherant-prolongement";
        }
    }

    @PostMapping("/prets/prolonger")
    public String traiterProlongement(@RequestParam("idPret") int idPret, Model model) {
        try {
            Pret pret = pretService.findById(idPret);
            // Vérifier si le prêt est prolongeable (pas de réservation en attente, pas de pénalité, etc.)
            boolean prolongeable = pretService.isProlongeable(pret);
            if (!prolongeable) {
                model.addAttribute("error", "Ce prêt ne peut pas être prolongé (réservation en attente ou pénalité en cours).");
                return "prolonger-pret";
            }
            pretService.prolongerPret(pret);
            model.addAttribute("success", "Le prêt a été prolongé avec succès.");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors du prolongement : " + e.getMessage());
        }
        return "prolonger-pret";
    }

    @GetMapping("/prets/prolongement-form")
    public String afficherFormulaireSaisieAdherant(Model model) {
        model.addAttribute("adherants", adherantService.findAll());
        return "saisir-adherant-prolongement";
    }

 
}