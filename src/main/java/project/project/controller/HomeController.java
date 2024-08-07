package project.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/add")
    public String add(Model model) {
        return "addMain";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        return "cart";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        return "register";
    }

    @GetMapping("/shop")
    public String shop(Model model) {
        return "shop";
    }

    @GetMapping("/info")
    public String info(Model model) {
        return "info";
    }

    @GetMapping("/add/add-gpu")
    public String addGPU(Model model) {
        return "addGpu";
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
