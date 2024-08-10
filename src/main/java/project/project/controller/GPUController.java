package project.project.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.project.model.dto.GpuDTO;
import project.project.service.impl.GpuService;

@Controller
public class GPUController {
    private final GpuService gpuService;

    public GPUController(GpuService gpuService, ModelMapper modelMapper) {
        this.gpuService = gpuService;
    }

    @GetMapping("/add/add-gpu")
    public String showAddGPUForm(Model model) {
        if (!model.containsAttribute("gpuData")) {
            model.addAttribute("gpuData", new GpuDTO());
        }
        return "addGpu";
    }

    @PostMapping("/add/add-gpu")
    public String addGPU(@Valid @ModelAttribute("gpuData") GpuDTO gpuDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("gpuData", gpuDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.gpuData", bindingResult);
            return "redirect:/add/add-gpu";
        }

        boolean success = gpuService.saveGPU(gpuDTO);
        if (!success) {
            redirectAttributes.addFlashAttribute("gpuData", gpuDTO);
            return "redirect:/add/add-gpu";
        }
        return "redirect:/add";
    }
}
