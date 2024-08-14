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
    public String showIndex(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("welcomeMessage", "Welcome " + principal.getName() +"!");
        } else {
            model.addAttribute("welcomeMessage", "Welcome anonymous!");
        }
        return "index";
    }

    @GetMapping("/add")
    public String showAdd(Model model) {
        return "add-main";
    }

    @GetMapping("/login/failed")
    public String showLoginFailed(Model model){
        return "login-failed";
    }

    @GetMapping("/error")
    public String handleError() {
        return "error";
    }
}
