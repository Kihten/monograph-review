package ru.omstu.monographreview.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MonographsController {

    @GetMapping("/monographs")
    public String viewAllMonographs() {
        return "monograph/monographs";
    }
}
