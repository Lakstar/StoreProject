package project.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.project.model.dto.RegisterDTO;
import project.project.service.impl.UserService;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegisterDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") RegisterDTO registerDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.out.println("Validation errors: " + result.getAllErrors());
            return "register";
        }
        try {
            userService.register(registerDTO);
        } catch (IllegalArgumentException e) {
            System.out.println("Registration error: " + e.getMessage());
            model.addAttribute("registrationError", e.getMessage());
            return "register";
        }
        return "index";
    }
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }
}
