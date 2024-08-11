package project.project.service;

import project.project.model.dto.CpuDTO;
import project.project.model.entity.CpuEntity;

public interface CpuService {
    public boolean save(CpuDTO cpuDTO);
    public CpuEntity getPartById(long id);
}
