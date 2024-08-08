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
import project.project.model.entity.GPU;
import project.project.service.GpuService;

@Controller
public class GPUController {
    private final GpuService gpuService;
    private final ModelMapper modelMapper;

    public GPUController(GpuService gpuService, ModelMapper modelMapper) {
        this.gpuService = gpuService;
        this.modelMapper = modelMapper;
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
            return "redirect:/add/addGPU";
        }

        GPU gpu = modelMapper.map(gpuDTO, GPU.class);
        gpuService.addGPU(gpu);
        return "redirect:/add";
    }
}
