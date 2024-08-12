package project.project.service;

import project.project.model.dto.CpuDTO;
import project.project.model.entity.CpuEntity;

import java.util.List;

public interface CpuService {
    public boolean save(CpuDTO cpuDTO);
    public CpuEntity getPartById(long id);
    List<CpuEntity> getAllCpus();
    void deleteCpu(long id);
}
