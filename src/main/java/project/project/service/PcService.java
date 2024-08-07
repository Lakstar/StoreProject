package project.project.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import project.project.model.dto.entity.PC;
import project.project.model.dto.entity.PCType;
import project.project.repository.*;

import java.util.List;

@Service
public class PcService {
    private final PCRepository pcRepository;
    private final CPURepository cpuRepository;
    private final GPURepository gpuRepository;
    private final MemoryRepository memoryRepository;
    private final RAMRepository ramRepository;

    public PcService(PCRepository pcRepository, CPURepository cpuRepository, GPURepository gpuRepository, MemoryRepository memoryRepository, RAMRepository ramRepository) {
        this.pcRepository = pcRepository;
        this.cpuRepository = cpuRepository;
        this.gpuRepository = gpuRepository;
        this.memoryRepository = memoryRepository;
        this.ramRepository = ramRepository;
    }

    public void savePC(PC pc) {
        pcRepository.save(pc);
    }

    public List<PC> getAllPCs() {
        return pcRepository.findAll();
    }

    public Model getData(Model model){
        model.addAttribute("cpus", cpuRepository.findAll());
        model.addAttribute("gpus", gpuRepository.findAll());
        model.addAttribute("memories", memoryRepository.findAll());
        model.addAttribute("rams", ramRepository.findAll());
        model.addAttribute("pcTypes", PCType.values());
        return model;
    }
}
