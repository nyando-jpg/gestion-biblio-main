package com.springjpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.springjpa.service.LivreService;
import com.springjpa.service.ExemplaireService;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

@Controller
public class LivreController {
    @Autowired
    private LivreService livreService;

    @Autowired
    private ExemplaireService exemplaireService;

    private String colorizeJson(String json) {
        // Coloration simple : clés, chaînes, nombres, booléens, null
        String html = json
            .replaceAll("(&|<|>)", "&#36;$1") // échappe &, <, >
            .replaceAll("(\r?\n)", "<br>")
            .replaceAll("(\s)", "&nbsp;");
        // Clés ("clé":)
        html = html.replaceAll("(\\\"[^\\\"]+\\\"):(&nbsp;)?", "<span style='color:#6ab7ff;'>$1</span>:$2");
        // Chaînes
        html = html.replaceAll(":&nbsp;\\\"([^\\\"]*)\\\"", ":&nbsp;<span style='color:#a6e22e;'>\"$1\"</span>");
        // Nombres
        html = html.replaceAll(":&nbsp;([0-9]+)", ":&nbsp;<span style='color:#f78c6c;'>$1</span>");
        // Booléens
        html = html.replaceAll(":&nbsp;(true|false)", ":&nbsp;<span style='color:#ffcb6b;'>$1</span>");
        // null
        html = html.replaceAll(":&nbsp;null", ":&nbsp;<span style='color:#b2ccd6;'>null</span>");
        return html;
    }

    @GetMapping("/livres")
    public String listLivres(Model model) {
        model.addAttribute("livres", livreService.findAll());
        return "livre";
    }

    @GetMapping("/livres/{id}")
    public String detailLivre(@PathVariable Integer id, Model model) {
        var livre = livreService.findById(id);
        var exemplaires = exemplaireService.findAll().stream()
            .filter(e -> e.getLivre().getIdLivre().equals(id))
            .toList();

        // Simplification du livre
        Map<String, Object> simpleLivre = new HashMap<>();
        simpleLivre.put("idLivre", livre.getIdLivre());
        simpleLivre.put("titre", livre.getTitre());
        if (livre.getAuteur() != null)
            simpleLivre.put("auteur", (livre.getAuteur().getNomAuteur() + " " + livre.getAuteur().getPrenomAuteur()).trim());
        if (livre.getEditeur() != null)
            simpleLivre.put("editeur", livre.getEditeur().getNomEditeur());
        simpleLivre.put("isbn", livre.getIsbn());
        simpleLivre.put("langue", livre.getLangue());
        simpleLivre.put("anneePublication", livre.getAnneePublication());
        simpleLivre.put("nbPage", livre.getNbPage());
        simpleLivre.put("synopsis", livre.getSynopsis());

        // Simplification des exemplaires
        List<Map<String, Object>> simpleExList = exemplaires.stream().map(ex -> {
            Map<String, Object> m = new HashMap<>();
            m.put("idExemplaire", ex.getIdExemplaire());
            m.put("dispo", ex.isDispo());
            // Ajoute d'autres champs utiles ici si besoin
            return m;
        }).toList();

        // Génération du JSON (pour affichage ou debug)
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> result = new HashMap<>();
            result.put("livre", livre);
            result.put("exemplaires", exemplaires);
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
            model.addAttribute("json", json);
            model.addAttribute("jsonHtml", colorizeJson(json));
        } catch (Exception e) {
            model.addAttribute("json", "Erreur lors de la génération du JSON");
            model.addAttribute("jsonHtml", "Erreur lors de la génération du JSON");
        }

        model.addAttribute("jsonLivreSimple", simpleLivre);
        model.addAttribute("jsonExemplairesSimples", simpleExList);
        return "livre-detail";
    }

    @GetMapping("/api/livres/{id}")
    @ResponseBody
    public Map<String, Object> getLivreJson(@PathVariable Integer id) {
        var livre = livreService.findById(id);
        var exemplaires = exemplaireService.findAll().stream()
            .filter(e -> e.getLivre().getIdLivre().equals(id))
            .toList();
        Map<String, Object> result = new HashMap<>();
        result.put("livre", livre);
        result.put("exemplaires", exemplaires);
        return result;
    }
}
