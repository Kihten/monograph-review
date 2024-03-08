package ru.omstu.monographreview.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/profile")
    public String viewProfile() {
        return "profile";
    }

}
