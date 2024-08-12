package project.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.project.model.dto.RegisterDTO;
import project.project.model.entity.PC;
import project.project.model.entity.UserEntity;
import project.project.service.impl.UserServiceImpl;

import java.util.List;

@Controller
public class UserController {

    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegisterDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") RegisterDTO registerDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        try {
            userServiceImpl.register(registerDTO);
        } catch (IllegalArgumentException e) {
            model.addAttribute("registrationError", e.getMessage());
            return "register";
        }
        return "redirect:/";
    }
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }

    @GetMapping("/user")
    public String getUserPage(@RequestParam("userId") Long userId, Model model) {
            UserEntity user = userServiceImpl.getUserById(userId);
            List<PC> orders = userServiceImpl.getOrdersByUserId(userId);

            if (user == null) {
                model.addAttribute("error", "User not found");
                return "login";
            }
            model.addAttribute("user", user);
            model.addAttribute("orders", orders);
            return "user";
    }

    @PostMapping("/user/updateUsername")
    public String updateUsername(@RequestParam("userId") Long userId,
                                 @RequestParam("newUsername") String newUsername,
                                 Model model) {
        try {
            userServiceImpl.updateUsername(userId, newUsername);
            return "redirect:/user?userId=" + userId;
        } catch (IllegalArgumentException e) {
            model.addAttribute("updateError", e.getMessage());
            UserEntity user = userServiceImpl.getUserById(userId);
            List<PC> orders = userServiceImpl.getOrdersByUserId(userId);
            model.addAttribute("user", user);
            model.addAttribute("orders", orders);
            return "user";
        }
    }
}
