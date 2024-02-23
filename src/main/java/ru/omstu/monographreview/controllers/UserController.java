package ru.omstu.monographreview.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    @GetMapping("/profile")
    public String viewProfile() {
        return "profile";
    }

//TODO: вместо "моих нонографий" сделать отображение только монографий пользователя на общей странице

//    @GetMapping("/my-monographs")
//    public String viewMyMonographs() {
//        return "my-monographs";
//    }

    @GetMapping("/monograph/{id}")
    public String viewMonograph(@PathVariable("id") long id) {
        return "monograph/monograph";
    }

    @GetMapping("/add-monograph")
    public String viewAddMonograph() {
        return "monograph/add-monograph";
    }
}
