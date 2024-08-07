package project.project.service;

import org.springframework.stereotype.Service;
import project.project.model.dto.entity.CPU;
import project.project.repository.CPURepository;

import java.util.List;

@Service
public class CpuService {
    private final CPURepository cpuRepository;

    public CpuService(CPURepository cpuRepository) {
        this.cpuRepository = cpuRepository;
    }

    public void saveCPU(CPU cpu) {
        cpuRepository.save(cpu);
    }

    public List<CPU> findAllCPUs() {
        return cpuRepository.findAll();
    }

    public CPU getCPUById(long id) {
        return cpuRepository.findById(id).orElseThrow(() -> new RuntimeException("CPU not found"));
    }

}
