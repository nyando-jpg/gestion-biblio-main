package com.springjpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FinPretController {
    @GetMapping("/finprets/ajouter")
    public String afficherFormulaireAjoutFinPret() {
        return "ajouter-finpret";
    }
}
