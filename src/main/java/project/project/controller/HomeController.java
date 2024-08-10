package project.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/add")
    public String add(Model model) {
        return "addMain";
    }

}
