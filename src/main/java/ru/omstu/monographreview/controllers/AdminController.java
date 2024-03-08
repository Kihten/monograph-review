package ru.omstu.monographreview.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.omstu.monographreview.models.User;
import ru.omstu.monographreview.services.UserService;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String viewAdminPage() {
        return "admin-page";
    }

    @GetMapping("/users")
    public String viewAllUsers(Model model, @PageableDefault(sort = "id") Pageable pageable,
                               @RequestParam(required = false) String sort) {
        Page<User> usersPage = userService.getAllUsers(pageable);
        model.addAttribute("users", usersPage.getContent());
        model.addAttribute("currentPage", usersPage.getNumber());
        model.addAttribute("totalPage", usersPage.getTotalPages());
        model.addAttribute("totalItems", usersPage.getTotalElements());
        model.addAttribute("sort", sort);
        return "users";
    }

    @GetMapping("users/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }
}
