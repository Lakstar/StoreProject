package project.project.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.project.model.dto.GpuDTO;
import project.project.model.entity.GPU;
import project.project.repository.GPURepository;

import java.util.List;

@Service
public class GpuService {
    private final GPURepository gpuRepository;
    private final ModelMapper modelMapper;

    public GpuService(GPURepository gpuRepository, ModelMapper modelMapper) {
        this.gpuRepository = gpuRepository;
        this.modelMapper = modelMapper;
    }

    public boolean saveGPU(GpuDTO gpuDTO) {
        try {
            GPU gpu = modelMapper.map(gpuDTO, GPU.class);
            gpuRepository.save(gpu);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public GPU getGPUById(long id) {
        return gpuRepository.findById(id).orElseThrow(() -> new RuntimeException("GPU not found"));
    }
}
