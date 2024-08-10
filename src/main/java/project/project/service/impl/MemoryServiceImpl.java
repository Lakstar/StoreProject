package project.project.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.project.model.dto.MemoryDTO;
import project.project.model.entity.Memory;
import project.project.repository.MemoryRepository;
import project.project.service.MemoryService;

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
            Memory memory = modelMapper.map(memoryDTO, Memory.class);
            memoryRepository.save(memory);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Memory getPartById(long id) {
        return memoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Memory not found with id: " + id));
    }
}
