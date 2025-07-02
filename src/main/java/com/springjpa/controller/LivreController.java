package com.springjpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.springjpa.service.LivreService;

@Controller
public class LivreController {
    @Autowired
    private LivreService livreService;

    @GetMapping("/livres")
    public String listLivres(Model model) {
        model.addAttribute("livres", livreService.findAll());
        return "livre";
    }
}
