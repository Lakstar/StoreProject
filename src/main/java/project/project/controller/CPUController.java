package project.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.project.model.entity.CPUType;

@Controller
public class CPUController {
    @GetMapping("/add/add-cpu")
    public String showAddCPUForm(Model model) {
        model.addAttribute("cpuTypes", CPUType.values());
        return "addCPU";
    }
}
