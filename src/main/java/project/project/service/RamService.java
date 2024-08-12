package project.project.service;

import project.project.model.dto.RamDTO;
import project.project.model.entity.RamEntity;

import java.util.List;

public interface RamService {
    public boolean save(RamDTO ramDTO);
    public RamEntity getPartById(long id);

    List<RamEntity> getAllRams();
    void deleteRam(long id);
}
