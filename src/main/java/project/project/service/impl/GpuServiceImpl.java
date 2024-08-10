package project.project.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.project.model.dto.GpuDTO;
import project.project.model.entity.GPU;
import project.project.repository.GPURepository;
import project.project.service.GpuService;

@Service
public class GpuServiceImpl implements GpuService {
    private final GPURepository gpuRepository;
    private final ModelMapper modelMapper;

    public GpuServiceImpl(GPURepository gpuRepository, ModelMapper modelMapper) {
        this.gpuRepository = gpuRepository;
        this.modelMapper = modelMapper;
    }

    @Override
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
    @Override
    public GPU getPartById(long id) {
        return gpuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GPU not found with id: " + id));
    }
}
