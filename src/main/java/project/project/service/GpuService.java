package project.project.service;

import org.springframework.stereotype.Service;
import project.project.model.dto.GpuDTO;
import project.project.model.entity.GPU;
import project.project.repository.GPURepository;

import java.util.List;

@Service
public class GpuService {
    private final GPURepository gpuRepository;

    public GpuService(GPURepository gpuRepository) {
        this.gpuRepository = gpuRepository;
    }

    public void addGPU(GPU gpu) {
        gpuRepository.save(gpu);
    }

    public GPU getGPUById(long id) {
        return gpuRepository.findById(id).orElseThrow(() -> new RuntimeException("GPU not found"));
    }
}
