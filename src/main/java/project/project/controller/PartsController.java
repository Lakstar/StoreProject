package project.project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.project.model.dto.*;
import project.project.model.enums.CPUType;
import project.project.model.enums.MemoryType;
import project.project.model.enums.RamSizes;
import project.project.model.enums.RamType;
import project.project.service.CpuService;
import project.project.service.GpuService;
import project.project.service.MemoryService;
import project.project.service.RamService;
import project.project.service.impl.CpuServiceImpl;
import project.project.service.impl.GpuServiceImpl;
import project.project.service.impl.MemoryServiceImpl;
import project.project.service.impl.RamServiceImpl;

@Controller
public class PartsController {
    private final RamService ramServiceImpl;
    private final CpuService cpuServiceImpl;
    private final MemoryService memoryServiceImpl;
    private final GpuService gpuServiceImpl;

    @Autowired
    public PartsController(RamService ramServiceImpl, CpuService cpuServiceImpl, MemoryService memoryServiceImpl, GpuService gpuServiceImpl) {
        this.ramServiceImpl = ramServiceImpl;
        this.cpuServiceImpl = cpuServiceImpl;
        this.memoryServiceImpl = memoryServiceImpl;
        this.gpuServiceImpl = gpuServiceImpl;
    }


    @GetMapping("/add/add-ram")
    public String showAddRamForm(Model model) {
        if(!model.containsAttribute("ramData")) {
            model.addAttribute("ramData", new RamDTO());
            model.addAttribute("ramSizes", RamSizes.values());
            model.addAttribute("ramTypes", RamType.values());
        }
        model.addAttribute("ramSizes", RamSizes.values());
        model.addAttribute("ramTypes", RamType.values());
        return "add-ram";
    }
    @GetMapping("/add/add-cpu")
    public String showAddCpuForm(Model model) {
        if(!model.containsAttribute("cpuData")) {
            model.addAttribute("cpuData", new CpuDTO());
            model.addAttribute("cpuTypes", CPUType.values());
        }
        model.addAttribute("cpuTypes", CPUType.values());
        return "add-cpu";
    }
    @GetMapping("/add/add-gpu")
    public String showAddGPUForm(Model model) {
        if (!model.containsAttribute("gpuData")) {
            model.addAttribute("gpuData", new GpuDTO());
        }
        return "add-gpu";
    }
    @GetMapping("/add/add-memory")
    public String showAddMemoryForm(Model model) {
        if(!model.containsAttribute("memoryData")) {
            model.addAttribute("memoryData", new MemoryDTO());
            model.addAttribute("memoryTypes", MemoryType.values());
        }
        model.addAttribute("memoryTypes", MemoryType.values());
        return "add-memory";
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


    @GetMapping("/view/parts")
    public String viewParts(Model model) {
        model.addAttribute("cpus", cpuServiceImpl.getAllCpus());
        model.addAttribute("gpus", gpuServiceImpl.getAllGpus());
        model.addAttribute("memories", memoryServiceImpl.getAllMemories());
        model.addAttribute("rams", ramServiceImpl.getAllRams());
        return "view-parts";
    }

    @PostMapping("/delete/cpu/{id}")
    public String deleteCpu(@PathVariable("id") long id) {
        cpuServiceImpl.deleteCpu(id);
        return "redirect:/view/parts";
    }

    @PostMapping("/delete/gpu/{id}")
    public String deleteGpu(@PathVariable("id") long id) {
        gpuServiceImpl.deleteGpu(id);
        return "redirect:/view/parts";
    }

    @PostMapping("/delete/memory/{id}")
    public String deleteMemory(@PathVariable("id") long id) {
        memoryServiceImpl.deleteMemory(id);
        return "redirect:/view/parts";
    }

    @PostMapping("/delete/ram/{id}")
    public String deleteRam(@PathVariable("id") long id) {
        ramServiceImpl.deleteRam(id);
        return "redirect:/view/parts";
    }
}
