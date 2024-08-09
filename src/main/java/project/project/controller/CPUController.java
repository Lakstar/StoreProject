package project.project.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.project.model.dto.CpuDTO;
import project.project.model.entity.CPUType;
import project.project.service.impl.CpuService;

@Controller
public class CPUController {

    private final CpuService cpuService;

    public CPUController(CpuService cpuService) {
        this.cpuService = cpuService;
    }

    @GetMapping("/add/add-cpu")
    public String showAddCpuForm(Model model) {
        model.addAttribute("cpuData", new CpuDTO());
        model.addAttribute("cpuTypes", CPUType.values());
        return "addCPU";
    }

    @PostMapping("/add/add-cpu")
    public String addCpu(@Valid @ModelAttribute("cpuData") CpuDTO cpuDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("cpuData", cpuDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.cpuData", bindingResult);
            return "redirect:/add/add-cpu";
        }

        boolean added = cpuService.save(cpuDTO);
        if(!added){
            redirectAttributes.addFlashAttribute("cpuData", cpuDTO);
            return "redirect:/add/add-cpu";
        }
        return "redirect:/add";
    }
}
