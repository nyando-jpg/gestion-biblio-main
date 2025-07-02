package com.springjpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.springjpa.service.AdminService;

@Controller
public class AdminLoginController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/admin-login")
    public ModelAndView loginAdmin(@RequestParam("nomAdmin") String nom,
                                   @RequestParam("prenomAdmin") String prenom,
                                   @RequestParam("password") String password) {
        boolean ok = adminService.checkLogin(nom, prenom, password);
        if (ok) {
            return new ModelAndView("admin-home");
        } else {
            ModelAndView mv = new ModelAndView("index");
            mv.addObject("loginError", true);
            return mv;
        }
    }
}
