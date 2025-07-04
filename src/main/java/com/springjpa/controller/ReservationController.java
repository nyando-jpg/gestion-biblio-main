package com.springjpa.controller;

import com.springjpa.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservations")
    public String listerReservations(Model model) {
        model.addAttribute("reservations", reservationService.findAll());
        return "reservations";
    }
}
