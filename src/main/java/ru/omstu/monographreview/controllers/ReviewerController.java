package ru.omstu.monographreview.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reviewed-monographs")
public class ReviewerController {

    @GetMapping("/review")
    public String viewReviewMonograph() {
        return "review";
    }
}
