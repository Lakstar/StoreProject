package project.project.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
        if(!model.containsAttribute("registerData"))
        {
            model.addAttribute("registerData", new RegisterDTO());
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") RegisterDTO data, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() || !data.getPassword().equals(data.getConfirmPassword())) {
            if (!data.getPassword().equals(data.getConfirmPassword())) {
                bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Passwords do not match.");
            }
            redirectAttributes.addFlashAttribute("registerData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);
            return "redirect:/register";
        }
        boolean success = userServiceImpl.register(data);
        if(!success){
            return "redirect:/register";
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
