package com.springjpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import com.springjpa.service.AdminService;
import com.springjpa.entity.Admin;

@Controller
public class AdminLoginController {
    @Autowired
    private AdminService adminService;

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
}
