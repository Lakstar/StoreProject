package project.project.service.impl;

import org.springframework.stereotype.Service;
import project.project.model.entity.Memory;
import project.project.repository.MemoryRepository;

import java.util.List;

@Service
public class MemoryService {
    private final MemoryRepository memoryRepository;

    public MemoryService(MemoryRepository memoryRepository) {
        this.memoryRepository = memoryRepository;
    }

    public void saveMemory(Memory memory) {
        memoryRepository.save(memory);
    }

    public List<Memory> findAllMemories() {
        return memoryRepository.findAll();
    }

    public Memory getMemoryById(long id) {
        return memoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Memory not found"));
    }
}
