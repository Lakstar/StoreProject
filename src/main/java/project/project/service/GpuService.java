package project.project.service;

import project.project.model.dto.GpuDTO;
import project.project.model.entity.GpuEntity;

public interface GpuService {
    public boolean saveGPU(GpuDTO gpuDTO);
    public GpuEntity getPartById(long id);
}
