package project.project.service;

import project.project.model.dto.GpuDTO;
import project.project.model.entity.GPU;

public interface GpuService {
    public boolean saveGPU(GpuDTO gpuDTO);
    public GPU getPartById(long id);
}
