package com.uoc.alquilatusvehiculos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/dashboard")
    public String userDashboard() {
        return "user/dashboard"; // templates/user/dashboard.html
    }

    @GetMapping("/new-rental")
    public String newRental() {
        return "user/new-rental"; // templates/user/new-rental.html
    }
}
