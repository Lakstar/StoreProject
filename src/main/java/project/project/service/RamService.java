package project.project.service;

import project.project.model.dto.RamDTO;
import project.project.model.entity.RamEntity;

public interface RamService {
    public boolean save(RamDTO ramDTO);
    public RamEntity getPartById(long id);
}
