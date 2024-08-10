package project.project.service;

import project.project.model.dto.CpuDTO;
import project.project.model.entity.CPU;

public interface CpuService {
    public boolean save(CpuDTO cpuDTO);
    public CPU getPartById(long id);
}
