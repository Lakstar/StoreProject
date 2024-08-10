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
import project.project.model.dto.GpuDTO;
import project.project.model.dto.MemoryDTO;
import project.project.model.dto.RamDTO;
import project.project.model.enums.CPUType;
import project.project.model.enums.MemoryType;
import project.project.model.enums.RamSizes;
import project.project.model.enums.RamType;
import project.project.service.impl.CpuServiceImpl;
import project.project.service.impl.GpuServiceImpl;
import project.project.service.impl.MemoryServiceImpl;
import project.project.service.impl.RamServiceImpl;

@Controller
public class AddPartsController {
    private final RamServiceImpl ramServiceImpl;
    private final CpuServiceImpl cpuServiceImpl;
    private final MemoryServiceImpl memoryServiceImpl;
    private final GpuServiceImpl gpuServiceImpl;

    public AddPartsController(RamServiceImpl ramServiceImpl, CpuServiceImpl cpuServiceImpl, MemoryServiceImpl memoryServiceImpl, GpuServiceImpl gpuServiceImpl) {
        this.ramServiceImpl = ramServiceImpl;
        this.cpuServiceImpl = cpuServiceImpl;
        this.memoryServiceImpl = memoryServiceImpl;
        this.gpuServiceImpl = gpuServiceImpl;
    }


    @GetMapping("/add/add-ram")
    public String showAddRamForm(Model model) {
        model.addAttribute("ramData", new RamDTO());
        model.addAttribute("ramSizes", RamSizes.values());
        model.addAttribute("ramTypes", RamType.values());
        return "addRAM";
    }

    @PostMapping("/add/add-ram")
    public String addRam(@Valid @ModelAttribute("ramData") RamDTO ramDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("ramData", ramDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.ramData", bindingResult);
            return "redirect:/add/add-ram";
        }

        boolean added = ramServiceImpl.save(ramDTO);
        if(!added){
            redirectAttributes.addFlashAttribute("ramData", ramDTO);
            return "redirect:/add/add-ram";
        }
        return "redirect:/add";
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

        boolean added = cpuServiceImpl.save(cpuDTO);
        if(!added){
            redirectAttributes.addFlashAttribute("cpuData", cpuDTO);
            return "redirect:/add/add-cpu";
        }
        return "redirect:/add";
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

        boolean success = gpuServiceImpl.saveGPU(gpuDTO);
        if (!success) {
            redirectAttributes.addFlashAttribute("gpuData", gpuDTO);
            return "redirect:/add/add-gpu";
        }
        return "redirect:/add";
    }

    @GetMapping("/add/add-memory")
    public String showAddMemoryForm(Model model) {
        model.addAttribute("memoryData", new MemoryDTO());
        model.addAttribute("memoryTypes", MemoryType.values());
        return "addMemory";
    }

    @PostMapping("/add/add-memory")
    public String addMemory(@Valid @ModelAttribute("memoryData") MemoryDTO memoryDTO,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("memoryData", memoryDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.memoryData", bindingResult);
            return "redirect:/add/add-memory";
        }

        boolean added = memoryServiceImpl.save(memoryDTO);
        if (!added) {
            redirectAttributes.addFlashAttribute("memoryData", memoryDTO);
            return "redirect:/add/add-memory";
        }
        return "redirect:/add";
    }
}
