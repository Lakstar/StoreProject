package project.project.service;

import project.project.model.dto.MemoryDTO;
import project.project.model.entity.MemoryEntity;

public interface MemoryService {
    public boolean save(MemoryDTO memoryDTO);
    public MemoryEntity getPartById(long id);
}
