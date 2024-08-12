package project.project.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import project.project.model.entity.PC;
import project.project.model.enums.PCType;
import project.project.repository.*;
import project.project.service.PcService;

import java.util.List;

@Service
public class PcServiceImpl implements PcService {
    private final PCRepository pcRepository;
    private final CPURepository cpuRepository;
    private final GPURepository gpuRepository;
    private final MemoryRepository memoryRepository;
    private final RAMRepository ramRepository;

    public PcServiceImpl(PCRepository pcRepository, CPURepository cpuRepository, GPURepository gpuRepository, MemoryRepository memoryRepository, RAMRepository ramRepository) {
        this.pcRepository = pcRepository;
        this.cpuRepository = cpuRepository;
        this.gpuRepository = gpuRepository;
        this.memoryRepository = memoryRepository;
        this.ramRepository = ramRepository;
    }

    @Override
    public void savePC(PC pc) {
        pcRepository.save(pc);
    }

    @Override
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
