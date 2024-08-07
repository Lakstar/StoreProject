package project.project.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.project.config.UserSession;
import project.project.model.dto.LoginDTO;
import project.project.model.dto.RegisterDTO;
import project.project.service.UserService;

@Controller
public class UserController {
    private final UserSession userSession;
    private final UserService userService;

    public UserController(UserSession userSession, UserService userService) {
        this.userSession = userSession;
        this.userService = userService;
    }


    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@Valid @ModelAttribute("loginData") LoginDTO loginDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (userSession.isLoggedIn()) {
            return "redirect:/home";
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("loginData", loginDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginData", bindingResult);
            return "redirect:/login";
        }
        boolean success = userService.login(loginDTO);
        if (!success) {
            redirectAttributes.addFlashAttribute("loginError", true);
            return "redirect:/login";
        }
        return "redirect:/home";
    }

    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute("registerData") RegisterDTO registerDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerData", registerDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);
            return "redirect:/register";
        }

        boolean success = userService.register(registerDTO);
        if (!success) {
            redirectAttributes.addFlashAttribute("registerError", true);
            return "redirect:/register";
        }

        return "redirect:/login"; // Redirect to login page after successful registration
    }

}
