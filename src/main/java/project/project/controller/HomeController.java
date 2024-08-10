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

    @GetMapping("/cart")
    public String cart(Model model) {
        return "cart";
    }

    @GetMapping("/shop")
    public String shop(Model model) {
        return "shop";
    }

    @GetMapping("/info")
    public String info(Model model) {
        return "info";
    }

    @GetMapping("/add/add-ram")
    public String addRAM(Model model) {
        return "addRAM";
    }
    @GetMapping("/add/add-memory")
    public String addMemory(Model model) {
        return "addMemory";
    }
}
