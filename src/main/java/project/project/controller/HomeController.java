package project.project.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.project.model.dto.CreateMonitorDTO;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("welcomeMessage", "Welcome " + principal.getName());
        } else {
            model.addAttribute("welcomeMessage", "Welcome anonymous");
        }
        return "index";
    }

    @GetMapping("/debug-session")
    public String debugSession(HttpSession session, Model model) {
        model.addAttribute("sessionUserId", session.getAttribute("userId"));
        return "debug-session";
    }

    @GetMapping("/add")
    public String add(Model model) {
        return "add-main";
    }
}
