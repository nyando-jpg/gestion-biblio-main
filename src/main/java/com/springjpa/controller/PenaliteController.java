package com.springjpa.controller;

import com.springjpa.service.PenaliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PenaliteController {
    @Autowired
    private PenaliteService penaliteService;

    @GetMapping("/penalites")
    public String listerPenalites(Model model) {
        model.addAttribute("penalites", penaliteService.findAll());
        return "penalites";
    }
}
