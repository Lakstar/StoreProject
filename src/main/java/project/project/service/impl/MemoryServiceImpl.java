package project.project.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.project.model.dto.MemoryDTO;
import project.project.model.entity.MemoryEntity;
import project.project.repository.MemoryRepository;
import project.project.service.MemoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemoryServiceImpl implements MemoryService {
    private final MemoryRepository memoryRepository;
    private final ModelMapper modelMapper;

    public MemoryServiceImpl(MemoryRepository memoryRepository, ModelMapper modelMapper) {
        this.memoryRepository = memoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean save(MemoryDTO memoryDTO) {
        try {
            MemoryEntity memoryEntity = modelMapper.map(memoryDTO, MemoryEntity.class);
            memoryRepository.save(memoryEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public MemoryEntity getPartById(long id) {
        return memoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MemoryEntity not found with id: " + id));
    }

    @Override
    public List<MemoryEntity> getAllMemories() {
        return memoryRepository.findAll();
    }

    @Override
    public void deleteMemory(long id) {
        memoryRepository.deleteById(id);
    }
}
