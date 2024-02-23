package ru.omstu.monographreview.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.omstu.monographreview.dto.UserDTO;
import ru.omstu.monographreview.models.User;
import ru.omstu.monographreview.services.UserService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    private String viewLoginForm() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String viewRegistrationForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "auth/register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDTO userDTO,
                               BindingResult bindingResult,
                               Model model) {
        Optional<User> user = userService.findByEmail(userDTO.getEmail());
        if (user.isPresent()) {
            bindingResult.rejectValue(
                    "email",
                    null,
                    "Этот адрес электронной почты уже зарегистрирован в системе");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute(userDTO);
            return "auth/register";
        }

        userService.saveUser(userDTO);
        return "redirect:/register?success";
    }
}
