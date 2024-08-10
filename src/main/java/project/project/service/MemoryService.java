package project.project.service;

import project.project.model.dto.MemoryDTO;
import project.project.model.entity.Memory;

public interface MemoryService {
    public boolean save(MemoryDTO memoryDTO);
    public Memory getPartById(long id);
}
