package project.project.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.project.model.dto.CpuDTO;
import project.project.model.entity.CpuEntity;
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
            CpuEntity cpuEntity = modelMapper.map(cpuDTO, CpuEntity.class);
            cpuRepository.save(cpuEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public CpuEntity getPartById(long id) {
        return cpuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CpuEntity not found with id: " + id));
    }
}
