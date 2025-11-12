package com.uoc.alquilatusvehiculos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @GetMapping({"/","/login"})
    public String login() { return "login"; }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password) {
        if ("admin@islatransfers.com".equalsIgnoreCase(username) && "Admin".equals(password)) {
            return "redirect:/admin/dashboard";
        }
        // demo: cualquier otro usuario entra a dashboard también
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/register")
    public String registerView() { return "register"; }

    @PostMapping("/register")
    public String registerPost() { return "redirect:/login?registered=true"; }
}
