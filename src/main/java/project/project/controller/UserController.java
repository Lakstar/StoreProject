package project.project.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.project.model.dto.OrderInfoDTO;
import project.project.model.dto.RegisterDTO;
import project.project.model.dto.UserInfoDTO;
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

    @GetMapping("/user/{userId}")
    public String getUserPage(@PathVariable("userId") Long userId, Model model) {
        UserInfoDTO user = userServiceImpl.getUserById(userId);
        List<OrderInfoDTO> orders = userServiceImpl.getOrdersByUserId(userId);

        model.addAttribute("user", user);
        model.addAttribute("orders", orders);

        return "user";
    }
    @GetMapping("/user")
    public String getUserPage(Model model) {
        Long userId = userServiceImpl.getCurrentUserId(); // Get the current user ID from the security context
        UserInfoDTO user = userServiceImpl.getUserById(userId);
        List<OrderInfoDTO> orders = userServiceImpl.getOrdersByUserId(userId);

        model.addAttribute("user", user);
        model.addAttribute("orders", orders);

        return "user";
    }
}
