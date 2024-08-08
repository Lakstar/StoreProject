package project.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.project.model.dto.PcDTO;
import project.project.model.entity.PC;
import project.project.repository.CPURepository;
import project.project.repository.GPURepository;
import project.project.repository.MemoryRepository;
import project.project.repository.RAMRepository;
import project.project.service.*;

@Controller
public class PCController {

    private final GpuService gpuService;
    private final CpuService cpuService;
    private final RamService ramService;
    private final MemoryService memoryService;
    private final PcService pcService;


    public PCController(CPURepository cpuRepository, GPURepository gpuRepository, MemoryRepository memoryRepository, RAMRepository ramRepository, GpuService gpuService, CpuService cpuService, RamService ramService, MemoryService memoryService, PcService pcService) {
        this.gpuService = gpuService;
        this.cpuService = cpuService;
        this.ramService = ramService;
        this.memoryService = memoryService;
        this.pcService = pcService;
    }

    @GetMapping("/add/add-pc")
    public String showAddPcForm(Model model) {
        model=pcService.getData(model);
        model.addAttribute("pc", new PcDTO());
        return "addLaptop";
    }

    @PostMapping("/add/add-pc")
    public String addPC(@ModelAttribute PcDTO pcDTO) {
        PC pc = new PC();
        pc.setName(pcDTO.getName());
        pc.setCpu(cpuService.getCPUById(pcDTO.getCpuId()));
        pc.setGpu(gpuService.getGPUById(pcDTO.getGpuId()));
        pc.setMemory(memoryService.getMemoryById(pcDTO.getMemoryId()));
        pc.setRam(ramService.getRAMById(pcDTO.getRamId()));
        pc.setPcType(pcDTO.getPcType());
        pc.setPrice(pcDTO.getPrice());
        pcService.savePC(pc);
        return "redirect:/add";
    }
}
