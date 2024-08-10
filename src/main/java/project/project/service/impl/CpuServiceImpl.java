package project.project.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.project.model.dto.CpuDTO;
import project.project.model.entity.CPU;
import project.project.repository.CPURepository;
import project.project.service.CpuService;

@Service
public class CpuServiceImpl implements CpuService {
    private final CPURepository cpuRepository;
    private final ModelMapper modelMapper;

    public CpuServiceImpl(CPURepository cpuRepository, ModelMapper modelMapper) {
        this.cpuRepository = cpuRepository;
        this.modelMapper = modelMapper;
    }

    @Override
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

    @Override
    public CPU getPartById(long id) {
        return cpuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CPU not found with id: " + id));
    }
}
