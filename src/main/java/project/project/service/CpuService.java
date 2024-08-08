package project.project.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.project.model.dto.CpuDTO;
import project.project.model.entity.CPU;
import project.project.repository.CPURepository;

import java.util.List;

@Service
public class CpuService {
    private final CPURepository cpuRepository;
    private final ModelMapper modelMapper;

    public CpuService(CPURepository cpuRepository, ModelMapper modelMapper) {
        this.cpuRepository = cpuRepository;
        this.modelMapper = modelMapper;
    }

    public boolean save(CpuDTO cpuDTO) {
        try {
            CPU cpu = modelMapper.map(cpuDTO, CPU.class);
            cpuRepository.save(cpu);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public CPU getCPUById(long id) {
        return cpuRepository.findById(id).orElseThrow(() -> new RuntimeException("CPU not found"));
    }

}
