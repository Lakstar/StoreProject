package project.project.service;

import project.project.model.dto.MemoryDTO;
import project.project.model.entity.MemoryEntity;

import java.util.List;

public interface MemoryService {
    public boolean save(MemoryDTO memoryDTO);
    public MemoryEntity getPartById(long id);
    List<MemoryEntity> getAllMemories();
    void deleteMemory(long id);
}
