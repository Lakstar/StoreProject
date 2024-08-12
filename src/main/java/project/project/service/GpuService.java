package project.project.service;

import project.project.model.dto.GpuDTO;
import project.project.model.entity.GpuEntity;

import java.util.List;

public interface GpuService {
    public boolean saveGPU(GpuDTO gpuDTO);
    public GpuEntity getPartById(long id);

    List<GpuEntity> getAllGpus();
    void deleteGpu(long id);
}
